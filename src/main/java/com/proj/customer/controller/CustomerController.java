package com.proj.customer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proj.customer.model.Customer;
import com.proj.customer.service.CustomerService;
import com.proj.customer.util.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    JwtUtil jwtUtil;

    @GetMapping("all")
    public List<Customer> getAllCustomer(HttpServletRequest request
        // ,@RequestHeader("X-customer-email") String customerEmail
        ) {
        String authHeader = request.getHeader("Authorization");
        // System.out.println("-----Customer Email => " + customerEmail);

            // System.out.println("--------------- auth header start");
            // System.out.println(authHeader.substring(7));
            // System.out.println("--------------- auth header end");

            String token = authHeader.substring(7);
            Claims claims = jwtUtil.extractAllClaims(token);

            // System.out.println("--------- all claims ------------");
            // System.out.println(claims);
            // // {sub=john@example.com, customerId=1, iat=1766127929, exp=1766129729}
            // System.out.println("--------all claims end --------------");

        return customerService.getAllCustomers();
    }
}
