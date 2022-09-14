package com.geekbrains.clientchat.controllers;

import com.geekbrains.clientchat.dialogs.Dialogs;
import com.geekbrains.clientchat.model.Network;
import com.geekbrains.clientchat.model.ReadMessageListener;
import com.geekbrains.command.Command;
import com.geekbrains.command.CommandType;
import com.geekbrains.command.commands.ClientMessageCommandData;
import com.geekbrains.command.commands.UpdateUserListCommandData;
import history.HistoryService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

public class ClientController {

    private static final Logger logger = LogManager.getLogger(ClientController.class);
    @FXML
    public TextField messageTextArea;

    @FXML
    public Button sendMessageButton;

    @FXML
    public TextArea chatTextArea;

    @FXML
    public ListView userList;

    public void sendMessage() {
        String message = messageTextArea.getText();

        if (message.isEmpty()) {
            messageTextArea.clear();
            return;
        }

        String sender = null;
        if (!userList.getSelectionModel().isEmpty()) {
            sender = userList.getSelectionModel().getSelectedItem().toString();
        }

        try {
            if (sender != null) {
                Network.getInstance().sendPrivateMessage(sender, message);

            } else {
                Network.getInstance().sendMessage(message);
            }

        } catch (IOException e) {
            Dialogs.NetworkError.SEND_MESSAGE.show();
        }

        appendMessageToChat("Я", message);
        logger.info("пользователь отправил сообщение");
    }

    public void appendMessageToChat(String sender, String message) {
        chatTextArea.appendText(DateFormat.getTimeInstance().format(new Date()));
        logger.info("И что же сейчас происходит ?");
        chatTextArea.appendText(System.lineSeparator());

        if (sender != null) {
            chatTextArea.appendText(HistoryService.loadHistory(sender));
            chatTextArea.appendText(sender + ":");

            HistoryService.saveHistory(sender, message);
            chatTextArea.appendText(System.lineSeparator());


        }
        chatTextArea.appendText(message);
        chatTextArea.appendText(System.lineSeparator());
        chatTextArea.appendText(System.lineSeparator());
        requestFocusForTextArea();
        messageTextArea.clear();
    }

    private void requestFocusForTextArea() {
        Platform.runLater(() -> messageTextArea.requestFocus());
    }

    public void initializeMessageHandler() {
        Network.getInstance().addReadMessageListener(new ReadMessageListener() {
            @Override
            public void processReceivedCommand(Command command) {
                if (command.getType() == CommandType.CLIENT_MESSAGE) {
                    ClientMessageCommandData data = (ClientMessageCommandData) command.getData();
                    appendMessageToChat(data.getSender(), data.getMessage());

                } else if (command.getType() == CommandType.UPDATE_USERS_LIST) {
                    UpdateUserListCommandData data = (UpdateUserListCommandData) command.getData();
                    Platform.runLater(() -> {
                        userList.setItems(FXCollections.observableArrayList(data.getUsers()));
                    });
                }
            }
        });
    }
}