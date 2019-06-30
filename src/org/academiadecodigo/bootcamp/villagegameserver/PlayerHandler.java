/**
 * Created by Jonathan Cuendet
 */

package org.academiadecodigo.bootcamp.villagegameserver;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class PlayerHandler implements Runnable {

    private Server server;
    private Socket clientSocket;
    private Prompt prompt;
    private String alias;
    private boolean wolf;
    private boolean dead;
    private CommandsHandler commandsHandler;
    private PrintStream out;
    private InputStream in;

    public PlayerHandler(Server server, Socket clientSocket) {
        this.commandsHandler = new CommandsHandler(this);
        this.server = server;
        this.clientSocket = clientSocket;
        this.wolf = false;
        this.dead = false;
    }

    public void init() {
        try {
            this.out = new PrintStream(clientSocket.getOutputStream(), true);
            this.in = clientSocket.getInputStream();
            this.prompt = new Prompt(in, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void makeWolf() {
        this.wolf = true;
    }

    public String wolfVotes() {
        StringInputScanner wolfVote = new StringInputScanner();
        wolfVote.setMessage("Who will you kill tonight?");
        return prompt.getUserInput(wolfVote); // TODO: 30/06/2019 REPLACE BY promptMENU LOGIC
    }

    public void setAlias(String message) {
        this.alias = message;
    }

    public String getAlias() {
        return alias;
    }

    @Override
    public void run() {

        init();

        this.alias = setRandomAlias();

        Server.log(this,"joined the server");
        String message;

        StringInputScanner question1 = new StringInputScanner();
        question1.setMessage(getAlias() + " send: ");
        while (!clientSocket.isClosed() && (clientSocket != null)) {
            synchronized (this) {

                if ((message = prompt.getUserInput(question1)) == null) {
                    System.out.println("null");
                    return;
                }
                commandsHandler.handlePlayerInput(message);
            }
        }
    }

    public void playerToKill(String message) {
        server.setPlayerToKill(message);
    }

    public void readyToPlay() {
        server.sendReadyStatus();
    }

    private String setRandomAlias() {
        String usr = Thread.currentThread().getName();
        return "User_" + usr.substring(usr.length() - 1);
    }

    public void die() {
        dead = true;
    }

    public boolean isDead() {
        return dead;
    }

    public void kill(PlayerHandler playerHandler) {

        playerHandler.die();
        server.sendKilledMessage(playerHandler);
    }

    public void broadCastMessage(String message) {
        server.broadCast(this, message);
    }

    private void closeAll(Closeable closeable) {

    }

    public void vote(String votedPlayer) {
        server.sendVote(votedPlayer);
    }

    public void returnMessage(String message) {
        out.println(message + ConsoleColors.RESET);
    }

    public void log(String message) {
        Server.log(this, message);
    }

    public void exit() {
        die();
    }

    public void quit() {

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
}
