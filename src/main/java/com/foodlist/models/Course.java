package com.foodlist.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "course", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    UUID uuid;

    String name;

    @ManyToMany
    @JoinTable(
            name = "course_ingredient",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    List<Ingredient> ingredients;

    @OneToOne
    Recipe recipe;

    public Course(UUID uuid, String name, List<Ingredient> ingredients, Recipe recipe) {
        this.uuid = uuid;
        this.name = name;
        this.ingredients = ingredients;
        this.recipe = recipe;
    }
}
