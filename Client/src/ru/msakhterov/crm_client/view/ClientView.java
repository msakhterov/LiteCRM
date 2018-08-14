package ru.msakhterov.crm_client.view;

import java.io.File;

public interface ClientView {

    String getLogin();

    String getPassword();

    String getEmail();

    File getFilePath(String fileName);

    void logAppend(String msg);

    void setView(ViewStatement statement);

    void setFilesList(String[][] filesList);

    void setFilesList();

    void setViewTitle(String title);
}
