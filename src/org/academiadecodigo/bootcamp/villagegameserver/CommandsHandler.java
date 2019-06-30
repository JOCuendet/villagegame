/**
 * Created by: Jonathan Cuendet
 */

package org.academiadecodigo.bootcamp.villagegameserver;

import java.util.StringJoiner;

public class CommandsHandler {
    private PlayerHandler playerHandler;

    public CommandsHandler(PlayerHandler playerHandler) {
        this.playerHandler = playerHandler;
    }


    private String getCommandOption(String message) {
        String[] option = message.split(" ");
        return option[1];
    }

    private  String join(String[] strings, int startIndex) {
        StringBuffer sb = new StringBuffer();
        for (int i=startIndex; i < strings.length; i++) {
            sb.append(strings[i]);
        }
        return sb.toString();
    }

    private String getCommand(String message) {

        String[] commandSplitted = message.split("/");

        String command = join(commandSplitted, 0);

        commandSplitted = command.split(" ");

        return commandSplitted[0];
    }

    public void handlePlayerInput(String message) {
        if (message.startsWith("/")) {
            // TODO: 29/06/2019 broadcast message.
            String teststring = getCommand(message);
            switch (teststring) {
                case "help":
                    playerHandler.returnMessage(getHelp());
                    break;
                case "exit":
                    playerHandler.exit();
                    break;
                case "alias":
                    playerHandler.setAlias(getCommandOption(message));
                    break;
                case "list":
                    playerHandler.returnMessage(playerHandler.showList());
                    break;
                case "ready":
                    playerHandler.readyToPlay();
                    break;
                case "quit":
                    playerHandler.quit();
                    break;
                case "vote":
                    playerHandler.vote(getCommandOption(message));
                    break;
                case "kill":
                    playerHandler.playerToKill(getCommandOption(message));
                    break;
                default:
                    playerHandler.returnMessage("command not found");
                    break;
            }
            return;
        }
        playerHandler.broadCastMessage(message);
        playerHandler.log(message);
    }

    private String getHelp() {
        String helpMessage = "\n" +
                "\n  # GENERAL #" +
                "\n /help                           [show help menu]" +
                "\n /exit                           [exit Game and Server]" +
                "\n /alias newalias                 [changes your alias/nickname]" +
                "\n /list -users                    [shows a list of all users connected]" +
                "\n" +
                "\n" +
                "\n # GAME #";

        if (!playerHandler.getIsGameStart()) {
            helpMessage += "\n /ready                          [set ready to play]" +
                    "\n /quit                           [Exit the game]";
        } else {
            if(true) { // TODO: 30/06/2019 FERNANDO MUDA ISTO COM A CENA DE SE JA VOTOU OU NAO
                helpMessage +=
                        "\n /vote                           [vote for the person you think it is the wolf]";
            }else{
                helpMessage += "\n /changevote                     [vote for the person you think it is the wolf]";
            }

            if (playerHandler.isWolf()) {
                helpMessage += "\n" +
                        "\n # YOU ARE THE WOLF #" +
                        "\n /kill                           [chose your victim]";
            }
        }

        helpMessage += "\n\n";
        return helpMessage;
    }
}
