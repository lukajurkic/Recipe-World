package hr.dice.luka_jurkic.service;

import hr.dice.luka_jurkic.rest.dto.IngredientDetailDTO;
import hr.dice.luka_jurkic.rest.dto.RecipeDetailDTO;
import hr.dice.luka_jurkic.rest.request.ImageRequest;

/**
 * Service for managing images for recipe and ingredient
 */
public interface ImageService {

    /**
     * Returns recipe with image data for given recipe id
     * @param recipeId unique identifier for each recipe
     * @return Recipe Detail Data Transfer Object
     */
    RecipeDetailDTO getRecipeWithImage(Long recipeId);

    /**
     * Return ingredient with image data for given ingredient id
     * @param ingredientId unique identifier for each ingredient
     * @return Ingredient Detail Data Transfer Object
     */
    IngredientDetailDTO getIngredientWithImage(Long ingredientId);

    /**
     * Returns updated recipe with uploaded image for given recipe id
     *
     * @param recipeId unique identifier for each recipe
     * @param request  information about image
     */
    void uploadRecipeImage(Long recipeId, ImageRequest request);

    /**
     * Returns update ingredient with uploaded image for given ingredient id
     *
     * @param ingredientId unique identifier for each ingredient
     * @param request      information about image
     */
    void uploadIngredientImage(Long ingredientId, ImageRequest request);

    /**
     * Deletes all images that are associated with recipe id
     * @param recipeId unique identifier for each recipe
     */
    void deleteRecipeImages(Long recipeId);

    /**
     * Deletes all images that are associated with ingredient id
     * @param ingredientId unique identifier for each ingredient
     */
    void deleteIngredientImages(Long ingredientId);

    /**
     * Returns true if image size is too large
     * @param request object in which is image data
     * @return true/false
     */
    boolean isTooLarge(ImageRequest request);

}
