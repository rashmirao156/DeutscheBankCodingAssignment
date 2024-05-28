package com.db.user.service;


import com.db.user.enums.UserRole;
import com.db.user.exception.UserException;
import com.db.user.exception.UserNotFoundException;
import com.db.user.model.User;
import com.db.user.repository.UserRepository;
import com.db.user.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.security.InvalidKeyException;

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
     *
     * @param user user.
     * @return added user object.
     */
    public User addUser(User user) throws UserException  {
        //encrypt user password.

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            if (userRepository.findByUserName(user.getUserName()).isPresent()) {
                throw new IllegalArgumentException("Username already present, Please try another username.");
            }
            if (!(UserRole.BUYER).equals(user.getRole()) && !(UserRole.SELLER).equals(user.getRole())) {
                throw new IllegalArgumentException("Invalid user role");
            }
            return userRepository.save(user);
    }

    /**
     * authenticate a new user using username and password.
     *
     * @param username user name
     * @param password login password.
     * @return message.
     */
    public String authenticateUser(String username, String password) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (passwordEncoder.matches(password, user.getPassword())) {
            return tokenProvider.createToken(user.getUserName(), String.valueOf(user.getRole()));
        } else {
            throw new BadCredentialsException("Invalid username/password");
        }
    }

    /**
     * check supplied for validity and return role if the token is valid.
     *
     * @param token token
     * @return role.
     * @throws InvalidKeyException e
     */
    public String validateUserToken(final String token) throws InvalidKeyException {

        if (tokenProvider.validateToken(token)) {
            return tokenProvider.getRole(token);
        }
        ;
        return null;
    }

}
