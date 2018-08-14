package ru.msakhterov.crm_client.view.jfx;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ru.msakhterov.crm_client.MainApp;
import ru.msakhterov.crm_client.events.EventType;
import ru.msakhterov.crm_common.entity.User;


public class RegScreenController {
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField emailField;

    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void onRegistration(){
        mainApp.setEntity(new User.Builder(loginField.getText(), passwordField.getText()).setEmail(emailField.getText()).build());
        mainApp.sendEvent(EventType.REGISTRATION);
    }

    @FXML
    private void onChangeToAuthScreen(){
        mainApp.showAuthScreen();
    }
}
