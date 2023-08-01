package com.example.FlashCards.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Component
@Entity
@Builder
@Table(name="users")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String password;
    @Transient
    private char[] passwordChars;
    private String email;

    @Enumerated(EnumType.STRING)
    private Idiom preferredLanguage;

    private boolean isAdmin;

    @OneToMany
    @JoinColumn(name="userId")
    private List<Course> courseList;


    public User(String username, char[] passwordChars, String email, Idiom preferredLanguage) {
        this.username = username;
        this.passwordChars = passwordChars;
        this.email = email;
        this.preferredLanguage = preferredLanguage;
        this.isAdmin = false;
    }



}
