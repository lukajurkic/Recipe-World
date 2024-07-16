package hr.dice.luka_jurkic.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "step")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StepEntity extends BaseEntity {

    @Column(name = "description")
    private String description;

    @Column(name = "number")
    private Integer number;

    @ManyToOne
    @JoinColumn(name = "recipe_id", foreignKey = @ForeignKey(name = "fk_step_recipe"))
    private RecipeEntity recipe;
}
