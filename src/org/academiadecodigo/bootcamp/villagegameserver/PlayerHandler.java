package org.academiadecodigo.bootcamp.villagegameserver;

import org.academiadecodigo.bootcamp.Prompt;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.PrintWriter;
import java.net.Socket;

public class PlayerHandler implements Runnable {

    private Server server;
    private Socket clientSocket;
    private Prompt prompt;
    private String alias;
    private boolean wolf;
    private boolean dead;

    public PlayerHandler(){
        this.wolf = false;
        this.dead = false;
    }

    public boolean isWolf() {
        return wolf;
    }

    public void makeWolf() {
        this.wolf = true;
    }

    public void setAlias(String alias) {
        //todo
    }

    @Override
    public void run() {

    }
    private void die(){
        // TODO: 6/28/19 kill his ass.
    }
    public void sendMessage(String message){
       server.broadCast(message);
    }

    private void closeAll(Closeable closeable){

    }
    private void kill(PlayerHandler playerHandler){
        // TODO: 6/28/19 o jogador que for morto, broadcast kill message change status to dead.
    }
    private void vote(){
        // TODO: 6/28/19 receber a lista de jogadores ingame e votar num deles atraves de um deles.
    }
}
