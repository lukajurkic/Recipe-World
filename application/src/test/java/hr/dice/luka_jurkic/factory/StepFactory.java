package hr.dice.luka_jurkic.factory;

import hr.dice.luka_jurkic.rest.request.StepRequest;

import static hr.dice.luka_jurkic.constants.StepTestConstants.NON_EXISTING_STEP_DESCRIPTION;

public class StepFactory {

    public static StepRequest validRequest() {
        StepRequest request = new StepRequest();
        request.setDescription(NON_EXISTING_STEP_DESCRIPTION);
        return request;
    }

    public static StepRequest paramRequest(String description) {
        StepRequest request = new StepRequest();
        request.setDescription(description);
        return request;
    }
}
