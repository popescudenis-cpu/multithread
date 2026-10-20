package it.meucci;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    private static final int PORTA = 3000;

    public static void main(String[] args) {
        System.out.println("Server in ascolto sulla porta " + PORTA);

        try (ServerSocket serverSocket = new ServerSocket(PORTA)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nuovo client connesso: " + clientSocket.getRemoteSocketAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            System.err.println("Errore del server: " + e.getMessage());
        }
    }
}