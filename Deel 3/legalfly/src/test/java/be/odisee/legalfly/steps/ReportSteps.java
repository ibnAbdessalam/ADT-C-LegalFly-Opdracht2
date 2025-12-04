package be.odisee.legalfly.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReportSteps {

    private Map<String, Object> reportData = new HashMap<>();
    private List<Map<String, Object>> infractions = new ArrayList<>();
    private ResponseEntity<String> response;

    @When("Ik een niewe report wil aanmaken")
    public void ik_een_niewe_report_wil_aanmaken() {
        reportData.clear();
        infractions.clear();
    }

    @And("ik vul {string} in voor het veld {string}")
    public void ik_vul_in_voor_het_veld(String waarde, String veld) {
        if (veld.equalsIgnoreCase("content")) veld = "reportContent";
        if (veld.equalsIgnoreCase("type")) veld = "reportType";
        reportData.put(veld, waarde);
    }

    @And("ik voeg een infraction toe met:")
    public void ik_voeg_een_infraction_toe_met(DataTable dataTable) {
        Map<String, String> infraction = dataTable.asMap(String.class, String.class);
        Map<String, Object> infractionObj = new HashMap<>();
        infractionObj.put("lawId", Integer.parseInt(infraction.get("lawId")));
        infractionObj.put("type", infraction.get("type"));
        infractionObj.put("details", infraction.get("details"));
        infractionObj.put("correctiveMeasure", infraction.get("correctiveMeasure"));
        infractions.add(infractionObj);
    }

    @When("ik verstuur het formulier om een nieuw report aan te maken")
    public void ik_verstuur_het_formulier_om_een_nieuw_report_aan_te_maken() {
        reportData.put("infractions", infractions);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(reportData, headers);
        response = restTemplate.postForEntity("http://localhost:8888/api/reports", request, String.class);
    }

    @Then("wordt het report succesvol aangemaakt")
    public void wordt_het_report_succesvol_aangemaakt() {
        assertNotNull(response);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @And("ik vul {string} voor het veld {string}")
    public void ikVulVoorHetVeld(String waarde, String veld) {
        if (veld.equalsIgnoreCase("content")) veld = "reportContent";
        if (veld.equalsIgnoreCase("type")) veld = "reportType";
        putSmart(reportData, veld, waarde);
    }

    @Given("ik alle reports wilt ophalen")
    public void ikAlleReportsWiltOphalen() {
        reportData.clear();
        infractions.clear();
        response = null;
    }

    @When("ik vraag alle reports op")
    public void ikVraagAlleReportsOp() {
        RestTemplate restTemplate = new RestTemplate();
        response = restTemplate.getForEntity("http://localhost:8888/api/reports", String.class);
    }

    @Then("ontvang ik een lijst van reports")
    public void ontvangIkEenLijstVanReports() {
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Expected 200 OK");
        assertNotNull(response.getBody(), "Body should not be null");
        String body = response.getBody().trim();
        assertTrue(body.startsWith("["), "Expected a JSON array as response");
    }

    private void putSmart(Map<String, Object> map, String key, String value) {
        if (key.equalsIgnoreCase("requestId") || key.equalsIgnoreCase("adminId") || key.equalsIgnoreCase("lawId")) {
            try {
                map.put(key, Integer.parseInt(value));
                return;
            } catch (NumberFormatException ignored) { /* val terug op string */ }
        }
        map.put(key, value);
    }
}