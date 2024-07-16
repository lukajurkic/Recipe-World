package hr.dice.luka_jurkic.persistence.entity;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RecipeImageEntity.class)
public abstract class RecipeImageEntity_ extends hr.dice.luka_jurkic.persistence.entity.BaseEntity_ {

	public static volatile SingularAttribute<RecipeImageEntity, byte[]> pictureData;
	public static volatile SingularAttribute<RecipeImageEntity, RecipeEntity> recipe;
	public static volatile SingularAttribute<RecipeImageEntity, String> description;

	public static final String PICTURE_DATA = "pictureData";
	public static final String RECIPE = "recipe";
	public static final String DESCRIPTION = "description";

}

