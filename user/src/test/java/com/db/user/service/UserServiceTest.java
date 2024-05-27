package com.db.user.service;


import com.db.user.model.User;
import com.db.user.repository.UserRepository;
import com.db.user.security.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.InvalidKeyException;
import java.util.Optional;

import static com.db.user.enums.UserRole.BUYER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    TokenProvider tokenProvider;
    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addUserTest() {
        User user = new User();
        user.setUserName("testUser");
        user.setRole(BUYER);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(passwordEncoder.encode(any())).thenReturn("hjjghk");
        final User savedUser = userService.addUser(user);
        assertEquals("testUser", savedUser.getUserName());
    }

    @Test
    public void addUserTestInvalidUser() {
        User user = new User();
        user.setUserName("testUser");
        user.setRole(null);
        when(passwordEncoder.encode(any())).thenReturn("hjjgk");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.addUser(user);
        });
        assertEquals("Invalid user role", exception.getMessage());
    }

    @Test
    public void authenticateUserTest() {
        User user = new User();
        user.setId(1L);
        user.setPassword("validpwd");
        when(userRepository.findByUserName("user")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        when(tokenProvider.createToken(any(), any())).thenReturn("token");
        String token = userService.authenticateUser("user", "validpwd");
        assertEquals("token", token);

    }

    @Test
    public void authenticateUserInvalidPassword() {
        User user = new User();
        user.setId(1L);
        user.setPassword("validpwd");
        when(userRepository.findByUserName("user")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(any(), any())).thenReturn(false);
        assertThrows(BadCredentialsException.class,
                () -> userService.authenticateUser("user", "validpwd"));
    }

    @Test
    public void validateTokenTest() throws InvalidKeyException {
        when(tokenProvider.validateToken(any())).thenReturn(true);
        when(tokenProvider.getRole(any())).thenReturn("tokenvalue");
        assertEquals("tokenvalue", userService.validateUserToken("token"));

    }
}
