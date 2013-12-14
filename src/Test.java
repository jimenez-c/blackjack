/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 21108766
 */

import Blackjack.*;

public class Test {
    public static void main(String[] args) {

        // création de la partie

        System.out.println("\n*****************");
        System.out.println("JEU DU BLACK JACK");
        System.out.println("*****************\n");

        System.out.println("Votre nom ?");
//      String playerName = Lecture.lireString();
        String playerName = "cyril";

        System.out.println("Contre combien de bots voulez-vous jouer ?");
//      int nbBots = Lecture.lireInt();
        int nbBots = 1;

        Player[] players = new Player[nbBots + 1];

        players[0] = new HumanPlayer(playerName);

        for(int i = 1; i < nbBots + 1; i ++) {
            players[i] = new BotPlayer();
        }

        // création de la partie
        Game game = new Game(players);

    }
}
