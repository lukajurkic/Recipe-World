package hr.dice.luka_jurkic.service.impl;

import hr.dice.luka_jurkic.mapper.IngredientMapper;
import hr.dice.luka_jurkic.persistence.entity.IngredientCategory;
import hr.dice.luka_jurkic.persistence.entity.IngredientEntity;
import hr.dice.luka_jurkic.persistence.entity.IngredientUnit;
import hr.dice.luka_jurkic.persistence.repository.IngredientRepository;
import hr.dice.luka_jurkic.persistence.repository.RecipeIngredientRepository;
import hr.dice.luka_jurkic.rest.dto.ImportedIngredientDTO;
import hr.dice.luka_jurkic.rest.dto.IngredientDTO;
import hr.dice.luka_jurkic.rest.request.IngredientRequest;
import hr.dice.luka_jurkic.service.IngredientService;
import hr.dice.luka_jurkic.service.exceptions.IngredientServiceException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;
    private final RecipeIngredientRepository recipeIngredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository, IngredientMapper ingredientMapper, RecipeIngredientRepository recipeIngredientRepository) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientMapper = ingredientMapper;
        this.recipeIngredientRepository = recipeIngredientRepository;
    }

    @Override
    public IngredientDTO getIngredient(Long ingredientId) {
        return ingredientMapper.toDto(fetchIngredient(ingredientId));
    }

    @Override
    public IngredientDTO getIngredientByName(String ingredientName) {
        return ingredientMapper.toDto(fetchIngredientByName(ingredientName));
    }

    @Override
    public List<IngredientDTO> getIngredientsByCategory(String category) {
        validateCategory(category);
        return ingredientMapper.toDto(ingredientRepository.findByCategory(IngredientCategory.valueOf(category)));
    }

    @Override
    public List<IngredientDTO> getIngredients() {
        return ingredientMapper.toDto(ingredientRepository.findAll());
    }

    @Override
    public List<String> getIngredientCategories() {
        List<String> categories = Stream.of(IngredientCategory.values()).map(IngredientCategory::name).collect(Collectors.toList());
        categories.remove("IMPORTED");
        return categories;
    }

    @Override
    public List<String> getRecipeIngredientUnits() {
        return Stream.of(IngredientUnit.values()).map(IngredientUnit::name).collect(Collectors.toList());
    }


    @Override
    public IngredientDTO createIngredient(IngredientRequest request) {
        checkUniqueness(request);
        return ingredientMapper.toDto(ingredientRepository.save(ingredientMapper.requestToEntity(request)));
    }

    @Override
    public List<IngredientDTO> createIngredients(List<ImportedIngredientDTO> importedIngredients) {
        List<IngredientDTO> newIngredients = new ArrayList<>();
        List<String> existingIngredients = getIngredients().stream().map(IngredientDTO::getName).toList();
        for (ImportedIngredientDTO importedIngredient : importedIngredients) {
            if (!existingIngredients.contains(importedIngredient.getName())) {
                IngredientRequest request = new IngredientRequest();
                request.setName(importedIngredient.getName());
                request.setCategory("IMPORTED");
                newIngredients.add(createIngredient(request));
            }
        }
        return newIngredients;
    }

    @Override
    public void deleteIngredient(Long ingredientId) {
        checkRecipeUsage(ingredientId);
        ingredientRepository.delete(fetchIngredient(ingredientId));
    }

    @Override
    public IngredientDTO updateIngredient(Long ingredientId, IngredientRequest request) {
        checkUniqueness(request);
        IngredientEntity ingredient = fetchIngredient(ingredientId);
        ingredient.setName(request.getName());
        ingredient.setCategory(IngredientCategory.valueOf(request.getCategory()));
        return ingredientMapper.toDto(ingredientRepository.save(ingredient));
    }

    @Override
    public boolean isUnique(String ingredientName, String category) {
        return ingredientRepository.findByNameAndCategory(ingredientName, IngredientCategory.valueOf(category)).isEmpty();
    }

    private IngredientEntity fetchIngredient(Long id) {
        return ingredientRepository.findById(id).orElseThrow(IngredientServiceException::notFound);
    }

    private IngredientEntity fetchIngredientByName(String name) {
        return ingredientRepository.findByName(name).orElseThrow(IngredientServiceException::notFound);
    }

    private void validateCategory(String category) {
        if (!getIngredientCategories().contains(category)) {
            throw IngredientServiceException.notFound("Category doesn't exist");
        }
    }

    private void checkUniqueness(IngredientRequest request) {
        ingredientRepository.findByNameAndCategory(request.getName(), IngredientCategory.valueOf(request.getCategory()))
                .ifPresent(ingredient -> {
                    throw IngredientServiceException.alreadyExists("Ingredient with name" + ingredient.getName() + " already exists.");
                });
    }

    private void checkRecipeUsage(Long ingredientId) {
        if (!recipeIngredientRepository.findAllByIngredientId(ingredientId).isEmpty()) {
            throw IngredientServiceException.inUse("This ingredient is in use");
        }
    }
}
