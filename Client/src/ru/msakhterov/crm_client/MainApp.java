package ru.msakhterov.crm_client;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ru.msakhterov.crm_client.controller.ClientController;
import ru.msakhterov.crm_client.events.EventManager;
import ru.msakhterov.crm_client.events.EventType;
import ru.msakhterov.crm_client.events.event_logger.FileLogger;
import ru.msakhterov.crm_client.view.ClientView;
import ru.msakhterov.crm_client.view.ViewStatement;
import ru.msakhterov.crm_client.view.jfx.AuthScreenController;
import ru.msakhterov.crm_client.view.jfx.RegScreenController;
import ru.msakhterov.crm_client.view.jfx.RootLayoutController;
import ru.msakhterov.crm_common.entity.Entity;
import ru.msakhterov.crm_common.entity.User;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class MainApp extends Application implements ClientView {

    private EventManager eventManager;

    private Stage primaryStage;
    private BorderPane rootLayout;
    private TextArea logArea;

    private Entity entity;

    private ObservableList<User> userData = FXCollections.observableArrayList();
    private final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Override
    public void init() throws Exception {
        eventManager = new EventManager();
        eventManager.addListener(new ClientController(this));
        eventManager.addListener(new FileLogger());
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("LiteCRM");
        initRootLayout();
        showAuthScreen();

    }

    public void initRootLayout() {
        try {
            // Загружаем корневой макет из fxml файла.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/jfx/RootLayout.fxml"));
            rootLayout = loader.load();

            // Отображаем сцену, содержащую корневой макет.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);
            logArea = controller.getLogArea();
            logArea.setEditable(false);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAuthScreen() {
        try {
            // Загружаем макет авторизационного экрана.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/jfx/AuthScreen.fxml"));
            AnchorPane authScreen = loader.load();

            // Помещаем сведения об адресатах в центр корневого макета.
            rootLayout.setCenter(authScreen);

            // Даём контроллеру доступ к главному приложению.
            AuthScreenController authController = loader.getController();
            authController.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showRegScreen() {
        try {
            // Загружаем макет авторизационного экрана.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/jfx/RegScreen.fxml"));
            AnchorPane regScreen = loader.load();

            // Помещаем сведения об адресатах в центр корневого макета.
            rootLayout.setCenter(regScreen);

            // Даём контроллеру доступ к главному приложению.
            RegScreenController regController = loader.getController();
            regController.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getLogin() {
        return ((User) entity).getLogin();
    }

    @Override
    public String getPassword() {
        return ((User) entity).getPassword();
    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public void logAppend(String msg) {
        msg = dateFormat.format(System.currentTimeMillis()) + ": " + msg;
        logArea.appendText(msg + "\n");
    }

    @Override
    public void setView(ViewStatement statement) {

    }

    @Override
    public void setViewTitle(String title) {

    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public void sendEvent(EventType event){
        eventManager.notifyListeners(event);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
