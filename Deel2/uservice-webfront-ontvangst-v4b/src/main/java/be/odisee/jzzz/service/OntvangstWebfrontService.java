package be.odisee.jzzz.service;

import java.util.Collection;
import java.util.List;

import be.odisee.jzzz.domain.Evenement;
import be.odisee.jzzz.domain.Lid;
import be.odisee.jzzz.helper.Aanwezigheid;

public interface OntvangstWebfrontService {

	Collection<Lid> zoekLidOpNaam(Lid zoeklid);

	Lid voegLidToe(Lid lid);

	Lid zoekLidOpId(int selectie);

	Evenement zoekEvenementVandaag();  // toegevoegd tbv toegang

	void daglidgeld(Lid gekozenLid);

	void jaarlidgeld(Lid gekozenLid);

	Lid nieuwlidDaglidgeld(Lid lid);

	Lid nieuwlidJaarlidgeld(Lid lid);

	Lid gratis(Lid lid); // toegevoegd tbv toegang

	List<Aanwezigheid> toonAanwezigheden(Evenement evenement); // toegevoegd tbv toegang

	double kastotaal(List<Aanwezigheid> aanwezigheden); // toegevoegd tbv toegang

}
