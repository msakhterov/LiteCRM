package ru.msakhterov.crm_server.core;

import ru.msakhterov.crm_common.network.SocketThread;
import ru.msakhterov.crm_common.network.SocketThreadListener;
import ru.msakhterov.crm_common.request.requests.Request;
import ru.msakhterov.crm_server.network.ClientThread;
import ru.msakhterov.crm_server.network.ServerSocketThread;
import ru.msakhterov.crm_server.network.ServerSocketThreadListener;
import ru.msakhterov.db_manager.DataBaseManager;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

import static ru.msakhterov.crm_common.logger.Logger.putLog;

public class Server implements ServerSocketThreadListener, SocketThreadListener {
    private ServerSocketThread serverSocketThread;
    private DataBaseManager dataBaseManager;
    private CopyOnWriteArrayList<SocketThread> clients = new CopyOnWriteArrayList<>();
    private ServerRequestService serverRequestService = new ServerRequestService();

    public void start(int port) {
        if (serverSocketThread != null && serverSocketThread.isAlive()) {
            putLog("Сервер уже запущен");
        } else {
            serverSocketThread = new ServerSocketThread(this, "Server thread", port, 2000);
            dataBaseManager = DataBaseManager.getDataBaseManager();
        }
    }

    public void stop() {
        if (serverSocketThread == null || !serverSocketThread.isAlive()) {
            putLog("Сервер не запущен");
        } else {
            serverSocketThread.interrupt();
            dataBaseManager.disconnect();
        }
    }

    @Override
    public void onStartServerSocketThread(ServerSocketThread thread) {
        putLog("Сервер запущен");
    }

    @Override
    public void onStopServerSocketThread(ServerSocketThread thread) {
        putLog("Cервер остановлен");
    }

    @Override
    public void onCreateServerSocket(ServerSocketThread thread, ServerSocket serverSocket) {
        putLog("Cоздан Server socket");
    }

    @Override
    public void onAcceptTimeout(ServerSocketThread thread, ServerSocket serverSocket) {
    }

    @Override
    public void onSocketAccepted(ServerSocketThread thread, Socket socket) {
        putLog("Клиент подключился: " + socket);
        String threadName = "SocketThread " + socket.getInetAddress() + ":" + socket.getPort();
        new ClientThread(this, threadName, socket);
    }

    @Override
    public void onServerSocketException(ServerSocketThread thread, Exception e) {
        putLog("Exception: " + e.getClass().getName() + ": " + e.getMessage());
    }

    @Override
    public synchronized void onStartSocketThread(SocketThread thread, Socket socket) {
        putLog("запущен");
    }

    @Override
    public synchronized void onStopSocketThread(SocketThread thread) {
        putLog("остановлен");
        ClientThread client = (ClientThread) thread;
        clients.remove(thread);
    }

    @Override
    public synchronized void onSocketIsReady(SocketThread thread, Socket socket) {
        putLog("готов к передаче данных");
        clients.add(thread);
    }

    @Override
    public synchronized void onReceiveRequest(SocketThread thread, Socket socket, Request request) {
        ClientThread client = (ClientThread) thread;
        if (client.isAuthorized()) {
            serverRequestService.checkAuthRequest(client, request);
        } else {
            serverRequestService.checkNonAuthRequest(client, request);
        }
    }

    @Override
    public synchronized void onSocketThreadException(SocketThread thread, Exception e) {
        putLog("Exception: " + e.getClass().getName() + ": " + e.getMessage());
    }
}

