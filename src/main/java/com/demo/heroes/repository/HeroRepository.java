package com.demo.heroes.repository;

import com.demo.heroes.domain.Hero;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@ComponentScan
public interface HeroRepository extends JpaRepository<Hero, Long> {
   List<Hero> findByNameContaining(String name);
}
