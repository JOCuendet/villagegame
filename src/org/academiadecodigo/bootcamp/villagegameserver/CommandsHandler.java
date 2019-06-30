/**
 * Created by: Jonathan Cuendet
 */

package org.academiadecodigo.bootcamp.villagegameserver;

public class CommandsHandler {
    private PlayerHandler playerHandler;

    public CommandsHandler(PlayerHandler playerHandler) {
        this.playerHandler = playerHandler;
    }


    private String getCommandOption(String message) {
        String[] option = message.split(" ");
        return option[1];
    }

    private String join(String[] strings, int startIndex) {
        StringBuffer sb = new StringBuffer();
        for (int i = startIndex; i < strings.length; i++) {
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
                    Server.log(playerHandler, "exited the game");
                    playerHandler.broadCastMessage(playerHandler.getAlias() + "committed suicide. he/she can talk to dead people now.");
                    playerHandler.exit();
                    break;
                case "alias":
                    Server.log(playerHandler,playerHandler.getAlias() + "Changed name to: "+ message);
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
                    playerHandler.broadCastMessage(playerHandler.getAlias() + " voted.");
                    Server.log(playerHandler, "has voted.");
                    playerHandler.vote(getCommandOption(message));
                    break;
                case "kill":
                    playerHandler.broadCastMessage("The wolf, as chosen.");
                    Server.log("wolf as chosen.");
                    playerHandler.playerToKill(getCommandOption(message));
                    break;
                case "test":
                    playerHandler.returnMessage("test");
                    break;
                default:
                    playerHandler.returnMessage("command not found");
                    break;
            }
            return;
        }
        playerHandler.broadCastMessage(message);
        Server.log(playerHandler, message);
    }

    private String getHelp() {
        String helpMessage = "\n" +
                "\n  /COMMAND                       [ DESCRIPTION ]" +
                "\n" +
                "\n  # GENERAL #" +
                "\n /help                           [ show help menu ]" +
                "\n /exit                           [ exit Game and Server ]" +
                "\n /alias newalias                 [ changes your alias/nickname ]" +
                "\n /list -users                    [ shows a list of all users connected" +
                "\n                                   red = hasn't voted yet." +
                "\n                                   green = has voted. ]" +
                "\n # GAME #";

        if (!playerHandler.getIsGameStart()) {
            helpMessage += "\n /ready                          [ set ready to play ]" +
                    "\n /quit                           [ Exit the game ]";
        } else {
            if (true) { // TODO: 30/06/2019 FERNANDO MUDA ISTO COM A CENA DE SE JA VOTOU OU NAO
                helpMessage +=
                        "\n /vote                           [ vote for the person you think it is the wolf ]";
            } else {
                helpMessage += "\n /changevote                     [ vote for the person you think it is the wolf ]";
            }

            if (playerHandler.isWolf()) {
                helpMessage += "\n" +
                        "\n # YOU ARE THE WOLF #" +
                        "\n /kill                           [ chose your victim ]";
            }
        }

        helpMessage += "\n\n";
        return helpMessage;
    }
}
