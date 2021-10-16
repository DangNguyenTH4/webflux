package com.example.webflux.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Builder
@Data
@Table
public class Member implements Persistable<String> {

  @Id private String id;

  private String name;


  @Override
  @Transient
  public boolean isNew() {
    return this.id == null;
  }

  @Override
  public String getId() {
    return this.id;
  }

  public Member setNew(){
    this.id = UUID.randomUUID().toString();
    return this;
  }
}
