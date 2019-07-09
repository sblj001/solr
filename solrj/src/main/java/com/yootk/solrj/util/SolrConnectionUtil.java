package com.yootk.solrj.util;

import com.yootk.solrj.util.interceptor.AuthRequestInterceptor;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.solr.client.solrj.impl.HttpClientUtil;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.params.ModifiableSolrParams;

public class SolrConnectionUtil {
    public static final String SOLR_HOST_URL = "http://192.168.28.136/solr/happy_core";
    public static final String USERNAME = "lee";   // 用户名
    public static final String PASSWORD = "happy"; // 密码
    private SolrConnectionUtil() {}
    public static HttpSolrClient getHttpSolrClient() {
        // 定义一个可以保存所有Solr基础配置信息的对象
        ModifiableSolrParams solrParams = new ModifiableSolrParams() ;
        solrParams.set(HttpClientUtil.PROP_BASIC_AUTH_USER,USERNAME) ;
        solrParams.set(HttpClientUtil.PROP_BASIC_AUTH_PASS,PASSWORD) ;
        // 将拦截器整合在当前的HttpClient创建的工具类之中
        HttpClientUtil.addRequestInterceptor(new AuthRequestInterceptor());
        CloseableHttpClient httpClient = HttpClientUtil.createClient(solrParams);// 设置相关的Solr处理参数
        HttpSolrClient solrClient = new HttpSolrClient.Builder(SOLR_HOST_URL).withHttpClient(httpClient).build();
        return solrClient ;
    }
}
