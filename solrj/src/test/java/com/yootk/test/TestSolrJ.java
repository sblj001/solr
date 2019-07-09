package com.yootk.test;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;
import java.util.Map;

@ContextConfiguration(locations = {"classpath:spring/spring-base.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestSolrJ {
    @Autowired
    private HttpSolrClient solrClient ;
    @Test
    public void testAddDocument() throws Exception {
        SolrInputDocument document = new SolrInputDocument() ; // 输入索引
        document.addField("id","989");
        document.addField("solr_s_name","小强王中后火腿");
        document.addField("solr_s_note","德国进口原材料，价格实惠，治疗百病");
        document.addField("solr_s_provider","马老二食品加工");
        document.addField("solr_s_catalog","熟食");
        document.addField("solr_d_price",79.52);
        document.addField("solr_s_photo","qiangqiang.png");
        document.addField("solr_i_isdelete",0);
        document.addField("solr_date_recdate",new Date());
        UpdateResponse response = this.solrClient.add(document);// 向Solr中追加Document
        System.out.println("【花费时间】" + response.getElapsedTime());
        this.solrClient.commit() ;// 提交修改
        this.solrClient.close();
    }
    @Test
    public void testQuery() throws Exception {
        SolrQuery solrQuery = new SolrQuery() ; // 创建查询对象
        solrQuery.setQuery("solr_keywords:*宝*") ; // 查询全部数据
        solrQuery.setSort("solr_d_price", SolrQuery.ORDER.desc) ; // 采用降序排列
        solrQuery.setHighlight(true) ; // 采取高亮配置
        solrQuery.addHighlightField("solr_s_name") ; // 追加高亮显示字段
        solrQuery.setHighlightSimplePre("<strong>") ;   // 高亮标记开始
        solrQuery.setHighlightSimplePost("</strong>") ;  // 高亮标记结束
        // 3、利用SolrClient发出Solr查询命令，随后获取查询响应结果
        QueryResponse queryResponse = this.solrClient.query(solrQuery);
        // 4、所有的HTTP服务器的响应信息都保存在了QueryResponse对象实例之中，根据响应获取数据
        SolrDocumentList results = queryResponse.getResults();// 获取相应的信息
        System.out.println("===================== 普通数据查询 ==================");
        // 5、Document保存的是每一个索引数据，而DocumentList返回的是索引集合
        System.out.println("【数据行数】" + results.getNumFound());
        // 6、所有的数据以List集合的形式出现
        for (SolrDocument document : results) { // 文档迭代输出
            System.out.println("【返回信息】id = " + document.get("id") + "、name = " + document.get("solr_s_name") + "、catalog = " + document.get("solr_s_catalog"));
        }
        System.out.println("===================== 显示高亮数据 ==================");
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();// 获取所有的高亮数据
        for (SolrDocument document : results) {
            Map<String, List<String>> resultMap = highlighting.get(document.get("id")) ;
            System.out.println("【高亮显示】" + resultMap.get("solr_s_name"));
        }
        this.solrClient.close();
    }
}
