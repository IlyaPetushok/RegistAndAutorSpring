package com.example.untitled.entity;


import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

public class User {
    @Id
    private int id;
    @Column("login")
    private String login;
    @Column("passwordHash")
    private String passwordHash;


    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public User(){
        super();
    }
    public User(String email){
        super();
        this.email=email;
    }

    public User(int id, String login, String password,String email){
        super();
        this.id=id;
        this.login=login;
        this.email=email;
        this.passwordHash= DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
    }

    public User(String login,String password,String email){
        super();
        this.login=login;
        this.passwordHash=password;
        this.email=email;
    }


    public void setId(int id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String password) {
        this.passwordHash = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", email='"+email+'\''+
                '}';
    }
}

