package cn.tishq.demo.config;

import cn.tishq.demo.service.HelloService;
import cn.tishq.demo.component.LoginHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Author: 孟红全
 * @Date: 19-4-23 下午12:53
 * @Version 1.0
 */

//指明当前类是一个配置类
//WebMvcConfigurerAdapter扩展SpringMvc功能
@Configuration
public class MyAppConfig extends WebMvcConfigurerAdapter{

    //用注解的方式给容器中添加组件
    //将方法的返回值添加到容器中
    //容器中这个组件的默认的id是方法名
    @Bean
    public HelloService helloService() {
        return  new HelloService();
    }


    //把登录拦截器添加到组件中
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
//        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
//                .excludePathPatterns("/index.html","/","/login","/assets/**");
        //拦截用户直接访问主页
        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/main.html");
    }


    public void addViewControllers(ViewControllerRegistry registry) {
        //super.addViewControllers(registry);
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/index.html").setViewName("login");
        registry.addViewController("/main.html").setViewName("home");
    }
}
