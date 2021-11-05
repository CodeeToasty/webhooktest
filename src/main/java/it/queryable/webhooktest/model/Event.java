package it.queryable.webhooktest.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import it.ness.queryable.annotations.Q;
import it.ness.queryable.annotations.QLike;
import org.hibernate.annotations.GenericGenerator;

import javax.annotation.processing.Generated;
import javax.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

@Entity
@FilterDef(name = "like.event_type", parameters = @ParamDef(name = "event_type", type = "string"))
@Filter(name = "like.event_type", condition = "lower(event_type) LIKE :event_type")
@FilterDef(name = "obj.datetime", parameters = @ParamDef(name = "datetime", type = "LocalDateTime"))
@Filter(name = "obj.datetime", condition = "datetime = :datetime")
@FilterDef(name = "from.datetime", parameters = @ParamDef(name = "datetime", type = "LocalDateTime"))
@Filter(name = "from.datetime", condition = "datetime >= :datetime")
@FilterDef(name = "to.datetime", parameters = @ParamDef(name = "datetime", type = "LocalDateTime"))
@Filter(name = "to.datetime", condition = "datetime <= :datetime")
public class Event extends PanacheEntityBase {

	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "uuid", unique = true)
	@Id
	public String uuid;

	@QLike
	public String event_type;

	@Q
	public LocalDateTime datetime;

	@Lob
	public String raw_data;

}
