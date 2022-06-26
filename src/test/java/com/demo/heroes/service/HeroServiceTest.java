package com.demo.heroes.service;

import com.demo.heroes.HeroInitializer;
import com.demo.heroes.domain.Hero;
import com.demo.heroes.repository.HeroRepository;
import com.demo.heroes.request.HeroRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class HeroServiceTest {


    private MockMvc mockMvc;

    @Autowired
    private HeroService heroService;

    @MockBean
    private HeroInitializer heroInitializer;

    @MockBean
    private HeroRepository heroRepository;

    @Autowired
    private WebApplicationContext wac;


    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new HeroService(heroRepository)).build();
    }

    @Test
    public void testInsertNewHero() {
        HeroRequest heroRequest = new HeroRequest();
        heroRequest.setName("Manolito el fuerte");
        Optional<Hero> heroDB = Optional.of(createHero(heroRequest));
        when(heroRepository.findById(anyLong())).thenReturn(heroDB);
        when(heroRepository.save(any(Hero.class))).thenReturn(heroDB.get());

        assert heroService.getHero(1L).getName().equals(heroRequest.getName());
    }

    @Test
    public void testUpdateHero() {
        HeroRequest heroRequest = new HeroRequest();
        heroRequest.setName("Manolito el fuerte");
        doNothing().when(heroInitializer).run();
        Hero hero = createHeroWithoutId(heroRequest);
        Optional<Hero> heroDB = Optional.of(hero);
        when(heroRepository.findById(hero.getId())).thenReturn(heroDB);
        when(heroRepository.save(hero)).thenReturn(hero);
        Long id =heroDB.get().getId();
        hero.setName("Manolito el fuerte 2");
        HeroRequest heroRequest2 = new HeroRequest();
        heroRequest2.setName(hero.getName());
        Hero heroUpdated =  heroService.updateHero(id, heroRequest2);
        assertEquals("Manolito el fuerte 2", heroUpdated.getName());
    }

    @Test
    public void testDeleteHero() {
        HeroRequest heroRequest = new HeroRequest();
        heroRequest.setName("Manolito el fuerte");
        doNothing().when(heroInitializer).run();
        Hero hero = createHero(heroRequest);
        heroService.deleteHero(hero.getId());
        assertNull(heroService.getHero(hero.getId()));
    }

    private Hero createHero(HeroRequest heroRequest) {
        Hero hero = new Hero();
        hero.setName(heroRequest.getName());
        hero.setId(1L);
        return hero;
    }

    private Hero createHeroWithoutId(HeroRequest heroRequest) {
        Hero hero = new Hero();
        hero.setName(heroRequest.getName());
        return hero;
    }
}
