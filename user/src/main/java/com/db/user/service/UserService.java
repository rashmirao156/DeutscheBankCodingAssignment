package com.db.user.service;


import com.db.user.model.User;
import com.db.user.enums.UserRole;
import com.db.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserService {

    @Autowired
    UserRepository userRepository;

    /**
     * Add a new user.
     * @param user user.
     * @return added user object.
     */
    public User addUser(User user) {
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

}
