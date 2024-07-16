package hr.dice.luka_jurkic.persistence.entity;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RecipeIngredientEntity.class)
public abstract class RecipeIngredientEntity_ extends hr.dice.luka_jurkic.persistence.entity.BaseEntity_ {

	public static volatile SingularAttribute<RecipeIngredientEntity, BigDecimal> amount;
	public static volatile SingularAttribute<RecipeIngredientEntity, IngredientUnit> unit;
	public static volatile SingularAttribute<RecipeIngredientEntity, IngredientEntity> ingredient;
	public static volatile SingularAttribute<RecipeIngredientEntity, RecipeEntity> recipe;

	public static final String AMOUNT = "amount";
	public static final String UNIT = "unit";
	public static final String INGREDIENT = "ingredient";
	public static final String RECIPE = "recipe";

}

