package ru.msakhterov.crm_common.network;

import ru.msakhterov.crm_common.request.requests.Request;

import java.net.Socket;

public interface SocketThreadListener {

    void onStartSocketThread(SocketThread thread, Socket socket);

    void onStopSocketThread(SocketThread thread);

    void onSocketIsReady(SocketThread thread, Socket socket);

    void onReceiveRequest(SocketThread thread, Socket socket, Request request);

    void onSocketThreadException(SocketThread thread, Exception e);

}
