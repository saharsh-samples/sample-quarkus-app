package org.saharsh.samples.quarkus.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StoredValue {

	private final long id;
	private final String value;
	private final long created;
	private final long updated;

	@JsonCreator
	public StoredValue(@JsonProperty("id") long id, @JsonProperty("value") String value,
			@JsonProperty("created") long created, @JsonProperty("updated") long updated) {
		this.id = id;
		this.value = value;
		this.created = created;
		this.updated = updated;
	}

	@JsonProperty("id")
	public long getId() {
		return id;
	}

	@JsonProperty("value")
	public String getValue() {
		return value;
	}

	@JsonProperty("created")
	public long getCreated() {
		return created;
	}

	@JsonProperty("updated")
	public long getUpdated() {
		return updated;
	}

	@Override
	public String toString() {
		return "Value [id=" + id + ", value=" + value + ", created=" + created + ", updated=" + updated + "]";
	}

}
