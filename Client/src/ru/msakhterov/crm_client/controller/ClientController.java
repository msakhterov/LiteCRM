package ru.msakhterov.crm_client.controller;

import ru.msakhterov.crm_client.view.ClientView;
import ru.msakhterov.crm_client.view.ViewStatement;
import ru.msakhterov.crm_common.entity.User;
import ru.msakhterov.crm_common.network.SocketThread;
import ru.msakhterov.crm_common.network.SocketThreadListener;
import ru.msakhterov.crm_common.request.RequestFabric;
import ru.msakhterov.crm_common.request.RequestSubjects;
import ru.msakhterov.crm_common.request.decorators.AuthRequestResponse;
import ru.msakhterov.crm_common.request.decorators.RegRequestResponse;
import ru.msakhterov.crm_common.request.requests.Request;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

public class ClientController implements ClientListener, SocketThreadListener {

    private ClientView client;
    private SocketThread socketThread;
    private RequestFabric requestFabric;
    private int selector = 0;

    public ClientController(ClientView client) {
        this.client = client;
        requestFabric = RequestFabric.getRequestFabric();
    }

    @Override
    public void onLogin() {
        selector = 1;
        connect();
    }

    @Override
    public void onRegistration() {
        selector = 2;
        connect();
    }

    @Override
    public void onDisconnect() {
        socketThread.close();
    }

//    @Override
//    public void onUpload() {
//        File selectedFile = client.getFilePath(null);
//        if (selectedFile != null) {
//            uploadFile(selectedFile);
//        }
//    }
//
//    @Override
//    public void onUpload(String filePath) {
//        File selectedFile = new File(filePath);
//        uploadFile(selectedFile);
//    }
//
//    @Override
//    public void onDownload(String fileName) {
//        socketThread.sendRequest(Requests.getDownloadRequest(fileName));
//    }
//
//    @Override
//    public void onDelete(String fileName) {
//        socketThread.sendRequest(Requests.getDeleteRequest(fileName));
//    }
//
//    @Override
//    public void onRename(String oldFileName, String newFileName) {
//        socketThread.sendRequest(Requests.getRenameRequest(oldFileName, newFileName));
//    }

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
        switch (request.getRequestSubject()) {
            case RequestSubjects.AUTH_ACCEPT:
                AuthRequestResponse authResponse = new AuthRequestResponse(request);
                client.setViewTitle(authResponse.getUserData());
                client.logAppend("Успешная авторизация");
                break;
            case RequestSubjects.AUTH_DENIED:
                client.logAppend("Ошибка авторизации");
                break;
            case RequestSubjects.REG_ACCEPT:
                RegRequestResponse regResponse = new RegRequestResponse(request);
                client.setViewTitle(regResponse.getUserData());
                client.logAppend("Успешная регистрация");
                break;
            case RequestSubjects.REG_DENIED:
                client.logAppend("Ошибка регистрации");
                break;
            case RequestSubjects.REQUEST_FORMAT_ERROR:
                client.logAppend("Ошибка запроса");
                socketThread.close();
                break;
            default:
                throw new RuntimeException("Unknown message format");
        }
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


