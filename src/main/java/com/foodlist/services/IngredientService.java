package com.foodlist.services;

import com.foodlist.models.Ingredient;
import com.foodlist.repositories.IngredientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public void addNew(Ingredient ingredient) {
        ingredientRepository.save(ingredient);
    }
}
