/**
 * Created by Jonathan Cuendet
 */

package org.academiadecodigo.bootcamp.villagegameserver;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class PlayerHandler implements Runnable {

    private Server server;
    private Socket clientSocket;
    private Prompt prompt;
    private String alias;
    private boolean wolf;
    private boolean dead;

    public PlayerHandler(Server server, Socket clientSocket){
        this.server = server;
        this.clientSocket = clientSocket;
        this.wolf = false;
        this.dead = false;
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

    public void setAlias(String message) {
        String[] str = message.split(" ");
        this.alias = str[1];
    }

    public String getAlias() {
        return alias;
    }

    @Override
    public void run() {
        this.alias = setRandomAlias();
        String message;
        try {
            this.prompt = new Prompt(clientSocket.getInputStream(), new PrintStream(clientSocket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringInputScanner question1 = new StringInputScanner();
        question1.setMessage(getAlias()+ ": ");
        if ((message = prompt.getUserInput(question1)) == null) {
            return;
        }

        if(message.startsWith("/vote ")){
            String[] str = message.split(" ");
            vote(str[1]);
            // TODO: 29/06/2019 broadcast message.
        }
        if(message.equals("/ready")){
            readyToPlay();
            // TODO: 29/06/2019 broadcast message.
        }
        if(message.equals("/start")){
            startGame();
        }
        if(message.startsWith("/alias ")){
            setAlias(message);
            // TODO: 29/06/2019 broadcast message.
        }
        if(message.startsWith("/wolfKills ")) {
            server.playerToKill(wolfKills(message));
        }
    }
    private String wolfKills(String message){
        String[] usrToKill = message.split(" ");
        return usrToKill[1];
    }
    private void startGame(){
        server.startGame();
    }

    private void readyToPlay(){
        server.sendReadyStatus();
    }

    private String setRandomAlias() {
        String usr = Thread.currentThread().getName();
        return "User_" + usr.substring(usr.length() - 1);
    }

    public void die(){
        //todo Edma
        // TODO: 6/28/19 kill his ass.
    }

    public void sendMessage(String message){
        server.broadCast(message);
    }

    private void closeAll(Closeable closeable){

    }

    public void kill(PlayerHandler playerHandler){
        // todo Edma
        // TODO: 6/28/19 o jogador que for morto, broadcast kill message change status to dead.
    }

    public void vote(String votedPlayer){
        server.sendVote(votedPlayer);
    }
}
