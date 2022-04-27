package com.geekbrains.clientchat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import static com.geekbrains.clientchat.Network.SERVER_HOST;
import static com.geekbrains.clientchat.Network.SERVER_PORT;

public class ClientChat extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientChat.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Java FX Application");
        stage.setScene(scene);

        ClientController controller = fxmlLoader.getController();
        controller.userList.getItems().addAll("user1", "user2");

        stage.show();

        connectToServer();
    }

    private void connectToServer() {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT)) {
            Network network = new Network();
            boolean resultConnectedToServer = network.connect();
            if (!resultConnectedToServer) {
                String errorMessage = "Невозможно установить сетевое соединение";
                System.err.println(errorMessage);
                showErrorDialog(errorMessage);
            }
        }catch (UnknownHostException e) {
            String errorMessage = "Host unknown";// Хост неизвестен
            System.err.println(errorMessage);
            showErrorDialog(errorMessage);
            e.printStackTrace();
        }catch (IOException e) {
            String errorMessage = "Network connection cannot be established";// Невозможно установить сетевое соединение
            System.err.println(errorMessage);
            showErrorDialog(errorMessage);
            e.printStackTrace();
        }
    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}