package ru.msakhterov.crm_common.request.requests;

import ru.msakhterov.crm_common.entity.Entity;

public abstract class RequestMaker {

    public abstract Request createRequest(Entity entity);
}
