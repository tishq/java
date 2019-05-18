package cn.tishq.springboot_login.component;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: 孟红全
 * @Date: 19-5-7 下午9:27
 * @Version 1.0
 */
public class LoginHandlerInterceptor implements HandlerInterceptor {
    //登录拦截器
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object user = request.getSession().getAttribute("userlogin");
        //未登录，返回登录页
        if(user == null) {
            request.setAttribute("msg","没有权限，请登录");
            request.getRequestDispatcher("/").forward(request,response);
            return false;
        }
        else {
            return true;
        }

    }
}
