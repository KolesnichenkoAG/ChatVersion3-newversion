package com.geekbrains.clientchat.controllers;

import com.geekbrains.clientchat.ClientChat;
import com.geekbrains.clientchat.dialogs.Dialogs;
import com.geekbrains.clientchat.model.Network;
import com.geekbrains.clientchat.model.ReadMessageListener;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.function.Consumer;

public class AuthController {

    public static final String AUTH_COMMAND = "/auth";
    public static final String AUTH_OK_COMMAND = "/authOk";

    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Button authButton;

    public ReadMessageListener readMessageListener;

    @FXML
    public void executeAuth() {
        String login = loginField.getText();
        String password = passwordField.getText();

        if (login == null || password == null || login.isBlank() || login.isBlank()) {
            Dialogs.AuthError.EMPTY_CREDENTIALS.show();
            return;
        }

        if (!isConnectedToServer()) {
        Dialogs.NetworkError.SERVER_CONNECT.show();
        }

        String authCommandMessage = String.format("%s %s %s", AUTH_COMMAND, login, password);

        try {
            Network.getInstance().sendMessage(authCommandMessage);
        } catch (IOException e) {
            Dialogs.NetworkError.SEND_MESSAGE.show();
            e.printStackTrace();
        }

    }

    public void initializeMessageHandler() {
       readMessageListener = getNetwork().addReadMessageListener(new ReadMessageListener() {
            @Override
            public void processReceivedMessage(String message) {
                if (message.startsWith(AUTH_OK_COMMAND)) {
                    String[] parts = message.split(" ");
                    String userName = parts[1];
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                           ClientChat.getInstance().switchToMainChatWindow(userName);
                        }
                    });
                }else {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Dialogs.AuthError.INVALID_CREDENTIALS.show();
                        }
                    });
                }
            }
        });
    }

    public boolean isConnectedToServer () {
        Network network = getNetwork();
        return network.isConnected() || network.connect();
    }

    private Network getNetwork() {
        return Network.getInstance();
    }

    public void close() {
        getNetwork().removeReadMessageListener(readMessageListener);
    }
}
