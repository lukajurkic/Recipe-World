package hr.dice.luka_jurkic.persistence.entity;

import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RecipeEntity.class)
public abstract class RecipeEntity_ extends hr.dice.luka_jurkic.persistence.entity.BaseEntity_ {

	public static volatile ListAttribute<RecipeEntity, RecipeImageEntity> images;
	public static volatile ListAttribute<RecipeEntity, CommentEntity> comments;
	public static volatile SingularAttribute<RecipeEntity, String> name;
	public static volatile ListAttribute<RecipeEntity, RecipeIngredientEntity> ingredients;
	public static volatile SingularAttribute<RecipeEntity, UserEntity> user;
	public static volatile ListAttribute<RecipeEntity, StepEntity> steps;

	public static final String IMAGES = "images";
	public static final String COMMENTS = "comments";
	public static final String NAME = "name";
	public static final String INGREDIENTS = "ingredients";
	public static final String USER = "user";
	public static final String STEPS = "steps";

}

