package org.saharsh.samples.quarkus.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.saharsh.samples.quarkus.model.StoredValue;
import org.saharsh.samples.quarkus.persistence.PersistedValue;

@ApplicationScoped
public class PersistentStorageService implements StorageService {

	private final EntityManager db;
	private long tableSize = 0;

	@Inject
	public PersistentStorageService(EntityManager db) {
		this.db = db;
	}

	@Override
	@Transactional
	public StoredValue store(String value) {
		final PersistedValue persistedValue = new PersistedValue();
		persistedValue.setValue(value);
		db.persist(persistedValue);
		return PersistentStorageService.transform(persistedValue).get();
	}

	@Override
	@Transactional
	public Optional<StoredValue> update(long id, String newValue) {
		final PersistedValue persisted = db.find(PersistedValue.class, id);
		if (persisted != null) {
			persisted.setValue(newValue);
			db.persist(persisted);
		}
		return PersistentStorageService.transform(persisted);
	}

	@Override
	@Transactional
	public Optional<StoredValue> delete(long id) {
		final PersistedValue persisted = db.find(PersistedValue.class, id);
		final Optional<StoredValue> stored = PersistentStorageService.transform(persisted);
		if (persisted != null) {
			db.remove(persisted);
		}
		return stored;
	}

	@Override
	public Optional<StoredValue> retreiveOne(long id) {
		return PersistentStorageService.transform(db.find(PersistedValue.class, id));
	}

	@Override
	public Map<Long, StoredValue> retrieveAll() {
		final TypedQuery<PersistedValue> query = db.createNamedQuery("PersistedValue.findAll", PersistedValue.class);
		final List<PersistedValue> persistedList = query.getResultList();
		final Map<Long, StoredValue> mapOfAllStored = new HashMap<>();
		persistedList.forEach(persisted -> {
			mapOfAllStored.put(persisted.getId(), PersistentStorageService.transform(persisted).get());
		});
		return mapOfAllStored;
	}

	@Override
	public boolean isReady() {
		try {
			final Query query = db.createNamedQuery("PersistedValue.count");
			tableSize = (Long) query.getSingleResult();
			return true;
		} catch (final Exception e) {
			return false;
		}
	}

	@Gauge(unit = "count")
	public long size() {
		return tableSize;
	}

	private static Optional<StoredValue> transform(PersistedValue persisted) {
		StoredValue stored = null;
		if (persisted != null) {
			stored = new StoredValue(
					persisted.getId(),
					persisted.getValue(),
					persisted.getDateCreated().getTime(),
					persisted.getLastUpdated().getTime()
					);
		}
		return Optional.ofNullable(stored);
	}

}
