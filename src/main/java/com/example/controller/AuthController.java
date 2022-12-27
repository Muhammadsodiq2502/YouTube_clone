package com.example.controller;

import com.example.dto.auth.AuthRegistrationDTO;
import com.example.service.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@Valid @RequestBody AuthRegistrationDTO dto){
        log.info("Registration --> " + dto);
        String response = authService.registration(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/verification/email/{jtwToken}")
    public ResponseEntity<String> emailVerification(@PathVariable("jtwToken") String jwt) {
        log.info("Verification --> " + jwt);
        String response = authService.verification(jwt);
        return ResponseEntity.ok(response);
    }


}
