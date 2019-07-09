package com.yootk.test;

import com.yootk.solrj.dao.IGoodsRepository;
import com.yootk.solrj.vo.Goods;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@ContextConfiguration(locations = {"classpath:spring/spring-base.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestSpringDataSolr {
    @Autowired  // 正规的做法是在业务层上注入
    private IGoodsRepository goodsRepository;

    @Test
    public void testKeywords() throws Exception {
        Sort sort = new Sort(Sort.Direction.DESC, "solr_d_price");
        Pageable pageable = PageRequest.of(0, 5, sort); // 分页与排序
        HighlightPage<Goods> result = this.goodsRepository.findByKeywordsContaining("宝", pageable);
        System.out.println("总记录数：" + result.getTotalElements());
        System.out.println("总页数：" + result.getTotalPages());
        List<HighlightEntry<Goods>> entryList = result.getHighlighted();// 得到查询结果
        entryList.forEach((entry) -> {
            System.out.println(entry.getEntity());
        }); // 直接输出最终结果
    }

    @Test
    public void testName() throws Exception {
        Sort sort = new Sort(Sort.Direction.DESC, "solr_d_price");
        Pageable pageable = PageRequest.of(0, 5, sort); // 分页与排序
        HighlightPage<Goods> result = this.goodsRepository.findByNameContaining("小强", pageable);
        System.out.println("总记录数：" + result.getTotalElements());
        System.out.println("总页数：" + result.getTotalPages());
        List<HighlightEntry<Goods>> entryList = result.getHighlighted();// 得到查询结果
        entryList.forEach((entry) -> {
            System.out.println(entry.getEntity());
        }); // 直接输出最终结果
    }

    @Test
    public void testFindAll() throws Exception {
        Iterable<Goods> all = this.goodsRepository.findAll();
        all.forEach(System.out::println); // 直接输出最终结果
    }
}
