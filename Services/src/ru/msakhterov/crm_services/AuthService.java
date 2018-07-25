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

    private boolean executeRequest() {
        User authUser = (User) request.getEntity();
        String login = authUser.getLogin();
        String password = authUser.getPassword();
        String dbPassword = dataBaseManager.checkAuth(login);
        if (password.equals(dbPassword)) {
            User user = dataBaseManager.getUser(login);
            if (user != null) {
                Request request = requestFabric.makeRequest(RequestSubjects.AUTH_ACCEPT, user);
                client.sendRequest(request);
                return true;
            } else {
                Request request = requestFabric.makeRequest(RequestSubjects.AUTH_DENIED, null);
                client.sendRequest(request);
                return false;
            }
        }
        return false;
    }
}
