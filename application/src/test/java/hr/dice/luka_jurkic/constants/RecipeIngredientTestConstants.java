package hr.dice.luka_jurkic.constants;

import java.math.BigDecimal;

public class RecipeIngredientTestConstants {

    public static Long RECIPE_INGREDIENT_ID = 10L;
    public static String RECIPE_INGREDIENT_RECIPE_NAME = "recipe1";
    public static String RECIPE_INGREDIENT_INGREDIENT_NAME = "beef";

    public static Integer RECIPE_INGREDIENTS_COUNT = 5;


    public static String NOT_IN_DATABASE_RECIPE_INGREDIENT_INGREDIENT_NAME = "mashed potato";

    public static String NON_EXISTING_RECIPE_INGREDIENT_RECIPE_NAME = "recipe";
    public static String NON_EXISTING_RECIPE_INGREDIENT_INGREDIENT_NAME = "chicken";
    public static BigDecimal NON_EXISTING_RECIPE_INGREDIENT_AMOUNT = BigDecimal.valueOf(149.99);
    public static String NON_EXISTING_RECIPE_INGREDIENT_UNIT = "G";

    public static String UPDATED_RECIPE_INGREDIENT_INGREDIENT_NAME = "curry";
    public static BigDecimal UPDATED_RECIPE_INGREDIENT_AMOUNT = BigDecimal.valueOf(2.6);
    public static String UPDATE_RECIPE_INGREDIENT_UNIT = "G";

    public static String RECIPE_INGREDIENT_BAD_REQUEST = "Bad Request";
    public static String RECIPE_INGREDIENT_ALREADY_EXISTS = "ingredient_in_recipe__already_exists";
}
