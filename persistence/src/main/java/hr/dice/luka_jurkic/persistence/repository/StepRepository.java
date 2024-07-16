package hr.dice.luka_jurkic.persistence.repository;

import hr.dice.luka_jurkic.persistence.entity.StepEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StepRepository extends JpaRepository<StepEntity, Long> {

    Optional<StepEntity> findByRecipeNameAndNumber(String recipeName, Integer number);

    List<StepEntity> findByRecipeName(String recipeName);
}
