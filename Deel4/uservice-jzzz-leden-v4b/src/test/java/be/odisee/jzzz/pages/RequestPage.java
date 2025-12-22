package be.odisee.jzzz.pages;


import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class RequestPage {

    WebDriver driver;

    public RequestPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openRequestPage() {
        driver.navigate().to("http://localhost:8080");
        driver.findElement(By.name("RequestTab")).click();
    }

    public void enterTitel(String titel) {
        driver.findElement(By.name("titel")).sendKeys(titel);
    }

    public void enterBeschrijving(String beschrijving) {
        driver.findElement(By.name("beschrijving")).sendKeys(beschrijving);
    }

    public void enterEmailKlant(String email) {
        driver.findElement(By.name("emailKlant")).sendKeys(email);
    }

    public void clickCreate() {
        driver.findElement(By.name("createRequest")).click();
    }
}
