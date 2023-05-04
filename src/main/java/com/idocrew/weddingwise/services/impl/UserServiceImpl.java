package com.idocrew.weddingwise.services.impl;

import com.idocrew.weddingwise.entity.Customer;
import com.idocrew.weddingwise.entity.User;
import com.idocrew.weddingwise.repositories.UserRepository;
import com.idocrew.weddingwise.services.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

@RequiredArgsConstructor
@Service("userService")
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EntityManager em;

    @Override
    public User findUserById(long id) {
        return userRepository.getReferenceById(id);
    }

    @Override
    public User findByUsername(String username) {
        String query = String.format("select u.* from users u where u.username='%s';", username);
        User user;
        try {
            user = (User) em.createNativeQuery(query, User.class).getSingleResult();
        } catch (NoResultException e) {
            throw new UsernameNotFoundException("No account found for " + username);
        }
        return user;
        //return userRepository.findByUsername(username);
    }

    @Override
    public User saveUser(User user) {
        userRepository.save(user);
        return user;
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public void editUserProfile(HttpServletRequest request, User userTemp) {
        User userEntity = (User) request.getSession().getAttribute("user");
        userEntity.setEmail(userTemp.getEmail());
        userEntity.setFirstName(userTemp.getFirstName());
        userEntity.setLastName(userTemp.getLastName());
        userEntity.setCity(userTemp.getCity());
        userEntity.setState(userTemp.getState());
        userRepository.save(userEntity);
    }
}
