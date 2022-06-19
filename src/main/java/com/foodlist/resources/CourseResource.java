package com.foodlist.resources;

import com.foodlist.services.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for courses
 */
@RestController
@AllArgsConstructor
public class CourseResource {
    CourseService courseService;

    /**
     * Get all courses that have all of the specified ingredients
     */
    @GetMapping("/getCourseByIngredient/{requiredIngredients}")
    public void getCourseByIngredient(@PathVariable List<String> requiredIngredients){
        courseService.getCoursesWithSpecificIngredients(requiredIngredients);
    }

    /**
     * Get all courses that do not have any of the specified ingredients
     */
    @GetMapping("/getCourseWithoutIngredient/{requiredIngredients}")
    public void getCourseWithoutIngredient(@PathVariable List<String> requiredIngredients){
        courseService.getCoursesWithoutSpecificIngredients(requiredIngredients);
    }

}
