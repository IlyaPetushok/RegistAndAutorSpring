package com.example.untitled.controller;

import com.example.untitled.captcha.CapthaVerif;
import com.example.untitled.dao.UserRepository;
import com.example.untitled.email.EmailSenderService;
import com.example.untitled.email.GenerateKey;
import com.example.untitled.entity.User;
import com.example.untitled.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    public String email2;

    GenerateKey generateKey = new GenerateKey();
    @Autowired
    UserRepository userRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    UserService userService;
    @Autowired
    private EmailSenderService senderService;

    @GetMapping("/")
    public String Home(Model model) {
        return "home.html";
    }

    @GetMapping("/reg")
    public String viewRegister(Model model) {
        return "reg.html";
    }

    @GetMapping("/auto")
    public String viewLogin(Model model) {
        return "auto.html";
    }

    @GetMapping("/email")
    public String email(Model model) {
        return "email.html";
    }

    @GetMapping("/email2")
    public String exepptEmail(Model model) {
        return "email2.html";
    }


    @GetMapping("/members")
    public Iterable<User> viewMembers(Model model) {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("members", users);
        return (Iterable<User>) model.getAttribute("members");
    }

    @PostMapping("/auto")
    public String login(javax.servlet.http.HttpServletRequest request,javax.servlet.http.HttpServletRequest response, Map<String, Object> model, @RequestParam String login, @RequestParam String password) {
        List<User> curUser = null;
        HttpSession session = request.getSession();
        Integer count = (Integer) session.getAttribute("count");
        if (count == null) {
            session.setAttribute("count", 0);
        } else {
            session.setAttribute("count",count+1);
        }

        String s = "select * from user where login='" + login + "' and" + " passwordHash='" + userService.getPasswordHash(password) + "'";
        curUser = jdbcTemplate.query(s, new BeanPropertyRowMapper<>(User.class));
        if (!curUser.isEmpty()) {
            return "redirect:/membersPage.html";
        }
        return "redirect:/auto.html";
    }

    @PostMapping("/email")
    public String email(@RequestParam String email) {
        if (email != "") {
            List<User> curUser = null;
            String s = "select * from user where email='" + email + "'";
            curUser = jdbcTemplate.query(s, new BeanPropertyRowMapper<>(User.class));
            //не забыть поставить !
            if (curUser.isEmpty()) {
                return "redirect:/email.html";
            }
            String Key = "null";
            Key = generateKey.New_Key();
            email2 = email;
            senderService.sendEmail(email,
                    "This is key",
                    Key);
            return "redirect:/email2.html";
        }
        return "redirect:/email.html";
    }

    @PostMapping("/email2")
    public String email2(@RequestParam String code) {
        String key;
        key = generateKey.ReturnKey();
        System.out.print(key);
        if (code != "") {
            if (code.equals(key)) {
                System.out.print(generateKey.ReturnKey());
                return "redirect:/reg.html";
            }
        }
        return "redirect:/email2.html";
    }

    @PostMapping("/reg")
    public String saveRegister(@RequestParam String login,
                               @RequestParam String password, @RequestParam String confirmPassword, String email, HttpServletRequest request) {

        email = email2;
        if (password.equals(confirmPassword)) {
            List<User> curUser = null;
            String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
            Boolean cheake = CapthaVerif.verify(gRecaptchaResponse);
            if (!cheake) {
                return "redirect:/reg.html";
            }
            String s = "select * from user where login='" + login + "'";
            curUser = jdbcTemplate.query(s, new BeanPropertyRowMapper<>(User.class));
            if (!curUser.isEmpty()) {
                return "redirect:/reg.html";
            }
            User newUser = new User(login, userService.getPasswordHash(password), email);
            userRepository.save(newUser);
            return "redirect:/auto.html";
        }
        return "redirect:/reg.html";
    }
}

