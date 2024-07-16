package hr.dice.luka_jurkic.service;

import hr.dice.luka_jurkic.rest.dto.ImportedIngredientDTO;
import hr.dice.luka_jurkic.rest.dto.RecipeIngredientDTO;
import hr.dice.luka_jurkic.rest.request.RecipeIngredientRequest;

import java.util.List;

/**
 * Service interface for managing ingredients for each recipe
 */
public interface RecipeIngredientService {

    /**
     * Returns one recipe ingredient with its details
     * @param recipeIngredientId unique identifier for each recipe ingredient
     * @return Recipe Ingredient Data Transfer Object
     */
    RecipeIngredientDTO getRecipeIngredient(Long recipeIngredientId);

    /**
     * Returns list of all ingredients that are associated with recipe which has recipe name
     * @param recipeName unique identifier for each recipe that is set by the user
     * @return List of Recipe Ingredient Data Transfer Objects
     */
    List<RecipeIngredientDTO> getRecipeIngredients(String recipeName);

    /**
     * Returns newly added ingredient to recipe with given recipe name
     * @param username unique identifier for each user
     * @param recipeName unique identifier for each recipe
     * @param recipeIngredientRequest information for adding ingredient to recipe
     * @return Recipe Ingredient Data Transfer Object
     */
    RecipeIngredientDTO createRecipeIngredient(String username, String recipeName, RecipeIngredientRequest recipeIngredientRequest);

    /**
     * Returns list of newly add ingredients in recipe with recipe name
     * @param username unique identifier for each user
     * @param recipeName unique identifier for each recipe
     * @param importedIngredients list of ingredients to be added
     * @return List of Recipe Ingredient Data Transfer Objects
     */
    List<RecipeIngredientDTO> createRecipeIngredients(String username, String recipeName, List<ImportedIngredientDTO> importedIngredients);

    /**
     * Deletes recipe ingredient with given recipe ingredient id
     * @param username unique identifier for each user
     * @param recipeName unique identifier for each recipe that is set by the user
     * @param id unique identifier for each recipe ingredient
     */
    void deleteRecipeIngredient(String username, String recipeName, Long id);

    /**
     * Deletes all recipe ingredients that are connected to recipe with given recipe name
     * @param username unique identifier for each user
     * @param recipeName unique identifier for each recipe
     */
    void deleteRecipeIngredientByRecipe(String username, String recipeName);

    /**
     * Returns updated ingredient with given ingredient name for recipe with given recipe name base on recipe ingredient request
     * @param username unique identifier for each user
     * @param recipeName unique identifier for each recipe
     * @param id unique identifier for each recipe ingredient
     * @param recipeIngredientRequest information for updating recipe ingredient
     * @return Recipe Ingredient Data Transfer Object
     */
    RecipeIngredientDTO updateRecipeIngredient(String username, String recipeName, Long id, RecipeIngredientRequest recipeIngredientRequest);
}
