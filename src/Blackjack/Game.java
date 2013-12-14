package Blackjack;

import Cards.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a black jack game.
 * @author cyril
 */

public class Game {
    /**
     * Players participating to this game.
     */
    private ArrayList<Player> players;
    /**
     * A special kind of player that handle the game deck.
     */
    private Dealer dealer;
    /**
     * Current round state.
     */
    private RoundState state;
    /**
     * Number of rounds elapsed.
     */
    private int nbRound;
    /**
     * Number of 52 cards decks in a game.
     */
    public final static int NB_DECKS_BLACKJACK = 6;


    /**
     * Game constructor. Create a new game with given players. Create a new Dealer. Launch the game.
     * @param players
     */
    public Game(Player[] players) {
        this.players = new ArrayList<>();
        this.players.addAll(Arrays.asList(players));
        dealer = new Dealer();
        this.players.add(dealer);
        nbRound = 1;

        String title = "Nouvelle partie avec ";
        for (int i = 0; i < this.players.size(); i++) {
            title += this.players.get(i).getName() + ", ";
        }
        Logger.write(title);

        // launch the first round.
        round();
    }

    /**
     * Decides if the game is over.
     * @return true or false.
     */
    private boolean isOver() {
        return players.size() == 1;
    }

    private void round() {
        Logger.write("\n************************");
        Logger.write("** Manche " + nbRound + " **");
        Logger.write("************************\n");

        state = new RoundState(players);

        // les joueurs donnent leurs mises
        for(Player player : players) {
            if( ! player.isDealer()) {
                int bet = player.bet();
                player.setStake(bet);
                Logger.write(player + " parie " + bet);
            }
        }

        // distribution des cartes
        dealer.deal(players);

        // vérification des blackjacks
        if(total(dealer).max() < 10) {
            if(checkBlackjack(players)) {
                state.setBlackjack();
            }
        }

        if( ! state.isBlackjack()) {
            // tour de chaque joueur
            while(state.isNextPlayer()) {
                Player currentPlayer = state.nextPlayer();
                currentPlayer.play(state, dealer);
            }
        }

        // gestion du ou des gagnants
        ArrayList<Player> winners = whoWins(players);

        // mise à jour des jetons de chaque joueur
        updateTokens(winners);

        // on retire leurs cartes aux joueurs
        dealer.takeCards(players);

        // incrémentation du nombre de manches
        nbRound++;

        // tant que le jeu n'est pas fini, relancer une manche.
        if( ! isOver()) {
            round();
        }
    }

    /**
     * Decides whether at least one player has a blackjack.
     * @param players The list of all players (hence implicitly their current hands)
     * @return true if at least one player has a blackjack, false otherwise
     */
    public static boolean checkBlackjack(ArrayList<Player> players) {
        for(Player player : players) {
            if(total(player).min() == 32) {
               return true;
            }
        }
        return false;
    }

    /**
     * Calculate total points of a player.
     * @param player
     * @return new Total object, with 32 as min and max if the player has a blackjack
     */
    public static Total total(Player player) {
        Deck hand = player.getHand();
        int min, max, value = 0, nbAces = 0;

        // ajout des cartes qui ne sont pas des as et comptage des as
        for (int i = 0; i < hand.getSize(); i++) {
            if(hand.get(i).isVisible()) {
                if(hand.get(i).getHeight() == 1) {
                    nbAces++;
                    value += 1;
                }
                else if(hand.get(i).getHeight() > 9) {
                    value += 10;
                }
                else {
                    value += hand.get(i).getHeight();
                }
            }
        }

        min = value;

        // ajout de dix points si on peut pour avoir le max
        if(value <= 11 && nbAces >= 1) {
            value += 10;
            max = value;
        } else {
            max = min;
        }

        // cas particulier : le joueur a un blackjack
        if(hand.getSize() == 2 && max == 21 && min == 11) {
            min = max = 32;
        }

        return new Total(min, max);
    }

    /**
     * Decides if a player has lost the round (e.g his total is > 21)
     * @param player
     * @return true if lost, false otherwise
     */
    public static boolean isBusted(Player player) {
        if(total(player).min() > 21 && total(player).min() != 32) {
            return true;
        }
        return false;
    }

    /**
     * Decides who won the round.
     * @param players
     * @return a list of winners, in an ArrayList object.
     */
    public ArrayList<Player> whoWins(ArrayList<Player> players) {

        // on créé un tableau pour stocker les gagnants
        ArrayList<Player> winners = new ArrayList<>();

        int bestScore = 0;

        for(Player player : players) {
            if( ! isBusted(player)) {
                if(total(player).max() > bestScore || (player.isDealer() && total(player).max() == bestScore)) {
                    bestScore = total(player).max();
                    winners.clear();
                    winners.add(player);
                }
                else if(total(player).max() == bestScore) {
                    winners.add(player);
                }
            } else {
                Logger.write(player.getName() + " a perdu, il perd sa mise :" + player.getStake());
                player.removeTokens(player.getStake());
            }
        }
        return winners;
    }

    /**
     * Update each player tokens in the end of a round.
     * @param winners
     */
    private void updateTokens(ArrayList<Player> winners) {
        // le joueur courant a-t-il été traité ? (gagné ou perdu)
        boolean done;
        for(Player player : players) {
            if( ! player.isDealer()) {
                done = false;
                for(Player winner : winners) {
                    if(winner == player) {
                        done = true;
                        int gain = player.getStake();
                        String txt = "";
                        if(total(player).max() == 32) {
                            gain = (int) (gain * 1.5);
                            txt = "avec un blackjack";
                        }
                        player.addTokens(gain);
                        Logger.write(player.getName() + " a gagné " + txt + ", il reçoit " + gain + " jetons [" + player.getTokens() + "]");
                    }
                }
                if(! done) {
                    player.removeTokens(player.getStake());
                    Logger.write(player.getName() + " a perdu, il donne " + player.getStake() + " au croupier. [" + player.getTokens() + "]");
                    // si le joueur n'a plus de jetons, il quitte la partie.
                    if(player.getTokens() <= 0) {
                        players.remove(player);
                        Logger.write(player.getName() + " n'a plus de jetons, il quitte la partie.");
                    }
                }
            }
        }
    }

}
