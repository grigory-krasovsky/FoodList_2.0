package com.foodlist.repositories;

import com.foodlist.models.Course;
import com.foodlist.models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    Optional<Course> findByName(String name);

    List<Course> findByIngredients_NameIn(List<String> ingredientNames);

    List<Course> findByIngredientsNotIn(List<Ingredient> ingredientNames);
}
