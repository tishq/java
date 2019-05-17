package cn.tishq.demo.controller;

//import cn.tishq.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @Author: 孟红全
 * @Date: 19-4-23 下午1:05
 * @Version 1.0
 */
@Controller
public class HelloController {



    @ResponseBody
    @RequestMapping("/hello")
    public String sayHello() {
        return "home";

    }

    @RequestMapping("/success")
    public String success(Map<String,Object> map) {
        map.put("hello","<h1>你好</h1>");
        map.put("users", Arrays.asList("lufei","mucheng","wuling"));
        return "success";
    }
}
