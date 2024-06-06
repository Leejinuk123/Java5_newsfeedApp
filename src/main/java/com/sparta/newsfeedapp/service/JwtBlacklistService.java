package com.sparta.newsfeedapp.Service;

import com.sparta.newsfeedapp.entity.BlacklistedToken;
import com.sparta.newsfeedapp.repository.BlacklistedTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class JwtBlacklistService {

    private BlacklistedTokenRepository blacklistedTokenRepository;

    public void blacklistToken(String token, LocalDateTime expirationDate) {
        blacklistedTokenRepository.save(new BlacklistedToken(token, expirationDate));
    }

    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokenRepository.existsByToken(token);
    }
}
