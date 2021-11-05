package it.queryable.webhooktest.service.rs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import io.vertx.core.json.JsonObject;
import it.queryable.api.service.RsRepositoryServiceV3;
import it.queryable.webhooktest.management.AppConstants;
import it.queryable.webhooktest.model.Event;
import it.queryable.webhooktest.model.EventType;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.LocalDateTime;

import static it.queryable.webhooktest.management.AppConstants.EVENTS_PATH;

@Path(EVENTS_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Singleton
public class EventServiceRs extends RsRepositoryServiceV3<Event, String> {


	static ObjectMapper mapper = new ObjectMapper();

	public EventServiceRs() {
		super(Event.class);
	}

	@Override
	protected String getDefaultOrderBy() {
		return "not_set";
	}

	@Override
	public PanacheQuery<Event> getSearch(String orderBy) throws Exception {
		PanacheQuery<Event> search;
		Sort sort = sort(orderBy);
		if (sort != null) {
			search = Event.find(null, sort);
		} else {
			search = Event.find(null);
		}
		if (nn("like.event_type")) {
			search.filter("like.event_type", Parameters.with("event_type", likeParamToLowerCase("like.event_type")));
		}
		if (nn("from.datetime")) {
			LocalDateTime date = LocalDateTime.parse(get("from.datetime"));
			search.filter("from.datetime", Parameters.with("datetime", date));
		}
		if (nn("to.datetime")) {
			LocalDateTime date = LocalDateTime.parse(get("to.datetime"));
			search.filter("to.datetime", Parameters.with("datetime", date));
		}
		return search;
	}

	@POST
	@Path("/test")
	public void jsontest(String json){
		JsonObject response = new JsonObject(json);
		pretty(response);
	}


	@POST
	@Path("/test2")
	public void jsontest2(String json){
		JsonObject response = new JsonObject(json);
		pretty(response);
	}

	@Override
	protected void prePersist(Event event) throws Exception{
		if (event.raw_data == null || event.raw_data.length() == 0)
			throw new Exception("raw_data can't be null");

		if (event.event_type == null)
			throw new Exception("eventype cannot be null");

		//creo l'evento se non è mai stato ricevutos
		if (Event.findById(event.event_type) == null ){
			EventType eventType = new EventType();
			eventType.name = event.event_type;
			eventType.active = true;
			eventType.persist();
		}

	}

	private void pretty(Object o) {
		try {
			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

}