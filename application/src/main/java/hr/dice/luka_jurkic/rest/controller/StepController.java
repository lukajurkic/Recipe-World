package hr.dice.luka_jurkic.rest.controller;

import hr.dice.luka_jurkic.rest.dto.StepDTO;
import hr.dice.luka_jurkic.rest.request.StepRequest;
import hr.dice.luka_jurkic.service.StepService;
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
@RequestMapping("/api/steps")
@Validated
public class StepController {

    private final StepService stepService;

    public StepController(StepService stepService) {
        this.stepService = stepService;
    }

    @GetMapping("/{recipeName}/step/{stepNumber}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public StepDTO getStep(@PathVariable String recipeName, @PathVariable Integer stepNumber) {
        return stepService.getStep(recipeName, stepNumber);
    }

    @GetMapping("/{recipeName}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public List<StepDTO> getSteps(@PathVariable String recipeName) {
        return stepService.getSteps(recipeName);
    }

    @PostMapping("/{recipeName}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public StepDTO createStep(@Valid @RequestBody StepRequest stepRequest, @PathVariable String recipeName) {
        return stepService.createStep(stepRequest,recipeName);
    }

    @DeleteMapping("/{recipeName}/step/{stepNumber}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public void deleteStep(@PathVariable String recipeName, @PathVariable Integer stepNumber) {
        stepService.deleteStep(recipeName, stepNumber);
    }

    @PutMapping("/{recipeName}/step/{stepNumber}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public StepDTO updateStep(@PathVariable Integer stepNumber, @Valid @RequestBody StepRequest request, @PathVariable String recipeName) {
        return stepService.updateStep(request, stepNumber, recipeName);
    }
}
