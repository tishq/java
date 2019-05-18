package cn.tishq.springboot_login.bean;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;


import java.util.Date;

/**
 * @Author: 孟红全
 * @Date: 19-4-22 上午11:57
 * @Version 1.0
 */
//lombok注解
@Data

//绑定全局配置文件
@ConfigurationProperties(prefix = "person")

//申明容器
@Component

//属性约束
@Validated

//绑定局部配置文件
@PropertySource(value = {"classpath:config/person.properties"})
public class Person {

    private String name;
    private Integer age;
    private Date birth;
    private Dog dog;
    @Email
    private String email;
}
