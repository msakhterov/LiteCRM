package ru.msakhterov.crm_services;

import ru.msakhterov.crm_common.entity.User;
import ru.msakhterov.crm_common.network.SocketThread;
import ru.msakhterov.crm_common.request.RequestFabric;
import ru.msakhterov.crm_common.request.RequestSubjects;
import ru.msakhterov.crm_common.request.requests.AuthRequest;
import ru.msakhterov.crm_common.request.requests.Request;
import ru.msakhterov.db_manager.DataBaseManager;

public class AuthService {
    private AuthRequest request;
    private DataBaseManager dataBaseManager;
    private RequestFabric requestFabric;
    private SocketThread client;

    public AuthService(SocketThread client, AuthRequest request) {
        this.request = request;
        this.dataBaseManager = DataBaseManager.getDataBaseManager();
        this.requestFabric = new RequestFabric();
        this.client = client;
    }

    public boolean executeRequest() {
        User authUser = (User) request.getEntity();
        User user = dataBaseManager.getUser(authUser.getLogin());
        System.out.println("Auth user pass: " + authUser.getPassword());
        System.out.println("User pass: " + user.getPassword());
        if (authUser.getPassword().equals(user.getPassword())) {
            Request request = requestFabric.makeRequest(RequestSubjects.AUTH_ACCEPT, user);
            client.sendRequest(request);
            return true;
        } else {
            Request request = requestFabric.makeRequest(RequestSubjects.AUTH_DENIED, null);
            client.sendRequest(request);
            return false;
        }
    }
}
