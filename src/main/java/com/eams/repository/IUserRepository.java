package com.eams.repository;

import com.eams.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    boolean existsByUsername(String username);
	User findByUsernameAndPassword(String username, String password);
}
