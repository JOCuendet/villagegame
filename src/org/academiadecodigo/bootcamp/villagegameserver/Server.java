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
    private ArrayList<String> votes;
    private boolean exit;
    private ArrayList<String> votes = new ArrayList<>();
    private boolean exit = false;
    private int countPlayers;
    private boolean isStartGame ;
    private PlayerHandler playerKilledByWolf;
    private CopyOnWriteArrayList<PlayerHandler> playersList;
    private PlayerHandler playerToKill;
    private int numberPlayers;
    private int port;

    public Server(int port,int numberPlayers){
        this.port=port;
        this.numberPlayers=numberPlayers;
        exit=false;
        isStartGame=false;
        this.playersList= new CopyOnWriteArrayList<>();
        countPlayers=0;
    }

    public CopyOnWriteArrayList<PlayerHandler> getPlayersList() {
        return playersList;
    }

    public ArrayList<String> getVotes() {
        return votes;
    }

    public boolean getIsStartGame(){
        return isStartGame;
    }
    public void sendVote(String player){
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

    public void startGame(){

        isStartGame = true;
        if (countPlayers > numberPlayers && isStartGame) {
            System.out.println("starto game");
            exit = true;
            clientSocket=null;
        }
    }

    public void start(){

        game = new Game(this,playersList);
        try {
            serverSocket = new ServerSocket(port);
            clientThreadPool = Executors.newFixedThreadPool(numberPlayers);
            while(playersList.size()<numberPlayers) {
                clientSocket = serverSocket.accept();
                playerhandler = new PlayerHandler(this, clientSocket);
                playersList.add(playerhandler);
                clientThreadPool.submit(playerhandler);
            }
            while(countPlayers!=numberPlayers) {

            }

            System.out.println("game started");
            game.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadCast(PlayerHandler playerHandler, String message){

        for (PlayerHandler player: playersList) {
            if(playerHandler.equals(player)){
                continue;
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

    public void sendKilledMessage(PlayerHandler player){
        String message="you've been killed by the wolf";
        if(player.isWolf()){
            message="you've been killed by villagers";
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
    public PlayerHandler getPlayerToKill() {
        return playerToKill;
    }

    public void log(PlayerHandler playerHandler, String message) {
        System.out.println(playerHandler.getAlias()+" - "+message);
    }
}