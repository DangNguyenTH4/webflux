package com.example.webflux.repository;

import com.example.webflux.model.Member;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

public interface MemberRepository extends R2dbcRepository<Member, String> {
    Mono<Member> findByName(String name);
}
