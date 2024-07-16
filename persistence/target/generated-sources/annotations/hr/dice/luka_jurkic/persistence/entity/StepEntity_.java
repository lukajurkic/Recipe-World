package hr.dice.luka_jurkic.persistence.entity;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(StepEntity.class)
public abstract class StepEntity_ extends hr.dice.luka_jurkic.persistence.entity.BaseEntity_ {

	public static volatile SingularAttribute<StepEntity, Integer> number;
	public static volatile SingularAttribute<StepEntity, RecipeEntity> recipe;
	public static volatile SingularAttribute<StepEntity, String> description;

	public static final String NUMBER = "number";
	public static final String RECIPE = "recipe";
	public static final String DESCRIPTION = "description";

}

