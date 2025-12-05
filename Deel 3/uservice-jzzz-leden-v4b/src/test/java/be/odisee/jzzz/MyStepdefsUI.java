package be.odisee.jzzz;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class MyStepdefsUI {
    WebDriver driver;
    // informatie voor the when
    String titel = "test";
    String beschrijving = "beschrijving om te testen";
    String emailKlant = "test.email@gmail.com";

    //
    @Before("@UI")
    public void before() {
    driver = new ChromeDriver();

    }

    @After
    public void tearDown() {
        driver.close();
    }

    @Given("^User is on the Request page$")
    public void userIsOnTheRequestPage() {
        driver.navigate().to("http://localhost:8082");
        driver.findElement(By.name("RequestTab")).click();
    }

    @When("The user enters a title for the request")
    public void theUserEntersATitleForTheRequest() {
        driver.findElement(By.name("titel")).sendKeys(titel);
    }

    @And("The user enters a description for the request")
    public void theUserEntersADescriptionForTheRequest() {
        driver.findElement(By.name("beschrijving")).sendKeys(beschrijving);
    }

    @And("The user enters the mail of the client")
    public void theUserEntersTheMailOfTheClient() {
        driver.findElement(By.name("emailKlant")).sendKeys(emailKlant);
    }

    @And("Enters the create button")
    public void entersTheCreateButton() {
        driver.findElement(By.name("createRequest")).click();
    }

    @And("The request is shown in the list of requests")
    public void theRequestIsShownInTheListOfRequests() {

    }
}
