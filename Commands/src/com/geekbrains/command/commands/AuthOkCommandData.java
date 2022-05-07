package com.geekbrains.command.commands;

public class AuthOkCommandData {

    private final String userName;

    public AuthOkCommandData(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
