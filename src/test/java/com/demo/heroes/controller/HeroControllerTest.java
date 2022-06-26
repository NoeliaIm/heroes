package com.demo.heroes.controller;


import com.demo.heroes.domain.Hero;
import com.demo.heroes.request.HeroRequest;
import com.demo.heroes.service.HeroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class HeroControllerTest {


    private  MockMvc mockMvc;


    @MockBean
    private HeroService heroService;

    @Captor
    private ArgumentCaptor<HeroRequest> argumentCaptor;

    @Autowired
    private WebApplicationContext wac;


    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new HeroController(heroService)).build();
    }

    @Test
    public void postForInsertNewHeroShouldCreateANewHeroInDatabase() throws Exception {
        HeroRequest heroRequest = new HeroRequest();
        heroRequest.setName("Manolito el fuerte");
        when(heroService.insertNewHero(argumentCaptor.capture())).thenReturn(1L);


        mockMvc.perform(post("/api/heroes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(heroRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "http://localhost/api/heroes/1"));

        assertThat(argumentCaptor.getValue().getName(), is("Manolito el fuerte"));

    }

    @Test
    public void updateHeroShouldUpdateHeroInDatabase() throws Exception {
        HeroRequest heroRequest = new HeroRequest();
        heroRequest.setName("Súper Manolito el fuerte");
        when(heroService.updateHero(eq(1L), argumentCaptor.capture())).thenReturn(createHero(1L, "Súper Manolito el fuerte"));

        mockMvc.perform(put("/api/heroes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(heroRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Súper Manolito el fuerte")))
                .andExpect(jsonPath("$.id", is(1)));
        assertEquals(argumentCaptor.getValue().getName(), "Súper Manolito el fuerte");
    }


    @Test
    public void postForInsertNewHeroShouldReturnBadRequestWhenHeroRequestIsNull() throws Exception {
        mockMvc.perform(post("/api/heroes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(null)))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void postForInsertNewHeroShouldReturnBadRequestWhenHeroRequestIsEmpty() throws Exception {
        mockMvc.perform(post("/api/heroes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString("")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postForInsertNewHeroShouldReturnBadRequestWhenHeroRequestIsInvalid() throws Exception {
        mockMvc.perform(post("/api/heroes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString("{}")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void allShouldReturnAllHeroes() throws Exception {
        mockMvc.perform(get("/api/heroes"))
                .andExpect(status().isOk());
    }


    @Test
    public void getShouldReturnHeroById() throws Exception {

        when(heroService.getHero(1L)).thenReturn(createHero(1L, "Manolito el fuerte"));

        mockMvc.perform(get("/api/heroes/1"))
                .andExpect(status().isOk())
                                   .andExpect(content().contentType( "application/json"))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Manolito el fuerte")));
    }

    @Test
    public void getAllHeroesShouldReturnAListOfHeroes() throws Exception {
       when(heroService.getAllHeroes()).thenReturn(List.of(createHero(1L, "Manolito el fuerte"), createHero(2L, "Manolón el fuertón")));

            mockMvc.perform(get("/api/heroes"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType( "application/json"))
                    .andExpect(jsonPath("$.[0].id", is(1)))
                    .andExpect(jsonPath("$.[0].name", is("Manolito el fuerte")))
                    .andExpect(jsonPath("$.[1].id", is(2)))
                    .andExpect(jsonPath("$.[1].name", is("Manolón el fuertón")))
                    .andExpect(jsonPath("$", hasSize(2)));
    }


    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Hero createHero(Long id, String name) {
        Hero hero = new Hero();
        hero.setName(name);
        hero.setId(id);
        return hero;
    }
}
