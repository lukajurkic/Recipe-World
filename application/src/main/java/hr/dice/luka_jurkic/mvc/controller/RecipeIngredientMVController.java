package hr.dice.luka_jurkic.mvc.controller;

import hr.dice.luka_jurkic.rest.dto.RecipeIngredientDTO;
import hr.dice.luka_jurkic.rest.request.RecipeIngredientRequest;
import hr.dice.luka_jurkic.service.IngredientService;
import hr.dice.luka_jurkic.service.RecipeIngredientService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("recipe/ingredients")
public class RecipeIngredientMVController {

    private final RecipeIngredientService recipeIngredientService;
    private final IngredientService ingredientService;

    public RecipeIngredientMVController(RecipeIngredientService recipeIngredientService, IngredientService ingredientService) {
        this.recipeIngredientService = recipeIngredientService;
        this.ingredientService = ingredientService;
    }

    @GetMapping("/all")
    public String getAllRecipeIngredients(@RequestParam(name = "recipeName")String recipeName, Model model){
        model.addAttribute("recipeName", recipeName);
        model.addAttribute("ingredients", recipeIngredientService.getRecipeIngredients(recipeName));
        return "recipe_ingredient/list_recipeIngredients";
    }

    @PostMapping("/delete")
    public String deleteRecipeIngredient(@RequestParam(name = "recipeName") String recipeName,
                                         @RequestParam(name = "recipeIngredientId") Long recipeIngredientId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        recipeIngredientService.deleteRecipeIngredient(authentication.getName(), recipeName, recipeIngredientId);
        return getAllRecipeIngredients(recipeName, model);
    }

    @GetMapping("/update")
    public String updateRecipeIngredientForm(@RequestParam(name = "recipeName") String recipeName,
                                             @RequestParam(name = "recipeIngredientId") Long recipeIngredientId, Model model) {
        RecipeIngredientDTO ingredient = recipeIngredientService.getRecipeIngredient(recipeIngredientId);
        RecipeIngredientRequest request = new RecipeIngredientRequest();
        request.setIngredientName(ingredient.getIngredientName());
        request.setUnit(ingredient.getUnit());
        request.setAmount(ingredient.getAmount());
        model.addAttribute("request", request);
        model.addAttribute("recipeName", recipeName);
        model.addAttribute("units", ingredientService.getRecipeIngredientUnits());
        model.addAttribute("id", recipeIngredientId);
        return "recipe_ingredient/form_updateRecipeIngredient";
    }

    @PostMapping("/update")
    public String updateRecipeIngredient(@RequestParam(name = "recipeName")String recipeName,
                                         @RequestParam(name = "recipeIngredientId") Long recipeIngredientId,
                                         @Valid @ModelAttribute(name = "request") RecipeIngredientRequest request,
                                         BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("recipeName", recipeName);
            model.addAttribute("units",  ingredientService.getRecipeIngredientUnits());
            model.addAttribute("id", recipeIngredientId);
            return "recipe_ingredient/form_updateRecipeIngredient";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        recipeIngredientService.updateRecipeIngredient(authentication.getName(), recipeName, recipeIngredientId, request);
        return getAllRecipeIngredients(recipeName, model);
    }
}
