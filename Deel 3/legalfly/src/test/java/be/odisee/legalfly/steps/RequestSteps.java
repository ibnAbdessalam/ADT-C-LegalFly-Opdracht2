package be.odisee.legalfly.steps;

import be.odisee.legalfly.Service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RequestSteps {

    @Autowired
    private RequestService requestService;

    private int juristId;
    private int lawId;
    private String content;
    private Exception exception;

}
