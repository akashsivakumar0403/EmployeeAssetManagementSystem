package com.eams.repository;
import com.eams.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Integer> {
    User findByUsernameAndPassword(String username, String password);
}
