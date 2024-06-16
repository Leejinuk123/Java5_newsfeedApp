package com.sparta.newsfeedapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "bio", length = 20)
    private String bio;

    @Column(name = "user_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatusEnum userStatus;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "status_change_time")
    private Timestamp statusChangeTime;

    @Column(name = "auth_number")
    private String authNumber;

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

    public void setAuthNumber(String authNumber){
        this.authNumber = authNumber;
    }

    public void setStatusToDeleted(){
        this.userStatus = UserStatusEnum.DELETED;
        this.statusChangeTime = new Timestamp(System.currentTimeMillis());
    }

    public void setStatusToChecked(){
        this.userStatus = UserStatusEnum.ACTIVE;
        this.statusChangeTime = new Timestamp(System.currentTimeMillis());
    }

    public void update(String newName, String newEmail, String newPassword, String newBio) {
        if (newName != null) this.name = newName;
        if (newEmail != null) this.email = newEmail;
        if (newPassword != null) this.password = newPassword;
        if (newBio != null) this.bio = newBio;
    }
}
