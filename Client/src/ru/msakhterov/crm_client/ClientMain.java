package ru.msakhterov.crm_client;

import ru.msakhterov.crm_client.view.swing.ClientGUI;

import javax.swing.*;

public class ClientMain {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClientGUI());
    }
}
