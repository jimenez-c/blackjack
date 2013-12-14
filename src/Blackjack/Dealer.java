package Blackjack;

import Cards.*;
import java.util.ArrayList;

/**
 * Represents a black jack dealer, that holds the game global deck.
 * @author cyril
 */
public class Dealer extends BotPlayer {

    /**
     * Game global deck. Every card is taken from there.
     * Note that dealer also has a Hand (a little deck) so that he can play, by inheritance.
     */
    private Deck deck;

    /**
     * Dealer's second card, hidden until his turn. We need a reference to this card in order to show it later.
     */
    private Card invisibleCard;

    public Dealer() {
        super("Le croupier");
        deck = Deck.createGameDeck(Game.NB_DECKS_BLACKJACK);
        this.tokens = 10000;
    }


    /**
     * Deals 2 cards to each player, 1 to the dealer.
     * You may "cheat" (give specific hand to some players) by changing the variable. For testing purposes only.
     * @param players
     * @return players with updated hands
     */
    public void deal(ArrayList<Player> players) {
        int cheat = 0;

        // pas de triche
        if(cheat == 0) {
            standardDealPlayers(players);
            standardDealDealer();
        }
        // les bots font blackjack
        else if(cheat == 1) {
            blackjackPlayers(players);
            standardDealDealer();
        }
        // le croupier fait blackjack
        else if(cheat == 2) {
            standardDealPlayers(players);
            blackjackDealer();
        }
        // le croupier et les bots font blackjack
        else if (cheat == 3) {
            blackjackPlayers(players);
            blackjackDealer();
        }
        // égalité entre deux joueurs
        else if(cheat == 4) {
            for(Player pl : players) {
                pl.addCard(new Card(10, 1));
                Card c = new Card(10, 2);
                pl.addCard(c);
                if(pl.isDealer()) {
                    c.hide();
                    invisibleCard = c;
                }
            }
        }

    }

    public void standardDealPlayers(ArrayList<Player> players) {
        for(Player player : players) {
            if( ! player.isDealer()) {
                giveCard(player);
                giveCard(player);
            }
        }
    }
    public void standardDealDealer() {
        // le dealer se distribue une carte face visible, une face cachée
        giveCard(this);
        invisibleCard = giveCard(this, false);
    }
    public void blackjackPlayers(ArrayList<Player> players) {
        for(Player player : players) {
            if( player.isBot()) {
                player.hand = new Deck();
                player.addCard(new Card(1, 1));
                player.addCard(new Card('K', 1));
            } else if (! player.isDealer()) {
                giveCard(player);
                giveCard(player);
            }
        }
    }
    public void blackjackDealer() {
        hand = new Deck();
        addCard(new Card(1, 2));
        invisibleCard = new Card('Q', 0);
        invisibleCard.hide();
        addCard(invisibleCard);
    }

    /**
     * Defines dealer's specific way of playing.
     * @param players
     * @param dealer
     * @see Player#play(java.util.ArrayList, Blackjack.Dealer)
     */
    @Override
    public void play(RoundState state, Dealer dealer) {
        // on retourne la carte cachée
        showInvisibleCard();
        // vérification finale des blackjacks
        if( ! Game.checkBlackjack(state.getPlayers())) {
            super.play(state, dealer);
        }
    }

    public void showInvisibleCard() {
        invisibleCard.show();
        Logger.write("Le croupier retourne sa carte : c'est un " + invisibleCard);
    }

    /**
     * Give a number of random cards to each player (taken from Dealer's deck)
     * @param players
     * @param nb the number of cards to give to each player.
     */
    public void giveCards(ArrayList<Player> players, int nb) {
        // distribution des cartes
        for (int i = 0; i < players.size(); i++) {
            for (int j = 0; j < nb; j++) {
                this.giveCard(players.get(i));
            }
        }
    }

    /**
     * Give a random card to one player (taken from Dealer's deck).
     * @param player
     * @param visible if false, given card will be hidden.
     * @return given card.
     */
    public Card giveCard(Player player, boolean visible) {
        // génération d'un indice aléatoire
        int index = Random.getInt(deck.getSize() - 1);
        // on récupère la carte correspondant à l'indice
        Card card = deck.pick(index);
        if( ! visible) {
            card.hide();
        }
        player.addCard(card);
        return card;
    }

    /**
     * Give a visible random card to one player.
     * @param player
     * @return the given card.
     */
    public Card giveCard(Player player) {
        return giveCard(player, true);
    }

    /**
     * Take every cards of every players, and replace them in Dealer's deck.
     * @param players
     */
    public void takeCards(ArrayList<Player> players) {
        for (int i = 0; i < players.size(); i++) {
            while(players.get(i).hand.getSize() != 0) {
                Card takenCard = players.get(i).hand.pick(0);
                deck.addCard(takenCard);
            }
        }
    }

//    // retire toutes ses cartes à un joueur
//    public void takeCards(Player player) {
//        for(int i = 0; i < player.hand.getSize(); i++) {
//            takeCard(player, i);
//        }
//    }
//
//    // retire une carte à un joueur
//    public void takeCard(Player player, int index) {
//        Card takenCard = player.hand.pick(index);
//        deck.addCard(takenCard);
//
//    }

}
