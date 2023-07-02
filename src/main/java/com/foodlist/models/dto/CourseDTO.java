package com.foodlist.models.dto;

import com.foodlist.models.db.Course;
import com.foodlist.models.db.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class CourseDTO {
    private String name;
    private String uuid;
    private List<String> ingredients;
    private String recipe;
}
