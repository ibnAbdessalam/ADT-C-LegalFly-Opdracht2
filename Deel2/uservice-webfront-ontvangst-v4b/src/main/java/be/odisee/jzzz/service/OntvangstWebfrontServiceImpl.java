package be.odisee.jzzz.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.client.Traverson;
import org.springframework.hateoas.client.Traverson.TraversalBuilder;

import be.odisee.jzzz.domain.*;
import be.odisee.jzzz.helper.Aanwezigheid;

@Service
public class OntvangstWebfrontServiceImpl implements OntvangstWebfrontService {

	private static final double DAGLIDGELD = 7.00;
	private static final double JAARLIDGELD = 55.00;

	@Override
	public Collection<Lid> zoekLidOpNaam(Lid zoeklid) {
		
		/* 
		 * HATEOAS json formaat vraagt een bijzondere benadering voor volgen links en interpretatie json
   	     * hiervoor nodig om volgende dependencies aan pom.xml toe te voegen
	     * org.springframework.hateoas	spring-hateoas
	     * org.springframework.plugin     spring-plugin-core   
	     * com.jayway.jsonpath            json-path
         */
		Traverson traverson = null;
		try {
			traverson = new Traverson(new URI("http://localhost:9081/lids"), MediaTypes.HAL_JSON);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("voornaam", zoeklid.getVoornaam());
		parameters.put("familienaam", zoeklid.getFamilienaam());
		TraversalBuilder tb = traverson.follow("search", "findByVoornaamStartingWithIgnoreCaseAndFamilienaamStartingWithIgnoreCaseOrderByFamilienaam")
										.withTemplateParameters(parameters);
		ParameterizedTypeReference<CollectionModel<Lid>> typeRefDevices = new ParameterizedTypeReference<CollectionModel<Lid>>() {};
		CollectionModel<Lid> resLeden = tb.toObject(typeRefDevices);
		Collection<Lid> leden = resLeden.getContent();

		/*
		 *  Van elk lid noteren of vervaldag voorbij is
		 *  Indien dat het geval is moet de vervaldag in het overzicht opvallend worden voorgesteld
		 */  
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		Date yesterday = cal.getTime();
		for (Lid lid : leden) {
			if (lid.getVervaldag() != null ) {
				if (lid.getVervaldag().before(yesterday)) lid.setVervallen(true);
			}
		}
		return leden;
	}

	@Override
	public Lid voegLidToe(Lid lid) {

		RestTemplate rt = new RestTemplate();
		return rt.postForObject("http://localhost:9081/lids", lid, Lid.class);		
	}

	@Override
	public Lid zoekLidOpId(int id) {

		RestTemplate rt = new RestTemplate();
		Lid lid = rt.getForObject("http://localhost:9081/lids/{id}", Lid.class, id);
		return lid;
	}

	/**
	 * Zoekt het evenement van vandaag en als het er nog niet is, wordt er een evenement gecreëerd
	 */
	@Override
	public Evenement zoekEvenementVandaag() {
		
		Calendar cal = Calendar.getInstance();
		Date today = cal.getTime();
		cal.add(Calendar.DATE, -1);
		Date yesterday = cal.getTime();
		cal.add(Calendar.DATE, +2);
		Date tomorrow = cal.getTime();

		// Hebben we al een evenement vandaag? ( gisteren < vandaag < morgen ... het kon niet anders)
		Evenement evenement = null;
		RestTemplate rt = new RestTemplate();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			// we zitten in een try-catch, want als er op vandaag nog geen evenement is, zouden we anders een error krijgen
			String url = "http://localhost:9082/evenements/search/findByDatumBetween?datumvan="+df.format(yesterday)
																					+"&datumtot="+df.format(tomorrow);
			evenement = rt.getForObject(url, Evenement.class);
		} catch (RestClientException e) {
			// Als er zo geen evenement is, creëren we er een
			if (evenement == null) {
				evenement = new Evenement(today, "Optreden");
				// de toegang microservice zal het Evenement creëren en een object met id retourneren
				evenement = rt.postForObject("http://localhost:9082/evenements", evenement, Evenement.class);
			}
		}
		return evenement;
	}
	
	/**
	 * Helperfunctie (na refactoring gedupliceerde code
	 * werkt volgens betaling van lidgeld ledenstatus bij
	 * en noteert de toegang van het lid
	 */
	private Lid lidgeld(String ledenUrlDeel, Lid persoon, double lidgeld, String reden) {
		RestTemplate rt = new RestTemplate();
		
		// leden microservice gebruiken, tenzij gratis
		if (!ledenUrlDeel.equals("gratis")) {
			persoon = rt.postForObject("http://localhost:9081/"+ledenUrlDeel, persoon, Lid.class);
		}
		
		// toegang microservice
		Evenement evenement = zoekEvenementVandaag();
		Toegang toegang = new Toegang(persoon.getId(),evenement.getId(), lidgeld, reden);
		rt.postForObject("http://localhost:9082/toegangs", toegang, Toegang.class);
		
		return persoon;
	}
	
	@Override
	public void daglidgeld(Lid gekozenLid) {

		lidgeld("bestaandDaglid", gekozenLid, DAGLIDGELD, "daglidgeld betaald");
	}

	@Override
	public void jaarlidgeld(Lid gekozenLid) {
		
		lidgeld("verlengJaarlid", gekozenLid, JAARLIDGELD, "jaarlidgeld betaald");
	}

	@Override
	public Lid nieuwlidDaglidgeld(Lid lid) {

		return lidgeld("nieuwDaglid", lid, DAGLIDGELD, "niew daglidgeld betaald");
	}

	@Override
	public Lid nieuwlidJaarlidgeld(Lid lid) {

		return lidgeld("nieuwJaarlid", lid, JAARLIDGELD, "niew jaarlidgeld betaald");
	}

	@Override
	public Lid gratis(Lid lid) {

		return lidgeld("gratis", lid, 0.00, lid.getRedengratis());
	}

	@Override
	public List<Aanwezigheid> toonAanwezigheden(Evenement evenement) {

		List<Aanwezigheid> aanwezigheden = new ArrayList<Aanwezigheid>();
		
		// weer een HATEOAS resultset oppikken
		Traverson traverson = null;
		try {
			traverson = new Traverson(new URI("http://localhost:9082/toegangs"), MediaTypes.HAL_JSON);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("evenementid", evenement.getId());
		ParameterizedTypeReference<CollectionModel<Toegang>> typeRefDevices = new ParameterizedTypeReference<CollectionModel<Toegang>>() {};
		Collection<Toegang> toegangen = traverson.follow("search", "findByEvenementidOrderByCreated")
													.withTemplateParameters(parameters)
													.toObject(typeRefDevices).getContent(); 
		
		for (Toegang toegang : toegangen) {
			Lid lid = zoekLidOpId(toegang.getLidid());
			Aanwezigheid aanwezigheid = new Aanwezigheid( lid.getVoornaam(), lid.getFamilienaam(), 
															toegang.getBedrag(), toegang.getReden(), toegang.getCreated());
			aanwezigheden.add(aanwezigheid);
		}
		
		return aanwezigheden;
	}

	@Override
	public double kastotaal(List<Aanwezigheid> aanwezigheden) {

		// Toepassing van een Java 8 filter om van een lijst de som van een veld te berekenen 
		return aanwezigheden.stream().mapToDouble(o->o.getBedrag()).sum();
	}
}
