package org.academiadecodigo.bootcamp.villagegameserver;

import org.academiadecodigo.bootcamp.Prompt;

import java.util.concurrent.CopyOnWriteArrayList;

public class Game {
    private CopyOnWriteArrayList<PlayerHandler> inGamePlayersList;
    private Server server;

    public Game(Server server, CopyOnWriteArrayList inGamePlayersList){
        this.server = server;
        this.inGamePlayersList = inGamePlayersList;
    }

    public void start(){
        // TODO: 6/28/19 start game logic
    }

    private void dayTime(){
        //todo NAND
        // TODO: 6/28/19 day time game logic
    }
    private void nightTime(){
        // TODO: 6/28/19 NAND
        // TODO: 6/28/19 night time game logic
    }

    private void assignRolls(){
        // TODO: 6/28/19 EDMA
        // TODO: 6/28/19 assign wolf status
    }
    private void votingDecisions(){
        // TODO: 6/28/19 EDMA

    }



}
