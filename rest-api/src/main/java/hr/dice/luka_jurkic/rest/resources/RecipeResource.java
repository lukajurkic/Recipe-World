package hr.dice.luka_jurkic.rest.resources;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeResource {

    private String title;

    private String servings;

    private String ingredients;

    private String instructions;
}
