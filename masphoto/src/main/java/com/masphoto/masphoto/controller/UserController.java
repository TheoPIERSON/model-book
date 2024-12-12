package com.masphoto.masphoto.controller;

import com.masphoto.masphoto.dto.AuthenticationDto;
import com.masphoto.masphoto.entities.User;
import com.masphoto.masphoto.service.JwtService;
import com.masphoto.masphoto.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;

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
    @PostMapping("/activate")
    public ResponseEntity<User> activateUser(@RequestBody Map<String, String> activation){
        User newUser = userService.activateCustomer(activation);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
    @PostMapping("/connexion")
    public Map<String, String>connexion(@RequestBody AuthenticationDto authenticationDto){
        final Authentication authenticate =  authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationDto.username(), authenticationDto.password())
        );
        if (authenticate.isAuthenticated()){
            System.out.print("authenticate ok");
            return this.jwtService.generate(authenticationDto.username());
        }
        return null;
    }
}
