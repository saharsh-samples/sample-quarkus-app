package org.saharsh.samples.quarkus.resources;

import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.saharsh.samples.quarkus.model.StoredValue;
import org.saharsh.samples.quarkus.service.StorageService;

@Path("/api/values")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ValuesResource {

	private final StorageService storage;

	@Inject
	public ValuesResource(StorageService storage) {
		this.storage = storage;
	}

	@GET
	public Map<Long, StoredValue> retrieveAll() {
		return storage.retrieveAll();
	}

	@POST
	public StoredValue create(String value) {
		return storage.store(value);
	}

	@GET
	@Path("{id: \\d+}")
	public StoredValue retreive(@PathParam("id") long id) {
		final Optional<StoredValue> retrieved = storage.retreiveOne(id);
		if (!retrieved.isPresent()) {
			throw new NotFoundException("No value mapped to id=" + id);
		}
		return retrieved.get();
	}

	@PUT
	@Path("{id: \\d+}")
	public StoredValue update(@PathParam("id") long id, String newValue) {
		final Optional<StoredValue> updated = storage.update(id, newValue);
		if (!updated.isPresent()) {
			throw new NotFoundException("No value mapped to id=" + id);
		}
		return updated.get();
	}

	@DELETE
	@Path("{id: \\d+}")
	public StoredValue delete(@PathParam("id") long id) {
		final Optional<StoredValue> deleted = storage.delete(id);
		if (!deleted.isPresent()) {
			throw new NotFoundException("No value mapped to id=" + id);
		}
		return deleted.get();
	}

}