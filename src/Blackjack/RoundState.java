/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Blackjack;

import java.util.ArrayList;

/**
 * Represents the current state of a round.
 * @author cyril
 */
public class RoundState {
    /**
     * Every players in the round.
     */
    private ArrayList<Player> players;
    /**
     * Index of current player in players.
     */
    private int currentPlayerIndex;
    /**
     * The player who is playing right now.
     */
    private Player currentPlayer;
    /**
     * True when a black jack is detected.
     */
    private boolean blackjack;

    public RoundState(ArrayList<Player> players) {
        this.players = players;
        currentPlayerIndex = 0;
        blackjack = false;
    }

    /**
     * Update and return the current player.
     * @return the new current player.
     */
    public Player nextPlayer() {
        currentPlayer = players.get(currentPlayerIndex++);
        return currentPlayer;
    }

    /**
     * True until every players have played.
     * @return
     */
    public boolean isNextPlayer() {
        if(currentPlayerIndex < players.size()) {
            return true;
        }
        return false;
    }

    /**
     * @return true if a blackjack has been detected.
     */
    public boolean isBlackjack()  {
        return blackjack;
    }

    /**
     * Called when a blackjack has been detected.
     */
    public void setBlackjack() {
        blackjack = true;
    }

    /**
     * Returns the players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }


}
