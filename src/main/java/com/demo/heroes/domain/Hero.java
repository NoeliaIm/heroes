package com.demo.heroes.domain;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Hero {
    @Id
    @GeneratedValue
    private Long id;

   @Column(nullable = false)
    private String name;

    @OneToMany
    @ToString.Exclude
    private Set<Film> films;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Hero hero = (Hero) o;
        return id != null && Objects.equals(id, hero.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
