package ru.msakhterov.crm_common.request.decorators;

import ru.msakhterov.crm_common.request.requests.Request;

public abstract class RequestResponse {
    private Request request;

    public RequestResponse(Request request) {
        this.request = request;
    }

}
