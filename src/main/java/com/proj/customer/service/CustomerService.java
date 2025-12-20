package com.proj.customer.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.proj.customer.dao.AutomobileDao;
import com.proj.customer.dao.CustomerDao;
import com.proj.customer.dto.CreateAutomobileRequest;
import com.proj.customer.dto.CreateUpdateResponse;
import com.proj.customer.dto.CustomerDetailsResponse;
import com.proj.customer.dto.CustomerRegistrationRequest;
import com.proj.customer.dto.LoginRequest;
import com.proj.customer.dto.LoginResponse;
import com.proj.customer.dto.SuccessResponseMessage;
import com.proj.customer.exception.CustomerAlreadyExistsException;
import com.proj.customer.exception.DataNotFoundException;
import com.proj.customer.exception.IncorrectEmailOrPasswordException;
import com.proj.customer.model.Automobile;
import com.proj.customer.model.Customer;
import com.proj.customer.model.PreviousInsurance;
import com.proj.customer.util.JwtUtil;

import jakarta.validation.Valid;

// import lombok.RequiredArgsConstructor;

@Service
// @RequiredArgsConstructor // constructior injection using annotation
public class CustomerService {

    @Autowired
    CustomerDao customerDao;

    @Autowired
    AutomobileDao automobileDao;

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
        customer.setFullName(request.getFullName());
        customer.setDob(request.getDob());
        customer.setCity(request.getCity());
        customer.setState(request.getState());
        customer.setCountry(request.getCountry());
        customer.setZipCode(request.getZipCode());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());

        // Encrypt password
        customer.setPassword(passwordEncoder.encode(request.getPassword()));

        // Save to DB
        customerDao.save(customer);

        return new SuccessResponseMessage("Customer registered successfully!");
    }

    public Customer getCustomerById(int customerId) {
        return customerDao.findById(customerId).orElseThrow(() -> new DataNotFoundException("Invalid Customer ID"));
    }

    public CreateUpdateResponse createAutomobile(CreateAutomobileRequest requestBody) {
        Automobile automobile = new Automobile();
        automobile.setCustomerId(requestBody.getCustomerId());
        automobile.setMade(requestBody.getMade());
        automobile.setMake(requestBody.getMade());
        automobile.setYear(requestBody.getYear());
        if (requestBody.getPreviousInsurance() != null) {
            automobile.setPreviousInsurance(requestBody.getPreviousInsurance());
        }

        // 3. Save the Entity (not the requestBody)
        Automobile savedAuto = automobileDao.save(automobile);

        // 4. Return a proper response
        return new CreateUpdateResponse(savedAuto.getId(), "Automobile registered successfully");
    }

    public CustomerDetailsResponse getCustomerFullDetails(Integer customerId) {
        // 1. Fetch the Customer
        Customer customer = customerDao.findById(customerId)
                .orElseThrow(() -> new DataNotFoundException("Invalid Customer ID"));

        // 2. Fetch all automobiles belonging to this customer
        List<Automobile> automobiles = automobileDao.findByCustomerId(customerId);

        // 3. Wrap them in the DTO and return
        return new CustomerDetailsResponse(customer, automobiles);
    }

}
