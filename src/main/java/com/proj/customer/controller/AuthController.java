package com.proj.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proj.customer.dto.CustomerRegistrationRequest;
import com.proj.customer.dto.LoginRequest;
import com.proj.customer.dto.LoginResponse;
import com.proj.customer.dto.SuccessResponseMessage;
import com.proj.customer.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/login") // always start paths with /
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        // Get token from service
        LoginResponse response = customerService.login(request);

        // Return as ResponseEntity
        return ResponseEntity.ok(response);
    }

    @PostMapping("register")
    public ResponseEntity<SuccessResponseMessage> register(@Valid @RequestBody CustomerRegistrationRequest request) {
        SuccessResponseMessage message = customerService.registerCustomer(request);
        return ResponseEntity.ok(message);
    }
}
