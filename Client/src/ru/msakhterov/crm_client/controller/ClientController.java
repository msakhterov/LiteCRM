package ru.msakhterov.crm_client.controller;

import ru.msakhterov.crm_client.events.EventListener;
import ru.msakhterov.crm_client.events.EventType;
import ru.msakhterov.crm_client.view.ClientView;
import ru.msakhterov.crm_client.view.ViewStatement;
import ru.msakhterov.crm_common.entity.User;
import ru.msakhterov.crm_common.network.SocketThread;
import ru.msakhterov.crm_common.network.SocketThreadListener;
import ru.msakhterov.crm_common.request.RequestFabric;
import ru.msakhterov.crm_common.request.RequestSubjects;
import ru.msakhterov.crm_common.request.decorators.RequestResponse;
import ru.msakhterov.crm_common.request.decorators.Response;
import ru.msakhterov.crm_common.request.decorators.UserRequestResponse;
import ru.msakhterov.crm_common.request.requests.Request;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

public class ClientController implements EventListener, SocketThreadListener {

    private ClientView client;
    private SocketThread socketThread;
    private RequestFabric requestFabric;
    private int selector = 0;

    public ClientController(ClientView client) {
        this.client = client;
        requestFabric = RequestFabric.getRequestFabric();

    }

    public void execute (EventType event){
        switch (event){
            case LOGIN:
                selector = 1;
                connect();
                break;
            case REGISTRATION:
                selector = 2;
                connect();
                break;
            case DISCONNECT:
                socketThread.close();
                break;
            default:
                throw new RuntimeException("Unknown event type format");
        }
    }

    private void connect() {
        Socket socket = null;
        try {
            socket = new Socket(client.getIP(), client.getPort());
        } catch (IOException e) {
            client.logAppend("Exception: " + e.getMessage());
        }
        socketThread = new SocketThread(this, "SocketThread", socket);
    }

    private void handleRequest(Request request) {
        Response requestResponse = new RequestResponse();
        switch (request.getRequestSubject()) {
            case RequestSubjects.AUTH_ACCEPT:
                requestResponse = new UserRequestResponse(requestResponse);
                break;
            case RequestSubjects.AUTH_DENIED:
                requestResponse = new UserRequestResponse(requestResponse);
                break;
            case RequestSubjects.REG_ACCEPT:
                requestResponse = new UserRequestResponse(requestResponse);
                break;
            case RequestSubjects.REG_DENIED:
                requestResponse = new UserRequestResponse(requestResponse);
                break;
            case RequestSubjects.REQUEST_FORMAT_ERROR:
                requestResponse = new UserRequestResponse(requestResponse);
                socketThread.close();
                break;
            default:
                throw new RuntimeException("Unknown message format");
        }
        client.logAppend(requestResponse.getLogMessage(request));
    }


    @Override
    public void onStartSocketThread(SocketThread thread, Socket socket) {
        client.logAppend("Поток сокета стартовал");
    }

    @Override
    public void onStopSocketThread(SocketThread thread) {
        client.logAppend("Соединение разорвано");
        client.setViewTitle(null);
        client.setView(ViewStatement.DISCONNECTED);
    }

    @Override
    public void onSocketIsReady(SocketThread thread, Socket socket) {
        client.logAppend("Соединение установлено");
        String login = client.getLogin();
        String password = client.getPassword();
        String email = client.getEmail();
        if (selector == 1) {
            User authUser = new User.Builder(login, password).build();
            Request authRequest = requestFabric.makeRequest(RequestSubjects.AUTH_REQUEST, authUser);
            thread.sendRequest(authRequest);
        } else {
            User regUser = new User.Builder(login, password).setUuid().setEmail(email).build();
            Request regRequest = requestFabric.makeRequest(RequestSubjects.REG_REQUEST, regUser);
            thread.sendRequest(regRequest);
        }
        client.setView(ViewStatement.CONNECTED);
    }

    @Override
    public void onReceiveRequest(SocketThread thread, Socket socket, Request request) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                handleRequest(request);
            }
        });
    }

    @Override
    public void onSocketThreadException(SocketThread thread, Exception e) {
        client.logAppend(e.toString());
    }

}


