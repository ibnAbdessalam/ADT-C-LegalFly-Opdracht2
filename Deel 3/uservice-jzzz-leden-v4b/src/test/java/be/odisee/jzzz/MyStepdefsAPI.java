package be.odisee.jzzz;

import be.odisee.jzzz.domain.legalFly.Request;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MyStepdefsAPI {

    @Autowired
    private WebTestClient webTestClient;

    private String endpoint;
    private EntityExchangeResult<String> lastResult;
    private Long lastCreatedId;

    @Given("I use the API endpoint {string}")
    public void iUseTheAPIEndpoint(String url) {
        this.endpoint = url;
    }

    // Matches: When I send a POST request with the following data:
    @When("I send a POST request with the following data:")
    public void iSendAPOSTRequestWithTheFollowingData(String jsonBody) {
        this.lastResult = webTestClient.post()
                .uri(endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(jsonBody)
                .exchange()
                .expectBody(String.class)
                .returnResult();
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

    // Helper step for UPDATE/DELETE scenarios
    @Given("I have a request with title {string}")
    public void iHaveARequestWithTitle(String title) {
        String json = String.format("{\"title\": \"%s\", \"clientEmail\": \"test@test.com\"}", title);

        EntityExchangeResult<Request> result = webTestClient.post()
                .uri("/api/requests")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(json)
                .exchange()
                .expectBody(Request.class)
                .returnResult();

        this.lastCreatedId = result.getResponseBody().getId();
    }

    @When("I send a PUT request to update title to {string}")
    public void iSendAPUTRequestToUpdateTitleTo(String newTitle) {
        String json = String.format("{\"title\": \"%s\", \"clientEmail\": \"test@test.com\", \"status\": \"PENDING\"}", newTitle);

        this.lastResult = webTestClient.put()
                .uri(endpoint + "/" + lastCreatedId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(json)
                .exchange()
                .expectBody(String.class)
                .returnResult();
    }

    @When("I send a DELETE request")
    public void iSendADELETERequest() {
        this.lastResult = webTestClient.delete()
                .uri(endpoint + "/" + lastCreatedId)
                .exchange()
                .expectBody(String.class)
                .returnResult();
    }
}