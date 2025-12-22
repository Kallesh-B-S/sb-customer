package com.proj.customer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proj.customer.dto.CreateAutomobileRequest;
import com.proj.customer.dto.CreateUpdateResponse;
import com.proj.customer.dto.CustomerDetailsResponse;
import com.proj.customer.dto.CustomerWrapper;
import com.proj.customer.model.Automobile;
import com.proj.customer.model.Customer;
import com.proj.customer.service.CustomerService;
import com.proj.customer.util.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    JwtUtil jwtUtil;

    @GetMapping
    public List<Customer> getAllCustomer(HttpServletRequest request
    // ,@RequestHeader("X-customer-email") String customerEmail
    ) {
        // String authHeader = request.getHeader("Authorization");
        // String token = authHeader.substring(7);
        // Claims claims = jwtUtil.extractAllClaims(token);

        // System.out.println("-----Customer Email => " + customerEmail);

        // System.out.println("--------------- auth header start");
        // System.out.println(authHeader.substring(7));
        // System.out.println("--------------- auth header end");

        // System.out.println("--------- all claims ------------");
        // System.out.println(claims);
        // // {sub=john@example.com, customerId=1, iat=1766127929, exp=1766129729}
        // System.out.println("--------all claims end --------------");

        return customerService.getAllCustomers();
    }

    @GetMapping("{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") Integer customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/email/{id}")
    public ResponseEntity<CustomerWrapper> getCustomerByEmail(@PathVariable("id") String email) {
        CustomerWrapper customer = customerService.getCustomerByEmail(email);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/{id}/automobile")
    public ResponseEntity<CustomerDetailsResponse> getCustomerFullDetails(@PathVariable("id") Integer customerId) {
        CustomerDetailsResponse response = customerService.getCustomerFullDetails(customerId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("automobile")
    public ResponseEntity<CreateUpdateResponse> createAutomobile(
            @Valid @RequestBody CreateAutomobileRequest requestBody) {
        CreateUpdateResponse response = customerService.createAutomobile(requestBody);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/automobile/{id}")
    public ResponseEntity<Automobile> getAutomobileById(@Valid @PathVariable("id") Integer automobileId) {
        Automobile response = customerService.getAutomobileById(automobileId);
        return ResponseEntity.ok(response);
    }
}
