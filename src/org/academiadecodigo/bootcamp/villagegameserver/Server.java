package org.academiadecodigo.bootcamp.villagegameserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {
    private ExecutorService clientThreadPool;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private Game game;
    private PlayerHandler playerhandler;
    private ArrayList<String> votes = new ArrayList<>();
    private boolean exit = false;
    private int countPlayers;
    private boolean isStartGame = false;
    private CopyOnWriteArrayList<PlayerHandler> playersList = new CopyOnWriteArrayList<>();
    private PlayerHandler playerToKill;

    public ArrayList<String> getVotes() {
        return votes;
    }

    public void sendVote(String player) {
        votes.add(player);
    }

    public void cleanVote() {
        votes.clear();
    }

    public void sendReadyStatus() {
        countPlayers++;
        if (countPlayers >= 4 && isStartGame) {
            System.out.println("starto game");
            exit = true;
        }
    }

    public boolean getIsStartGame() {
        return isStartGame;
    }

    public void startGame() throws InterruptedException {
        isStartGame = true;
        if (countPlayers >= 4 && isStartGame) { // (isStartGame é sempre true, ou seja, isto estar aqui é redundante)
            System.out.println("start game");
            game.start();
            exit = true;
            clientSocket = null;
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    public void start() {
        game = new Game(this, playersList);
        try {
            serverSocket = new ServerSocket(9999);
            clientThreadPool = Executors.newFixedThreadPool(40);
            while (!exit) {
                clientSocket = serverSocket.accept();
                playerhandler = new PlayerHandler(this, clientSocket);
                playersList.add(playerhandler);
                clientThreadPool.submit(playerhandler);
            }
            System.out.println("game started");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadCast(String message) {
// TODO: 6/28/19 JAY
        for (PlayerHandler player : playersList) {
            PrintWriter outMessage = null;
            try {
                outMessage = new PrintWriter(player.getClientSocket().getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            outMessage.println(message);
            outMessage.flush();
        }
    }

    public void sendKilledMessage(PlayerHandler player) {
        String message = "you'be been killed by the wolf";
        if (player.isWolf()) {
            message = "you've been killed by villagers";
        }

        PrintWriter outMessage = null;
        try {
            outMessage = new PrintWriter(player.getClientSocket().getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        outMessage.println(message);
        outMessage.flush();
    }

    public void setPlayerToKill(String playerNameKilledByWolf) {

        for (PlayerHandler player : playersList) {
            if (player.getAlias().equals(playerNameKilledByWolf)) {
                playerToKill = player;
            }
        }
    }

    public PlayerHandler getPlayerToKill() {
        return playerToKill;
    }
}