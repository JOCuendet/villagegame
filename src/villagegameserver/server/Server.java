package villagegameserver.server;

import villagegameserver.aesthetics.ConsoleColors;
import villagegameserver.game.Game;

import java.io.DataOutputStream;
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
    private boolean exit;
    private int countPlayers;
    private boolean isStartGame;
    private CopyOnWriteArrayList<PlayerHandler> playersList;
    private int numberPlayers;
    private int port;

    public Server(int port, int numberPlayers) {
        this.port = port;
        this.numberPlayers = numberPlayers;
        exit = false;
        isStartGame = false;
        this.playersList = new CopyOnWriteArrayList<>();
        countPlayers = 0;
        playersList = new CopyOnWriteArrayList<>();
        votes = new ArrayList<>();
    }

    public synchronized void sendVote(String player) {
        votes.add(player);

        if (votes.size() == playersList.size()) {

            game.toNight = true;
            synchronized (this) {
                game.notifyDay();
            }
        }
    }

    public synchronized void sendReadyStatus() {
        synchronized (this) {
            countPlayers++;
        }
        if (countPlayers >= 4 && isStartGame) {
            exit = true;
        }
    }

    public void start() {
        log("server started");
        game = new Game(this, playersList);

        try {
            serverSocket = new ServerSocket(port);
            clientThreadPool = Executors.newFixedThreadPool(numberPlayers);
            synchronized (this) {
                while (playersList.size() < numberPlayers) {
                    log("Server waiting new connection");
                    clientSocket = serverSocket.accept();
                    playerhandler = new PlayerHandler(this, clientSocket);
                    playersList.add(playerhandler);
                    clientThreadPool.submit(playerhandler);
                    if (playersList.size() == numberPlayers) {
                        break;
                    }
                }
            }

            game.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadCast(PlayerHandler playerHandler, String message) {

        for (PlayerHandler player : playersList) {
            if (playerHandler.equals(player)) {
                continue;
            }
            DataOutputStream outMessage = null;
            try {
                outMessage = new DataOutputStream(player.getClientSocket().getOutputStream());
                outMessage.writeBytes(message + ConsoleColors.RESET);
                log(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void broadCast(String message) {

        for (PlayerHandler player : playersList) {

            DataOutputStream outMessage = null;
            try {
                outMessage = new DataOutputStream(player.getClientSocket().getOutputStream());
                outMessage.writeBytes(message + ConsoleColors.RESET);
                log(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setPlayerToKill(String playerNameKilledByWolf) {

        for (PlayerHandler player : playersList) {
            if (player.getAlias().equals(playerNameKilledByWolf)) {
                playerToKill = player;
                player.die();
                sendKilledMessage(playerToKill);
                log("player to be killed by wolf chosen");
            }
        }
        game.toDay = true;
        game.notifyNight();
    }

    public void sendKilledMessage(PlayerHandler player) {

        String message = "you've been killed by the wolf\n";
        if (player.isWolf()) {
            message = "you've been killed by villagers\n";
        }
        DataOutputStream outMessage = null;
        try {
            outMessage = new DataOutputStream(player.getClientSocket().getOutputStream());
            outMessage.writeBytes(message  + ConsoleColors.RESET);
            log(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void log(PlayerHandler playerHandler, String message) {
        System.out.println(ConsoleColors.RESET + playerHandler.getAlias() + " - " + message);
    }

    public static void log(String message) {
        System.out.println(ConsoleColors.RESET + message);
    }

    public CopyOnWriteArrayList<PlayerHandler> getPlayersList() {
        return playersList;
    }

    public Game getGame() {
        return game;
    }

    public ArrayList<String> getVotes() {
        return votes;
    }

    public boolean getIsStartGame() {
        return isStartGame;
    }
}