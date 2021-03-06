package ru.msakhterov.crm_common.network;

import ru.msakhterov.crm_common.request.requests.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketThread extends Thread {

    private SocketThreadListener listener;
    private Socket socket;
    private ObjectOutputStream out;

    public SocketThread(SocketThreadListener listener, String name, Socket socket) {
        super(name);
        this.socket = socket;
        this.listener = listener;
        start();
    }

    @Override
    public void run() {
        try {
            listener.onStartSocketThread(this, socket);
            out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            listener.onSocketIsReady(this, socket);
            Request request = null;
            while (!isInterrupted()) {
                try {
                    request = (Request) in.readObject();
                } catch (ClassNotFoundException e) {
                    listener.onSocketThreadException(this, e);
                }
                if (request != null) {
                    listener.onReceiveRequest(this, socket, request);
                }
            }
        } catch (IOException e) {
            listener.onSocketThreadException(this, e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                listener.onSocketThreadException(this, e);
            }
            listener.onStopSocketThread(this);
        }
    }

    public synchronized boolean sendRequest(Request request) {
        try {
            out.writeObject(request);
            out.flush();
            return true;
        } catch (IOException e) {
            listener.onSocketThreadException(this, e);
            close();
            return false;
        }
    }

    public synchronized void close() {
        interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            listener.onSocketThreadException(this, e);
        }
    }
}
