package com.example.webflux.controller;

import com.example.webflux.model.Customer;
import com.example.webflux.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/v1/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<Customer> getCustomers(){
        return customerService.loadAllCustomer();
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Customer> getStreamCustomers(){
        return customerService.loadStreamCustomer();
    }
    @GetMapping(value = "/default", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Customer> getStreamCustomersDefault(){
        return customerService.loadStreamCustomerDefault();
    }



}
