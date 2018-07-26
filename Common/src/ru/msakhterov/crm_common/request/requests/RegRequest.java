package ru.msakhterov.crm_common.request.requests;

import ru.msakhterov.crm_common.entity.Entity;
import ru.msakhterov.crm_common.request.RequestSubjects;

public class RegRequest implements Request {

    private static final String requestSubject = RequestSubjects.REG_REQUEST;
    private Entity entity;

    RegRequest(Entity entity) {
        this.entity = entity;
    }

    @Override
    public String getRequestSubject() {
        return requestSubject;
    }

    @Override
    public Entity getEntity() {
        return entity;
    }

}