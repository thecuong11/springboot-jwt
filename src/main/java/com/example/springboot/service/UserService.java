package com.example.springboot.service;

import com.example.springboot.dto.UserDTO;
import com.example.springboot.entity.User;
import com.example.springboot.exception.NotFoundException;
import com.example.springboot.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Create
    public User create(UserDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        return userRepository.save(user);
    }

    //Read all
    public List<User> getAll() {
        return userRepository.findAll();
    }

    //Read one
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found with id = " + id));
    }

    //Delete
    public void delete(Long id) {
        userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found with id = " + id));
        userRepository.deleteById(id);
    }

    //Update
    public User update(Long id, UserDTO dto) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found with id = " + id));
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        return userRepository.save(user);
    }
}
