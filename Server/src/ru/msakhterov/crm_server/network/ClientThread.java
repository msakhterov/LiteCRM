package ru.msakhterov.crm_server.network;


import ru.msakhterov.crm_common.network.SocketThread;
import ru.msakhterov.crm_common.network.SocketThreadListener;

import java.net.Socket;

public class ClientThread extends SocketThread {

    private String user;
    private boolean isAuthorized;

    public ClientThread(SocketThreadListener listener, String name, Socket socket) {
        super(listener, name, socket);
    }

    public String getUser() {
        return user;
    }

    public boolean isAuthorized() {
        return isAuthorized;
    }

}
