package com.masphoto.masphoto.controller;

import com.masphoto.masphoto.entities.User;
import com.masphoto.masphoto.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers (){
        List<User> users = (List<User>) userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @PostMapping(path = "/add-user")
    public ResponseEntity<User> addUser(@RequestBody User user){
        User newUser = userService.addUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
