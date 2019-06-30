/**
 * Created by: Jonathan Cuendet
 */

package org.academiadecodigo.bootcamp.villagegameserver;

public class CommandsHandler {
    private PlayerHandler playerHandler;

    public CommandsHandler(PlayerHandler playerHandler) {
        this.playerHandler = playerHandler;
    }

    private String getCommand(String message) {
        String[] command = message.split("/");
        return command[1];
    }

    public void handlePlayerInput(String message) {
        if (message.startsWith("/")) {
            // TODO: 29/06/2019 broadcast message.
            switch (getCommand(message)) {
                case "help":
                    playerHandler.makeWolf();
                    playerHandler.returnMessage(getHelp());
                    break;
                case "ready":
                    playerHandler.readyToPlay();
                    break;
                case "start":
                    playerHandler.startGame();
                    break;
                case "alias":
                    playerHandler.setAlias(message);
                    break;
//                case "wolfKills":
//                    playerHandler.playerToKill(getCommandOption(message));
//                    break;
                case "test":
                    playerHandler.returnMessage("command test accepted");
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
                "\n /exit                           [exit chat]" +
                "\n /alias newalias                 [changes your alias/nickname]" +
                "\n /list -users                    [shows a list of all users connected]" +
                "\n" +
                "\n" +
                "\n # GAME #";

        if (!playerHandler.isServerStartgame()) {
            helpMessage += "\n /ready                          [set ready to play]" +
                    "\n /start                          [if Players with ready-to-play above 4 the game will start]";
        } else {
            helpMessage +=
                    "\n /vote                           [vote for the person you think it is the wolf]";

            if (playerHandler.isWolf()) {
                helpMessage += "\n" +
                        "\n # YOU ARE THE WOLF #" +
                        "\n /kill                           [choose your victim]";
            }
        }

        helpMessage += "\n\n";

        return helpMessage;
    }
}


