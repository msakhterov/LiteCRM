package ru.msakhterov.crm_common.request;

import ru.msakhterov.crm_common.entity.Entity;
import ru.msakhterov.crm_common.request.requests.*;

public class RequestFabric {
//    private static RequestFabric requestFabric = new RequestFabric();

    public static RequestFabric getRequestFabric() {
        return new RequestFabric();
    }

    public Request makeRequest(String subject, Entity entity) {
        RequestMaker maker = createRequestMaker(subject);
        return maker.createRequest(entity);
    }

    private RequestMaker createRequestMaker(String subject) {
        switch (subject) {
            case RequestSubjects.AUTH_REQUEST:
                return new AuthRequestMaker();
            case RequestSubjects.REG_REQUEST:
                return new RegRequestMaker();
            case RequestSubjects.AUTH_ACCEPT:
                return new AuthAcceptRequestMaker();
            case RequestSubjects.REG_ACCEPT:
                return new RegAcceptRequestMaker();
            default:
                throw new IllegalArgumentException("RequestType not supported.");
        }
    }


}

