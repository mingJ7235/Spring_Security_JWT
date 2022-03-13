package com.security.jwt.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//스프링 시큐리티에 UsernamePasswordAuthenticationFilter 가 있음.
// /login 요청해서 username, password 를 post로 전송하면 UsernamePasswordAuthenticationFilter 가 동작한다.
// 하지만, 현재 지금 securityconfig에서 formLogin을 disable했으므로 정상적으로 동작안한다.
// 그렇기에 securityconfig에 다시 재등록을 해줘야한다.
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    // /login 요청을 하면 로그인 시도를 위해서 실행되는 함수다.
    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) throws AuthenticationException {

        //1. username, password 받아서

        //2. 정상인지 로그인 시도를 함 authenticationManager로 로그인 시도를 하면
        // PrincipalDetailsService가 호출되고 loadUserByUsername() 함수 실행된다.

        //3. PrincipalDetails를 세션에 담고 -> 세션에 담는 이유 ?  세션에 값이 있어야 권한 관리를 해줄 수 있다. 권한 관리를 위해서 담는것!

        //4. JWT 토큰 만들어서 응답하면된다.

        System.out.println("JwtAuthenticationFilter : 로그인 시도중");
        return super.attemptAuthentication(request, response);
    }

}
