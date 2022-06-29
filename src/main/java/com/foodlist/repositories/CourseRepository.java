package com.foodlist.repositories;

import com.foodlist.models.Course;
import com.foodlist.models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    Optional<Course> findByName(String name);

    List<Course> findByIngredients_NameIn(List<String> ingredientNames);

    @Query("""
            SELECT c FROM Course c 
            WHERE c NOT IN 
            (SELECT c FROM Course c JOIN c.ingredients i WHERE i.name IN :ingredients)
            """)
    List<Course> findByIngredientsExceptionFilter(@Param("ingredients") List<String> ingredients);
}
