package com.geekbrains.clientchat;

import com.geekbrains.clientchat.controllers.AuthController;
import com.geekbrains.clientchat.controllers.ClientController;
import com.geekbrains.clientchat.model.Network;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class ClientChat extends Application {

    private static ClientChat INSTANCE;

    private Stage chatStage;
    private Stage authStage;

    private FXMLLoader chatWindowLoader;
    private FXMLLoader authLoader;


    @Override
    public void start(Stage primaryStage) throws IOException {
        this.chatStage = primaryStage;

        initViews();
        getChatStage().show();
        getAuthStage().show();
        getAuthController().initializeMessageHandler();
    }

    private void initViews() throws IOException {
        initChatWindow();
        initAuthDialog();
    }

    private void initChatWindow() throws IOException {
        chatWindowLoader = new FXMLLoader();
        chatWindowLoader.setLocation(ClientChat.class.getResource("hello-view.fxml"));

        Parent root = chatWindowLoader.load();
        chatStage.setScene(new Scene(root));
    }

    private void initAuthDialog() throws IOException {
        authLoader = new FXMLLoader();
        authLoader.setLocation(ClientChat.class.getResource("authDialog.fxml"));
        AnchorPane authDialogPanel = authLoader.load();

        authStage = new Stage();
        authStage.initOwner(chatStage);
        authStage.initModality(Modality.WINDOW_MODAL);
        authStage.setScene(new Scene(authDialogPanel));
    }

    private void connectToServer(ClientController clientController) {
        boolean resultConnectedToServer = Network.getInstance().connect();
        if (!resultConnectedToServer) {
            String errorMessage = "Невозможно установить сетевое соединение";
            System.err.println(errorMessage);
            showErrorDialog(errorMessage);
        }


        clientController.setApplication(this);

        chatStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                Network.getInstance().close();
            }
        });
    }
    public void switchToMainChatWindow(String userName) {
        getChatStage().setTitle(userName);
        getAuthController().close();
        getAuthStage().close();
        getChatController().initializeMessageHandler();
    }

    public void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void init() throws Exception {
        INSTANCE = this;
    }

    public static void main(String[] args) {
        launch();
    }

    public Stage getAuthStage() {
        return authStage;
    }

    public Stage getChatStage() {
        return chatStage;
    }

    public ClientController getChatController() {
        return chatWindowLoader.getController();
    }

    public AuthController getAuthController() {
        return authLoader.getController();
    }

    public static ClientChat getInstance() {
        return INSTANCE;
    }

}