package hr.dice.luka_jurkic.service;

import hr.dice.luka_jurkic.rest.dto.ImportedRecipeDTO;
import hr.dice.luka_jurkic.rest.dto.RecipeDTO;
import hr.dice.luka_jurkic.rest.request.RecipeRequest;

import java.util.List;

/**
 * Service interface for managing recipes
 */
public interface RecipeService {

    /**
     * Returns recipe with recipe id
     * @param recipeId unique identifier for each recipe
     * @return Recipe Data Transfer Object
     */
    RecipeDTO getRecipe(Long recipeId);

    /**
     * Returns list of all recipes from datasource
     * @return List of Recipe Data Transfer Objects
     */
    List<RecipeDTO> getRecipes();

    /**
     * Returns recipe with recipe name
     * @param name unique identifier for each recipe that is set by the user
     * @return Recipe Data Transfer Object
     */
    RecipeDTO getRecipeByName(String name);

    /**
     * Returns list of recipes that are managed by user with username
     * @param username unique identifier for each user that is set by the user
     * @return List of Recipe Data Transfer Objects
     */
    List<RecipeDTO> getRecipesByUser(String username);

    /**
     * Returns newly create recipe base on recipe request
     * @param username unique identifier for each user
     * @param recipeRequest all information for creating recipe
     * @return Recipe Data Transfer Object
     */
    RecipeDTO createRecipe(String username, RecipeRequest recipeRequest);

    /**
     * Returns newly created recipe based on information from imported recipe
     * @param username unique identifier for each user
     * @param importedRecipe information about imported recipe
     * @return Recipe Data Transfer Object
     */
    RecipeDTO createRecipe(String username, ImportedRecipeDTO importedRecipe);

    /**
     * Deletes recipe with given recipe id
     * @param username unique identifier for each user
     * @param recipeId unique identifier for each recipe
     */
    void deleteRecipe(String username, Long recipeId);

    /**
     * Delete recipe with given recipe name
     * @param username unique identifier for each user
     * @param name unique identifier for each recipe that is set by the user
     */
    void deleteRecipeByName(String username, String name);

    /**
     * Deletes all recipes which are connected to user with given username
     * @param username unique identifier for each user
     */
    void deleteRecipesByUser(String username);

    /**
     * Returns updated recipe with given recipeId
     * @param username unique identifier for each user
     * @param recipeId unique identifier for each recipe
     * @param recipeRequest information for updating recipe
     * @return Recipe Data Transfer Object
     */
    RecipeDTO updateRecipe(String username, Long recipeId, RecipeRequest recipeRequest);

    /**
     * Returns updated recipe with given recipe name
     * @param username unique identifier for each user
     * @param name unique identifier for each recipe that is set by the user
     * @param recipeRequest information for updating recipe
     * @return Recipe Data Transfer Object
     */
    RecipeDTO updateRecipeByName(String username, String name, RecipeRequest recipeRequest);

    /**
     * Exports pdf that contains recipe information to root folder of application
     * @param recipeName unique identifier for each recipe
     * @return file name under which exported pdf is saved
     */
    String exportToPdf(String recipeName);

    /**
     * Fetches recipes with 'recipe name to import' from external api
     * @param recipeNameToImport name of recipe to be fetched, doesn't have to be unique
     * @return List of Imported Recipe Data Transfer Objects
     */
    List<ImportedRecipeDTO> importRecipes(String recipeNameToImport);

    /**
     * Returns true if given recipe name is unique in database
     * @param recipeName recipe name to be checked
     * @return true/false
     */
    boolean isUnique(String recipeName);
}
