package com.example.webflux.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AdminRequest {
    @NotNull(message = "Name not null")
    @NotBlank(message = "Not allow blank")
    private String name;

}
