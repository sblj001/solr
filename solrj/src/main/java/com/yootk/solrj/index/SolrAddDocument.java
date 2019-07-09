package com.yootk.solrj.index;

import com.yootk.solrj.util.SolrConnectionUtil;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;

import java.util.Date;

public class SolrAddDocument {
    public static void main(String[] args) throws Exception {
        HttpSolrClient solrClient = SolrConnectionUtil.getHttpSolrClient() ;
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
        UpdateResponse response = solrClient.add(document);// 向Solr中追加Document
        System.out.println("【花费时间】" + response.getElapsedTime());
        solrClient.commit() ;// 提交修改
        solrClient.close();
    }
}
