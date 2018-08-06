package ru.msakhterov.crm_common.request.decorators;

import ru.msakhterov.crm_common.entity.User;
import ru.msakhterov.crm_common.request.requests.Request;

public class UserRequestResponse extends RequestResponseDecorator {

    public UserRequestResponse(Response response) {
        super(response);
    }

    @Override
    public String getLogMessage(Request request) {
        User user = (User) request.getEntity();
        StringBuilder userDescription = new StringBuilder();
        if(user != null){
            userDescription.append(" Пользователь: ");
            userDescription.append(user.getFirstName());
            userDescription.append(" ");
            userDescription.append(user.getLastName());
        }
        return super.getLogMessage(request) + userDescription.toString();
    }


}
