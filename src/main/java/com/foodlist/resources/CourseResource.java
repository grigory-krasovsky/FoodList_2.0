package com.foodlist.resources;

import com.foodlist.models.db.Course;
import com.foodlist.models.dto.CourseDTO;
import com.foodlist.services.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for courses
 */
@RestController
@AllArgsConstructor
@RequestMapping("/courses")
public class CourseResource {
    CourseService courseService;

    /**
     * Get all courses that have all of the specified ingredients
     */
    @GetMapping("/getCourseByIngredient/{requiredIngredients}")
    public void getCourseByIngredient(@PathVariable List<String> requiredIngredients) {
        courseService.getCoursesWithSpecificIngredients(requiredIngredients);
    }

    /**
     * Get all courses that do not have any of the specified ingredients
     */
    @GetMapping("/getCourseWithoutIngredient/{requiredIngredients}")
    public void getCourseWithoutIngredient(@PathVariable List<String> requiredIngredients) {
        courseService.getCoursesWithoutSpecificIngredients(requiredIngredients);
    }

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        return new ResponseEntity<>(courseService.getAll().stream().map(Course::toDto).collect(Collectors.toList()), HttpStatus.OK);
    }
}
