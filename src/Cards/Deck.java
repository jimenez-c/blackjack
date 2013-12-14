package Cards;

import java.util.ArrayList;

/**
 * Represents a set of cards in general (may be a classical 52-cards deck, but also the hand of a player).
 * @author cyril
 */

public class Deck {
    /**
     * Cards in the deck.
     */
    protected ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
    }

    public Deck(int nb) {
        this();
        for(int i = 0; i < nb; i++) {
            addCard(new Card());
        }
    }

    /**
     * Merging of two decks.
     * @param deck
     */
//    public Deck(Deck deck) {
//        this();
//        addDeck(deck);
//    }

//    public void addDeck(Deck deck) {
//        for(int i = 0; i < deck.getSize(); i++) {
//            this.addCard(deck.get(i));
//        }
//    }

    /**
     * Add a card to the deck.
     * @param card the card to be added.
     * @return
     */
    public void addCard(Card card) {
        cards.add(card);
//        return card;
    }

    public int getSize() {
        return cards.size();
    }

    public Card get(int i) {
        return cards.get(i);
    }

    public String toString() {
        String str = "";
        for(int i = 0; i < getSize(); i++) {
            str += get(i) + "\n";
        }
        return str;
    }

    public Card pick(int i) {
        return cards.remove(i);
    }

    /**
     * Create a <em>nb</em> * 52-cards deck, e.g a deck containing 52 * <em>nb</em> cards.
     * @param nb
     * @return
     */
    public static Deck createGameDeck(int nb) {
        Deck deck = new Deck();

        // on boucle sur le nombre de paquets de cartes
        for(int i = 0; i < nb; i++) {
            // on boucle sur chaque famille
            for(int j = 0; j < 4; j++) {
                // on boucle sur chaque carte
                for(int k = 1; k <= 10; k++) {
                    deck.addCard(new Card(k, j));
                }
                deck.addCard(new Card('K', j));
                deck.addCard(new Card('Q', j));
                deck.addCard(new Card('J', j));
            }
        }
        return deck;
    }


}