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
@Table(name = "ingredient_image")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientImageEntity extends BaseEntity {

    @Column(columnDefinition = "CLOB", name = "picture_data")
    private byte[] pictureData;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "ingredient_id", foreignKey = @ForeignKey(name = "fk_image_ingredient"))
    private IngredientEntity ingredient;
}
