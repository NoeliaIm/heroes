package com.demo.heroes.service;

import com.demo.heroes.domain.Hero;
import com.demo.heroes.repository.HeroRepository;
import com.demo.heroes.request.HeroRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@ComponentScan
public class HeroService {

    private final HeroRepository heroRepository;

    @Autowired
    public HeroService(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }


    public Long insertNewHero(HeroRequest heroRequest) {
        Hero hero = new Hero();
        hero.setName(heroRequest.getName());
        hero = heroRepository.save(hero);
        return hero.getId();
    }

    public Hero updateHero(Long id, HeroRequest heroRequest) {
       Optional<Hero> heroDB = heroRepository.findById(id);

       if(heroDB.isEmpty()){
           throw new IllegalArgumentException("No existe el héroe con id: " + id);
       }

       Hero heroUpdate = heroDB.get();
       heroUpdate.setName(heroRequest.getName());
         return heroRepository.save(heroUpdate);
    }

    public void deleteHero(Long id) {
        heroRepository.deleteById(id);
    }

    public Hero getHero(Long id)  {
        return heroRepository.findById(id).orElse(null);
    }

    public List<Hero> getAllHeroes() {

        return  heroRepository.findAll();
    }


    public List<Hero> getHeroesByNameLike(String name) {
        return heroRepository.findByNameContaining(name);
    }
}
