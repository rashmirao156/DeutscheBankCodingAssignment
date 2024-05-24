package com.db.user.service;


import com.db.user.enums.UserRole;
import com.db.user.model.User;
import com.db.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;



public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void addUserTest(){
        User user = new User();
        user.setUserName("testUser");
        user.setRole(UserRole.BUYER);
        when(userRepository.save(any(User.class))).thenReturn(user);
        User savedUser = userService.addUser(user);
        assertEquals("testUser", savedUser.getUserName());
    }

    @Test
    public void addUserTestInvalidUser(){
        User user = new User();
        user.setUserName("testUser");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.addUser(user);
        });
        assertEquals("Invalid user role", exception.getMessage());
    }
}
