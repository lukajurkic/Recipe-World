package hr.dice.luka_jurkic.rest.controller;

import hr.dice.luka_jurkic.rest.dto.IngredientDTO;
import hr.dice.luka_jurkic.rest.request.IngredientRequest;
import hr.dice.luka_jurkic.service.IngredientService;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
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
@RequestMapping(path = "/api/ingredients")
@Validated
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/{ingredientId}")
    public IngredientDTO getIngredient(@PathVariable Long ingredientId) {
        return ingredientService.getIngredient(ingredientId);
    }

    @GetMapping("/category/{ingredientCategory}")
    public List<IngredientDTO> getIngredientsByCategory(@PathVariable String ingredientCategory) {
        return ingredientService.getIngredientsByCategory(ingredientCategory);
    }

    @GetMapping("/name/{ingredientName}")
    public IngredientDTO getIngredientByName(@PathVariable String ingredientName) {
        return ingredientService.getIngredientByName(ingredientName);
    }

    @GetMapping
    public List<IngredientDTO> getIngredients() {
        return ingredientService.getIngredients();
    }

    @PostMapping
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public IngredientDTO createIngredient(@Valid @RequestBody IngredientRequest request) {
        return ingredientService.createIngredient(request);
    }

    @DeleteMapping("/{ingredientId}")
    @Secured("ROLE_ADMIN")
    public void deleteIngredient(@PathVariable Long ingredientId) {
        ingredientService.deleteIngredient(ingredientId);
    }

    @PutMapping("/{ingredientId}")
    @Secured("ROLE_ADMIN")
    public IngredientDTO updateIngredient(@PathVariable Long ingredientId, @Valid @RequestBody IngredientRequest request) {
        return ingredientService.updateIngredient(ingredientId, request);
    }
}
