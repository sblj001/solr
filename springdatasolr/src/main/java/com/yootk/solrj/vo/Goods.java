package com.yootk.solrj.vo;

import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.Date;

@SolrDocument(collection = "happy_core")
public class Goods {    // 描述的是Solr索引结构
    @Id
    @Indexed("id")      // Solr索引结构的名称
    private String id;
    @Indexed("solr_s_name")      // Solr索引结构的名称
    private String name;
    @Indexed("solr_s_catalog")      // Solr索引结构的名称
    private String catalog;
    @Indexed("solr_s_provider")      // Solr索引结构的名称
    private String provider;
    @Indexed("solr_s_note")      // Solr索引结构的名称
    private String note;
    @Indexed("solr_s_photo")      // Solr索引结构的名称
    private String photo;
    @Indexed("solr_d_price")      // Solr索引结构的名称
    private Double price;
    @Indexed("solr_i_isdelete")      // Solr索引结构的名称
    private Integer isdelete;
    @Indexed("solr_date_recdate")      // Solr索引结构的名称
    private Date recdate;
    @Indexed("solr_keywords")      // Solr索引结构的名称
    private String keywords;

    @Override
    public String toString() {
        return "Goods{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", catalog='" + catalog + '\'' +
                ", provider='" + provider + '\'' +
                ", note='" + note + '\'' +
                ", photo='" + photo + '\'' +
                ", price=" + price +
                ", isdelete=" + isdelete +
                ", recdate=" + recdate +
                ", keywords='" + keywords + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

    public Date getRecdate() {
        return recdate;
    }

    public void setRecdate(Date recdate) {
        this.recdate = recdate;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}
