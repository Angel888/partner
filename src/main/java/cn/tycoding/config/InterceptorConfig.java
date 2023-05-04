//package cn.tycoding.config;
//
//import cn.tycoding.interceptor.JWTInterceptor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class InterceptorConfig implements WebMvcConfigurer {
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//
//        registry.addInterceptor(new JWTInterceptor())
//                .addPathPatterns("/interceptorVerify/**")
//                .excludePathPatterns(
//                        "/",
//                        "/chat/**",
//                        "/user/register",
//                        "/user/login"
//                );
//
//
//    }
//}
////
////
