package com.demo.heroes.controller;

import com.demo.heroes.domain.Hero;
import com.demo.heroes.request.HeroRequest;
import com.demo.heroes.service.HeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/heroes")
public class HeroController {

    private final HeroService heroService;

    @Autowired
    public HeroController(HeroService heroService) {
        this.heroService = heroService;
    }


    @PostMapping
    public ResponseEntity  <Void> createANewHero(@Valid @RequestBody HeroRequest heroRequest, UriComponentsBuilder uriComponentsBuilder){
        Long id =  heroService.insertNewHero(heroRequest);
        UriComponents uriComponents = uriComponentsBuilder.path("/api/heroes/{id}").buildAndExpand(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponents.toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Hero> getHero(@PathVariable("id") Long id){
        return ResponseEntity.ok(heroService.getHero(id));
    }

    @GetMapping
    public ResponseEntity<List<Hero>> getAllHeroes() {
        return ResponseEntity.ok(heroService.getAllHeroes());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hero> updateHero(@PathVariable("id") Long id, @Valid @RequestBody HeroRequest heroRequest) {

        return ResponseEntity.ok(heroService.updateHero(id, heroRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHero(@PathVariable("id") Long id) {
        heroService.deleteHero(id);
        return ResponseEntity.ok().build();
    }

}
