package com.foodlist.services;

import com.foodlist.models.Course;
import com.foodlist.models.Ingredient;
import com.foodlist.repositories.CourseRepository;
import com.foodlist.repositories.IngredientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseService {
    CourseRepository courseRepository;
    IngredientRepository ingredientRepository;

    /**
     * Filter courses by the list of required ingredients: the course must have all specified ingredients
     * @param ingredientNames
     * @return
     */
    public List<Course> getCoursesByIngredientNames(List<String> ingredientNames){
        List<Course> withAtLeastOneIngredient = courseRepository.findByIngredients_NameIn(ingredientNames);
        List<Course> filteredCourses = withAtLeastOneIngredient.stream().filter(course -> course.getIngredients()
                .stream().map(Ingredient::getName).collect(Collectors.toList())
                .containsAll(ingredientNames)).collect(Collectors.toList());
        return filteredCourses;
    }
}
