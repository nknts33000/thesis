package com.example.platform.security.config;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.platform.exceptions.UserNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//@RequiredArgsConstructor
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private UserAuthenticationProvider userAuthenticationProvider;

    public JwtAuthFilter(UserAuthenticationProvider userAuthenticationProvider){
        this.userAuthenticationProvider=userAuthenticationProvider;
    }

//    @Autowired
//    public void setAuthenticationProvider(UserAuthenticationProvider userAuthenticationProvider){
//        this.userAuthenticationProvider=userAuthenticationProvider;
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header= request.getHeader(HttpHeaders.AUTHORIZATION);

        if(header !=null){
            String[] elements =header.split(" ");
            System.out.println(elements[0]);
            System.out.println(elements[1]);
            if(elements.length==2 && "Bearer".equals(elements[0])){
                try{
                    SecurityContextHolder.getContext().setAuthentication(
                            userAuthenticationProvider.validateToken(elements[1])
                    );
                }catch (RuntimeException e){
                    SecurityContextHolder.clearContext();
                    throw e;
                } catch (UserNotFoundException e) {
                    throw new RuntimeException(e);
                }
//                catch (TokenExpiredException e){
//                    throw new TokenExpiredException("Token is expired",);
//                }
            }
        }

        filterChain.doFilter(request,response);

    }
}
