package hr.dice.luka_jurkic.rest.controller;

import hr.dice.luka_jurkic.rest.dto.RecipeDTO;
import hr.dice.luka_jurkic.rest.request.RecipeRequest;
import hr.dice.luka_jurkic.service.RecipeService;
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
@RequestMapping(path = "/api/recipes")
@Validated
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/id/{recipeId}")
    public RecipeDTO getRecipe(@PathVariable Long recipeId) {
        return recipeService.getRecipe(recipeId);
    }

    @GetMapping
    public List<RecipeDTO> getRecipes() {
        return recipeService.getRecipes();
    }

    @GetMapping("/{recipeName}")
    public RecipeDTO getRecipeByName(@PathVariable String recipeName) {
        return recipeService.getRecipeByName(recipeName);
    }

    @GetMapping("/user/{username}")
    public List<RecipeDTO> getRecipesByUser(@PathVariable String username) {
        return recipeService.getRecipesByUser(username);
    }

    @PostMapping
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public RecipeDTO createRecipe(Authentication authentication, @Valid @RequestBody RecipeRequest recipeRequest) {
        return recipeService.createRecipe(authentication.getName(), recipeRequest);
    }

    @DeleteMapping("/id/{recipeId}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public void deleteRecipe(Authentication authentication, @PathVariable Long recipeId) {
        recipeService.deleteRecipe(authentication.getName(), recipeId);
    }

    @DeleteMapping("/{recipeName}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public void deleteRecipeByName(Authentication authentication, @PathVariable String recipeName) {
        recipeService.deleteRecipeByName(authentication.getName(), recipeName);
    }

    @PutMapping("/id/{recipeId}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public RecipeDTO updateRecipe(Authentication authentication, @PathVariable Long recipeId, @Valid @RequestBody RecipeRequest recipeRequest) {
        return recipeService.updateRecipe(authentication.getName(), recipeId, recipeRequest);
    }

    @PutMapping("/{recipeName}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public RecipeDTO updateRecipeByName(Authentication authentication, @PathVariable String recipeName, @Valid @RequestBody RecipeRequest recipeRequest) {
        return recipeService.updateRecipeByName(authentication.getName(), recipeName, recipeRequest);
    }

}
