package com.example.es.repository;

import com.example.es.entity.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @Author: 孟红全
 * @Date: 19-5-10 上午9:31
 * @Version 1.0
 */

public interface ArticleRepository extends ElasticsearchRepository<Article,Integer> {
}
