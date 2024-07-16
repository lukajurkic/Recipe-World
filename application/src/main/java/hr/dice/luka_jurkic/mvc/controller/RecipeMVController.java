package hr.dice.luka_jurkic.mvc.controller;

import hr.dice.luka_jurkic.mapper.RecipeMapper;
import hr.dice.luka_jurkic.rest.dto.CommentContentDTO;
import hr.dice.luka_jurkic.rest.dto.ImportedRecipeDTO;
import hr.dice.luka_jurkic.rest.dto.RecipeDTO;
import hr.dice.luka_jurkic.rest.request.ImageRequest;
import hr.dice.luka_jurkic.rest.request.RecipeIngredientRequest;
import hr.dice.luka_jurkic.rest.request.RecipeRequest;
import hr.dice.luka_jurkic.service.CommentService;
import hr.dice.luka_jurkic.service.ImageService;
import hr.dice.luka_jurkic.service.IngredientService;
import hr.dice.luka_jurkic.service.RecipeIngredientService;
import hr.dice.luka_jurkic.service.RecipeService;
import hr.dice.luka_jurkic.service.StepService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.commons.io.FileUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/recipes")
public class RecipeMVController {

    private final RecipeService recipeService;
    private final RecipeMapper recipeMapper;
    private final RecipeIngredientService recipeIngredientService;
    private final IngredientService ingredientService;
    private final CommentService commentService;
    private final ImageService imageService;
    private final StepService stepService;

    public RecipeMVController(RecipeService recipeService, RecipeMapper recipeMapper, RecipeIngredientService recipeIngredientService, IngredientService ingredientService,
                              CommentService commentService, ImageService imageService, StepService stepService) {
        this.recipeService = recipeService;
        this.recipeMapper = recipeMapper;
        this.recipeIngredientService = recipeIngredientService;
        this.ingredientService = ingredientService;
        this.commentService = commentService;
        this.imageService = imageService;
        this.stepService = stepService;
    }

    @GetMapping
    public String getRecipe(@RequestParam(name = "recipeName") String recipeName, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> images = new ArrayList<>();
        List<CommentContentDTO> userComments = commentService.getCommentContentsByUserAndRecipe(authentication.getName(), recipeName);
        List<CommentContentDTO> comments = commentService.getCommentContentByRecipe(recipeName);
        comments.removeAll(userComments);
        imageService.getRecipeWithImage(recipeService.getRecipeByName(recipeName).getId()).getImages().forEach(imageDTO ->
                images.add(Base64.getEncoder().encodeToString(imageDTO.getPictureData())));
        model.addAttribute("recipe", recipeService.getRecipeByName(recipeName));
        model.addAttribute("ingredients", recipeIngredientService.getRecipeIngredients(recipeName));
        model.addAttribute("comments", comments);
        model.addAttribute("userComments", userComments);
        model.addAttribute("images", images);
        model.addAttribute("steps", stepService.getSteps(recipeName));
        return "recipe/show_recipe";
    }

    @GetMapping("/all")
    public String getRecipes(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("recipes", recipeService.getRecipesByUser(auth.getName()));
        return "recipe/list_recipes";
    }

    @GetMapping("/new")
    public String createRecipeForm(Model model) {
        model.addAttribute("request", new RecipeRequest());
        return "recipe/form_createRecipe";
    }

    @PostMapping("/create")
    public String createRecipe(@Valid @ModelAttribute(name = "request") RecipeRequest request,
                               BindingResult bindingResult, Model model) {
        if (!recipeService.isUnique(request.getName())) {
            bindingResult.rejectValue("name", "error.request", "Recipe name taken");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("request", request);
            return "recipe/form_createRecipe";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return addIngredientToRecipeForm(recipeService.createRecipe(auth.getName(), request), "ALL", model);
    }

    @GetMapping("/addIngredientForm")
    public String addIngredientToRecipeForm(RecipeDTO recipe, String category, Model model) {
        model.addAttribute("recipeName", recipe.getName());
        model.addAttribute("units", ingredientService.getRecipeIngredientUnits());
        model.addAttribute("recipeIngredientRequest", new RecipeIngredientRequest());
        if (Objects.equals(category, "ALL")) {
            model.addAttribute("ingredients", ingredientService.getIngredients());
        } else {
            model.addAttribute("ingredients", ingredientService.getIngredientsByCategory(category));
        }
        model.addAttribute("categories", ingredientService.getIngredientCategories());
        return "recipe/form_addRecipeIngredient";
    }

    @GetMapping("/addIngredient")
    public String addIngredientToRecipe(@RequestParam(name = "category", required = false) String category,
                                        @RequestParam(name = "recipeName") String recipeName, Model model) {
        if (category == null) {
            category = "ALL";
        }
        return addIngredientToRecipeForm(recipeService.getRecipeByName(recipeName), category, model);
    }

    @PostMapping("/addIngredient")
    public String addIngredientToRecipe(@RequestParam(name = "recipeName") String recipeName,
                                        @Valid @ModelAttribute(name = "recipeIngredientRequest") RecipeIngredientRequest request,
                                        BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("recipeName", recipeName);
            model.addAttribute("units", ingredientService.getRecipeIngredientUnits());
            model.addAttribute("categories", ingredientService.getIngredientCategories());
            model.addAttribute("ingredients", ingredientService.getIngredients());
            return "recipe/form_addRecipeIngredient";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        recipeIngredientService.createRecipeIngredient(auth.getName(), recipeName, request);
        return addIngredientToRecipeForm(recipeService.getRecipeByName(recipeName), "ALL", model);
    }

    @GetMapping("/delete")
    public String deleteRecipeForm(@RequestParam(name = "recipeId") Long recipeId, Model model) {
        model.addAttribute("recipe", recipeService.getRecipe(recipeId));
        return "recipe/form_deleteRecipe";
    }

    @PostMapping("/delete")
    public String deleteRecipe(@RequestParam(name = "recipeId") Long recipeId, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        recipeService.deleteRecipe(auth.getName(), recipeId);
        model.addAttribute("recipes", recipeService.getRecipesByUser(auth.getName()));
        return "recipe/list_recipes";
    }

    @GetMapping("/update")
    public String updateRecipeForm(@RequestParam(name = "recipeId") Long recipeId, Model model) {
        model.addAttribute("request", recipeMapper.recipeDTOToRequest(recipeService.getRecipe(recipeId)));
        model.addAttribute("recipeId", recipeId);
        model.addAttribute("recipeName", recipeService.getRecipe(recipeId).getName());
        return "recipe/form_updateRecipe";
    }

    @PostMapping("/update")
    public String updateRecipe(@Valid @ModelAttribute(name = "request") RecipeRequest request,
                               BindingResult bindingResult,
                               @RequestParam(name = "recipeId") Long recipeId, Model model) {
        if (!recipeService.isUnique(request.getName())) {
            bindingResult.rejectValue("name", "error.request", "Recipe name taken");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("recipeId", recipeId);
            model.addAttribute("recipeName", recipeService.getRecipe(recipeId).getName());
            return "recipe/form_updateRecipe";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        recipeService.updateRecipe(auth.getName(), recipeId, request);
        model.addAttribute("recipes", recipeService.getRecipesByUser(auth.getName()));
        return "recipe/list_recipes";
    }

    @GetMapping("/upload")
    public String uploadImage(@RequestParam(name = "recipeId") Long recipeId, Model model) {
        model.addAttribute("recipe", recipeService.getRecipe(recipeId));
        model.addAttribute("request", new ImageRequest());
        return "recipe/form_uploadImage";
    }

    @PostMapping("/upload")
    public String uploadImage(@RequestParam(name = "recipeId") Long recipeId,
                              @Valid @ModelAttribute(name = "request") ImageRequest request,
                              BindingResult bindingResult,
                              @RequestParam("image") MultipartFile file, Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("recipe", recipeService.getRecipe(recipeId));
            return "recipe/form_uploadImage";
        }
        request.setPictureData(file.getBytes());
        imageService.uploadRecipeImage(recipeId, request);
        return getRecipe(recipeService.getRecipe(recipeId).getName(), model);
    }

    @GetMapping("/images/delete")
    public String deleteImagesForm(@RequestParam(name = "recipeId") Long recipeId, Model model) {
        model.addAttribute("recipe", recipeService.getRecipe(recipeId));
        return "recipe/form_deleteRecipeImage";
    }

    @PostMapping("/images/delete")
    public String deleteImages(@RequestParam(name = "recipeId") Long recipeId, Model model) {
        imageService.deleteRecipeImages(recipeId);
        return getRecipe(recipeService.getRecipe(recipeId).getName(), model);
    }

    @GetMapping("/exportToPdf/{recipeName}")
    public void exportToPdf(HttpServletResponse response, @PathVariable String recipeName) {
        String filename = recipeService.exportToPdf(recipeName);
        try {
            File file = new File(filename);

            if (file.exists()) {
                FileUtils.copyFile(file, response.getOutputStream());

                response.setContentType("application/pdf");
                response.addHeader("Content-disposition", "attachment; filename" + filename);
                response.flushBuffer();
            } else {
                System.out.println("Something went wrong. File not found.");
            }
        }
        catch (IOException e) {
            System.out.println("Something went wrong. File could not be created.");
            System.out.println(e.getMessage());
        }
    }

    @GetMapping("/import")
    public String importRecipeForm(Model model) {
        model.addAttribute("importedRecipes", new ArrayList<ImportedRecipeDTO>());
        return "recipe/form_importRecipe";
    }

    @PostMapping("/import")
    public String importRecipe(@RequestParam(name = "recipeNameToImport") String recipeNameToImport, Model model) {
        List<ImportedRecipeDTO> importedRecipes = recipeService.importRecipes(recipeNameToImport);
        model.addAttribute("importedRecipes", importedRecipes);
        model.addAttribute("recipeNameToImport", recipeNameToImport);
        return "recipe/form_importRecipe";
    }

    @PostMapping("/select")
    public String selectRecipe(@RequestParam(name = "listIndex") Integer listIndex, @RequestParam(name = "recipeNameToImport") String recipeNameToImport, Model model) {
        List<ImportedRecipeDTO> importedRecipeDTOS = recipeService.importRecipes(recipeNameToImport);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        recipeService.createRecipe(authentication.getName(), importedRecipeDTOS.get(listIndex));
        return getRecipes(model);
    }

}
