package com.eams.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "role_descriptions")
public class Model {

	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private User.Role role;

    @Column(nullable = false, length = 1000)
    private String responsibilities;

    public Model() {
    }

    public Model(User.Role role, String responsibilities) {
        this.role = role;
        this.responsibilities = responsibilities;
    }

    public int getId() {
        return id;
    }

    public User.Role getRole() {
        return role;
    }

    public void setRole(User.Role role) {
        this.role = role;
    }

    public String getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(String responsibilities) {
        this.responsibilities = responsibilities;
    }

    @Override
    public String toString() {
        return "Model{" +
                "id=" + id +
                ", role=" + role +
                ", responsibilities='" + responsibilities + '\'' +
                '}';
    }
}
