package hr.dice.luka_jurkic.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "recipe_ingredient")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredientEntity extends BaseEntity {

    @Column(name = "amount")
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "unit")
    private IngredientUnit unit;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "recipe_id", foreignKey = @ForeignKey(name = "fk_recipe_ingredient_recipe"))
    private RecipeEntity recipe;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "ingredient_id", foreignKey = @ForeignKey(name = "fk_recipe_ingredient_ingredient"))
    private IngredientEntity ingredient;
}
