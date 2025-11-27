package be.odisee.jzzz.controller;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import be.odisee.jzzz.domain.*;
import be.odisee.jzzz.helper.*;

import be.odisee.jzzz.service.OntvangstWebfrontService;

@Controller
@RequestMapping("/")
public class OntvangstController {
	
	@Autowired
	protected OntvangstWebfrontService ontvangstWebFrontService = null;
	
	/**
	 * Helpermethode: datum van vandaag op model zetten
	 * en instellen als er op vandaag nog geen evenement is
	 * 
	 * @param model
	 */
	private void zetEvenementDatum(ModelMap model) {
		Evenement evenement = ontvangstWebFrontService.zoekEvenementVandaag();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");
		String evenementDatum = dateFormat.format(evenement.getDatum());
		model.addAttribute("today",evenementDatum);
	}
	
	/**
	 * Informatie bijeenbrengen om het zoekscherm te tonen
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value={"/","/home.html", "/index.html", "/zoekleden.html"}, method=RequestMethod.GET)
	public String index(ModelMap model) {
		
		// Datum van vandaag op het model zetten
		zetEvenementDatum(model);
		
		// Formulier om opzoekpatronen in te geven voorbereiden met leeg Lid-object
		Lid lid = new Lid();
		model.addAttribute("zoeklid", lid);
		
		return "index";
	}
	
	/**
	 * Een lijst van leden opzoeken die voldoet dan voornaam + familienaamcriteria
	 * 
	 * @param zoeklid
	 * @param model
	 * @return
	 */
	@RequestMapping(value={"/","/home.html", "/index.html", "/zoekleden.html"}, method=RequestMethod.POST, params={"zoek"})
	public String zoekLeden(@ModelAttribute("zoeklid") Lid zoeklid, ModelMap model) {
		
		// Datum van vandaag op het model zetten
		zetEvenementDatum(model);
		
		// Zoeken van leden op combinatie van naam + voornaam
		Collection<Lid> leden = ontvangstWebFrontService.zoekLidOpNaam(zoeklid);
		
		// Model opvullen
		model.addAttribute("zoeklid", zoeklid);
		model.addAttribute("leden",leden);
		Keuze keuze = new Keuze();
		model.addAttribute("keuze",keuze);
		
		return "index";
	}
	
	/**
	 * Een formulier tonen om een nieuw lid in te voeren, want er werd op de "Nieuw" knop gedrukt
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value={"/","/home.html", "/index.html", "/zoekleden.html"}, 
										method=RequestMethod.POST, params={"nieuw"})
	public String nieuwLidFormulier(@ModelAttribute("zoeklid") Lid lid, ModelMap model) {
		
		// Model opvullen
		model.addAttribute("nieuwlid", lid);

		return "nieuwlid";
	}

	/**
	 * Er werden gegevens voor een nieuw lid ingevoerd en op de knop DagLidgeld, Jaarlidgeld of Gratis geklikt
	 * 
	 * @param lid een object met de gegevens van het nieuwe lid
	 * @param daglidgeld is niet null als op de Daglidgeld-knop werd gedrukt
	 * @param jaarlidgeld is niet null als op de Jaarlidgeld-knop werd gedrukt
	 * @param gratis is niet null als op de Gratis-knop werd gedrukt
	 * @param model
	 * @return de naam van de view die de gebruiker zal informeren van de goede afloop
	 */
	@RequestMapping(value={"/nieuwlid.html"}, method=RequestMethod.POST)
	public String nieuwLidToevoegen(@ModelAttribute("nieuwlid") Lid lid, 
							@RequestParam(required=false, value="daglidgeld") String daglidgeld, 
							@RequestParam(required=false, value="jaarlidgeld") String jaarlidgeld, 
							@RequestParam(required=false, value="gratis") String gratis, 
							ModelMap model) {
		
		// Datum van vandaag op het model zetten
		zetEvenementDatum(model);
		
		// Formulier om opzoekpatronen in te geven voorbereiden met leeg Lid-object
		Lid zoeklid = new Lid();
		model.addAttribute("zoeklid", zoeklid);
		
		// Wie willen we toevoegen?
		String naam = lid.getVoornaam() + " "+ lid.getFamilienaam();

		// De actie is afhankelijk van de knop die gedrukt werd
		if (daglidgeld != null) {
			lid = ontvangstWebFrontService.nieuwlidDaglidgeld(lid);
			model.addAttribute("boodschap", "Daglidgeld genoteerd voor nieuw lid " + naam + " en id " + lid.getId());
		} else if (jaarlidgeld != null) {
			lid = ontvangstWebFrontService.nieuwlidJaarlidgeld(lid);
			model.addAttribute("boodschap", "Jaarlidgeld genoteerd voor niew lid " + naam + " en id " + lid.getId());
		} else if (gratis != null) {
			String redengratis = lid.getRedengratis();  // want dadelijk zijn we die reden anders kwijt
			lid = ontvangstWebFrontService.voegLidToe(lid);
			lid.setRedengratis(redengratis);    // terug die reden oppikken en bij het lid bewaren
			ontvangstWebFrontService.gratis(lid);
			model.addAttribute("boodschap", "Gratis genoteerd voor nieuw lid " 
								+ naam + " en id " + lid.getId() + " met reden: " + redengratis );
		}
		return "boodschap";
	}


	/**
	 * Er werd een lid gekozen en op de knop DagLidgeld, Jaarlidgeld of Gratis geklikt
	 * 
	 * @param keuze is een object dat het id van het gekozen lid bevat en de eventuele reden voor gratis toegang
	 * @param daglidgeld is niet null als op de Daglidgeld-knop werd gedrukt
	 * @param jaarlidgeld is niet null als op de Jaarlidgeld-knop werd gedrukt
	 * @param gratis is niet null als op de Gratis-knop werd gedrukt
	 * @param model
	 * @return de naam van de view die de gebruiker zal informeren van de goede afloop
	 */
	@RequestMapping(value={"/kieslid.html"}, method=RequestMethod.POST)
	public String bestaandLidActie(@ModelAttribute("keuze") Keuze keuze, 
							@RequestParam(required=false, value="daglidgeld") String daglidgeld, 
							@RequestParam(required=false, value="jaarlidgeld") String jaarlidgeld, 
							@RequestParam(required=false, value="gratis") String gratis, 
							ModelMap model) {
		
		// Datum van vandaag op het model zetten
		zetEvenementDatum(model);
		
		// Formulier om opzoekpatronen in te geven voorbereiden met leeg Lid-object
		Lid lid = new Lid();
		model.addAttribute("zoeklid", lid);

		// Maar het belangrijkste is natuurlijk wie van de leden er gekozen werd
		Lid gekozenLid = ontvangstWebFrontService.zoekLidOpId(keuze.getSelectie());
		String naam = gekozenLid.getVoornaam() + " "+ gekozenLid.getFamilienaam();

		// De actie is afhankelijk van de knop die gedrukt werd
		if (daglidgeld != null) {
			ontvangstWebFrontService.daglidgeld(gekozenLid);
			model.addAttribute("boodschap", "Daglidgeld genoteerd voor " + naam );
		} else if (jaarlidgeld != null) {
			ontvangstWebFrontService.jaarlidgeld(gekozenLid);
			model.addAttribute("boodschap", "Jaarlidgeld genoteerd voor " + naam );
		} else if (gratis != null) {
			String redengratis = keuze.getRedengratis();
			gekozenLid.setRedengratis(redengratis);
			ontvangstWebFrontService.gratis(gekozenLid);
			model.addAttribute("boodschap", "Gratis genoteerd voor " + naam + " met reden: " + redengratis );
		}
		
		return "boodschap";
	}

	/**
	 * Toont een lijst van de aanwezigen
	 * 
	 * @param model
	 * @return de naam van de view die de lijst zal tonen
	 */
	@RequestMapping(value={"/aanwezig.html"}, method=RequestMethod.GET)
	public String aanwezig(ModelMap model) {

		// Datum van vandaag op het model zetten
		zetEvenementDatum(model);
		
		// Zoeken van leden op combinatie van naam + voornaam
		List<Aanwezigheid> aanwezigheden = 
				ontvangstWebFrontService.toonAanwezigheden(ontvangstWebFrontService.zoekEvenementVandaag());
		
		// Model opvullen
		model.addAttribute("aanwezigheden", aanwezigheden);
		model.addAttribute("kastotaal", ontvangstWebFrontService.kastotaal(aanwezigheden));

		return "aanwezigen";
	}
}
