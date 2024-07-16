package hr.dice.luka_jurkic.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImportedIngredientDTO {

    private String name;

    private String amount;

    private String unit;

    private String description;
}
