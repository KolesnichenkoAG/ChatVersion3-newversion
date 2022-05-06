package com.geekbrains.clientchat.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class Network {

    private List<ReadMessageListener> listeners = new CopyOnWriteArrayList<>();

    private static Network INSTANCE;
    public static final String SERVER_HOST = "127.0.0.1";
    public static final int SERVER_PORT = 8189;

    private int port;
    private String host;

    private Socket socket;
    private DataInputStream socketInput;
    private DataOutputStream socketOutput;
    private Thread readMessageProcess;
    private boolean connected;

    private Network(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private Network() {
        this(SERVER_HOST, SERVER_PORT);
    }

    public static Network getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Network();
        }

        return INSTANCE;
    }

    public boolean connect() {
        try {
            socket = new Socket(host, port);
            socketInput = new DataInputStream(socket.getInputStream()); // чтение
            socketOutput = new DataOutputStream(socket.getOutputStream()); // запись
            readMessageProcess = startReadMessageProcess();
            connected = true;
            return true;
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public void sendMessage(String message) throws IOException {
        try {
            socketOutput.writeUTF(message);
        } catch (IOException e) {
            System.err.println("Не удалось отправить сообщение на сервер");
            e.printStackTrace();
            throw e;
        }
    }

    public Thread startReadMessageProcess() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (Thread.currentThread().isInterrupted()) {
                            return;
                        }
                        String message = socketInput.readUTF();

                        for (ReadMessageListener listener : listeners) {
                            listener.processReceivedMessage(message);
                        }

                    } catch (IOException e) {
                        System.err.println("Не удалось получить сообщение от сервера");
                        e.printStackTrace();
                        close();
                        break;
                    }
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
        return thread;
    }

    public ReadMessageListener addReadMessageListener(ReadMessageListener listener) {
        this.listeners.add(listener);
        return listener;
    }

    public void removeReadMessageListener(ReadMessageListener listener) {
        this.listeners.remove(listener);
    }

    public void close() {
        try {
            connected =false;
            socket.close();
            readMessageProcess.interrupt();
        } catch (IOException e) {
            System.err.println("Не удалось закрыть сетевое соединение");
        }
    }

    public boolean isConnected() {
        return connected;
    }
}