package org.academiadecodigo.bootcamp.villagegameserver;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game {

    private CopyOnWriteArrayList<PlayerHandler> inGamePlayersList;
    private Server server;

    public Game(Server server, CopyOnWriteArrayList inGamePlayersList) {
        this.server = server;
        this.inGamePlayersList = inGamePlayersList;
    }

    public void start() {
        // TODO: 6/28/19 start game logic
        assignRoles();
    }


    /**
     * during day, players discuss in order to try to get an opinion on who's the wolf
     * when everybody has voted, day ends and night begins
     */
    private void dayTime() {

        // everything goes as normal during the day in terms of chat
        // when voting completes game proceeds, determined by game.start()

        String result = votingDecisions(); // waits for voting decisions

        for (PlayerHandler player : inGamePlayersList) {

            if (player.getAlias().equals(result)) {

                player.die();
            } // else nothing happens
        }
    }

    /**
     * during night, chat is silent to alive people
     * wolf gets prompt from server asking for a killing vote
     * when wolf votes, day begins with a message indicating which player died
     */
    private void nightTime() {

        for (PlayerHandler player : inGamePlayersList) {

            if (player.isWolf()) {
                // TODO: 29/06/2019 wolf gets menu to votekill and picks a player, which dies
                // TODO: 29/06/2019 other player's chat gets silent == does not broadcast
                // TODO: 29/06/2019 voting decision is blocking by library implementation

                PlayerHandler votedByWolf = server.getPlayerToKill(); // vote() in handler class...
                player.kill(votedByWolf);
                break;
            }
        }
    }

    private void assignRoles() {

        int rand = (int) (Math.random() * inGamePlayersList.size());
        inGamePlayersList.get(rand).makeWolf();
    }

    private Map<String, Integer> votesStatistic() {

        Map<String, Integer> votes = new HashMap<>();

        for (String vote : server.getVotes()) {

            if (votes.containsKey(vote)) {
                votes.replace(vote, votes.get(vote) + 1);
                continue;
            }
            votes.put(vote, 1);
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