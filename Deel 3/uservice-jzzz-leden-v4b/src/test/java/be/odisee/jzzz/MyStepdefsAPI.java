package be.odisee.jzzz;

import be.odisee.jzzz.domain.legalFly.Request; // Make sure this import matches your package structure
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MyStepdefsAPI {

    @Autowired
    private WebTestClient webTestClient;

    private String endpoint;

    // We store the *Result* (data), not the *Spec* (stream), so we can read it multiple times
    private EntityExchangeResult<String> lastResult;

    @Given("I use the API endpoint {string}")
    public void iUseTheAPIEndpoint(String url) {
        this.endpoint = url;
    }

    @When("I send a POST request with the following data:")
    public void iSendAPOSTRequestWithTheFollowingData(DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);

        // Construct the Request object
        Request request = new Request(); // Assuming you have a default constructor or setters
        request.setTitle(data.get("title"));
        request.setDescription(data.get("description"));
        request.setClientEmail(data.get("clientEmail"));
        // If your Request class constructor is different, adjust above

        this.lastResult = webTestClient.post()
                .uri(endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectBody(String.class) // Consumes stream here
                .returnResult();          // Stores result in memory
    }

    @When("I send a GET request")
    public void iSendAGETRequest() {
        this.lastResult = webTestClient.get()
                .uri(endpoint)
                .exchange()
                .expectBody(String.class)
                .returnResult();
    }

    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int statusCode) {
        // Check status from the stored result
        assertEquals(statusCode, lastResult.getStatus().value());
    }

    @Then("the response should contain {string}")
    public void theResponseShouldContain(String expectedValue) {
        String body = lastResult.getResponseBody();
        assertNotNull("Response body is null", body);
        assertTrue("Response body did not contain: " + expectedValue, body.contains(expectedValue));
    }

    @Then("the response should contain a list of requests")
    public void theResponseShouldContainAListOfRequests() {
        String body = lastResult.getResponseBody();
        assertNotNull("Response body is null", body);
        assertTrue("Response is not a JSON list", body.trim().startsWith("["));
    }
}