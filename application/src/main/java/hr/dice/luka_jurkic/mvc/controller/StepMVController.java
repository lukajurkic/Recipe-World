package hr.dice.luka_jurkic.mvc.controller;

import hr.dice.luka_jurkic.rest.request.StepRequest;
import hr.dice.luka_jurkic.service.StepService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/steps")
@Controller
public class StepMVController {

    private final StepService stepService;

    public StepMVController(StepService stepService) {
        this.stepService = stepService;
    }

    @GetMapping
    public String getRecipeSteps(@RequestParam(name = "recipeName") String recipeName, Model model) {
        model.addAttribute("recipeName", recipeName);
        model.addAttribute("steps", stepService.getSteps(recipeName));
        return "step/show_recipeSteps";
    }

    @GetMapping("/new")
    public String createRecipeStepForm(@RequestParam(name = "recipeName") String recipeName, Model model) {
        model.addAttribute("recipeName", recipeName);
        model.addAttribute("request", new StepRequest());
        return "step/form_createRecipeStep";
    }

    @PostMapping("/create")
    public String createRecipeStep(@RequestParam(name = "recipeName") String recipeName,
                                   @Valid @ModelAttribute(name = "request") StepRequest stepRequest,
                                   BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> System.out.println(objectError.getDefaultMessage()));
            model.addAttribute("recipeName", recipeName);
            return "step/form_createRecipeStep";
        }
        stepService.createStep(stepRequest, recipeName);
        return getRecipeSteps(recipeName, model);
    }

    @PostMapping("/delete")
    public String deleteRecipeStep(@RequestParam(name = "recipeName") String recipeName,
                                   @RequestParam(name = "stepNumber") Integer stepNumber, Model model) {
        stepService.deleteStep(recipeName, stepNumber);
        return getRecipeSteps(recipeName, model);
    }

    @GetMapping("/update")
    public String updateRecipeStepForm(@RequestParam(name = "recipeName") String recipeName,
                                       @RequestParam(name = "stepNumber") Integer stepNumber, Model model) {
        model.addAttribute("stepNumber", stepNumber);
        model.addAttribute("recipeName", recipeName);
        StepRequest request = new StepRequest();
        request.setDescription(stepService.getStep(recipeName, stepNumber).getDescription());
        model.addAttribute("request", request);
        return "step/form_updateStep";
    }

    @PostMapping("/update")
    public String updateRecipe(@RequestParam(name = "recipeName") String recipeName,
                                @Valid @ModelAttribute(name = "request") StepRequest request,
                               BindingResult bindingResult,
                               @RequestParam(name = "stepNumber") Integer stepNumber, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("stepNumber", stepNumber);
            model.addAttribute("recipeName", recipeName);
            return "step/form_updateStep";
        }
        stepService.updateStep(request, stepNumber, recipeName);
        return getRecipeSteps(recipeName, model);
    }

    @PostMapping("/fixNumbers")
    public String fixNumbering(@RequestParam(name = "recipeName") String recipeName, Model model) {
        stepService.fixNumbering(recipeName);
        return getRecipeSteps(recipeName, model);
    }
}
