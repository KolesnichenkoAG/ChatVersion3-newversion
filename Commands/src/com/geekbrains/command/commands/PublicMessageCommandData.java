package com.geekbrains.command.commands;

public class PublicMessageCommandData {

    private final String message;

    public PublicMessageCommandData(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
