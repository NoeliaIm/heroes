package com.demo.heroes.service;

import com.demo.heroes.domain.Hero;
import com.demo.heroes.repository.HeroRepository;
import com.demo.heroes.request.HeroRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@ComponentScan
public class HeroService {

    private final HeroRepository heroRepository;

    @Autowired
    public HeroService(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }


    public Long insertNewHero(HeroRequest heroRequest) {
        return null;
    }

    public Hero updateHero(Long id, HeroRequest heroRequest) {
        return  null;
    }

    public void deleteHero(Long id) {

    }

    public Hero getHero(Long id)  {
        return  null;
    }

    public List<Hero> getAllHeroes() {
        return null;
    }

}
