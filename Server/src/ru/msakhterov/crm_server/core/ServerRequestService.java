package ru.msakhterov.crm_server.core;

import ru.msakhterov.crm_common.request.RequestFabric;
import ru.msakhterov.crm_common.request.RequestSubjects;
import ru.msakhterov.crm_common.request.requests.AuthRequest;
import ru.msakhterov.crm_common.request.requests.RegRequest;
import ru.msakhterov.crm_common.request.requests.Request;
import ru.msakhterov.crm_server.network.ClientThread;
import ru.msakhterov.crm_services.RegService;

import static ru.msakhterov.crm_common.logger.Logger.putLog;

public class ServerRequestService {

    public void checkNonAuthRequest(ClientThread client, Request request) {
        switch (request.getRequestSubject()) {
            case RequestSubjects.AUTH_REQUEST:
                request = (AuthRequest) request;

                break;
            case RequestSubjects.REG_REQUEST:
                RegService service = new RegService(client, (RegRequest) request);
                putLog(service.executeRequest() ? "успешная регистрация" : "ошибка регистрации");

                break;
            default:
                RequestFabric fabric = new RequestFabric();
                Request errorRequest = fabric.makeRequest(RequestSubjects.REQUEST_FORMAT_ERROR, null);
                client.sendRequest(errorRequest);
                break;
        }
    }

    public void checkAuthRequest(ClientThread client, Request request) {

    }
}
