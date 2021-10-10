package com.example.webflux.repository;

import com.example.webflux.model.Customer;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
public class CustomerRepository {
  private void sleep(int i) {
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public List<Customer> getCustomers() {
    Random r = new Random(1000);
    return IntStream.rangeClosed(1, 10)
        .peek(i -> sleep(i))
        .peek(i -> System.out.println("" +
                "Processing count: " + i))
        .mapToObj(
            i ->
                new Customer(
                    String.valueOf(i), "CustomerName" + r.nextInt() + i, "CustomerAddress" + i))
        .collect(Collectors.toList());
  }

  public Flux<Customer> getStreamCustomers() {
    System.out.println("Call get stream cus");
    SecureRandom r = new SecureRandom();
    return Flux.range(1, 10)
        .delayElements(Duration.ofMillis(500))
        .doOnNext(i -> System.out.println("Flux stream processing count: " + i))
        .map(
            i ->
                new Customer(
                    String.valueOf(i), "CustomerName" + r.nextInt() + i, "CustomerAddress" + i))
        .doOnNext(c -> System.out.println(c.toString()));
  }

  public List<Customer> getDefault(){
    SecureRandom r = new SecureRandom();
    return IntStream.rangeClosed(1, 10)
            .peek(i -> System.out.println("Processing count default: " + i))
            .mapToObj(
                    i ->
                            new Customer(
                                    String.valueOf(i), "CustomerName" + r.nextInt() + i, "CustomerAddress" + i))
            .collect(Collectors.toList());
  }
}
