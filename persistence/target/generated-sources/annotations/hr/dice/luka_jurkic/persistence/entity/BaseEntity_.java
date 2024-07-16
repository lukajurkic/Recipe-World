package hr.dice.luka_jurkic.persistence.entity;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.Instant;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BaseEntity.class)
public abstract class BaseEntity_ {

	public static volatile SingularAttribute<BaseEntity, Instant> createdAt;
	public static volatile SingularAttribute<BaseEntity, Instant> modifiedAt;
	public static volatile SingularAttribute<BaseEntity, Long> id;
	public static volatile SingularAttribute<BaseEntity, Integer> version;

	public static final String CREATED_AT = "createdAt";
	public static final String MODIFIED_AT = "modifiedAt";
	public static final String ID = "id";
	public static final String VERSION = "version";

}

