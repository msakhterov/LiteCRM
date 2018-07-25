package ru.msakhterov.crm_common.request.decorators;

import ru.msakhterov.crm_common.entity.User;
import ru.msakhterov.crm_common.request.requests.AuthRequest;
import ru.msakhterov.crm_common.request.requests.Request;

public class AuthRequestResponse extends RequestResponse {
    private AuthRequest authRequest;

    public AuthRequestResponse(Request request) {
        super(request);
        authRequest = (AuthRequest) request;
    }

    public String getUserData() {
        User user = (User) authRequest.getEntity();
        StringBuilder builder = new StringBuilder();
        builder.append(user.getFirstName()).append(" ").append(user.getLastName());
        return builder.toString();
    }


}
