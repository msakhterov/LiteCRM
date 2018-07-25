package ru.msakhterov.crm_common.request.decorators;

import ru.msakhterov.crm_common.entity.User;
import ru.msakhterov.crm_common.request.requests.RegAcceptRequest;
import ru.msakhterov.crm_common.request.requests.Request;

public class RegRequestResponse extends RequestResponse {
    private RegAcceptRequest regAcceptRequest;

    public RegRequestResponse(Request request) {
        super(request);
        regAcceptRequest = (RegAcceptRequest) request;
    }

    public String getUserData() {
        User user = (User) regAcceptRequest.getEntity();
        StringBuilder builder = new StringBuilder();
        builder.append(user.getFirstName()).append(" ").append(user.getLastName());
        return builder.toString();
    }


}
