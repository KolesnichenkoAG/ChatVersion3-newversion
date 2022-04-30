package com.geekbrains.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {

    public void start(int port) {
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server has been started");
            Socket clientSocket = serverSocket.accept();
            DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Failed to bind port " + port);
        }
    }
}
