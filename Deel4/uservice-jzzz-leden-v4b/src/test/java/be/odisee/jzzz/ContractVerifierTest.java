package be.odisee.jzzz;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import io.restassured.response.ResponseOptions;

import static org.springframework.cloud.contract.verifier.assertion.SpringCloudContractAssertions.assertThat;
import static com.toomuchcoding.jsonassert.JsonAssertion.assertThatJson;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;

@SuppressWarnings("rawtypes")
public class ContractVerifierTest extends BaseTestClass {

	@Test
	public void validate_getStatusContract() throws Exception {
		// given:
			MockMvcRequestSpecification request = given();


		// when:
			ResponseOptions response = given().spec(request)
					.get("/api/requests/1/state");

		// then:
			assertThat(response.statusCode()).isEqualTo(200);

		// and:
			String responseBody = response.getBody().asString();
			assertThat(responseBody).isEqualTo("PENDING");
	}

	@Test
	public void validate_pOSTnieuwRequestContract() throws Exception {
		// given:
			MockMvcRequestSpecification request = given()
					.header("Content-Type", "application/json")
					.body("{\"title\":\"test \",\"description\":\"i want this to be verified\",\"clientEmail\":\"ahmed@alhilou.be\"}");

		// when:
			ResponseOptions response = given().spec(request)
					.post("/api/requests/AddRequest");

		// then:
			assertThat(response.statusCode()).isEqualTo(201);

		// and:
			DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
			assertThatJson(parsedJson).field("['title']").isEqualTo("test ");
			assertThatJson(parsedJson).field("['status']").isEqualTo("PENDING");
			assertThatJson(parsedJson).field("['description']").isEqualTo("i want this to be verified");
			assertThatJson(parsedJson).field("['clientEmail']").isEqualTo("ahmed@alhilou.be");
	}

}
