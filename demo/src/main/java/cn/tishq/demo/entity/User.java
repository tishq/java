package cn.tishq.demo.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author: 孟红全
 * @Date: 19-5-8 上午11:02
 * @Version 1.0
 */
//jpa注解，配置映射关系
@Entity
@Table(name = "user")
@Data
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //自增主键
    private Integer id;

    @Email
    @Column(name = "username", length = 25) //指定列名和长度
    private String username;

    private String password;
}