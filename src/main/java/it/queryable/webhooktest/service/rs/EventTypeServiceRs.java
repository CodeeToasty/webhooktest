package it.queryable.webhooktest.service.rs;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import it.queryable.api.service.RsRepositoryServiceV3;
import it.queryable.webhooktest.management.AppConstants;
import it.queryable.webhooktest.model.EventType;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path(AppConstants.EVENTSTYPE_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Singleton
public class EventTypeServiceRs extends RsRepositoryServiceV3<EventType, String> {

	public EventTypeServiceRs() {
		super(EventType.class);
	}

	@Override
	protected String getDefaultOrderBy() {
		return "not_set";
	}

	@Override
	public PanacheQuery<EventType> getSearch(String orderBy) throws Exception {
		PanacheQuery<EventType> search;
		Sort sort = sort(orderBy);
		if (sort != null) {
			search = EventType.find(null, sort);
		} else {
			search = EventType.find(null);
		}
		if (nn("obj.active")) {
			Boolean valueof = _boolean("obj.active");
			search.filter("obj.active", Parameters.with("active", valueof));
		}
		return search;
	}
}