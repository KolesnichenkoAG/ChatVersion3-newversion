package com.geekbrains.clientchat.dialogs;

import javafx.scene.control.Alert;

public class Dialogs {

    public enum AuthError {
        EMPTY_CREDENTIALS("Логин и пароль должны быть указаны"),
        INVALID_CREDENTIALS("Логин и пароль заданы некорректно");

        private static final String TITLE = "Ошибка аутенфикации";
        private static final String TYPE = TITLE;

        private final String message;

        AuthError(String message) {
            this.message = message;
        }

        public void show() {

        }
    }

    private static void showDialog(Alert.AlertType type, String title, String type) {

    }
}
