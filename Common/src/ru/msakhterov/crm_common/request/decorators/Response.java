package ru.msakhterov.crm_common.request.decorators;

import ru.msakhterov.crm_common.entity.Entity;
import ru.msakhterov.crm_common.request.requests.Request;

public interface Response {

    String getLogMessage(Request request);

}
