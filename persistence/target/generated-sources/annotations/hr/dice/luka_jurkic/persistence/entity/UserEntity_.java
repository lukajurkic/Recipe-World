package hr.dice.luka_jurkic.persistence.entity;

import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDate;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserEntity.class)
public abstract class UserEntity_ extends hr.dice.luka_jurkic.persistence.entity.BaseEntity_ {

	public static volatile SingularAttribute<UserEntity, String> firstName;
	public static volatile SingularAttribute<UserEntity, String> lastName;
	public static volatile ListAttribute<UserEntity, RecipeEntity> recipes;
	public static volatile SingularAttribute<UserEntity, String> password;
	public static volatile SingularAttribute<UserEntity, UserRole> role;
	public static volatile ListAttribute<UserEntity, CommentEntity> comments;
	public static volatile SingularAttribute<UserEntity, LocalDate> dateOfBirth;
	public static volatile SingularAttribute<UserEntity, String> username;

	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String RECIPES = "recipes";
	public static final String PASSWORD = "password";
	public static final String ROLE = "role";
	public static final String COMMENTS = "comments";
	public static final String DATE_OF_BIRTH = "dateOfBirth";
	public static final String USERNAME = "username";

}

