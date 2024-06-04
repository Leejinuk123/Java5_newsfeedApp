package com.sparta.newsfeedapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userId", nullable = false, unique = true)
    private String userId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "bio", length = 20)
    private String bio;

    @Column(name = "userStatus", nullable = false)
    @Enumerated(EnumType.STRING)
    private String userStatus;

    @Column(name = "refreshToken")
    private String refreshToken;

    @Column(name = "statusChangeTime")
    private Timestamp statusChangeTime;

    @Column(name = "createdAt")
    private Timestamp createdAt;

    @Column(name = "updatedAt")
    private Timestamp updatedAt;
}
