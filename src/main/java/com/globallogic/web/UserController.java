package com.globallogic.web;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.globallogic.model.SuccessResponse;
import com.globallogic.model.User;
import com.globallogic.service.UserService;
import com.globallogic.web.exception.DataValidationException;
import com.globallogic.web.exception.UserNotFoundException;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public Iterable<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User findOne(@PathVariable String id) {
        try {
            UUID uuid = UUID.fromString(id);
            return userService.findById(uuid);
        } catch (Exception e) {
            throw new UserNotFoundException();
        }
        

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse create(@RequestBody User user, @RequestHeader HttpHeaders headers) {
        try {
            return userService.create(user, headers.get("Authorization").get(0));
        } catch (Exception e) {
            if (e instanceof DataValidationException) {
                throw e;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
            }            
        }
    }


}
