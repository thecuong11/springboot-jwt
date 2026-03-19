package com.example.springboot.controller;

import com.example.springboot.dto.UserDTO;
import com.example.springboot.entity.User;
import com.example.springboot.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class Controller {
    private final UserService userService;

    //Cóntroctor Injection
    public Controller(UserService userService) {
        this.userService = userService;
    }

    //Create
    @PostMapping
    public User create(@RequestBody UserDTO dto) {
        return userService.create(dto);
    }

    //Get All
    @GetMapping("/list")
    public List<User> getAll() {
        return userService.getAll();
    }

    //Get by id
    @GetMapping("/findone/{id}")
    public User getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    //Delete
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    //Update
    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody UserDTO dto) {
        return userService.update(id, dto);
    }
}
