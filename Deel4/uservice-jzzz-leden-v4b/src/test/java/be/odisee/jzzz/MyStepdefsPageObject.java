package be.odisee.jzzz;
import be.odisee.jzzz.pages.RequestPage;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class MyStepdefsPageObject {
    WebDriver driver;
    RequestPage requestPage;

    String titel = "test";
    String beschrijving = "beschrijving om te testen";
    String emailKlant = "test.email@gmail.com";

    @Before("@UI")
    public void before() {
        driver = new ChromeDriver();
        requestPage = new RequestPage(driver);
    }

    @After
    public void tearDown() {
        driver.close();
    }

    @Given("User is on the Request page")
    public void userIsOnTheRequestPage() {
        requestPage.openRequestPage();
    }

    @When("The user enters a title for the request")
    public void theUserEntersATitleForTheRequest() {
        requestPage.enterTitel(titel);
    }

    @And("The user enters a description for the request")
    public void theUserEntersADescriptionForTheRequest() {
        requestPage.enterBeschrijving(beschrijving);
    }

    @And("The user enters the mail of the client")
    public void theUserEntersTheMailOfTheClient() {
        requestPage.enterEmailKlant(emailKlant);
    }

    @And("Enters the create button")
    public void entersTheCreateButton() {
        requestPage.clickCreate();
    }
}
