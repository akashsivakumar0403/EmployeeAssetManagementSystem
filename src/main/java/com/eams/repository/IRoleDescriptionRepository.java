package com.eams.repository;

import com.eams.entity.Model;
import com.eams.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleDescriptionRepository extends JpaRepository<Model, Integer> {
    Optional<Model> findByRole(User.Role role);
}
