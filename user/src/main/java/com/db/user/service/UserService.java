package com.db.user.service;


import com.db.user.exception.UserNotFoundException;
import com.db.user.model.User;
import com.db.user.enums.UserRole;
import com.db.user.repository.UserRepository;
import com.db.user.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    TokenProvider tokenProvider;

    /**
     * Add a new user.
     * @param user user.
     * @return added user object.
     */
    public User addUser(User user) {
        //encrypt user password.
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (!(UserRole.BUYER).equals(user.getRole()) && !(UserRole.SELLER).equals(user.getRole())) {
            throw new IllegalArgumentException("Invalid user role");
        }
      return  userRepository.save(user);
    }

    /**
     * Get the user information using userName.
     * @param userName userName.
     * @return User object.
     */
    public Optional<User> fetchUserByName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public String authenticateUser(String username, String password) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (passwordEncoder.matches(password, user.getPassword())) {
            return tokenProvider.createToken(user.getUserName(), String.valueOf(user.getRole()));
        } else {
            throw new BadCredentialsException("Invalid username/password");
        }
    }

}
