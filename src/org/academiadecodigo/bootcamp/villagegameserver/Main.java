package org.academiadecodigo.bootcamp.villagegameserver;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Port number");
        int port = scanner.nextInt();

        System.out.println("Number of players");
        int numberPlayers = scanner.nextInt();

        Server server = new Server(port,numberPlayers);
        server.start();
    }
}