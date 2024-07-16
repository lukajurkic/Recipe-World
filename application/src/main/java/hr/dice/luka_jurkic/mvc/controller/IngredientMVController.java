package hr.dice.luka_jurkic.mvc.controller;

import hr.dice.luka_jurkic.rest.dto.IngredientDTO;
import hr.dice.luka_jurkic.rest.request.ImageRequest;
import hr.dice.luka_jurkic.rest.request.IngredientRequest;
import hr.dice.luka_jurkic.service.ImageService;
import hr.dice.luka_jurkic.service.IngredientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/ingredients")
public class IngredientMVController {

    private final IngredientService ingredientService;
    private final ImageService imageService;

    @Autowired
    public IngredientMVController(IngredientService ingredientService, ImageService imageService) {
        this.ingredientService = ingredientService;
        this.imageService = imageService;
    }

    @GetMapping
    public String getIngredient(@RequestParam(name = "ingredientId") Long ingredientId, Model model) {
        List<String> images = new ArrayList<>();
        imageService.getIngredientWithImage(ingredientId).getImages().forEach(imageDTO -> images.add(Base64.getEncoder().encodeToString(imageDTO.getPictureData())));
        model.addAttribute("ingredient", ingredientService.getIngredient(ingredientId));
        model.addAttribute("images", images);
        return "ingredient/show_ingredient";
    }

    @GetMapping("/name")
    public String getIngredientByName(@RequestParam(name = "ingredientName") String ingredientName, Model model) {
        return getIngredient(ingredientService.getIngredientByName(ingredientName).getId(), model);
    }

    @GetMapping("/all")
    public String getIngredients(@RequestParam(name = "category", required = false) String category, Model model) {
        List<IngredientDTO> ingredients = ingredientService.getIngredients();
        if(category != null && !category.equals("ALL")){
            ingredients = ingredientService.getIngredientsByCategory(category);
        }
        model.addAttribute("ingredients", ingredients);
        model.addAttribute("categories", ingredientService.getIngredientCategories());
        return "ingredient/list_ingredients";
    }

    @PostMapping("/create")
    public String createIngredient(@Valid @ModelAttribute("request") IngredientRequest request, BindingResult binding, Model model) {
        if(!ingredientService.isUnique(request.getName(), request.getCategory())){
            binding.rejectValue("name", "error.request", "Ingredient name taken");
        }
        if (binding.hasErrors()) {
            model.addAttribute("categories", ingredientService.getIngredientCategories());
            return "ingredient/form_createIngredient";
        }
        IngredientDTO ingredient = ingredientService.createIngredient(request);
        model.addAttribute("ingredient", ingredient);
        return getIngredient(ingredient.getId(), model);
    }

    @GetMapping("/new")
    public String newIngredientForm(Model model) {
        model.addAttribute("categories", ingredientService.getIngredientCategories());
        model.addAttribute("request", new IngredientRequest());
        return "ingredient/form_createIngredient";
    }

    @GetMapping("/delete/{ingredientId}")
    public String deleteIngredientForm(@PathVariable Long ingredientId, Model model) {
        model.addAttribute("ingredient", ingredientService.getIngredient(ingredientId));
        return "ingredient/form_deleteIngredient";
    }

    @PostMapping("/delete")
    public String deleteIngredient(@RequestParam(name = "ingredientId") Long ingredientId, Model model) {
        imageService.deleteIngredientImages(ingredientId);
        ingredientService.deleteIngredient(ingredientId);
        return getIngredients(null, model);
    }

    @GetMapping("/update/{ingredientId}")
    public String updateIngredientForm(@PathVariable Long ingredientId, Model model) {
        List<String> categories = ingredientService.getIngredientCategories();
        IngredientDTO ingredient = ingredientService.getIngredient(ingredientId);
        IngredientRequest request = new IngredientRequest();
        request.setCategory(ingredient.getCategory());
        request.setName(ingredient.getName());
        model.addAttribute("categories", categories);
        model.addAttribute("request", request);
        model.addAttribute("ingredientId", ingredientId);
        return "ingredient/form_updateIngredient";
    }

    @PostMapping("/update")
    public String updateIngredient(
            @Valid @ModelAttribute("request") IngredientRequest request,
            BindingResult binding,
            @RequestParam(name = "ingredientId") Long ingredientId, Model model) {
        if(!ingredientService.isUnique(request.getName(), request.getCategory())){
            binding.rejectValue("name", "error.request", "Ingredient name taken");
        }
        if (binding.hasErrors()) {
            List<String> categories = ingredientService.getIngredientCategories();
            model.addAttribute("categories", categories);
            model.addAttribute("ingredientId", ingredientId);
            return "ingredient/form_updateIngredient";
        }
        model.addAttribute("ingredient", ingredientService.updateIngredient(ingredientId, request));
        return getIngredient(ingredientId, model);
    }

    @GetMapping("/upload")
    public String uploadImage(@RequestParam(name = "ingredientId") Long ingredientId, Model model) {
        model.addAttribute("ingredient", ingredientService.getIngredient(ingredientId));
        model.addAttribute("request", new ImageRequest());
        return "ingredient/form_uploadImage";
    }

    @PostMapping("/upload")
    public String uploadImage(@RequestParam(name = "ingredientId") Long ingredientId,
                              @Valid @ModelAttribute(name = "request") ImageRequest request,
                              BindingResult bindingResult,
                              @RequestParam("image") MultipartFile file, Model model) throws IOException {
        request.setPictureData(file.getBytes());
        if(imageService.isTooLarge(request)) {
            bindingResult.rejectValue("pictureData", "error.request", "Image too large");

        }
        if(bindingResult.hasErrors()){
            model.addAttribute("ingredient", ingredientService.getIngredient(ingredientId));
            return "ingredient/form_uploadImage";
        }
        request.setPictureData(file.getBytes());
        imageService.uploadIngredientImage(ingredientId, request);
        return getIngredient(ingredientId, model);
    }

    @PostMapping("/images/delete")
    public String deleteImage(@RequestParam(name = "ingredientId") Long ingredientId,
                              Model model) {
        imageService.deleteIngredientImages(ingredientId);
        return getIngredient(ingredientId, model);
    }
}
