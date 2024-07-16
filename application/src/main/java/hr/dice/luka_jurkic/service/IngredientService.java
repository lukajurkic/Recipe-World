package hr.dice.luka_jurkic.service;

import hr.dice.luka_jurkic.rest.dto.ImportedIngredientDTO;
import hr.dice.luka_jurkic.rest.dto.IngredientDTO;
import hr.dice.luka_jurkic.rest.request.IngredientRequest;

import java.util.List;

/**
 * Service interface for managing ingredients
 */
public interface IngredientService {

    /**
     * Returns ingredient with ingredient id
     * @param ingredientId unique identifier for each ingredient
     * @return Ingredient Data Transfer Object
     */
    IngredientDTO getIngredient(Long ingredientId);

    /**
     * Returns list of all ingredients that match ingredient name
     * @param ingredientName closely describes ingredient, doesn't has to be unique
     * @return List of Ingredient Data Transfer Objects
     */
    IngredientDTO getIngredientByName(String ingredientName);

    /**
     * Returns list of all ingredient that match ingredient category
     * @param category puts ingredient in one of major categories
     * @return List of Ingredient Data Transfer Objects
     */
    List<IngredientDTO> getIngredientsByCategory(String category);

    /**
     * Returns list of all ingredients from datasource
     * @return List of Ingredient Data Transfer Objects
     */
    List<IngredientDTO> getIngredients();

    /**
     * Returns all ingredients available in database
     * @return List of Strings
     */
    List<String> getIngredientCategories();

    /**
     * Return list of all possible units for ingredient
     * @return List of Strings
     */
    List<String> getRecipeIngredientUnits();

    /**
     * Creates new ingredient with information from request
     * @param request gives information for creating ingredient
     * @return Ingredient Data Transfer Object
     */
    IngredientDTO createIngredient(IngredientRequest request);

    /**
     * Creates all ingredients that doesn't exist
     * @param importedIngredients list with ingredients to be created
     * @return List of Ingredient Data Transfer Objects
     */
    List<IngredientDTO> createIngredients(List<ImportedIngredientDTO> importedIngredients);

    /**
     * Deletes ingredient with matching ingredient id
     * @param ingredientId unique identifier for each ingredient
     */
    void deleteIngredient(Long ingredientId);

    /**
     * Changes ingredient with given ingredient id to match data from request
     * @param ingredientId unique identifier for each ingredient
     * @param request gives information for updating ingredient
     * @return Ingredient Data Transfer Object
     */
    IngredientDTO updateIngredient(Long ingredientId, IngredientRequest request);

    /**
     * Returns true is ingredient name is unique in database
     * @param ingredientName name to be checked
     * @param category category for given name to be checked
     * @return true/false
     */
    boolean isUnique(String ingredientName, String category);
}
