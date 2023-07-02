package com.foodlist.services;

import com.foodlist.models.db.Course;
import com.foodlist.models.db.Ingredient;
import com.foodlist.repositories.CourseRepository;
import com.foodlist.repositories.IngredientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseService {
    CourseRepository courseRepository;
    IngredientRepository ingredientRepository;

    /**
     * Filter courses by the list of required ingredient names: the course must have all specified ingredients
     * @param ingredientNames
     * @return
     */
    public List<Course> getCoursesWithSpecificIngredients(List<String> ingredientNames) {
        List<Course> withAtLeastOneIngredient = courseRepository.findByIngredients_NameIn(ingredientNames);
        return withAtLeastOneIngredient.stream().filter(course -> new HashSet<>(course.getIngredients()
                .stream().map(Ingredient::getName).toList())
                .containsAll(ingredientNames)).collect(Collectors.toList());
    }

    /**
     * Filter courses by the list of required ingredient names: the course must not have any of specified ingredients
     * @param ingredientNames
     * @return
     */
    public List<Course> getCoursesWithoutSpecificIngredients(List<String> ingredientNames) {
        List<Ingredient> ingredients = ingredientRepository.findByNameIn(ingredientNames);
        return courseRepository.findByIngredientsExceptionFilter(ingredientNames);
    }

    public List<Course> getAll() {
        List<Course> all = courseRepository.findAll();
        return all;
    }
}
