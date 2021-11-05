package it.queryable.webhooktest.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import it.ness.queryable.annotations.Q;

import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

@Entity
@FilterDef(name = "obj.active", parameters = @ParamDef(name = "active", type = "boolean"))
@Filter(name = "obj.active", condition = "active = :active")
public class EventType extends PanacheEntityBase {

	@Id
	public String name;

	@Q
	public boolean active;

}
