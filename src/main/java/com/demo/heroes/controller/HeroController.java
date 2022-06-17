package com.demo.heroes.controller;

import com.demo.heroes.service.HeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/heros")
public class HeroController {
    @Autowired
    private HeroService heroService;


}
