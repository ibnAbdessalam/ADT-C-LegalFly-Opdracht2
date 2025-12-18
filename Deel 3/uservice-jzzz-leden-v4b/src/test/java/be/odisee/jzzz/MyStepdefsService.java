package be.odisee.jzzz;

import be.odisee.jzzz.domain.legalFly.Request;
import be.odisee.jzzz.service.legalFly.RequestService;

import io.cucumber.datatable.DataTable;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;

import io.cucumber.java.nl.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyStepdefsService {

    @Autowired
    RequestService requestService;

    private Request lastCreatedRequest;
    private List<Request> lastRequestList;

    // ---------------------------
    // Scenario 1: request aanmaken
    // ---------------------------

    @Gegeven("een jurist met id {int} en een wet met id {int}")
    public void eenJuristMetIdEnEenWetMetId(int juristId, int wetId) {
        // In deze test gebruiken we enkel de IDs als context
        // (zoals bij het docentvoorbeeld: geen database setup hier)
        assertThat(juristId).isGreaterThan(0);
        assertThat(wetId).isGreaterThan(0);
    }

    @Wanneer("ik een request aanmaak met de inhoud {string}")
    public void ikEenRequestAanmaakMetDeInhoud(String inhoud) {
        Request request = new Request();
        request.setTitle(inhoud);
        request.setStatus("NIEUW");

        lastCreatedRequest = requestService.createRequest(request);
    }

    @Dan("wordt de request succesvol aangemaakt")
    public void wordtDeRequestSuccesvolAangemaakt() {
        assertThat(lastCreatedRequest).isNotNull();
        assertThat(lastCreatedRequest.getId()).isNotNull();
        assertThat(lastCreatedRequest.getTitle()).isEqualTo("Test request");
    }

    // ---------------------------
    // Scenario 2: alle requests ophalen
    // ---------------------------

    @Wanneer("ik alle requests opvraag")
    public void ikAlleRequestsOpvraag() {
        lastRequestList = requestService.getAllRequests();
    }

    @Dan("ontvang ik een lijst van requests")
    public void ontvangIkEenLijstVanRequests() {
        assertThat(lastRequestList).isNotNull();
        assertThat(lastRequestList).isInstanceOf(List.class);
    }
}
