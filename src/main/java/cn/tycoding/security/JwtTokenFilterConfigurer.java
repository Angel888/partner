//package cn.tycoding.security;
//
//import lombok.RequiredArgsConstructor;
//import murraco.exception.CustomException;
//import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.DefaultSecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@RequiredArgsConstructor
//public class JwtTokenFilterConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
//
//  private final JwtTokenProvider jwtTokenProvider;
////  private  final JwtTokenFilter customFilter;
////  public JwtTokenFilterConfigurer(JwtTokenProvider jwtTokenProvider) {
////    this.jwtTokenProvider = jwtTokenProvider;
////  }
//
//  @Override
//  public void configure(HttpSecurity http) throws Exception {
//    JwtTokenFilter customFilter = new JwtTokenFilter(jwtTokenProvider);
//    http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
//  }
//
//
//  @RequiredArgsConstructor
//  private class JwtTokenFilter extends OncePerRequestFilter {  //todo 看一下这样写成private class会不会报错  可以写成static吗？
//
//    private final JwtTokenProvider jwtTokenProvider;
//
////  public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
////    this.jwtTokenProvider = jwtTokenProvider;
////  }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
//      String token = jwtTokenProvider.resolveToken(httpServletRequest);
//      try {
//        if (token != null && jwtTokenProvider.validateToken(token)) {
//          Authentication auth = jwtTokenProvider.getAuthentication(token);
//          SecurityContextHolder.getContext().setAuthentication(auth);
//        }
//      } catch (CustomException ex) {
//        //this is very important, since it guarantees the user is not authenticated at all
//        SecurityContextHolder.clearContext();
//        httpServletResponse.sendError(ex.getHttpStatus().value(), ex.getMessage());
//        return;
//      }
//
//      filterChain.doFilter(httpServletRequest, httpServletResponse);
//    }
//
//  }
//}
//
//
