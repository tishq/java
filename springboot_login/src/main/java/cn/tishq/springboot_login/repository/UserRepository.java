package cn.tishq.springboot_login.repository;

import cn.tishq.springboot_login.entity.User;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: 孟红全
 * @Date: 19-5-8 上午11:21
 * @Version 1.0
 */
//继承JpaRepository,来完成对表的crud和排序分页操作
public interface UserRepository extends JpaRepository<User,Integer> {

    @CachePut(cacheNames = "users") //同步更新缓存
    @Override
    List<User> findAll();
}
