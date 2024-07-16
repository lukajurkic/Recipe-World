package hr.dice.luka_jurkic.persistence.repository;

import hr.dice.luka_jurkic.persistence.entity.IngredientEntity;
import hr.dice.luka_jurkic.persistence.entity.IngredientImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientImageRepository extends JpaRepository<IngredientImageEntity, Long> {

    List<IngredientImageEntity> findByIngredient(IngredientEntity ingredient);

    void deleteByIngredient(IngredientEntity ingredient);
}
