package org.academiadecodigo.bootcamp.villagegame.game;

import org.academiadecodigo.bootcamp.villagegame.aesthetics.AsciiArt;
import org.academiadecodigo.bootcamp.villagegame.server.PlayerHandler;
import org.academiadecodigo.bootcamp.villagegame.server.Server;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game {

    private CopyOnWriteArrayList<PlayerHandler> inGamePlayersList;
    private Server server;
    private Map<String, Integer> votes;
    private PlayerHandler wolf;
    public volatile boolean toNight;
    public volatile boolean toDay;
    private volatile boolean voteTime;

    public Game(Server server, CopyOnWriteArrayList inGamePlayersList) {
        this.server = server;
        this.inGamePlayersList = inGamePlayersList;
        this.votes = new HashMap<>();
        toNight = false;
        toDay = false;
        voteTime = false;
    }

    public void start() {

        System.out.println("game started");
        server.broadCast(AsciiArt.gameStartString());
        Server.log("game started");
        assignRoles();
        start2();
    }

    private synchronized void start2() {

        while (!wolf.isDead() || inGamePlayersList.size() > 1) {
            while (!toNight) {
                try {
                    if (wolf.isDead()) return;
                    dayTime();
                    wait();
                } catch (InterruptedException e) {
                    System.out.println("erro");
                }
            }
            System.out.println("end of daytime");

            while (!voteTime) {
                try {
                    if (wolf.isDead()) {
                        gameEnds();
                        return;
                    }
                    voteTime();
                    wait();
                } catch (InterruptedException e) {
                    System.out.println("erro");
                }
            }
            while (!toDay) {

                try {
                    if (wolf.isDead()) {
                        gameEnds();
                        return;
                    }
                    nightTime();
                    wait();
                } catch (InterruptedException e) {
                    System.out.println("erro");
                }
            }
            toDay = false;
            toNight = false;
            gameEnds();
        }
    }

    public void gameEnds() {

        server.broadCast(AsciiArt.gameOver());
        String winningStr;
        if (wolf.isDead()) {
            winningStr = "  ============================= Villagers Won! =============================";
        } else {
            winningStr = "  ================================ Wolf Won! ===============================";
        }
        server.broadCast(winningStr);
        Server.log(winningStr);
    }

    public synchronized void notifyDay() {
        System.out.println(toNight);
        notifyAll();
    }

    public synchronized void notifyNight() {
        notifyAll();
    }

    private synchronized void dayTime() {

        Server.log("daytime started");
        server.broadCast(AsciiArt.dayTimeMessage());
        server.broadCast("\n Waiting for all players to vote. Type /help to see commands. \n");
    }

    public synchronized void voteTime() {

        String result = votingDecisions(); // waits for voting decisions
        if (!result.equals("Tied")) {

            for (PlayerHandler player : inGamePlayersList) {
                if (player.getAlias().equals(result)) {
                    server.broadCast(AsciiArt.ExecuteVillager());
                    player.die();
                    notifyDay();

                    break;
                }
            }
        }
        votes.clear();
    }

    public synchronized void votingTime() {
        this.voteTime = true;
        notifyAll();
    }

    private synchronized void nightTime() {
        synchronized (this) {
            Server.log("night time started");
            server.broadCast(AsciiArt.nightTimeMessage());
        }
    }

    private void assignRoles() {

        int rand = (int) (Math.random() * inGamePlayersList.size());
        wolf = inGamePlayersList.get(rand);
        wolf.makeWolf();
        Server.log("wolf chosen.");

        wolf.returnMessage(AsciiArt.wolfInformation());
    }

    private Map<String, Integer> votesStatistic() {

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
        int count = 0;

        for (String key : votesStatistic().keySet()) {
            if (max < votesStatistic().get(key)) {
                max = votesStatistic().get(key);
                mostVoted = key;
            }
        }

        for (Integer value : votesStatistic().values()) {

            if (value == max) {
                count++;
            }
        }

        return count > 0 ? "Tied" : mostVoted;
    }
}