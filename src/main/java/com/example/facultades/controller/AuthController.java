package com.example.facultades.controller;


import com.example.facultades.dto.AuthLoguinRequestDTO;
import com.example.facultades.dto.AuthLoguinResponseDTO;
import com.example.facultades.service.UserDetailsServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private UserDetailsServiceImp userDetails;

    @PostMapping("/password")
    public ResponseEntity<AuthLoguinResponseDTO> loguin(@RequestBody AuthLoguinRequestDTO authLoguinRequestDTO){
        return ResponseEntity.ok(userDetails.loguin(authLoguinRequestDTO));
    }

    @GetMapping("/login")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public void  loguinOauth(){
    }
}
