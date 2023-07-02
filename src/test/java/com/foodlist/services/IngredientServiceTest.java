package com.foodlist.services;

import com.foodlist.models.db.Ingredient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootTest
class IngredientServiceTest {

    @Autowired
    IngredientService ingredientService;

    @Test
    public void verifyAddingNew() {
        Ingredient ingredient = new Ingredient(UUID.randomUUID(), "test ingredient", Timestamp.valueOf(LocalDateTime.now()));
        ingredientService.addNew(ingredient);
    }

}