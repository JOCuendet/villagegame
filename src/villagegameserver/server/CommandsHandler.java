/**
 * Created by: Jonathan Cuendet
 */

package villagegameserver.server;

import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;

import java.util.ArrayList;

public class CommandsHandler {

    private PlayerHandler playerHandler;

    public CommandsHandler(PlayerHandler playerHandler) {
        this.playerHandler = playerHandler;
    }

    private String join(String[] strings, int startIndex) {
        StringBuffer sb = new StringBuffer();
        for (int i = startIndex; i < strings.length; i++) {
            sb.append(strings[i]);
        }
        return sb.toString();
    }

    private String getCommandOption(String message) {
        String[] option = message.split(" ");
        return option[1];
    }

    private String getCommand(String message) {

        String[] commandSplitted = message.split("/");
        String command = join(commandSplitted, 0);
        commandSplitted = command.split(" ");
        return commandSplitted[0];
    }

    private String[] getPeopleAlive() {
        ArrayList<String> peopleAlive = new ArrayList<>();
        for (PlayerHandler player : playerHandler.getFromServerPlayerList()) {
            if (!player.isDead()) {
                peopleAlive.add(player.getAlias());
            }
        }
        String[] peopleLive = peopleAlive.toArray(new String[peopleAlive.size()]);
        return peopleLive;
    }

    public void handlePlayerInput(String message) {
        if (message.startsWith("/")) {

            String command = getCommand(message);

            switch (command) {
                case "help":
                    playerHandler.returnMessage(getHelp());
                    break;
                case "exit":
                    Server.log(playerHandler, "exited the game");
                    playerHandler.broadCastMessage(playerHandler.getAlias() + "committed suicide. he/she can talk to dead people now.");
                    playerHandler.exit();
                    break;
                case "alias":
                    Server.log(playerHandler, playerHandler.getAlias() + " Changed name to: " + getCommandOption(message));
                    playerHandler.broadCastMessage(playerHandler.getAlias() + " Changed name: " + getCommandOption(message));
                    playerHandler.setAlias(getCommandOption(message));
                    break;
                case "list":
                    playerHandler.returnMessage(playerHandler.showList());
                    break;
                case "ready":
                    playerHandler.broadCastMessage(playerHandler.getAlias() + " is ready to play.");
                    Server.log(playerHandler, "ready to play.");
                    playerHandler.readyToPlay();
                    break;
                case "quit":
                    playerHandler.broadCastMessage(playerHandler.getAlias() + " has left the game and the Server.");
                    Server.log(playerHandler, "left the Server.");
                    playerHandler.quit();
                    break;
                case "vote":
                    if(!playerHandler.isDead()) {
                        String[] voteOptions = getPeopleAlive();
                        MenuInputScanner menu = new MenuInputScanner(voteOptions);
                        menu.setMessage("vote");
                        int answerIdx = playerHandler.getPrompt().getUserInput(menu);
                        playerHandler.broadCastMessage(playerHandler.getAlias() + " voted.");
                        Server.log(playerHandler, "has voted.");
                        playerHandler.vote(voteOptions[answerIdx - 1]);
                    }
                    break;
                case "kill":
                    if(playerHandler.isWolf()) {
                        String[] voteOptions2 = getPeopleAlive();
                        MenuInputScanner menu2 = new MenuInputScanner(voteOptions2);
                        menu2.setMessage("vote");
                        int answerIdx2 = playerHandler.getPrompt().getUserInput(menu2);
                        playerHandler.broadCastMessage(playerHandler.getAlias() + " has voted.");
                        Server.log(playerHandler, " has voted.");
                        playerHandler.playerToKill(voteOptions2[answerIdx2 - 1]);
                    }
                    break;
                case "execute":
                    playerHandler.server.getGame().votingTime();
                    break;
                default:
                    playerHandler.returnMessage("command not found");
                    break;
            }
            return;
        }
        playerHandler.broadCastMessage(playerHandler.getAlias() + ": " + message + "\n");
        Server.log(playerHandler, message);
    }

    private String getHelp() {
        String helpMessage = "\n" +
                "\n  /COMMAND                       [ DESCRIPTION ]" +
                "\n" +
                "\n  # GENERAL #" +
                "\n /help                           [show help menu]" +
                "\n /vote                           [vote for the person you think it is the wolf]" +
                "\n /alias new alias                [changes your alias/nickname]" +
                "\n /list                           [shows a list of all users connected]" +
                "\n /exit                           [exit Game and Server]" +
                "\n                                   " +
                "\n                                   " +
                "\n # GAME #";

        if (!playerHandler.getIsGameStart()) {
            helpMessage += "\n /ready                          [ set ready to play ]" +
                    "\n /quit                           [ Exit the game ]";
        } else {
                helpMessage +=
                        "\n /vote                           [ vote for the person you think it is the wolf ]" +
                        "\n /execute                     [ executes the most voted player during day time ]";
        }

        if (playerHandler.isWolf()) {
            helpMessage += "\n" +
                    "\n # YOU ARE THE WOLF #" +
                    "\n /kill   Player name             [ chose your victim ]";
        }
        helpMessage += "\n\n";
        return helpMessage;
    }
}
