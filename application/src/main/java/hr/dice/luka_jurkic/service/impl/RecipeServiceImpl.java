package hr.dice.luka_jurkic.service.impl;

import hr.dice.luka_jurkic.client.RecipeClient;
import hr.dice.luka_jurkic.mapper.RecipeMapper;
import hr.dice.luka_jurkic.mapper.RecipeResourceMapper;
import hr.dice.luka_jurkic.persistence.entity.RecipeEntity;
import hr.dice.luka_jurkic.persistence.entity.UserEntity;
import hr.dice.luka_jurkic.persistence.repository.RecipeRepository;
import hr.dice.luka_jurkic.persistence.repository.UserRepository;
import hr.dice.luka_jurkic.rest.dto.ImportedRecipeDTO;
import hr.dice.luka_jurkic.rest.dto.RecipeDTO;
import hr.dice.luka_jurkic.rest.request.RecipeRequest;
import hr.dice.luka_jurkic.rest.resources.RecipeResource;
import hr.dice.luka_jurkic.service.IngredientService;
import hr.dice.luka_jurkic.service.RecipeIngredientService;
import hr.dice.luka_jurkic.service.RecipeService;
import hr.dice.luka_jurkic.service.StepService;
import hr.dice.luka_jurkic.service.exceptions.RecipeServiceException;
import hr.dice.luka_jurkic.service.exceptions.UserServiceException;
import hr.dice.luka_jurkic.utils.PdfUtils;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final RecipeClient recipeClient;
    private final RecipeIngredientService recipeIngredientService;
    private final IngredientService ingredientService;
    private final StepService stepService;

    private final RecipeMapper recipeMapper;
    private final RecipeResourceMapper resourceMapper;

    private final PdfUtils pdfUtils;

    private final String X_API_KEY = "B8ZrA+frC/5zZ6Fv7Ixggw==P74TWfnreR7HyZND";

    public RecipeServiceImpl(RecipeRepository recipeRepository, UserRepository userRepository, RecipeMapper recipeMapper, RecipeClient recipeClient, RecipeIngredientService recipeIngredientService,
                             IngredientService ingredientService, StepService stepService, RecipeResourceMapper resourceMapper, PdfUtils pdfUtils) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.recipeMapper = recipeMapper;
        this.recipeClient = recipeClient;
        this.recipeIngredientService = recipeIngredientService;
        this.ingredientService = ingredientService;
        this.stepService = stepService;
        this.resourceMapper = resourceMapper;
        this.pdfUtils = pdfUtils;
    }

    @Override
    public RecipeDTO getRecipe(Long recipeId) {
        return recipeMapper.toDto(fetchRecipe(recipeId));
    }

    @Override
    public List<RecipeDTO> getRecipes() {
        return recipeMapper.toDto(recipeRepository.findAll());
    }

    @Override
    public RecipeDTO getRecipeByName(String name) {
        return recipeMapper.toDto(fetchRecipeByName(name));
    }

    @Override
    public List<RecipeDTO> getRecipesByUser(String username) {
        fetchUser(username);
        return recipeMapper.toDto(recipeRepository.findByUserUsername(username));
    }

    @Override
    public RecipeDTO createRecipe(String username, RecipeRequest recipeRequest) {
        checkUniqueness(recipeRequest.getName());
        RecipeEntity recipe = new RecipeEntity();
        recipe.setUser(fetchUser(username));
        recipe.setName(recipeRequest.getName());
        return recipeMapper.toDto(recipeRepository.save(recipe));
    }

    @Override
    public RecipeDTO createRecipe(String username, ImportedRecipeDTO importedRecipe) {
        setUniqueName(importedRecipe);
        RecipeRequest request = new RecipeRequest();
        request.setName(importedRecipe.getName());
        RecipeDTO recipe = createRecipe(username, request);
        ingredientService.createIngredients(importedRecipe.getImportedIngredients());
        stepService.createSteps(recipe.getName(), importedRecipe.getImportedSteps());
        recipeIngredientService.createRecipeIngredients(username, recipe.getName(), importedRecipe.getImportedIngredients());
        return recipe;
    }

    @Override
    public void deleteRecipe(String username, Long recipeId) {
        recipeRepository.delete(fetchRecipeByIdAndUsername(username, recipeId));
    }

    @Override
    public void deleteRecipeByName(String username, String name) {
        recipeRepository.delete(fetchRecipeByNameAndUsername(username, name));
    }

    @Override
    public void deleteRecipesByUser(String username) {
        recipeRepository.deleteAllByUserUsername(username);
    }

    @Override
    public RecipeDTO updateRecipe(String username, Long recipeId, RecipeRequest recipeRequest) {
        RecipeEntity recipe = fetchRecipeByIdAndUsername(username, recipeId);
        if(!Objects.equals(recipe.getName(), recipeRequest.getName())) {
            checkUniqueness(recipeRequest.getName());
        }
        recipe.setName(recipeRequest.getName());
        recipe.setUser(fetchUser(username));
        return recipeMapper.toDto(recipeRepository.save(recipe));
    }

    @Override
    public RecipeDTO updateRecipeByName(String username, String name, RecipeRequest recipeRequest) {
        return updateRecipe(username, fetchRecipeByNameAndUsername(username, name).getId(), recipeRequest);
    }

    @Override
    public String exportToPdf(String recipeName) {
        pdfUtils.deleteOldPdfFiles();
        pdfUtils.export(fetchRecipeByName(recipeName));
        return pdfUtils.getFileName(fetchRecipeByName(recipeName));
    }

    @Override
    public List<ImportedRecipeDTO> importRecipes(String recipeNameToImport) {
        List<RecipeResource> importedRecipesResource = recipeClient.findByName(recipeNameToImport, X_API_KEY);
        List<ImportedRecipeDTO> importedRecipes = resourceMapper.resourceToDto(importedRecipesResource);
        resourceMapper.convertToLocalUnits(importedRecipes, ingredientService.getRecipeIngredientUnits());
        return importedRecipes;
    }

    @Override
    public boolean isUnique(String recipeName) {
        return recipeRepository.findByName(recipeName).isEmpty();
    }

    private RecipeEntity fetchRecipe(Long recipeId) {
        return recipeRepository.findById(recipeId).orElseThrow(RecipeServiceException::notFound);
    }

    private RecipeEntity fetchRecipeByName(String name) {
        return recipeRepository.findByName(name).orElseThrow(RecipeServiceException::notFound);
    }

    private RecipeEntity fetchRecipeByIdAndUsername(String username, Long recipeId) {
        return recipeRepository.findByIdAndUserUsername(recipeId, username).orElseThrow(RecipeServiceException::notFound);
    }

    private RecipeEntity fetchRecipeByNameAndUsername(String username, String name) {
        return recipeRepository.findByNameAndUserUsername(name, username).orElseThrow(RecipeServiceException::notFound);
    }

    private void checkUniqueness(String recipeName) {
        recipeRepository.findByName(recipeName).ifPresent(recipe -> {
            throw RecipeServiceException.alreadyExists("Recipe with name '" + recipe.getName() + "' already exists");
        });
    }

    private UserEntity fetchUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(UserServiceException::notFound);
    }

    private void setUniqueName(ImportedRecipeDTO importedRecipe) {
        int number = 1;
        if(recipeRepository.findByName(importedRecipe.getName()).isEmpty()) return;
        while(true) {
            if(recipeRepository.findByName(importedRecipe.getName() + "(" + number + ")").isPresent()) {
                number++;
            } else break;
        }
        importedRecipe.setName(importedRecipe.getName() + "(" + number + ")");
    }
}
