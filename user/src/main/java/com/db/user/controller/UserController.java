package com.db.user.controller;

import com.db.user.exception.UserException;
import com.db.user.model.User;
import com.db.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidKeyException;
import java.util.Collections;
import java.util.Map;

@RestController()
public class UserController {

    @Autowired
    UserService userService;

    /**
     * API to add a new User.
     *
     * @param user
     * @return
     */
    @PostMapping("user/add")
    private ResponseEntity<HttpStatus> addUser(@RequestBody User user) throws UserException {

        userService.addUser(user);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    /**
     * API to authenticate user using username and password
     * Returns JWT token upon successful authentication.
     *
     * @return user object.
     */
    @PostMapping("user/authenticate")
    private ResponseEntity<Map<String, String>> getUserToken(@RequestBody Map<String, String> input) {

        // Return user if user exists.
        String token = userService.authenticateUser(input.get("userName"), input.get("password"));
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }

    /**
     * API to validate user token and extract user role.
     *
     * @return user object.
     */
    @PostMapping("user/validate")
    private ResponseEntity<String> validateToken(@RequestBody String token) throws InvalidKeyException {
        String role = userService.validateUserToken(token);
        return ResponseEntity.ok(role);

    }

}
