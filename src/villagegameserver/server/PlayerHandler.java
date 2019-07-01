/**
 * Created by Jonathan Cuendet
 */

package villagegameserver.server;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;
import villagegameserver.aesthetics.AsciiArt;
import villagegameserver.aesthetics.ConsoleColors;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class PlayerHandler implements Runnable {

    public volatile Server server;
    private Socket clientSocket;
    private volatile Prompt prompt;
    private String alias;
    private boolean wolf;
    private boolean dead;
    private CommandsHandler commandsHandler;
    private PrintStream out;
    private InputStream in;
    private StringInputScanner question1;

    PlayerHandler(Server server, Socket clientSocket) {
        this.commandsHandler = new CommandsHandler(this);
        this.server = server;
        this.clientSocket = clientSocket;
        this.wolf = false;
        this.dead = false;
    }

    private void init() {
        try {
            this.out = new PrintStream(clientSocket.getOutputStream(), true);
            this.in = clientSocket.getInputStream();
            this.prompt = new Prompt(in, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        init();
        this.alias = setRandomAlias();
        Server.log(this, "joined the server");
        returnMessage(AsciiArt.welcomeMessage());

        BufferedReader userInputStream = null;
        String message;

        while (!clientSocket.isClosed() && (clientSocket != null)) {
            synchronized (this) {
                try {
                    userInputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    if ((message = userInputStream.readLine()) == null) {
                        quit();
                        exit();
                        return;
                    }
                    commandsHandler.handlePlayerInput(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void playerToKill(String message) {
        server.setPlayerToKill(message);
    }

    public void readyToPlay() {
        server.sendReadyStatus();
    }

    public void die() {
        dead = true;
    }

    public void broadCastMessage(String message) {
        server.broadCast(this, message);
    }

    public void vote(String votedPlayer) {
        server.sendVote(votedPlayer);
    }

    public void returnMessage(String message) {
        out.println(message + ConsoleColors.RESET);
    }

    public void quit() {
        die();
    }

    public void exit() {

        try {
            server.getPlayersList().remove(this);
            in.close();
            out.close();
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String showList() {

        StringBuilder clientsList = new StringBuilder();
        for (PlayerHandler list : server.getPlayersList()) {
            clientsList.append(list.alias + " , ");
        }
        clientsList.substring(clientsList.length() - 2);

        return clientsList + "";
    }

    public CopyOnWriteArrayList<PlayerHandler> getFromServerPlayerList() {
        return server.getPlayersList();
    }

    private String setRandomAlias() {
        String usr = Thread.currentThread().getName();
        return "User_" + usr.substring(usr.length() - 1);
    }

    public void makeWolf() {
        this.wolf = true;
    }

    public void setAlias(String message) {
        this.alias = message;
    }

    public boolean isDead() {
        return dead;
    }

    public boolean getIsGameStart() {
        return server.getIsStartGame();
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public boolean isWolf() {
        return wolf;
    }

    public String getAlias() {
        return alias;
    }

    public Prompt getPrompt() {
        return prompt;
    }
}