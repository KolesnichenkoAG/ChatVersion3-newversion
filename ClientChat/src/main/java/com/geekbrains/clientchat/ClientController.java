package com.geekbrains.clientchat;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.function.Consumer;

public class ClientController {

    @FXML
    public TextField messageTextArea;

    @FXML
    public Button sendMessageButton;

    @FXML
    public TextArea chatTextArea;

    @FXML
    public ListView userList;

    private Network network;
    private ClientChat application;

    public void sendMessage(){
        String message = messageTextArea.getText();
        try {
            network.sendMessage(message);
        } catch (IOException e) {
            application.showErrorDialog("Ошибка передачи данных по сети");
        }
        appendMessageToChat(message);
    }

    public void appendMessageToChat(String message) {
        if (!messageTextArea.getText().isEmpty()) {
            chatTextArea.appendText(DateFormat.getDateTimeInstance().format(new Date()));
            chatTextArea.appendText(System.lineSeparator());
            if (!userList.getSelectionModel().isEmpty()){
                String sender = userList.getSelectionModel().getSelectedItem().toString();
                chatTextArea.appendText(sender + ": ");
            } else {
                chatTextArea.appendText("я: ");
            }
            chatTextArea.appendText(messageTextArea.getText().trim());
            chatTextArea.appendText(System.lineSeparator());
            chatTextArea.appendText(System.lineSeparator());
            messageTextArea.requestFocus();
            messageTextArea.clear();
        }
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
        network.waitMessages(new Consumer<String>() {
            @Override
            public void accept(String message) {
                appendMessageToChat(message);
            }
        });
    }

    public ClientChat getApplication() {
        return application;
    }

    public void setApplication(ClientChat application) {
        this.application = application;
    }
/*private void requestFocus (){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                messageField.requestFocus();
            }
        });
    } */
}