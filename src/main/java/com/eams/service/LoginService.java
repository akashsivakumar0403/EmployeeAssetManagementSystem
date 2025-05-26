package com.eams.service;
import com.eams.entity.User;
import com.eams.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private IUserRepository userRepository;

    public boolean login(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, password);
        return user != null;
    }
}