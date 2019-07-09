package com.yootk.solrj.dao;

import com.yootk.solrj.vo.Goods;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.repository.Highlight;
import org.springframework.data.solr.repository.SolrCrudRepository;


public interface IGoodsRepository extends SolrCrudRepository<Goods, String> {
    // 此时明确描述了对于当前的关键字的查询使用“keywords”这个属性，“Containing”描述包含
    @Highlight(prefix = "<strong>",postfix = "</strong>")
    public HighlightPage<Goods> findByKeywordsContaining(String keywords, Pageable page) ;
    // 根据名称进行模糊查询
    @Highlight(prefix = "<strong>",postfix = "</strong>")
    public HighlightPage<Goods> findByNameContaining(String keywords, Pageable page) ;
}
