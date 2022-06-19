package com.foodlist.resources;

import com.foodlist.services.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class CourseResource {

    CourseService courseService;

    @GetMapping("/getCourseByIngredient/{requiredIngredients}")
    public void getCourseByIngredient(@PathVariable List<String> requiredIngredients){
        courseService.getCoursesByIngredientNames(requiredIngredients);
    }

}
