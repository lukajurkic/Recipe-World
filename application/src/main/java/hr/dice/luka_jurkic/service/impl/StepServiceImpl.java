package hr.dice.luka_jurkic.service.impl;

import hr.dice.luka_jurkic.mapper.StepMapper;
import hr.dice.luka_jurkic.persistence.entity.RecipeEntity;
import hr.dice.luka_jurkic.persistence.entity.StepEntity;
import hr.dice.luka_jurkic.persistence.repository.RecipeRepository;
import hr.dice.luka_jurkic.persistence.repository.StepRepository;
import hr.dice.luka_jurkic.rest.dto.ImportedStepDTO;
import hr.dice.luka_jurkic.rest.dto.StepDTO;
import hr.dice.luka_jurkic.rest.request.StepRequest;
import hr.dice.luka_jurkic.service.StepService;
import hr.dice.luka_jurkic.service.exceptions.RecipeServiceException;
import hr.dice.luka_jurkic.service.exceptions.StepServiceException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class StepServiceImpl implements StepService {

    private final StepRepository stepRepository;
    private final RecipeRepository recipeRepository;
    private final StepMapper stepMapper;

    public StepServiceImpl(StepRepository stepRepository, RecipeRepository recipeRepository, StepMapper stepMapper) {
        this.stepRepository = stepRepository;
        this.recipeRepository = recipeRepository;
        this.stepMapper = stepMapper;
    }

    @Override
    public StepDTO getStep(String recipeName, Integer stepNumber) {
        return stepMapper.toDto(fetchStep(recipeName, stepNumber));
    }

    @Override
    public List<StepDTO> getSteps(String recipeName) {
        return stepMapper.toDto(stepRepository.findByRecipeName(recipeName)).stream().sorted(Comparator.comparing(StepDTO::getNumber)).toList();
    }

    @Override
    public StepDTO createStep(StepRequest stepRequest, String recipeName) {
        StepEntity step = stepMapper.requestToEntity(stepRequest);
        step.setNumber(findLastNumberForRecipe(recipeName) + 1);;
        RecipeEntity recipe = fetchRecipeByName(recipeName);
        step.setRecipe(recipe);
        return stepMapper.toDto(stepRepository.save(step));
    }

    @Override
    public List<StepDTO> createSteps(String recipeName, List<ImportedStepDTO> importedSteps) {
        List<StepDTO> steps = new ArrayList<>();
        for(ImportedStepDTO importedStep : importedSteps) {
            StepRequest stepRequest = new StepRequest();
            stepRequest.setDescription(importedStep.getDescription());
            steps.add(createStep(stepRequest, recipeName));
        }
        return steps;
    }

    @Override
    public void deleteStep(String recipeName, Integer stepNumber) {
        stepRepository.delete(fetchStep(recipeName, stepNumber));
    }

    @Override
    public StepDTO updateStep(StepRequest stepRequest, Integer stepNumber, String recipeName) {
        StepEntity step = fetchStep(recipeName, stepNumber);
        step.setDescription(stepRequest.getDescription());
        return stepMapper.toDto(stepRepository.save(step));
    }

    @Override
    public void fixNumbering(String recipeName) {
        fixRecipeStepNumber(fetchRecipeByName(recipeName));
    }

    private StepEntity fetchStep(String recipeName, Integer stepNumber) {
        return stepRepository.findByRecipeNameAndNumber(recipeName, stepNumber).orElseThrow(StepServiceException::notFound);
    }

    private Integer findLastNumberForRecipe(String recipeName){
        List<StepEntity> steps = stepRepository.findByRecipeName(recipeName);
        return steps.isEmpty() ? 0 :
                steps.stream().max(Comparator.comparing(StepEntity::getNumber))
                .orElseThrow(RuntimeException::new)
                .getNumber();
    }

    private RecipeEntity fetchRecipeByName(String name) {
        return recipeRepository.findByName(name).orElseThrow(RecipeServiceException::notFound);
    }

    private void fixRecipeStepNumber(RecipeEntity recipe) {
        List<StepEntity> steps = recipe.getSteps();
        steps = steps.stream().sorted(Comparator.comparing(StepEntity::getNumber)).toList();
        for (int i = 0; i < steps.size(); i++) {
            steps.get(i).setNumber(i+1);
            stepRepository.save(steps.get(i));
        }
    }
}
