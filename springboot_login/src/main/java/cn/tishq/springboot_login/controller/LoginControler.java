package cn.tishq.springboot_login.controller;

import cn.tishq.springboot_login.entity.User;
import cn.tishq.springboot_login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @Author: 孟红全
 * @Date: 19-5-7 下午6:59
 * @Version 1.0
 */
@Controller
public class LoginControler {
    @Autowired
    UserRepository userRepository;

    //map用来放错误信息
    //若登录成功把user放到session中，给拦截器做判断
    @PostMapping(value = "/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Map<String,Object> map,
                        HttpSession session) {

        List<User> users = userRepository.findAll();
        for(User user:users) {
            //登录成功
            if(user.getUsername().equals(username) &&
                    user.getPassword().equals(password)) {
                session.setAttribute("userlogin","username");
                return "redirect:/main.html";
            }
        }

        //登录失败
        map.put("msg","密码错误，请重新输入");
        return "login";
    }
}
