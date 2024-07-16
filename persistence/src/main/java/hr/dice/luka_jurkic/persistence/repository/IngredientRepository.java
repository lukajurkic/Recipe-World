package hr.dice.luka_jurkic.persistence.repository;

import hr.dice.luka_jurkic.persistence.entity.IngredientCategory;
import hr.dice.luka_jurkic.persistence.entity.IngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends JpaRepository<IngredientEntity, Long> {


    Optional<IngredientEntity> findByName(String name);

    Optional<IngredientEntity> findByNameAndCategory(String name, IngredientCategory category);

    List<IngredientEntity> findByCategory(IngredientCategory category);
}
