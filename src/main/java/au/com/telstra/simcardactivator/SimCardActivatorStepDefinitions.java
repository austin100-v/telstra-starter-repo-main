package com.example.telstra.repository;
import com.example.telstra.model.User;

public class SimCardActivatorStepDefinitions {

    private RestTemplate restTemplate = new RestTemplate();
    private ResponseEntity<String> response;

    @Given("a SIM card with ICCID {string}")
    public void givenSimCard(String iccid) {
        // Set up or record the ICCID in the context
        // This can be used later in the test
        this.iccid = iccid;
    }

    @When("I submit an activation request for the SIM card")
    public void submitActivationRequest() {
        String url = "http://localhost:8080/activate";
        HttpEntity<SimActivationRequest> entity = new HttpEntity<>(new SimActivationRequest(iccid));
        response = restTemplate.postForEntity(url, entity, String.class);
    }

    @Then("the SIM card activation should be successful")
    public void verifySuccessfulActivation() {
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals("Activation successful", response.getBody());
    }

    @Then("the SIM card activation should fail")
    public void verifyFailedActivation() {
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Assert.assertEquals("Activation failed", response.getBody());
    }

    @Then("the activation record should be stored in the database with a status of {string}")
    public void verifyDatabaseRecord(String status) {
        // Query the database and assert the record matches the status
    }
}
