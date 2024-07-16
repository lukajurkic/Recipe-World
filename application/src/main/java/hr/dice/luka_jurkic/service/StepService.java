package hr.dice.luka_jurkic.service;

import hr.dice.luka_jurkic.rest.dto.ImportedStepDTO;
import hr.dice.luka_jurkic.rest.dto.StepDTO;
import hr.dice.luka_jurkic.rest.request.StepRequest;

import java.util.List;

/**
 * Service for managing steps for recipes
 */
public interface StepService {

    /**
     * Returns step details for given recipe name and step number
     * @param recipeName unique identifier for each recipe
     * @param stepNumber unique identifier for each step in recipe
     * @return Step Data Transfer Object
     */
    StepDTO getStep(String recipeName, Integer stepNumber);

    /**
     * Returns list of all steps for given recipe name
     * @param recipeName unique identifier for each recipe
     * @return List of Step Data Transfer Objects
     */
    List<StepDTO> getSteps(String recipeName);

    /**
     * Returns newly created step for recipe with given recipe name
     * @param stepRequest all information for creating new step in recipe
     * @param recipeName unique identifier for each recipe
     * @return Step Data Transfer Object
     */
    StepDTO createStep(StepRequest stepRequest, String recipeName);

    /**
     * Returns list of created steps for given recipeName
     * @param recipeName unique identifier for each recipe
     * @param importedSteps list of imported steps to be added
     * @return List of Step Data Transfer Objects
     */
    List<StepDTO> createSteps(String recipeName, List<ImportedStepDTO> importedSteps);

    /**
     * Deletes step in given recipe with given recipe name, under step number
     * @param recipeName unique identifier for each recipe
     * @param stepNumber unique identifier for each step in recipe
     */
    void deleteStep(String recipeName, Integer stepNumber);

    /**
     * Returns updated step with data from request for step with step number
     * @param stepRequest information for updating step
     * @param stepNumber unique identifier for each step in recipe
     * @param recipeName unique identifier for each reicpe
     * @return Step Data Transfer Object
     */
    StepDTO updateStep(StepRequest stepRequest, Integer stepNumber, String recipeName);

    /**
     * Updates recipe steps so numbers go from 1 to number of steps, with step of 1
     * @param recipeName unique identifier for each recipe
     */
    void fixNumbering(String recipeName);
}
