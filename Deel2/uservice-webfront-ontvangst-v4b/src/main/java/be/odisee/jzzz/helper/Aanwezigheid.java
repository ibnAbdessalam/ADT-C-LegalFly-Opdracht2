package be.odisee.jzzz.helper;

import java.util.Date;

/**
 * Geen echte domeinklasse, maar meer een - nadelig - gevolg van microservices
 * We willen een lijst van aanwezigen
 * De namen zullen komen van de leden microservice
 * De andere informatie zal komen uit de toegang microservice
 * 
 * @author hans
 *
 */
public class Aanwezigheid {

	private int id;
	private String voornaam;
	private String familienaam;
	double bedrag;
	String reden;
	private Date created;

	public Aanwezigheid(String voornaam, String familienaam, double bedrag, String reden, Date created) {
		this.voornaam = voornaam;
		this.familienaam = familienaam;
		this.bedrag = bedrag;
		this.reden = reden;
		this.created = created;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVoornaam() {
		return voornaam;
	}

	public void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
	}

	public String getFamilienaam() {
		return familienaam;
	}

	public void setFamilienaam(String familienaam) {
		this.familienaam = familienaam;
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

	
}
