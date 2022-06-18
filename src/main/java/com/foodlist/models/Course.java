package com.foodlist.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "course", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    UUID uuid;

    String name;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "course_ingredient",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    List<Ingredient> ingredients;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    Recipe recipe;

    public Course(UUID uuid, String name, List<Ingredient> ingredients, Recipe recipe) {
        this.uuid = uuid;
        this.name = name;
        this.ingredients = ingredients;
        this.recipe = recipe;
    }
}
