package org.saharsh.samples.quarkus.persistence;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity
@Table(name = "vals")
@NamedQuery(name = "PersistedValue.findAll", query = "SELECT pv FROM PersistedValue pv")
@NamedQuery(name = "PersistedValue.count", query = "SELECT count(pv) FROM PersistedValue pv")
public class PersistedValue {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "value", length = 255, nullable = false)
	private String value;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created")
	@Generated(GenerationTime.INSERT)
	private Date dateCreated;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_updated")
	@Generated(GenerationTime.ALWAYS)
	private Date lastUpdated;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@Override
	public String toString() {
		return "PersistedValue [id=" + id + ", value=" + value + ", dateCreated=" + dateCreated + ", lastUpdated="
				+ lastUpdated + "]";
	}

}
