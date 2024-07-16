package hr.dice.luka_jurkic.constants;

import hr.dice.luka_jurkic.persistence.entity.IngredientCategory;

public class IngredientTestConstants {

    public static Long INGREDIENT_ID = 14L;
    public static String INGREDIENT_NAME = "chilli";
    public static IngredientCategory INGREDIENT_CATEGORY = IngredientCategory.VEGETABLE;
    public static String UPDATED_INGREDIENT_NAME = "Chilli";
    public static IngredientCategory UPDATED_INGREDIENT_CATEGORY = IngredientCategory.SPICE;
    public static String ANOTHER_INGREDIENT_NAME = "onion";
    public static IngredientCategory ANOTHER_INGREDIENT_CATEGORY = IngredientCategory.VEGETABLE;

    public static Long NON_EXISTING_INGREDIENT_ID = 69L;

    public static String NON_EXISTING_INGREDIENT_NAME = "salt";
    public static IngredientCategory NON_EXISTING_INGREDIENT_CATEGORY = IngredientCategory.SPICE;

    public static Integer INGREDIENTS_COUNT = 10;
    public static IngredientCategory GET_BY_CATEGORY_INGREDIENT_CATEGORY = IngredientCategory.MEAT;
    public static IngredientCategory GET_BY_CATEGORY_NON_EXISTING_INGREDIENT_CATEGORY = IngredientCategory.LIQUID;
    public static String INVALID_INGREDIENT_CATEGORY = "EXOTIC";
    public static String MISTYPED_INGREDIENT_CATEGORY = "meat";
    public static Integer GET_BY_CATEGORY_INGREDIENTS_COUNT = 3;

    public static final String INGREDIENT_NOT_FOUND = "ingredient__not_found";
    public static final String INGREDIENT_BAD_REQUEST = "Bad Request";
    public static final String INGREDIENT_ALREADY_EXISTS = "ingredient__already_exists";

    public static Integer EMPTY_LIST_COUNT = 0;
}
