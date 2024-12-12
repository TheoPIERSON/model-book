package com.masphoto.masphoto.service;


import com.masphoto.masphoto.entities.Role;
import com.masphoto.masphoto.entities.User;
import com.masphoto.masphoto.repositories.UserRepository;
import com.masphoto.masphoto.validation.ValidationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.regex.Pattern;

@AllArgsConstructor
@Service
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private ValidationService validationService;

    private static boolean isEmailValid(String email) {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        return Pattern.matches(regex, email);
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User addUser (User user) {
        if (!isEmailValid(user.getEmail())) {
            throw new RuntimeException("Votre email n'est pas valide");
        }

        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            throw new RuntimeException("Cet email est déjà utilisé");
        }
        String cryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(cryptedPassword);
        Role userRole = new Role();
        userRole.setName("USER");
        user.setRole(userRole);
        validationService.register(user);

        return userRepository.save(user);

    }

}