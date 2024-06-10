package com.sparta.newsfeedapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

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

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "bio", length = 20)
    private String bio;

    @Column(name = "userStatus", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatusEnum userStatus;

    @Column(name = "refreshToken")
    private String refreshToken;

    @Column(name = "statusChangeTime")
    private Timestamp statusChangeTime;

    public User(String userId, String password, String email, String name, String bio, UserStatusEnum userStatus){
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.name = name;
        this.bio = bio;
        this.userStatus = userStatus;
    }

    public void setRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    public void deactivateUser(){
        this.userStatus = UserStatusEnum.DELETED;
        this.statusChangeTime = new Timestamp(System.currentTimeMillis());
    }
    public void update(String newName, String newEmail, String newPassword, String newBio) {
        if (newName != null) this.name = newName;
        if (newEmail != null) this.email = newEmail;
        if (newPassword != null) this.password = newPassword;

        // Todo : 비밀번호 바꾸기 싫을경우 null check 를 서비스에서?
        if (newBio != null) this.bio = newBio;
    }
}
