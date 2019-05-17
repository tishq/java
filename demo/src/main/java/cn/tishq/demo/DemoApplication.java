package cn.tishq.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


//导入spring配置文件，让其生效
//用配置文件的方式往容器中添加组件
//@ImportResource(locations = {"classpath:beans.xml"})


@EnableCaching //开启缓存
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


}
