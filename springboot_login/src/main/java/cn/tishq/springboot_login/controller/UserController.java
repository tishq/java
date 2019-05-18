package cn.tishq.springboot_login.controller;

import cn.tishq.springboot_login.entity.User;
import cn.tishq.springboot_login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: 孟红全
 * @Date: 19-5-8 下午7:45
 * @Version 1.0
 */
@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;



    //restfull风格 查询所有用户
    @GetMapping("/users")
    public List<User> getAllUser() {
        return userRepository.findAll();

    }

    //restfull风格 添加用户
    @PostMapping("/user")
    public User insertUser(User user) {
        User save = userRepository.save(user);
        return save;
    }


}
