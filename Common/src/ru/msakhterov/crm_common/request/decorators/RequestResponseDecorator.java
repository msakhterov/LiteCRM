package ru.msakhterov.crm_common.request.decorators;

import ru.msakhterov.crm_common.request.requests.Request;

public class RequestResponseDecorator implements Response {
    private Response response;

    public RequestResponseDecorator(Response response){
        this.response = response;
    }

    @Override
    public String getLogMessage(Request request) {
        return response.getLogMessage(request);
    }

}
