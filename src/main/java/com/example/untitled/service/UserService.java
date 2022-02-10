package com.example.untitled.service;

import com.example.untitled.dao.UserRepository;
import com.example.untitled.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public String getPasswordHash(String password){
        return DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
    }
    public ArrayList<User> loadUserList(){
        return (ArrayList<User>) userRepository.findAll();
    }
}
