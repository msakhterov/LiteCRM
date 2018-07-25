package ru.msakhterov.crm_common.request.requests;

import ru.msakhterov.crm_common.entity.Entity;
import ru.msakhterov.crm_common.request.RequestSubjects;

public class RegAcceptRequest implements Request {

    private static final String requestSubject = RequestSubjects.REG_ACCEPT;
    private Entity entity;

    RegAcceptRequest(Entity entity) {
        this.entity = entity;
    }

    @Override
    public String getRequestSubject() {
        return requestSubject;
    }

    public Entity getEntity() {
        return entity;
    }

}