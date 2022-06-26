package com.demo.heroes;

import com.demo.heroes.domain.Hero;
import com.demo.heroes.repository.HeroRepository;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HeroInitializer implements CommandLineRunner {

    private final HeroRepository heroRepository;

    public HeroInitializer(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }

    @Override
    public void run(String... args) {

        log.info("Creando datos");
        Faker faker = new Faker();
        for (int i = 0; i < 10; i++) {
            Hero hero = new Hero();
            hero.setName(faker.superhero().name());
            heroRepository.save(hero);
        }
        log.info("Fin creación datos");
    }
}
