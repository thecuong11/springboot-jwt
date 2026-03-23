package com.example.springboot.exception.custom;

    public class RoleNotFoundException extends RuntimeException{
    public RoleNotFoundException(){
        super("Role not found");
    }

    public RoleNotFoundException(String role){
        super("Role not found: " + role);
    }
}
