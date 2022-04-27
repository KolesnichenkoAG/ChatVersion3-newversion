package lesson6;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    private static final int PORT = 8189;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)){
            System.out.println("Сервер начал работу, ожидаем новые подключения");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Клиент подключился");

        } catch (IOException e) {
            System.err.println("Ошибка при подключении к порту" + PORT);
            e.printStackTrace();
        }
    }
}
