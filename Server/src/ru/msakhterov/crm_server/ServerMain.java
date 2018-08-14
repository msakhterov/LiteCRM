package ru.msakhterov.crm_server;

import ru.msakhterov.crm_common.constants.NetworkConstants;
import ru.msakhterov.crm_server.core.Server;

import java.util.Scanner;

public class ServerMain {

    private static final Scanner scanner = new Scanner(System.in);
    private static Server server = new Server();
    private static boolean end = false;

    public static void main(String[] args) {
        System.out.println("Введите:\n\"1\" для старта сервера \n\"2\" для остановки сервера");
        while (!end) {
            int a = Integer.parseInt(scanner.next());
            switch (a) {
                case 1:
                    server.start(NetworkConstants.CONNECT_PORT);
                    break;
                case 2:
                    server.stop();
                    end = true;
                    break;
                default:
                    System.out.println("Повторите ввод:\n\"1\" для старта сервера \n\"2\" для остановки сервера");
                    break;
            }
        }
    }
}
