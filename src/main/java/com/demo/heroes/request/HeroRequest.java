package com.demo.heroes.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class HeroRequest {

    @NotEmpty
    private String name;



}
