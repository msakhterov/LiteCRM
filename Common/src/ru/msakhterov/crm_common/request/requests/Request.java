package ru.msakhterov.crm_common.request.requests;

import ru.msakhterov.crm_common.entity.Entity;

import java.io.Serializable;

public interface Request extends Serializable {

    String getRequestSubject();

    Entity getEntity();
}
