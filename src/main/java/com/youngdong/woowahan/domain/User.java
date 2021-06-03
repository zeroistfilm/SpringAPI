package com.youngdong.woowahan.domain;

import java.util.Locale;

//    UID   int          not null auto_increment primary key,
//    Email varchar(100) not null,
//    Name  varchar(40) not null,
public class User {
    private Long uid;
    private String name;
    private String email;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void isVailid() {

        if (this.getName().isEmpty() && this.getEmail().isEmpty()) {
            throw new IllegalStateException("회원 이름과 이메일 정보가 없습니다");
        }

        if (this.getEmail().isEmpty()) {
            throw new IllegalStateException("회원 이메일 정보가 없습니다");
        }
        if (this.getName().isEmpty()) {
            throw new IllegalStateException("회원 이름 정보가 없습니다");
        }

        if (!isVailidEmail(this.getEmail())) {
            throw new IllegalStateException("회원 이메일 정보가 양식에 맞지 않습니다");
        }

    }


    public boolean isVailidEmail(String email) {

        //앞 뒤 트림
        email = email.trim();

        //@기준 분리
        String[] splited = email.split("@");
        if (splited.length != 2) { //@ 없음.
            return false;
        }

        //.기준 분리
        String[] domainToTopDomain = splited[1].split("\\.");
        if (domainToTopDomain.length != 2) { //. 없음.
            return false;
        }

        String account = splited[0];
        String domain = domainToTopDomain[0];
        String topdomain = domainToTopDomain[1];

        // 3가지 정보가 모두 들어 있는 경우
        if (!account.isEmpty() && !domain.isEmpty() && !topdomain.isEmpty()) {
            return true;
        } else {
            return false;
        }


    }

}