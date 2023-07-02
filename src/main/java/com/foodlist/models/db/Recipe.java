package com.foodlist.models.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "recipe", schema = "public")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private UUID uuid;
    @Column(name = "recipe_text")
    private String recipeText;

    public Recipe(UUID uuid, String recipeText) {
        this.uuid = uuid;
        this.recipeText = recipeText;
    }

}
