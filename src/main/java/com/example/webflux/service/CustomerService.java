package com.example.webflux.service;

import com.example.webflux.controller.CustomerController;
import com.example.webflux.model.Customer;
import com.example.webflux.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
  @Autowired private CustomerRepository customerRepository;

  public List<Customer> loadAllCustomer() {
    long start = System.currentTimeMillis();
    List<Customer> result = customerRepository.getCustomers();
    long end = System.currentTimeMillis();
    System.out.println("Total execution time: " + (end - start));
    return result;
  }

  public Flux<Customer> loadStreamCustomer() {
    long start = System.currentTimeMillis();
    Flux<Customer> result = customerRepository.getStreamCustomers();
    long end = System.currentTimeMillis();
    System.out.println("Total execution time: " + (end - start));
    return result;
  }

  public Flux<Customer> loadStreamCustomerDefault() {
    long start = System.currentTimeMillis();
    Flux<Customer> result =
        Flux.fromStream(customerRepository.getDefault().stream())
            .doOnNext(i -> System.out.println(i))
            .delayElements(Duration.ofMillis(200));
    long end = System.currentTimeMillis();
    System.out.println("Total execution time: " + (end - start));
    return result;
  }
}
