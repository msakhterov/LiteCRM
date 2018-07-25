package ru.msakhterov.crm_common.request.requests;

import ru.msakhterov.crm_common.entity.Entity;

public class AuthRequestMaker extends RequestMaker {

    @Override
    public Request createRequest(Entity entity) {
        return new AuthRequest(entity);
    }
}
