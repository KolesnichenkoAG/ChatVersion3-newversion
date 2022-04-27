package com.geekbrains.clientchat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Network {

    public static final String SERVER_HOST = "127.0.0.1";
    public static final int SERVER_PORT = 8189;

    private int port;
    private String host;

    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    public Network(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Network() {
        this(SERVER_HOST, SERVER_PORT);
    }

    public boolean connect() {
        try {
            Socket socket = new Socket(host, port);
            inputStream = new DataInputStream(socket.getInputStream()); // чтение
            outputStream = new DataOutputStream(socket.getOutputStream()); // запись
            return true;
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }
    
}
