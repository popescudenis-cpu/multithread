package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("localhost", 3000);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        Scanner tastiera = new Scanner(System.in);

        System.out.println("Connesso al server");

        while (true) {
            System.out.print("Inserisci l'operazione o 'exit' per uscire: ");
            String inputUtente = tastiera.nextLine();

            out.println(inputUtente);

            if (inputUtente.equalsIgnoreCase("exit")) {
                System.out.println("Uscita dal programma...");
                break;
            }

            String rispostaServer = in.readLine();

            if (rispostaServer == null) {
                System.out.println("Il server ha chiuso la connessione.");
                break;
            }

            System.out.println("Risposta dal server: " + rispostaServer);
        }

        tastiera.close();
        out.close();
        in.close();
        socket.close();

        System.out.println("Tutte le risorse sono state chiuse.");
    }
}
