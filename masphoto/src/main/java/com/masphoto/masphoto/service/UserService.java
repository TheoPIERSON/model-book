package com.masphoto.masphoto.service;


import com.masphoto.masphoto.entities.User;
import com.masphoto.masphoto.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static boolean isEmailValid(String email) {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        return Pattern.matches(regex, email);
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User addCustomer(User user) {

        return userRepository.save(user);
    }

}