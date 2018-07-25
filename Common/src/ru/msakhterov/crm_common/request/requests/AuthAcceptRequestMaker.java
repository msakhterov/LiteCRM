package ru.msakhterov.crm_common.request.requests;

import ru.msakhterov.crm_common.entity.Entity;

public class AuthAcceptRequestMaker extends RequestMaker {

    @Override
    public Request createRequest(Entity entity) {
        return new AuthAcceptRequest(entity);
    }
}
