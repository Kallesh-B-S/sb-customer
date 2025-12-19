package com.proj.customer.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.proj.customer.dao.CustomerDao;
import com.proj.customer.dto.CustomerRegistrationRequest;
import com.proj.customer.dto.LoginRequest;
import com.proj.customer.dto.LoginResponse;
import com.proj.customer.dto.SuccessResponseMessage;
import com.proj.customer.exception.CustomerAlreadyExistsException;
import com.proj.customer.exception.IncorrectEmailOrPasswordException;
import com.proj.customer.model.Customer;
import com.proj.customer.util.JwtUtil;

// import lombok.RequiredArgsConstructor;

@Service
// @RequiredArgsConstructor // constructior injection using annotation
public class CustomerService {

    @Autowired
    CustomerDao customerDao;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder;

    // Manual constructor injection
    public CustomerService(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public List<Customer> getAllCustomers() {
        return customerDao.findAll();
    }

    public LoginResponse login(LoginRequest request) {
        Customer customer = customerDao.findByEmail(request.getEmail())
                .orElseThrow(() -> new IncorrectEmailOrPasswordException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), customer.getPassword())) {
            throw new IncorrectEmailOrPasswordException("Invalid email or password");
        }

        return new LoginResponse(jwtUtil.generateToken(customer.getEmail(), customer.getId()));
    }

    public SuccessResponseMessage registerCustomer(CustomerRegistrationRequest request) {

        // Check if email already exists
        if (customerDao.findByEmail(request.getEmail()).isPresent()) {
            throw new CustomerAlreadyExistsException("Customer with email " + request.getEmail() + " already exists");
        }

        // Map DTO to entity
        Customer customer = new Customer();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setDob(request.getDob());
        customer.setAddressLine1(request.getAddressLine1());
        customer.setAddressLine2(request.getAddressLine2()); // optional
        customer.setCity(request.getCity());
        customer.setState(request.getState());
        customer.setCountry(request.getCountry());
        customer.setZipCode(request.getZipCode());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());

        // Encrypt password
        customer.setPassword(passwordEncoder.encode(request.getPassword()));

        // Set created timestamp
        customer.setCreatedAt(LocalDateTime.now());

        // Save to DB
        customerDao.save(customer);

        return new SuccessResponseMessage("Customer registered successfully!");
    }

}
