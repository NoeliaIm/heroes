package com.demo.heroes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class HeroesApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext app =  SpringApplication.run(HeroesApplication.class, args);

        Arrays.stream(app.getBeanDefinitionNames()).forEach(System.out::println);
    }

}
