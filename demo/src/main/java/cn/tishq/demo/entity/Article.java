package cn.tishq.demo.entity;
import lombok.Data;
import org.springframework.data.annotation.Id;
//import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Entity;

/**
 * @Author: 孟红全
 * @Date: 19-5-9 下午8:15
 * @Version 1.0
 */
@Data
public class Article {
    @Id
    private Integer id;
    private String title;
    private String url;


}

