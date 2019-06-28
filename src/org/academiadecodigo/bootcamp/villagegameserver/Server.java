package org.academiadecodigo.bootcamp.villagegameserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;

public class Server {

    private ExecutorService clientThreadPool;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private Game game;
    private PlayerHandler playerhandler;

    private CopyOnWriteArrayList<PlayerHandler> playersList;


    public static void main(String[] args) {

    }

    public void start(){

    }


    public void broadCast(String message){
// TODO: 6/28/19 JAY
    }
}
