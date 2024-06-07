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
    
    //양방향 매핑
    @OneToMany(mappedBy = "user")
    private List<Comment> commentList;

    @Column(name = "userId", nullable = false, unique = true)
    private String userId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false, unique = true)
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
    }
    public void update(String newName, String newEmail, String newPassword, String newBio) {
        if (newName != null) this.name = newName;
        if (newEmail != null) this.email = newEmail;
        if (newPassword != null) this.password = newPassword;
        if (newBio != null) this.bio = newBio;
    }
}
