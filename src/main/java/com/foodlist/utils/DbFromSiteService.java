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

    public void addCoursesToDb(Integer firstPage, Integer latestPage) {
        List<List<String>> listOfPagesWithRecipeUrls = getRecipesUrls(firstPage, latestPage);
        List<String> recipeUrls = listOfPagesWithRecipeUrls.stream().flatMap(Collection::stream).collect(Collectors.toList());
        List<ParsedRecipe> parsedRecipes = recipeUrls.stream().map(this::parseRecipeUrl).collect(Collectors.toList());

        List<Ingredient> ingredients = new ArrayList<>();
        List<Recipe> recipes = new ArrayList<>();
        List<Course> courses = new ArrayList<>();

        for (ParsedRecipe parsedRecipe : parsedRecipes) {
            ingredients.addAll(parsedRecipe.ingredients);
            Recipe recipe = new Recipe(UUID.randomUUID(), parsedRecipe.recipeText);
            recipes.add(recipe);
            courses.add(new Course(UUID.randomUUID(), parsedRecipe.recipeTitle(), parsedRecipe.ingredients, recipe));
        }

        resolveDuplicateIngredients(ingredients);
        System.out.println("finish");

    }

    /**
     * Get urls to recipes which will be subsequently retrieved and put to db
     * @param firstPage the page where the recipes start collecting from
     * @param latestPage the page where the recipes end collecting
     * @return
     */
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
            return null;
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
                ingredients.stream().map(x -> new Ingredient(UUID.randomUUID(), x.text())).collect(Collectors.toList()),
                recipe.toString());
    }

    private record ParsedRecipe(UUID id, String recipeTitle, List<Ingredient> ingredients, String recipeText){

    }

    /**
     * Filter ingredient list to remove all duplicates
     * @param ingredients
     * @return
     */
    //Todo probably need to generify it to make it work with all entities that have names
    private List<Ingredient> resolveDuplicateIngredients(List<Ingredient> ingredients){

        List<Ingredient> existingIngredients = ingredientRepository.findAll();
        Set<String> seenIngredients = existingIngredients.stream().map(Ingredient::getName).collect(Collectors.toSet());

        ingredients.removeIf(ingredient -> !seenIngredients.add(ingredient.getName()));

        return ingredients;

    }
}
