package com.yootk.solrj.config;

import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.ContextAwareAuthScheme;
import org.apache.http.auth.Credentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;
import org.apache.solr.client.solrj.impl.HttpClientUtil;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

import java.io.IOException;

@Configuration
@PropertySource("classpath:config/solr.properties")
@EnableSolrRepositories(basePackages = {"com.yootk.solrj.dao"})
public class SolrConfig {
    @Value("${solr.host.url}")
    private String solrHostUrl ;
    @Value("${solr.basic.username}")
    private String username ;
    @Value("${solr.basic.password}")
    private String password ;
    @Value("${solr.host.connection.timeout}")
    private int connectionTimeout ;
    @Value("${solr.host.socket.timeout}")
    private int socketTimeout ;
    @Value("${solr.host.max.connections}")
    private int maxConnection ;
    @Value("${solr.host.per.max.connections}")
    private int preMaxConnection ;
    @Bean("solrClient")
    public HttpSolrClient getHttpSolrClient() {
        // 定义一个可以保存所有Solr基础配置信息的对象
        ModifiableSolrParams solrParams = new ModifiableSolrParams() ;
        solrParams.set(HttpClientUtil.PROP_BASIC_AUTH_USER,this.username) ;
        solrParams.set(HttpClientUtil.PROP_BASIC_AUTH_PASS,this.password) ;
        solrParams.set(HttpClientUtil.PROP_MAX_CONNECTIONS,this.maxConnection) ; // 允许最大的连接数量
        solrParams.set(HttpClientUtil.PROP_ALLOW_COMPRESSION,true) ; // 允许进行数据的压缩传输
        solrParams.set(HttpClientUtil.PROP_MAX_CONNECTIONS_PER_HOST,this.preMaxConnection) ;
        solrParams.set(HttpClientUtil.PROP_FOLLOW_REDIRECTS,false) ; // 不进行重定向配置
        // 将拦截器整合在当前的HttpClient创建的工具类之中
        HttpClientUtil.addRequestInterceptor(new AuthRequestInterceptor());
        CloseableHttpClient httpClient = HttpClientUtil.createClient(solrParams);// 设置相关的Solr处理参数
        HttpSolrClient solrClient = new HttpSolrClient.Builder(this.solrHostUrl)
                .withHttpClient(httpClient).withConnectionTimeout(this.connectionTimeout)
                .withSocketTimeout(this.socketTimeout).build();
        return solrClient ;
    }
    private class AuthRequestInterceptor implements HttpRequestInterceptor {
        // 对于当前的Solr服务器认证的机制使用的是HttpBase模式完成的
        private ContextAwareAuthScheme authScheme = new BasicScheme() ;
        @Override
        public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
            // 根据HTTP上下文获取当前目标服务器的认证处理对象
            AuthState authState = (AuthState) httpContext.getAttribute(HttpClientContext.TARGET_AUTH_STATE);
            // 随后需要考虑当前的状态是否存在
            if (authState != null && authState.getAuthScheme() == null) {   // 现在没有具体的认证出合理模式
                CredentialsProvider credentialsProvider = (CredentialsProvider) httpContext.getAttribute(HttpClientContext.CREDS_PROVIDER) ; // 获取认证提供者
                HttpHost targetHost = (HttpHost) httpContext.getAttribute(HttpClientContext.HTTP_TARGET_HOST) ;// 获取目标主机
                // 根据访问的目标主机，通过认证提供者对象创建一个具体的认证信息
                Credentials credentials = credentialsProvider.getCredentials(new AuthScope(targetHost.getHostName(), targetHost.getPort()));
                if (credentials == null) {  // 没有提供认证处理
                    throw new HttpException("【"+targetHost.getHostName()+"】没有HTTP认证处理支持！") ;
                }
                httpRequest.addHeader(authScheme.authenticate(credentials,httpRequest,httpContext));
            }
        }
    }
}
