package hr.dice.luka_jurkic.persistence.entity;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(IngredientImageEntity.class)
public abstract class IngredientImageEntity_ extends hr.dice.luka_jurkic.persistence.entity.BaseEntity_ {

	public static volatile SingularAttribute<IngredientImageEntity, IngredientEntity> ingredient;
	public static volatile SingularAttribute<IngredientImageEntity, byte[]> pictureData;
	public static volatile SingularAttribute<IngredientImageEntity, String> description;

	public static final String INGREDIENT = "ingredient";
	public static final String PICTURE_DATA = "pictureData";
	public static final String DESCRIPTION = "description";

}

