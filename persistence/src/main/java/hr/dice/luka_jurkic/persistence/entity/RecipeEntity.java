package hr.dice.luka_jurkic.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "recipe")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_recipe_user"))
    private UserEntity user;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<CommentEntity> comments;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<RecipeIngredientEntity> ingredients;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<RecipeImageEntity> images;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<StepEntity> steps;
}

