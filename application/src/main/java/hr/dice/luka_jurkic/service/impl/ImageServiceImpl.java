package hr.dice.luka_jurkic.service.impl;

import hr.dice.luka_jurkic.mapper.ImageMapper;
import hr.dice.luka_jurkic.mapper.IngredientMapper;
import hr.dice.luka_jurkic.mapper.RecipeMapper;
import hr.dice.luka_jurkic.persistence.entity.IngredientEntity;
import hr.dice.luka_jurkic.persistence.entity.IngredientImageEntity;
import hr.dice.luka_jurkic.persistence.entity.RecipeEntity;
import hr.dice.luka_jurkic.persistence.entity.RecipeImageEntity;
import hr.dice.luka_jurkic.persistence.repository.IngredientImageRepository;
import hr.dice.luka_jurkic.persistence.repository.IngredientRepository;
import hr.dice.luka_jurkic.persistence.repository.RecipeImageRepository;
import hr.dice.luka_jurkic.persistence.repository.RecipeRepository;
import hr.dice.luka_jurkic.rest.dto.IngredientDetailDTO;
import hr.dice.luka_jurkic.rest.dto.RecipeDetailDTO;
import hr.dice.luka_jurkic.rest.request.ImageRequest;
import hr.dice.luka_jurkic.service.ImageService;
import hr.dice.luka_jurkic.service.exceptions.ImageServiceException;
import hr.dice.luka_jurkic.service.exceptions.IngredientServiceException;
import hr.dice.luka_jurkic.service.exceptions.RecipeServiceException;
import hr.dice.luka_jurkic.utils.ImageUtils;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ImageServiceImpl implements ImageService {

    private final static Integer MAXIMUM_IMAGE_SIZE = 1048576;
    private final static Integer MAXIMUM_IMAGES_FOR_INGREDIENT = 1;
    private final static Integer MAXIMUM_IMAGES_FOR_RECIPE = 5;

    private final RecipeImageRepository recipeImageRepository;
    private final IngredientImageRepository ingredientImageRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;

    private final ImageMapper imageMapper;
    private final RecipeMapper recipeMapper;
    private final IngredientMapper ingredientMapper;

    public ImageServiceImpl(RecipeImageRepository recipeImageRepository, IngredientImageRepository ingredientImageRepository, IngredientRepository ingredientRepository,
                            RecipeRepository recipeRepository, ImageMapper imageMapper, RecipeMapper recipeMapper, IngredientMapper ingredientMapper) {
        this.recipeImageRepository = recipeImageRepository;
        this.ingredientImageRepository = ingredientImageRepository;
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
        this.imageMapper = imageMapper;
        this.recipeMapper = recipeMapper;
        this.ingredientMapper = ingredientMapper;
    }

    @Override
    public RecipeDetailDTO getRecipeWithImage(Long recipeId) {
        RecipeDetailDTO recipeDetails = recipeMapper.toDetailDTO(fetchRecipe(recipeId));
        recipeDetails.setImages(imageMapper.toRecipeImageDto(recipeImageRepository.findByRecipe(fetchRecipe(recipeId))));
        return recipeDetails;
    }

    @Override
    public IngredientDetailDTO getIngredientWithImage(Long ingredientId) {
        IngredientDetailDTO ingredientDetails = ingredientMapper.toDetailDTO(fetchIngredient(ingredientId));
        ingredientDetails.setImages(imageMapper.toIngredientImageDto(ingredientImageRepository.findByIngredient(fetchIngredient(ingredientId))));
        return ingredientDetails;
    }

    @Override
    public void uploadRecipeImage(Long recipeId, ImageRequest request) {
        validateImageSize(request);
        validateRecipeImageCount(recipeId);
        ImageUtils.validateFile(request.getPictureData());
        RecipeImageEntity recipeImage = imageMapper.toRecipeEntity(request);
        recipeImage.setRecipe(fetchRecipe(recipeId));
        recipeImageRepository.save(recipeImage);
        getRecipeWithImage(recipeId);
    }

    @Override
    public void uploadIngredientImage(Long ingredientId, ImageRequest request) {
        validateImageSize(request);
        validateIngredientImageCount(ingredientId);
        ImageUtils.validateFile(request.getPictureData());
        IngredientImageEntity ingredientImage = imageMapper.toIngredientEntity(request);
        ingredientImage.setIngredient(fetchIngredient(ingredientId));
        ingredientImageRepository.save(ingredientImage);
        getIngredientWithImage(ingredientId);
    }

    @Override
    public void deleteRecipeImages(Long recipeId) {
        recipeImageRepository.deleteByRecipe(fetchRecipe(recipeId));
    }

    @Override
    public void deleteIngredientImages(Long ingredientId) {
        ingredientImageRepository.deleteByIngredient(fetchIngredient(ingredientId));
    }

    @Override
    public boolean isTooLarge(ImageRequest request) {
        return request.getPictureData().length > MAXIMUM_IMAGE_SIZE;
    }

    private IngredientEntity fetchIngredient(Long ingredientId) {
        return ingredientRepository.findById(ingredientId).orElseThrow(IngredientServiceException::notFound);
    }

    private RecipeEntity fetchRecipe(Long recipeId) {
        return recipeRepository.findById(recipeId).orElseThrow(RecipeServiceException::notFound);
    }

    private void validateImageSize(ImageRequest request) {
        if (request.getPictureData().length > MAXIMUM_IMAGE_SIZE) {
            throw ImageServiceException.sizeTooLarge();
        }
    }

    private void validateIngredientImageCount(Long ingredientId) {
        List<IngredientImageEntity> ingredientImages = ingredientImageRepository.findByIngredient(fetchIngredient(ingredientId));
        if (ingredientImages.size() >= MAXIMUM_IMAGES_FOR_INGREDIENT) {
            throw IngredientServiceException.imageLimitReached("Limit for images on this ingredient reached");
        }
    }

    private void validateRecipeImageCount(Long recipeId) {
        List<RecipeImageEntity> recipeImages = recipeImageRepository.findByRecipe(fetchRecipe(recipeId));
        if (recipeImages.size() >= MAXIMUM_IMAGES_FOR_RECIPE) {
            throw RecipeServiceException.imageLimitReached("Limit for images on this recipe reached");
        }
    }
}
