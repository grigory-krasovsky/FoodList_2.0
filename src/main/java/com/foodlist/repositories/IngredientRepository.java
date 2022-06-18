package com.foodlist.repositories;

import com.foodlist.models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository <Ingredient, Integer> {

}
