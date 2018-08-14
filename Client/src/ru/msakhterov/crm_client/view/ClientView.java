package ru.msakhterov.crm_client.view;

import java.io.File;

public interface ClientView {

    String getLogin();

    String getPassword();

    String getEmail();

    void logAppend(String msg);

    void setView(ViewStatement statement);

    void setViewTitle(String title);
}
