package hr.dice.luka_jurkic.rest.dto;

import hr.dice.luka_jurkic.rest.base.BaseStep;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StepDTO extends BaseStep {

    private Long id;

    private Integer number;

    private RecipeDTO recipe;
}
