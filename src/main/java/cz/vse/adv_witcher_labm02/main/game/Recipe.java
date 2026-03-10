package cz.vse.adv_witcher_labm02.main.game;

import java.util.*;
/**
 * Třída spravující recepty pro kombinování předmětů.
 * Umožňuje definovat kombinace předmětů s libovolným počtem ingrediencí
 * a zjistit výsledek kombinace.
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-12
 */

public class Recipe {
    private final String result; // The resulting item of the recipe
    private final Set<String> ingredients; // Ingredients needed to create the item

    /**
     * Constructs a recipe with a result and its required ingredients.
     *
     * @param result      the resulting item name
     * @param ingredients the list of ingredients needed
     */
    public Recipe(String result, List<String> ingredients) {
        this.result = result;
        this.ingredients = new HashSet<>(ingredients);
    }

    /**
     * Gets the result of the recipe.
     *
     * @return the result item name
     */
    public String getResult() {
        return result;
    }

    /**
     * Gets the list of ingredients for the recipe.
     *
     * @return a list of ingredient names
     */
    public List<String> getIngredients() {
        return new ArrayList<>(ingredients);
    }

    /**
     * Checks if the given list of ingredients matches this recipe.
     *
     * @param providedIngredients the ingredients to check
     * @return true if they match, false otherwise
     */
    public boolean matches(Set<String> providedIngredients) {
        Set<String> lowerCaseProvided = new HashSet<>();
        providedIngredients.forEach(ingredient -> lowerCaseProvided.add(ingredient.toLowerCase()));

        Set<String> lowerCaseRecipe = new HashSet<>();
        ingredients.forEach(ingredient -> lowerCaseRecipe.add(ingredient.toLowerCase()));

        return lowerCaseRecipe.equals(lowerCaseProvided);
    }

    /**
     * Static method to retrieve the result of a recipe from a map of recipes.
     *
     * @param recipesMap          the map of recipes
     * @param providedIngredients the ingredients to check
     * @return the resulting item name, or null if no match is found
     */
    public static String getResult(Map<String, Recipe> recipesMap, Set<String> providedIngredients) {
        for (Recipe recipe : recipesMap.values()) {
            if (recipe.matches(providedIngredients)) {
                return recipe.getResult();
            }
        }
        return null;
    }

    /**
     * Static method to list all recipes in a readable format.
     *
     * @param recipesMap the map of recipes
     */
    public static void listRecipes(Map<String, Recipe> recipesMap) {
        if (recipesMap.isEmpty()) {
            System.out.println("No recipes have been defined.");
        } else {
            System.out.println("Defined recipes:");
            recipesMap.forEach((key, recipe) -> {
                System.out.println(" - Combine " + String.join(", ", recipe.getIngredients()) + " to create " + recipe.getResult());
            });
        }
    }
}
