package be.odisee.jzzz;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Alert;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class MyStepdefsUI {

    WebDriver driver;
    WebDriverWait wait;

    @Before("@UI")
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @After("@UI")
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("I am on the LegalFly application")
    public void iAmOnTheLegalFlyApplication() {
        driver.get("http://localhost:8080");
        // Click the tab to make the form visible (since it's hidden by default)
        wait.until(ExpectedConditions.elementToBeClickable(By.name("RequestTab"))).click();
    }

    @When("I enter {string} in the title field")
    public void iEnterInTheTitleField(String text) {
        WebElement titleInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("titel")));
        titleInput.clear();
        titleInput.sendKeys(text);
    }

    @When("I enter {string} in the email field")
    public void iEnterInTheEmailField(String text) {
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("emailKlant")));
        emailInput.clear();
        emailInput.sendKeys(text);
    }

    @When("I click the Create button")
    public void iClickTheCreateButton() {
        WebElement createBtn = wait.until(ExpectedConditions.elementToBeClickable(By.name("createRequest")));
        createBtn.click();
    }

    @Then("I should see {string} in the list of requests")
    public void iShouldSeeInTheListOfRequests(String expectedText) {
        try {
            wait.until(ExpectedConditions.alertIsPresent()).accept();
        } catch (Exception e) {
        }

        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.className("requests-list"), expectedText));

        String listContent = driver.findElement(By.className("requests-list")).getText();
        assertThat(listContent).contains(expectedText);
    }

    @When("I create a request via the UI with title {string}")
    public void iCreateARequestWithTitle(String title) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("titel"))).sendKeys(title);
        driver.findElement(By.name("emailKlant")).sendKeys("delete@test.com");
        driver.findElement(By.name("createRequest")).click();

        wait.until(ExpectedConditions.alertIsPresent()).accept();
    }

    @When("I click the Delete button for the request {string}")
    public void iClickTheDeleteButtonForTheRequest(String title) {
        String xpath = String.format("//div[contains(@class,'request-item') and .//span[contains(text(), '%s')]]//button[contains(@class, 'btn-danger')]", title);
        WebElement deleteBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        deleteBtn.click();
    }

    @When("I accept the confirmation dialog")
    public void iAcceptTheConfirmationDialog() {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();

        try {
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();
        } catch (Exception e) {
        }
    }

    @Then("I should NOT see {string} in the list of requests")
    public void iShouldNOTSeeInTheListOfRequests(String text) {
        // Simple wait to ensure UI updates
        try { Thread.sleep(1000); } catch (InterruptedException e) {}

        String pageText = driver.findElement(By.className("requests-list")).getText();
        assertThat(pageText).doesNotContain(text);
    }

    @Then("I should see an alert with message {string}")
    public void iShouldSeeAnAlertWithMessage(String expectedMessage) {
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        assertThat(alertText).isEqualTo(expectedMessage);
        alert.accept();
    }
}