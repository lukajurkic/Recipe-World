package hr.dice.luka_jurkic.persistence.repository;

import hr.dice.luka_jurkic.persistence.entity.RecipeEntity;
import hr.dice.luka_jurkic.persistence.entity.RecipeImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeImageRepository extends JpaRepository<RecipeImageEntity, Long> {

    List<RecipeImageEntity> findByRecipe(RecipeEntity recipe);

    void deleteByRecipe(RecipeEntity recipe);
}
