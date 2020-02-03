package org.saharsh.samples.quarkus.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.saharsh.samples.quarkus.model.StoredValue;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection(fields = false, methods = true)
@ApplicationScoped
public class InMemoryStorageService implements StorageService {

	private final AtomicLong idCounter = new AtomicLong();
	private final Map<Long, StoredValue> store = new HashMap<>();
	private final Map<Long, StoredValue> storeView = Collections.unmodifiableMap(store);

	@Override
	public StoredValue store(String value) {

		final long now = System.currentTimeMillis();
		final StoredValue storedValue = new StoredValue(idCounter.incrementAndGet(), value, now, now);

		store.put(storedValue.getId(), storedValue);

		return storedValue;
	}

	@Override
	public Optional<StoredValue> update(long id, String newValue) {

		final StoredValue existing = store.get(id);
		if (existing == null) {
			return Optional.empty();
		}

		final StoredValue storedValue = new StoredValue(existing.getId(), newValue, existing.getCreated(),
				System.currentTimeMillis());
		store.put(storedValue.getId(), storedValue);
		return Optional.of(storedValue);
	}

	@Override
	public Optional<StoredValue> delete(long id) {
		final StoredValue deleted = store.remove(id);
		return deleted == null ? Optional.empty() : Optional.of(deleted);
	}

	@Override
	public Optional<StoredValue> retreiveOne(long id) {
		final StoredValue retrieved = store.get(id);
		return retrieved == null ? Optional.empty() : Optional.of(retrieved);
	}

	@Override
	public Map<Long, StoredValue> retrieveAll() {
		return storeView;
	}

	@Override
	public boolean isReady() {
		return true;
	}

	@Gauge(unit = "count")
	public long size() {
		return store.size();
	}

}
