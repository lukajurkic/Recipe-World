package hr.dice.luka_jurkic.persistence.entity;

import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(IngredientEntity.class)
public abstract class IngredientEntity_ extends hr.dice.luka_jurkic.persistence.entity.BaseEntity_ {

	public static volatile ListAttribute<IngredientEntity, IngredientImageEntity> images;
	public static volatile SingularAttribute<IngredientEntity, String> name;
	public static volatile SingularAttribute<IngredientEntity, IngredientCategory> category;

	public static final String IMAGES = "images";
	public static final String NAME = "name";
	public static final String CATEGORY = "category";

}

