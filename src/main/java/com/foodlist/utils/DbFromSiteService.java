package com.foodlist.utils;

import com.foodlist.models.Course;
import com.foodlist.models.Ingredient;
import com.foodlist.models.Recipe;
import com.foodlist.repositories.CourseRepository;
import com.foodlist.repositories.IngredientRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Get Recipes from site
 */
@Service
@Slf4j
@AllArgsConstructor
public class DbFromSiteService {

    private final IngredientRepository ingredientRepository;
    private final CourseRepository courseRepository;

    /**
     * Add courses from https://www.gastronom.ru to db.
     * @param firstPage - the first page where we get courses from
     * @param latestPage - the last page where we get courses from
     */
    public void addCoursesToDb(Integer firstPage, Integer latestPage) {
        List<List<String>> listOfPagesWithRecipeUrls = getRecipesUrls(firstPage, latestPage);
        List<String> recipeUrls = listOfPagesWithRecipeUrls.stream().flatMap(Collection::stream).collect(Collectors.toList());
        List<ParsedRecipe> parsedRecipes = recipeUrls.stream().map(this::parseRecipeUrl).collect(Collectors.toList());
        List<Course> courses = new ArrayList<>();

        for (ParsedRecipe parsedRecipe : parsedRecipes) {
            Recipe recipe = new Recipe(null, UUID.randomUUID(), parsedRecipe.recipeText);
            courses.add(new Course(null, UUID.randomUUID(), parsedRecipe.recipeTitle(), parsedRecipe.ingredients, recipe));
        }
        //Todo make course.name unique
        courseRepository.saveAll(resolveDuplicateCourses(courses));

        System.out.println("finish");
    }

    private List<List<String>> getRecipesUrls(Integer firstPage, Integer latestPage) {
        List<List<String>> result = new ArrayList<>();
        String url = "https://www.gastronom.ru/search/type/recipe/?t=&veget=14&page=";
        for (int x = firstPage; x <= latestPage; x++) {
            String currentUrl = url + x;
            Document doc = getDocument(currentUrl);
            String cssClass = "material-anons__img-wrapp js-fix";
            String altCssClass = "material-anons__img-wrapp js-fix material-anons__no-recipe";
            List<String> recipeUrls = parseUrl(doc, cssClass, altCssClass).map(i -> ("https://www.gastronom.ru" + i.attributes().get("href"))).collect(Collectors.toList());
            result.add(recipeUrls);
        }
        return result;
    }

    private Document getDocument(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            log.info("Unable to connect: " + ioException.getMessage());
            throw null;
        }
    }

    private Stream<Element> parseUrl(Document doc, String cssClass, String altCssClass) {
        Elements result = doc.getElementsByClass(cssClass);
        if (result.size() == 0 && altCssClass != null) {
            result = doc.getElementsByClass(altCssClass);
        }
        return result.stream();
    }

    private ParsedRecipe parseRecipeUrl(String url) {
        Document doc = getDocument(url);
        List<Element> title = parseUrl(doc, "recipe__title", null).collect(Collectors.toList());
        List<Element> ingredients = parseUrl(doc, "recipe__ingredient", null).collect(Collectors.toList());
        List<Element> recipeTextInArray = parseUrl(doc, "recipe__step-text", null).collect(Collectors.toList());
        StringJoiner recipe = new StringJoiner(" ");
        recipeTextInArray.forEach(i -> recipe.add(i.text()));
        return new ParsedRecipe(
                UUID.randomUUID(),
                title.size() == 1 ? title.get(0).text() : null,
                ingredients.stream().map(x -> new Ingredient(null, UUID.randomUUID(), x.text())).collect(Collectors.toList()),
                recipe.toString());
    }

    private record ParsedRecipe(UUID id, String recipeTitle, List<Ingredient> ingredients, String recipeText){
    }

    /**
     * Filter ingredient list to remove all duplicates
     * @param courses
     * @return
     */
    //Todo probably need to generify it to make it work with all entities that have names
    private List<Course> resolveDuplicateCourses(List<Course> courses){

        List<Course> existingCourses = courseRepository.findAll();
        Set<String> seenIngredients = existingCourses.stream().map(Course::getName).collect(Collectors.toSet());

        courses.removeIf(ingredient -> !seenIngredients.add(ingredient.getName()));

        return courses;
    }
}
