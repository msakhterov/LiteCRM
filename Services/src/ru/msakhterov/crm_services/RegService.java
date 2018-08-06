package ru.msakhterov.crm_services;


import ru.msakhterov.crm_common.entity.User;
import ru.msakhterov.crm_common.network.SocketThread;
import ru.msakhterov.crm_common.request.RequestFabric;
import ru.msakhterov.crm_common.request.RequestSubjects;
import ru.msakhterov.crm_common.request.requests.RegRequest;
import ru.msakhterov.crm_common.request.requests.Request;
import ru.msakhterov.db_manager.DataBaseManager;

public class RegService {
    private RegRequest request;
    private DataBaseManager dataBaseManager;
    private RequestFabric requestFabric;
    private SocketThread client;

    public RegService(SocketThread client, RegRequest request) {
        this.request = request;
        this.dataBaseManager = DataBaseManager.getDataBaseManager();
        this.requestFabric = new RequestFabric();
        this.client = client;
    }

    public boolean executeRequest() {
        User regUser = (User)request.getEntity();
        int result = dataBaseManager.makeReg(regUser);
        if (result == 1) {
            User user = dataBaseManager.getUser(regUser.getLogin());
            if (user != null) {
                Request request = requestFabric.makeRequest(RequestSubjects.REG_ACCEPT, user);
                client.sendRequest(request);
                return true;
            } else {
                Request request = requestFabric.makeRequest(RequestSubjects.REG_DENIED, null);
                client.sendRequest(request);
                return false;
            }
        }
        return false;
    }
}
