/**
 * Created by: Jonathan Cuendet
 */

package villagegameserver.aesthetics;

public class AsciiArt {

    public static String gameStartString() {
        String gameStart = "\n" + ConsoleColors.PURPLE +
                "\n   _______  _______  _______  _______    _______ _________ _______  _______ _________" +
                "\n  (  ____ \\(  ___  )(       )(  ____ \\  (  ____ \\\\__   __/(  ___  )(  ____ )\\__   __/" +
                "\n  | (    \\/| (   ) || () () || (    \\/  | (    \\/   ) (   | (   ) || (    )|   ) (   " +
                "\n  | |      | (___) || || || || (__      | (_____    | |   | (___) || (____)|   | |" +
                "\n  | | ____ |  ___  || |(_)| ||  __)     (_____  )   | |   |  ___  ||     __)   | |" +
                "\n  | | \\_  )| (   ) || |   | || (              ) |   | |   | (   ) || (\\ (      | |   " +
                "\n  | (___) || )   ( || )   ( || (____/\\  /\\____) |   | |   | )   ( || ) \\ \\__   | |   " +
                "\n  (_______)|/     \\||/     \\|(_______/  \\_______)   )_(   |/     \\||/   \\__/   )_(   " +
                "\n  ";
        return gameStart; // epic FONT
    }

    public static String dayTimeMessage() {
        String dayTime = "\n" + ConsoleColors.YELLOW +
                "\n  ============================ STARTING ===========================" +
                "\n   ______   _______            __________________ _______  _______" +
                "\n  (  __  \\ (  ___  )|\\     /|  \\__   __/\\__   __/(       )(  ____\\ " +
                "\n  | (  \\  )| (   ) |( \\   / )     ) (      ) (   | () () || (    \\/" +
                "\n  | |   ) || (___) | \\ (_) /      | |      | |   | || || || (__" +
                "\n  | |   | ||  ___  |  \\   /       | |      | |   | |(_)| ||  __)" +
                "\n  | |   ) || (   ) |   ) (        | |      | |   | |   | || (" +
                "\n  | (__/  )| )   ( |   | |        | |   ___) (___| )   ( || (____/\\ " +
                "\n  (______/ |/     \\|   \\_/        )_(   \\_______/|/     \\|(_______/" +
                "\n  ========================== TIME TO VOTE! =========================" +
                "\n";
        return dayTime;  // epic FONT
    }

    public static String nightTimeMessage() {
        String nightTime = "\n" + ConsoleColors.BLUE_BOLD +
                "\n  ===================================== STARTING =====================================" +
                "\n   _       _________ _______          _________  __________________ _______  _______" +
                "\n  ( (    /|\\__   __/(  ____ \\|\\     /|\\__   __/  \\__   __/\\__   __/(       )(  ____ \\ " +
                "\n  |  \\  ( |   ) (   | (    \\/| )   ( |   ) (        ) (      ) (   | () () || (    \\/" +
                "\n  |   \\ | |   | |   | |      | (___) |   | |        | |      | |   | || || || (__    " +
                "\n  | (\\ \\) |   | |   | | ____ |  ___  |   | |        | |      | |   | |(_)| ||  __)" +
                "\n  | | \\   |   | |   | | \\_  )| (   ) |   | |        | |      | |   | |   | || (" +
                "\n  | )  \\  |___) (___| (___) || )   ( |   | |        | |   ___) (___| )   ( || (____/\\" +
                "\n  |/    )_)\\_______/(_______)|/     \\|   )_(        )_(   \\_______/|/     \\|(_______/" +
                "\n  =========================== THERE WILL BE BLOOD TONIGHT ===========================" +
                "\n";

        return nightTime;  // epic FONT
    }

    public static String gameOver() {
        String gameOver = "\n" + ConsoleColors.CYAN_BOLD +
                "\n  ================================ GAME OVER ================================" +
                "\n   _______  _______  _______  _______    _______           _______  _______ " +
                "\n  (  ____ \\(  ___  )(       )(  ____ \\  (  ___  )|\\     /|(  ____ \\(  ____ )" +
                "\n  | (    \\/| (   ) || () () || (    \\/  | (   ) || )   ( || (    \\/| (    )|" +
                "\n  | |      | (___) || || || || (__      | |   | || |   | || (__    | (____)|" +
                "\n  | | ____ |  ___  || |(_)| ||  __)     | |   | |( (   ) )|  __)   |     __)" +
                "\n  | | \\_  )| (   ) || |   | || (        | |   | | \\ \\_/ / | (      | (\\ (   " +
                "\n  | (___) || )   ( || )   ( || (____/\\  | (___) |  \\   /  | (____/\\| ) \\ \\__" +
                "\n  (_______)|/     \\||/     \\|(_______/  (_______)   \\_/   (_______/|/   \\__/" +
                "\n " +
                "\n        =============================================================== " +
                "\n       |                          # CREDITS #                          |" +
                "\n       |                                                               |" +
                "\n       |     //Jonathan Cuendet             // João Sanches            |" +
                "\n       |     // Edma Augusto                // Fernando Abreu          |" +
                "\n       |                                                               |" +
                "\n        =============================================================== " +
                "\n ";
        return gameOver;  // epic FONT
    }

    public static String wolfInformation(){

        String wolf = "\n" + ConsoleColors.RED +
                "\n   =============================================================== " +
                "\n  |                                                               |" +
                "\n  |                      YOU ARE THE WOLF                         |" +
                "\n  |                                                               |" +
                "\n   =============================================================== " +
                "\n ";
        return wolf;  // epic FONT

    }

    public static String ExecuteVillager() {
        String execute = "\n" + ConsoleColors.GREEN_BOLD +
                "\n   =============================================================== " +
                "\n  |                       use command                              |" +
                "\n  |                        /execute                                |" +
                "\n  |               to kill the most voted player                    |" +
                "\n   =============================================================== " +
                "\n ";
        return execute;  // epic FONT
    }

    public static String welcomeMessage(){
        String welcomeMessage = "\n" + ConsoleColors.PURPLE_BOLD_BRIGHT +
                "\n   =============================================================== " +
                "\n  |                        VILLAGE GAME                           |" +
                "\n  |                                                               |" +
                "\n  |                kill the wolf before he kills                  |" +
                "\n  |                     all the villagers                         |" +
                "\n  |                                                               |" +
                "\n  |                         # RULES #                             |" +
                "\n  |                                                               |" +
                "\n  |                      # During the day #                       |" +
                "\n  |              the players chat and try to guess                |" +
                "\n  |                 who the wolf is by voting                     |" +
                "\n  |          day ends when the /execute command is typed          |" +
                "\n  |                                                               |" +
                "\n  |                      # During the night #                     |" +
                "\n  |                    the wolf uses the /kill                    |" +
                "\n  |                  to murder the chosen player                  |" +
                "\n  |                                                               |" +
                "\n  |                   # type /help for commands #                 |" +
                "\n  |                                                               |" +
                "\n   =============================================================== " +
                "\n ";
        return welcomeMessage;  // epic FONT
    }
}