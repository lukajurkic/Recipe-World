package hr.dice.luka_jurkic.persistence.repository;

import hr.dice.luka_jurkic.persistence.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findByUserUsername(String username);

    List<CommentEntity> findByUserUsernameAndRecipeName(String username, String recipeName);

    List<CommentEntity> findByRecipeName(String recipeName);

    void deleteByUserUsername(String username);
}
