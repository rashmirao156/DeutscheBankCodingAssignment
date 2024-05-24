package com.db.user.controller;

import com.db.user.exception.UserNotFoundException;
import com.db.user.model.User;
import com.db.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private ResponseEntity<HttpStatus> addUser(@RequestBody User user) {
        userService.addUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * API to get user details using user name.
     *
     * @param userName userName
     * @return user object.
     */
    @GetMapping("user/{userName}")
    private User getUser(@PathVariable String userName) {

        // Return user if user exists.
        return userService.fetchUserByName(userName).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

}
