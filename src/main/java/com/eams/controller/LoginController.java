package com.eams.controller;
import com.eams.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public String login(@RequestParam String username, @RequestParam String password) {
        boolean success = loginService.login(username, password);
        if (success) {
            return "Login successful!";
        } else {
            return "Invalid username or password.";
        }
    }
}
