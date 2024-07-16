package hr.dice.luka_jurkic.persistence.repository;

import hr.dice.luka_jurkic.persistence.entity.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {

    Optional<RecipeEntity> findByName(String name);

    Optional<RecipeEntity> findByIdAndUserUsername(Long id, String username);

    Optional<RecipeEntity> findByNameAndUserUsername(String name, String username);

    List<RecipeEntity> findByUserUsername(String username);

    void deleteAllByUserUsername(String username);
}
