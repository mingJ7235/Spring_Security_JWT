package com.security.jwt.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Data
public class Member {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String password;
    private String roles; // USER, ADMIN

    // Role이 복수개를 리턴 하도록 하기위해서는 ?  -> 차후에 enum으로 변경
    public List<String> getRoleList () {
        if(this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }
}
