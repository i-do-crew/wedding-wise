package com.idocrew.weddingwise.services;

import com.idocrew.weddingwise.entity.SecureToken;
import com.idocrew.weddingwise.entity.User;

public interface SecureTokenService {

    SecureToken createSecureToken(User user);
    void saveSecureToken(final SecureToken token);
    SecureToken findByToken(final String token);
    void removeToken(final SecureToken token);
    void removeTokenByToken(final String token);
}
