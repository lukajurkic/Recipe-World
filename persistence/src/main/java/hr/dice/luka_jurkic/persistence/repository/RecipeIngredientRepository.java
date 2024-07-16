package hr.dice.luka_jurkic.persistence.repository;

import hr.dice.luka_jurkic.persistence.entity.RecipeIngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredientEntity, Long> {

    List<RecipeIngredientEntity> findAllByRecipeName(String name);

    Optional<RecipeIngredientEntity> findByRecipeNameAndIngredientName(String recipeName, String ingredientName);

    List<RecipeIngredientEntity> findAllByIngredientId(Long ingredientId);

    void deleteAllByRecipeName(String recipeName);

}
