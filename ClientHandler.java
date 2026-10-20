package it.meucci;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
            String testo;
            while ((testo = in.readLine()) != null) {
                if ("exit".equalsIgnoreCase(testo.trim())) {
                    break;
                }

                String risposta = elaboraCalcolo(testo);
                out.println(risposta);
            }
        } catch (IOException e) {
            System.err.println("Errore nella comunicazione con il client: " + e.getMessage());
        } finally {
            chiudiConnessione();
        }
    }

    private String elaboraCalcolo(String testo) {
        try {
            String[] parti = testo.trim().split(" ");
            if (parti.length != 3) {
                return "Errore: Formato non valido. Usa 'numero operatore numero'";
            }

            float num1 = Float.parseFloat(parti[0]);
            String operatore = parti[1];
            float num2 = Float.parseFloat(parti[2]);

            switch (operatore) {
                case "+":
                    return "Risultato: " + (num1 + num2);
                case "-":
                    return "Risultato: " + (num1 - num2);
                case "*":
                    return "Risultato: " + (num1 * num2);
                case "/":
                    if (num2 == 0) {
                        return "Errore: Divisione per zero";
                    }
                    return "Risultato: " + (num1 / num2);
                default:
                    return "Errore: Operatore non riconosciuto";
            }
        } catch (NumberFormatException e) {
            return "Errore: Formato dei numeri non valido";
        }
    }

    private void chiudiConnessione() {
        try {
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
                System.out.println("Connessione con un client chiusa.");
            }
        } catch (IOException e) {
            System.err.println("Errore durante la chiusura del socket: " + e.getMessage());
        }
    }
}
