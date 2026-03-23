package com.example.springboot.exception.custom;

public class UsernameAlreadyExistsException extends RuntimeException{
    public UsernameAlreadyExistsException(){
        super("Username already exists");
    }

    public  UsernameAlreadyExistsException(String username){
        super("Username already exists: " + username);
    }
}
