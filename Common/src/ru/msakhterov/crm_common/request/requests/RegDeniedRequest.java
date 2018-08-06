package ru.msakhterov.crm_common.request.requests;

import ru.msakhterov.crm_common.entity.Entity;
import ru.msakhterov.crm_common.request.RequestSubjects;

public class RegDeniedRequest implements Request {

    private static final String requestSubject = RequestSubjects.REG_DENIED;
    private Entity entity;

    RegDeniedRequest(Entity entity) {
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