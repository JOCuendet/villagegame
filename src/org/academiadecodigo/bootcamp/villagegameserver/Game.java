package org.academiadecodigo.bootcamp.villagegameserver;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game {

    private CopyOnWriteArrayList<PlayerHandler> inGamePlayersList;
    private Server server;
    private Map<String, Integer> votes;
    private PlayerHandler wolf;
    private int numberOfVotes = 0;
    private PlayerHandler votedByWolf;

    public Game(Server server, CopyOnWriteArrayList inGamePlayersList) {
        this.server = server;
        this.inGamePlayersList = inGamePlayersList;
        this.votes = new HashMap<>();
    }

    public void start() {
        server.broadCast(AsciiArt.gameStartString());
        Server.log("game started");
        assignRoles();

        // TODO: 29/06/2019 day logic: until voting ends day happens, after that night happens
        while (!wolf.isDead() || inGamePlayersList.size() > 1) {

            dayTime();
            nightTime();
        }
    }

    /**
     * during day, players discuss in order to try to get an opinion on who's the wolf
     * when everybody has voted, day ends and night begins
     */
    private void dayTime(){
        Server.log("daytime started");
        server.broadCast(AsciiArt.dayTimeMessage());

        String result = votingDecisions(); // waits for voting decisions

        // TODO: 30/06/2019 isto em baixo é supostamente o tipo de implementação que previne o dia de
        // TODO: 30/06/2019 acontecer se o nº de votos não for igual ao nº de jogadores
        while (votes.keySet().size() != numberOfVotes) {
            server.broadCast("Waiting for all players to vote. Type /help to see commands.");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (PlayerHandler player : inGamePlayersList) {

            if (player.getAlias().equals(result)) {
                server.broadCast("There will be BLOOD tonight.");
                player.die();
                inGamePlayersList.remove(player);
                break;
            }
        }
        votes.clear();
        // nightTime();      //todo | inicialmente tinha isto aqui, mas como o dia e a noite são bloqueantes, o while loop do start
                             //todo | fá-los naturalmente acontecerem um a seguir ao outro
    }


    /**
     * during night, chat is silent to alive people
     * wolf gets prompt from server asking for a killing vote
     * when wolf votes, day begins with a message indicating which player died
     */
    private void nightTime() {
        Server.log("night time started");
        server.broadCast(AsciiArt.nightTimeMessage());
        for (PlayerHandler player : inGamePlayersList) {

            if (player.isWolf()) {

                server.setPlayerToKill(player.wolfVotes());
                votedByWolf = server.getPlayerToKill();
                player.kill(votedByWolf);
                break;
            }
        }
        Server.log("player " +votedByWolf.getAlias() + " killed by wolf.");
        server.broadCast("Player: "+ votedByWolf.getAlias() +" got killed by the WOLF");
        // dayTime(); same as in dayTime
    }

    private void assignRoles() {

        int rand = (int) (Math.random() * inGamePlayersList.size());
        wolf = inGamePlayersList.get(rand);
        wolf.makeWolf();
        Server.log("wolf chosen.");
    }

    private Map<String, Integer> votesStatistic() {

        for (String vote : server.getVotes()) {

            if (votes.containsKey(vote)) {
                votes.replace(vote, votes.get(vote) + 1);
                numberOfVotes++;
                continue;
            }
            votes.put(vote, 1);
            numberOfVotes++;
        }
        return votes;
    }

    private String votingDecisions() {

        int max = 0;
        String mostVoted = "";

        for (String key : votesStatistic().keySet()) {

            if (max < votesStatistic().get(key)) {

                max = votesStatistic().get(key);
                mostVoted = key;
            }
        }
        return votesStatistic().containsValue(max) ? "Tied" : mostVoted;
    }
}