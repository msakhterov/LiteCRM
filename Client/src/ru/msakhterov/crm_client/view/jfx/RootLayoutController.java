package ru.msakhterov.crm_client.view.jfx;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ru.msakhterov.crm_client.MainApp;
import ru.msakhterov.crm_client.events.EventType;
import ru.msakhterov.crm_common.entity.User;


public class RootLayoutController {
    @FXML
    private TextArea logArea;

    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public TextArea getLogArea() {
        return logArea;
    }
}
