package com.foodlist.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "recipe", schema = "public")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    UUID uuid;
    @Column(name = "recipe_text")
    String recipeText;

    public Recipe(UUID uuid, String recipeText) {
        this.uuid = uuid;
        this.recipeText = recipeText;
    }

}
