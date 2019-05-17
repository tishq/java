package com.example.es.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @Author: 孟红全
 * @Date: 19-5-10 上午9:29
 * @Version 1.0
 */
@Document(indexName = "it", type = "article")
@Data
public class Article {
    private Integer id;
    private String title;
    private String url;
}

