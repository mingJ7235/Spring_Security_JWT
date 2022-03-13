package com.security.jwt.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.jwt.auth.PrincipalDetails;
import com.security.jwt.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        try {
            ObjectMapper om = new ObjectMapper();
            Member member = om.readValue(request.getInputStream(), Member.class);
            System.out.println(member);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(member.getUsername(), member.getPassword());
            
            // PrincipalDetailsService의 loadUserByUsername() 함수가 실행되는 것임
            // 함수가 실행이 정상이면 authentication이 리턴된다.
            //즉, DB에 있는 username과 password가 일치한다는 것이다.
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            
            PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
            System.out.println("로그인 완료됨 : " + principal.getMember().getUsername()); // 로그인이 정상적으로 되었다는 뜻이다.
            // authentication 객체가 session 영역에 저장을 해야하는데, 저장을 위해 return 해준다. 이러면 session에 저장이된다.
            //return 의 이유는 권한 관리를 security가 대신 해주기 때문에 편하려고 하는 것이다.
            //굳이 JWT 토큰을 사용하면서 세션을 만들 이유가 없다. 근데 단지 권한 처리 때문에 session에 넣어 주는 것이다.
            return authentication;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    //attemptAuthentication 실행 후 인증이 정상적으로 되었으면 successfulAuthentication 함수가 실행된다.
    //JWT 토큰을 만들어서 request 요청한 사용자에게 jwt토큰을 response해주면 된다.
    @Override
    protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain, final Authentication authResult) throws IOException, ServletException {
        System.out.println("successfulAuthentication 실행됨 : 인증이 완료됨을 의미함");
        super.successfulAuthentication(request, response, chain, authResult);
    }

}
