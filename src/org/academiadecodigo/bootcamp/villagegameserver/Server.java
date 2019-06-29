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
    private PlayerHandler playerToKill;
    private ArrayList<String> votes;
    private boolean exit=false;
    private int countPlayers;
    private boolean isStartGame = false;

    private PlayerHandler playerKilledByWolf;

    public ArrayList<String> getVotes(){
        return votes;
    }
    public void sendVote(String player){
        votes.add(player);
    }
    public void cleanVote(){
        votes.clear();
    }
    public void sendReadyStatus(){
        countPlayers++;
        if(countPlayers>=4 && isStartGame ){
            exit=true;
        }
    }
    public void startGame(){
        isStartGame=true;
    }
    private CopyOnWriteArrayList<PlayerHandler> playersList = new CopyOnWriteArrayList<>();
    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
    public void start(){
        game = new Game(this,playersList);
        try {
            serverSocket = new ServerSocket(9999);
            clientThreadPool = Executors.newFixedThreadPool(40);
            while(!exit) {
                clientSocket = serverSocket.accept();
                playerhandler = new PlayerHandler(this, clientSocket);
                playersList.add(playerhandler);
                clientThreadPool.submit(playerhandler);
            }
            game.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void broadCast(String message){
// TODO: 6/28/19 JAY
        for (PlayerHandler player: playersList) {
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

    public PlayerHandler getWolfVote() {
        return playerKilledByWolf;
    }

    public void playerToKill(String playerNameKilledByWolf) {
        for(PlayerHandler player : playersList)
        {
            if( player.getAlias().equals(playerNameKilledByWolf)){
                playerKilledByWolf = player;
                playerToKill = player;
            }
        }
    }

    public PlayerHandler getPlayerToKill() {
        return playerToKill;
    }
}