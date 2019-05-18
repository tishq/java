package cn.tishq.demo;


import cn.tishq.springboot_login.entity.Article;
import cn.tishq.springboot_login.entity.User;
import cn.tishq.springboot_login.bean.Person;


import cn.tishq.springboot_login.repository.UserRepository;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    Person person;

    @Autowired
    ApplicationContext ioc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StringRedisTemplate stringRedisTemplate; //k-v都是字符串

    @Autowired
    RedisTemplate redisTemplate;//k-v都是对象


//测试springdata elasticsea,这回和springdata jdbc冲突,导致无法注入bean
//    @Autowired
//    ArticleRepository articleRepository;
//
//    @Test
//    public void testSpringbootES() {
//        Article article = new Article();
//        articleRepository.index(article);
//    }

    @Autowired
    JestClient jestClient;

    @Test //jestes索引一个文档
    public void testJestES() {
        Article artical =new Article();
        artical.setId(1);
        artical.setTitle("aa");
        artical.setUrl("https://www.baidu.com");
        Index index = new Index.Builder(artical).index("dd").type("ff").build();
        try {
            jestClient.execute(index);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test //测试jestes搜索
    public void testJestES01() {
        String json = "{\n" +
                "    \"query\" : {\n" +
                "        \"match\" : {\n" +
                "            \"title\" : \"aa\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
        Search search = new Search.Builder(json).addIndex("dd").addType("ff").build();
        try {
            SearchResult result = jestClient.execute(search);
            System.out.println(result.getJsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testRedis() {
//        stringRedisTemplate.opsForValue().set("username","it@upc");
//        stringRedisTemplate.opsForValue().set("password","123456");
        String username = stringRedisTemplate.opsForValue().get("username");
        String password = stringRedisTemplate.opsForValue().get("password");
        System.out.println(username+"\n"+password );


        User user = userRepository.getOne(1);
        redisTemplate.opsForValue().set("user3",user);

    }

    @Test
    public void testJpa() {
        Integer integer = 1;
        User user = userRepository.getOne(integer);
        System.out.println(user);
    }

    @Test
    public void testHelloService() {
        boolean boo = ioc.containsBean("helloService");
        System.out.println(boo);
    }

    @Test
    public void contextLoads() {
        System.out.println(person);

    }

    @Test
    public void  logTest() {

        //日志级别
        //由低到高 trace<debug<info<warn<error
        //springBoot默认使用info级别
        logger.trace("这是trace日志");
        logger.debug("这是debug日志");
        logger.info("这是info日志");
        logger.warn("这是warn日志");
        logger.error("这是error日志");

    }

}
