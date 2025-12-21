package be.odisee.jzzz;

import be.odisee.jzzz.dao.legalFly.RequestRepository;
import be.odisee.jzzz.domain.legalFly.Request;
import be.odisee.jzzz.service.legalFly.RequestService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@Service
public class MyStepdefsService {

    @Autowired
    RequestService requestService;

    @Autowired
    RequestRepository requestRepository;

    private Request lastRequest;
    private Exception lastException;

    @Given("there are no requests")
    public void thereAreNoRequests() {
        // Crucial fix: Clean the database before every scenario
        requestRepository.deleteAll();
    }

    @When("I create a request with title {string}")
    public void iCreateARequestWithTitle(String title) {
        Request req = new Request();
        req.setTitle(title);
        req.setClientEmail("test@example.com");
        lastRequest = requestService.createRequest(req);
    }

    @Then("the created request should have status {string}")
    @Then("the request should have status {string}")
    public void theRequestShouldHaveStatus(String expectedStatus) {
        assertThat(lastRequest.getStatus()).isEqualTo(expectedStatus);
    }

    @When("I try to create a request with an empty title")
    public void iTryToCreateARequestWithAnEmptyTitle() {
        Request req = new Request();
        req.setTitle("");
        try {
            requestService.createRequest(req);
        } catch (Exception e) {
            lastException = e;
        }
    }

    @Then("I should receive an error message {string}")
    public void iShouldReceiveAnErrorMessage(String expectedMessage) {
        assertThat(lastException).isNotNull();
        assertThat(lastException.getMessage()).isEqualTo(expectedMessage);
    }

    @When("I update the request status to {string}")
    public void iUpdateTheRequestStatusTo(String newStatus) {
        lastRequest.setStatus(newStatus);
        requestService.updateRequest(lastRequest.getId(), lastRequest);
        lastRequest = requestService.getRequestById(lastRequest.getId()).orElse(null);
    }

    @When("I delete the request")
    public void iDeleteTheRequest() {
        requestService.deleteRequest(lastRequest.getId());
    }

    @Then("the request should no longer exist")
    public void theRequestShouldNoLongerExist() {
        boolean exists = requestService.getRequestById(lastRequest.getId()).isPresent();
        assertThat(exists).isFalse();
    }

    @Then("the request list should contain {int} request")
    public void theRequestListShouldContainRequest(int count) {
        assertThat(requestService.getAllRequests()).hasSize(count);
    }
}