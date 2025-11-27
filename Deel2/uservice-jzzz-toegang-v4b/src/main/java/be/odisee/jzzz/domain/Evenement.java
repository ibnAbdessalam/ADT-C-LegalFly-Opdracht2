package be.odisee.jzzz.domain;

import java.util.Date;

import jakarta.persistence.*;

@Entity
public class Evenement {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id; 
	private Date datum;
	private String omschrijving;
	private Date created;
	private Date updated;
	
	public Evenement() {}

	public Evenement(Date datum, String omschrijving) {
		this.datum = datum;
		this.omschrijving = omschrijving;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public String getOmschrijving() {
		return omschrijving;
	}

	public void setOmschrijving(String omschrijving) {
		this.omschrijving = omschrijving;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	@PrePersist
	protected void onCreate() {
		updated = created = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		updated = new Date();
	}
}
