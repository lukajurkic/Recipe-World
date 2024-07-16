
package hr.dice.luka_jurkic.rest.controller;

import hr.dice.luka_jurkic.rest.dto.RecipeIngredientDTO;
import hr.dice.luka_jurkic.rest.request.RecipeIngredientRequest;
import hr.dice.luka_jurkic.service.RecipeIngredientService;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/recipes/{recipeName}")
@Validated
public class RecipeIngredientController {

    private final RecipeIngredientService recipeIngredientService;

    public RecipeIngredientController(RecipeIngredientService recipeIngredientService) {
        this.recipeIngredientService = recipeIngredientService;
    }

    @GetMapping("/ingredients")
    public List<RecipeIngredientDTO> getRecipeIngredients(@PathVariable String recipeName) {
        return recipeIngredientService.getRecipeIngredients(recipeName);
    }

    @PostMapping("/ingredient")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public RecipeIngredientDTO createRecipeIngredient(Authentication authentication, @PathVariable String recipeName, @Valid @RequestBody RecipeIngredientRequest request) {
        return recipeIngredientService.createRecipeIngredient(authentication.getName(), recipeName, request);
    }

    @DeleteMapping("/ingredient/{recipeIngredientId}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public void deleteRecipeIngredient(Authentication authentication, @PathVariable String recipeName, @PathVariable Long recipeIngredientId) {
        recipeIngredientService.deleteRecipeIngredient(authentication.getName(), recipeName, recipeIngredientId);
    }

    @PutMapping("/ingredient/{recipeIngredientId}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public RecipeIngredientDTO updateRecipeIngredient(Authentication authentication, @PathVariable String recipeName, @PathVariable Long recipeIngredientId, @Valid @RequestBody RecipeIngredientRequest request) {
        return recipeIngredientService.updateRecipeIngredient(authentication.getName(), recipeName, recipeIngredientId, request);
    }
}
