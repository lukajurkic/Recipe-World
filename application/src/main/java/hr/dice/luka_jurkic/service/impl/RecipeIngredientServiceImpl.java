package hr.dice.luka_jurkic.service.impl;

import hr.dice.luka_jurkic.mapper.RecipeIngredientMapper;
import hr.dice.luka_jurkic.persistence.entity.IngredientEntity;
import hr.dice.luka_jurkic.persistence.entity.IngredientUnit;
import hr.dice.luka_jurkic.persistence.entity.RecipeEntity;
import hr.dice.luka_jurkic.persistence.entity.RecipeIngredientEntity;
import hr.dice.luka_jurkic.persistence.repository.IngredientRepository;
import hr.dice.luka_jurkic.persistence.repository.RecipeIngredientRepository;
import hr.dice.luka_jurkic.persistence.repository.RecipeRepository;
import hr.dice.luka_jurkic.rest.dto.ImportedIngredientDTO;
import hr.dice.luka_jurkic.rest.dto.RecipeIngredientDTO;
import hr.dice.luka_jurkic.rest.request.RecipeIngredientRequest;
import hr.dice.luka_jurkic.service.RecipeIngredientService;
import hr.dice.luka_jurkic.service.exceptions.IngredientServiceException;
import hr.dice.luka_jurkic.service.exceptions.RecipeIngredientServiceException;
import hr.dice.luka_jurkic.service.exceptions.RecipeServiceException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RecipeIngredientServiceImpl implements RecipeIngredientService {

    private final RecipeIngredientRepository recipeIngredientRepository;
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeIngredientMapper recipeIngredientMapper;

    public RecipeIngredientServiceImpl(RecipeIngredientRepository recipeIngredientRepository, RecipeRepository recipeRepository,
                                       IngredientRepository ingredientRepository, RecipeIngredientMapper recipeIngredientMapper) {
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.recipeIngredientMapper = recipeIngredientMapper;
    }

    @Override
    public RecipeIngredientDTO getRecipeIngredient(Long recipeIngredientId) {
        return recipeIngredientMapper.toDto(fetchRecipeIngredient(recipeIngredientId));
    }

    @Override
    public List<RecipeIngredientDTO> getRecipeIngredients(String recipeName) {
        return recipeIngredientMapper.toDto(recipeIngredientRepository.findAllByRecipeName(recipeName));
    }

    @Override
    public RecipeIngredientDTO createRecipeIngredient(String username, String recipeName, RecipeIngredientRequest recipeIngredientRequest) {
        RecipeIngredientEntity recipeIngredient = new RecipeIngredientEntity(
                recipeIngredientRequest.getAmount(),
                IngredientUnit.valueOf(recipeIngredientRequest.getUnit()),
                fetchRecipe(recipeName),
                fetchIngredient(recipeIngredientRequest.getIngredientName())
        );
        return recipeIngredientMapper.toDto(recipeIngredientRepository.save(recipeIngredient));
    }

    @Override
    public List<RecipeIngredientDTO> createRecipeIngredients(String username, String recipeName, List<ImportedIngredientDTO> importedIngredients) {
        List<RecipeIngredientDTO> recipeIngredients = new ArrayList<>();
        for(ImportedIngredientDTO importedIngredient : importedIngredients) {
            RecipeIngredientRequest request = new RecipeIngredientRequest();
            request.setIngredientName(importedIngredient.getName());
            request.setUnit(importedIngredient.getUnit().toUpperCase());
            request.setAmount(BigDecimal.valueOf(Double.parseDouble(importedIngredient.getAmount())));
            recipeIngredients.add(createRecipeIngredient(username, recipeName, request));
        }
        return recipeIngredients;
    }

    @Override
    public void deleteRecipeIngredient(String username, String recipeName, Long id) {
        validateRecipeOwnership(username, recipeName);
        recipeIngredientRepository.delete(fetchRecipeIngredient(id));
    }

    @Override
    public void deleteRecipeIngredientByRecipe(String username, String recipeName) {
        validateRecipeOwnership(username, recipeName);
        recipeIngredientRepository.deleteAllByRecipeName(recipeName);
    }

    @Override
    public RecipeIngredientDTO updateRecipeIngredient(String username, String recipeName, Long id, RecipeIngredientRequest recipeIngredientRequest) {
        RecipeIngredientEntity recipeIngredient = fetchRecipeIngredient(id);
        validateRecipeOwnership(username, recipeName);
        recipeIngredient.setRecipe(fetchRecipe(recipeName));
        recipeIngredient.setIngredient(fetchIngredient(recipeIngredientRequest.getIngredientName()));
        recipeIngredient.setUnit(IngredientUnit.valueOf(recipeIngredientRequest.getUnit()));
        recipeIngredient.setAmount(recipeIngredientRequest.getAmount());
        return recipeIngredientMapper.toDto(recipeIngredientRepository.save(recipeIngredient));
    }

    private void validateRecipeOwnership(String username, String recipeName) {
        recipeRepository.findByNameAndUserUsername(recipeName, username).orElseThrow(RecipeServiceException::accessDenied);
    }

    private RecipeEntity fetchRecipe(String recipeName) {
        return recipeRepository.findByName(recipeName).orElseThrow(RecipeServiceException::notFound);
    }

    private IngredientEntity fetchIngredient(String ingredientName) {
        return ingredientRepository.findByName(ingredientName).orElseThrow(IngredientServiceException::notFound);
    }

    private RecipeIngredientEntity fetchRecipeIngredient(Long id) {
        return recipeIngredientRepository.findById(id).orElseThrow(RecipeIngredientServiceException::notFound);

    }
}
