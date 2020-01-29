package org.saharsh.samples.quarkus.service;

import java.util.Map;
import java.util.Optional;

import org.saharsh.samples.quarkus.model.StoredValue;

/**
 * Simple Storage Service used to store arbitrary text values
 *
 * @author Saharsh Singh
 *
 */
public interface StorageService {

	/**
	 * Create an entry in storage for new value to store
	 *
	 * @param value text value to store
	 *
	 * @return {@link StoredValue} object containing value and storage metadata
	 */
	StoredValue store(String value);

	/**
	 * Replace value mapped to specified ID with new value
	 *
	 * @param id
	 * @param newValue
	 *
	 * @return either updated {@link StoredValue} if ID found, or empty
	 *         {@link Optional} otherwise
	 */
	Optional<StoredValue> update(long id, String newValue);

	/**
	 * Delete value mapped to specified ID
	 *
	 * @param id
	 *
	 * @return either deleted {@link StoredValue} if ID found, or empty
	 *         {@link Optional} otherwise
	 */
	Optional<StoredValue> delete(long id);

	/**
	 * Retrieve value mapped to specified ID
	 *
	 * @param id
	 *
	 * @return either mapped {@link StoredValue} if ID found, or empty
	 *         {@link Optional} otherwise
	 */
	Optional<StoredValue> retreiveOne(long id);

	/**
	 * @return map containing all stored values with their IDs used as keys
	 */
	Map<Long, StoredValue> retrieveAll();

	/** @return boolean flag indicating whether storage is ready */
	boolean isReady();

}
