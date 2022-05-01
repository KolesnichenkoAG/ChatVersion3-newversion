package com.geekbrains.clientchat.controllers;

import com.geekbrains.clientchat.ClientChat;
import com.geekbrains.clientchat.Network;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AuthController {

    public static final String AUTH_COMMAND = "/auth";

    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Button authButton;

    private ClientChat clientChat;
    private Network network;

    @FXML
    public void executeAuth() {
        String login = loginField.getText();
        String password = passwordField.getText();

        if (login == null || password == null || login.isBlank() || login.isBlank()) {
            clientChat.showErrorDialog("Логин и пароль должны быть указаны");
            return;
        }

        String authCommandMessage = String.format("%s %s %s", AUTH_COMMAND, login, password);

        try {
            network.sendMessage(authCommandMessage);
        } catch (IOException e) {
            clientChat.showErrorDialog("Ошибка передачи данных по сети");
            e.printStackTrace();
        }

    }

    public void setClientChat(ClientChat clientChat) {
        this.clientChat = clientChat;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }
}
