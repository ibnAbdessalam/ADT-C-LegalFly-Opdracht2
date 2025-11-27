package be.odisee.jzzz.domain;

import java.util.Date;

import jakarta.persistence.*;

@Entity
public class Toegang {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id; 
	private int lidid;  // merk op geen Lid object hier ... microservice onafhankelijk
	private int evenementid; // we gaan het heel simpel houden
	double bedrag;
	String reden;
	private Date created;
	private Date updated;
	
	public Toegang() {}

	public Toegang(int lidid, int evenementid, double bedrag, String reden) {
		this.lidid = lidid;
		this.evenementid = evenementid;
		this.bedrag = bedrag;
		this.reden = reden;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLidid() {
		return lidid;
	}

	public void setLidid(int lidid) {
		this.lidid = lidid;
	}

	public int getEvenementid() {
		return evenementid;
	}

	public void setEvenement(int evenementid) {
		this.evenementid = evenementid;
	}

	public double getBedrag() {
		return bedrag;
	}

	public void setBedrag(double bedrag) {
		this.bedrag = bedrag;
	}

	public String getReden() {
		return reden;
	}

	public void setReden(String reden) {
		this.reden = reden;
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

	public void setEvenementid(int evenementid) {
		this.evenementid = evenementid;
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
