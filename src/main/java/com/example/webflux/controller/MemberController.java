package com.example.webflux.controller;

import com.example.webflux.model.Member;
import com.example.webflux.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Persistable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping(value = "/v1/member")
@RequiredArgsConstructor
public class MemberController {
  private final MemberRepository memberRepository;

  @GetMapping
  public Flux<Member> getAll() {
    return memberRepository.findAll();
  }

  @GetMapping(value = "/{name}")
  public Mono<Member> getOne(@PathVariable String name) {

    Mono<Member> result = memberRepository.findByName(name);

    result.flatMap(member ->{
      if(member == null){
        Member a = Member.builder().name(name).build();
        Mono<Member> result1 = memberRepository.save(a);
        a.setNew();
        System.out.println(result1.subscribe());
        return Mono.just(result1);
        }else{return Mono.just(result);}
    });
    return result;
  }

  @PostMapping(value = "")
  public Mono<Member> createOne(Member member) {
    return memberRepository.save(member);
  }
}
