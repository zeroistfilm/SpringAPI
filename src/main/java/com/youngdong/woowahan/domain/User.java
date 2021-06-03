package com.youngdong.woowahan.domain;

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
}
