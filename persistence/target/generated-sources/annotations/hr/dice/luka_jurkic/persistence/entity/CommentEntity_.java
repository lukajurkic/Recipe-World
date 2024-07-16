package hr.dice.luka_jurkic.persistence.entity;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CommentEntity.class)
public abstract class CommentEntity_ extends hr.dice.luka_jurkic.persistence.entity.BaseEntity_ {

	public static volatile SingularAttribute<CommentEntity, RecipeEntity> recipe;
	public static volatile SingularAttribute<CommentEntity, String> text;
	public static volatile SingularAttribute<CommentEntity, String> title;
	public static volatile SingularAttribute<CommentEntity, UserEntity> user;

	public static final String RECIPE = "recipe";
	public static final String TEXT = "text";
	public static final String TITLE = "title";
	public static final String USER = "user";

}

