package com.idocrew.weddingwise.services.impl;

import com.idocrew.weddingwise.entity.SecureToken;
import com.idocrew.weddingwise.entity.User;
import com.idocrew.weddingwise.repositories.SecureTokenRepository;
import com.idocrew.weddingwise.services.SecureTokenService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
public class SecureTokenServiceImpl implements SecureTokenService {

    private static final BytesKeyGenerator DEFAULT_TOKEN_GENERATOR = KeyGenerators.secureRandom(15);
    private static final Charset US_ASCII = StandardCharsets.US_ASCII;

    @Value("${secure.token.validity}")
    private int tokenValidityInSeconds;

    @Autowired
    SecureTokenRepository secureTokenRepository;

    @Override
    public SecureToken createSecureToken(User user){
        String tokenValue = new String(Base64.encodeBase64URLSafeString(DEFAULT_TOKEN_GENERATOR.generateKey()).getBytes(), US_ASCII);
        SecureToken secureToken = new SecureToken();
        secureToken.setUser(user);
        secureToken.setToken(tokenValue);
        secureToken.setExpiresAt(LocalDateTime.now().plusSeconds(getTokenValidityInSeconds()));
        this.saveSecureToken(secureToken);
        return secureToken;
    }

    @Override
    public void saveSecureToken(SecureToken token) {
        secureTokenRepository.save(token);
    }

    @Override
    public SecureToken findByToken(String token) {
        return secureTokenRepository.findByToken(token);
    }

    @Override
    public void removeToken(SecureToken token) {
        secureTokenRepository.delete(token);
    }

    @Override
    public void removeTokenByToken(String token) {
        secureTokenRepository.removeByToken(token);
    }

    public int getTokenValidityInSeconds() {
        return tokenValidityInSeconds;
    }
}
