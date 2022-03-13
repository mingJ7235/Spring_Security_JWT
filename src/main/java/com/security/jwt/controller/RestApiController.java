package com.security.jwt.controller;

import com.security.jwt.model.Member;
import com.security.jwt.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestApiController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final MemberRepository memberRepository;

    @GetMapping ("/home")
    public String home () {
        return "<h1>home</h1>";
    }

    @PostMapping ("/token")
    public String token () {
        return "<h1>token</h1>";
    }

    @PostMapping ("join")
    public String join (@RequestBody Member member) {
        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        member.setRoles("ROLE_USER");
        memberRepository.save(member);
        return "회원가입 완료";
    }

    // user, manager, admin
    @GetMapping ("/api/v1/user")
    public String user() {
        return "user";
    }

    // manager, admin
    @GetMapping ("/api/v1/manager")
    public String manager() {
        return "manager";
    }

    //admin
    @GetMapping ("/api/v1/admin")
    public String admin() {
        return "admin";
    }

}
