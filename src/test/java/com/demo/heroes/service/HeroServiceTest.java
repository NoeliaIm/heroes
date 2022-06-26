package com.demo.heroes.service;

import com.demo.heroes.domain.Hero;
import com.demo.heroes.repository.HeroRepository;
import com.demo.heroes.request.HeroRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class HeroServiceTest {


    private MockMvc mockMvc;

    @InjectMocks
    private HeroService heroService;


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
        Hero hero = createHero(heroRequest);
        when(heroRepository.save(hero)).thenReturn(hero);
        Long resultado = heroService.insertNewHero(heroRequest);
        assertEquals(hero.getId(), resultado);
        assertEquals(heroRequest.getName(), hero.getName());
    }

    @Test
    public void testUpdateHero() {
        HeroRequest heroRequest = new HeroRequest();
        Hero hero = createHero(heroRequest);
        when(heroRepository.findById(1L)).thenReturn(Optional.of(hero));
        HeroRequest newHeroRequest = new HeroRequest();
        newHeroRequest.setName("MANOLITO EL FUERTE");
        heroService.updateHero(1L, newHeroRequest);
        verify(heroRepository).save(hero);
    }

    @Test
    public void testDeleteHero() {
        HeroRequest heroRequest = new HeroRequest();
        heroRequest.setName("Manolito el fuerte");
        Hero hero = createHero(heroRequest);
        heroService.deleteHero(hero.getId());
        verify(heroRepository).delete(hero);
    }

    private Hero createHero(HeroRequest heroRequest) {
        Hero hero = new Hero();
        hero.setName(heroRequest.getName());
        hero.setId(1L);
        return hero;
    }
}
