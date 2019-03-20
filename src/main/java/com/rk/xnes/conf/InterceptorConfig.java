package com.rk.xnes.conf;
//                            _ooOoo_
//                           o8888888o
//                           88" . "88
//                           (| -_- |)
//                           O\  =  /O
//                        ____/`---'\____
//                      .'  \\|     |//  `.
//                     /  \\|||  :  |||//  \
//                    /  _||||| -:- |||||-  \
//                    |   | \\\  -  /// |   |
//                    | \_|  ''\---/''  |   |
//                    \  .-\__  `-`  ___/-. /
//                  ___`. .'  /--.--\  `. . __
//               ."" '<  `.___\_<|>_/___.'  >'"".
//              | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//              \  \ `-.   \_ __\ /__ _/   .-` /  /
//         ======`-.____`-.___\_____/___.-`____.-'======
//                            `=---='
//        ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
//                      Buddha Bless, No Bug !

import com.rk.xnes.interceptor.AdminPageInterceptor;
import com.rk.xnes.interceptor.TaskInterceptor;
import com.rk.xnes.interceptor.UserLoginnterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * spring boot 的配置类 @Configuration
 * 在该配置类中扩展配置的springmvc的内容
 *
 * 配置了一些拦截器
 * 配置了直接的路由
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //在此处new interceptor并注入

        //1.admin interceptor
        List<String> patterns = new ArrayList<>();
        patterns.add("/admin/index");
        patterns.add("/admin/certify");
        patterns.add("/admin/helpinfo");
        registry.addInterceptor(new AdminPageInterceptor()).addPathPatterns(patterns);

        //2.task interceptor
        registry.addInterceptor(new TaskInterceptor()).addPathPatterns("/task/**");

        //3.user interceptor
        List<String> userPatterns = new ArrayList<>();
        userPatterns.add("/user/login");
        userPatterns.add("/user/loginoff");
        userPatterns.add("/user/regist");
        userPatterns.add("/user/findpassword");
        userPatterns.add("/user/getq");
        registry.addInterceptor(new UserLoginnterceptor()).addPathPatterns("/user/**").excludePathPatterns(userPatterns);
    }

    /**
     * 添加不经过controller直接跳转页面
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/regist").setViewName("/frontend/regist");
        registry.addViewController("/login").setViewName("/frontend/login");
        registry.addViewController("/forget").setViewName("/frontend/findpassword");
        registry.addViewController("/admin/login").setViewName("/backend/login");
    }
}
