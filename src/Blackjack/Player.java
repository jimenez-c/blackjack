package Blackjack;

import Cards.*;

public abstract class Player {

    /**
     * Player's name.
     */
    protected String name;
    /**
     * Player's total tokens.
     */
    protected int tokens;
    /**
     * Number of tokens this player wish to bet (updated each round).
     */
    protected int stake;

    /**
     * Current cards in player's hand.
     */
    protected Deck hand;

    // choose() constants
    protected final static int CHOICE_CARD = 1;
    protected final static int CHOICE_STOP = 2;

    public Player(String name) {
        this.name = name;
        this.tokens = 50;
        this.hand = new Deck();
    }

    /**
     * @return this player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * @return this player's hand.
     */
    public Deck getHand() {
        return hand;
    }

    /**
     * Ask player to choose an action until the choice is {@link Player#CHOICE_STOP} or the player is busted.
     * @param players the current player needs to be informed of other's players hands to take a good decision.
     * @param dealer the dealer must be here to give new cards to the current player if needed.
     */
    public void play(RoundState state, Dealer dealer) {
        Logger.write("\n**   Tour de " + name + "   **\n");

        printCards();
        while (choose() == CHOICE_CARD) {
            Logger.write(name + " demande une carte.");
            dealer.giveCard(this);
            if (Game.isBusted(this)) {
                Logger.write(name + " a perdu.");
                break;
            }
        }

        if( ! Game.isBusted(this)) {
            Logger.write(name + " s'arrête là.");
            printCards();
        }
    }

    /**
     * Print player's hand (in std output).
     */
    public void printCards() {
        if(hand.getSize() == 0) {
            Logger.write(name + " n'a aucune carte.");
        } else {
            String str = name + " a ", end;
            for (int i = 0; i < hand.getSize(); i++) {
                end = (i == hand.getSize() - 1) ? "." : ", ";
                str += "le " + hand.get(i) + end;
            }
            str += " => " + Game.total(this) + "\n";
            Logger.write(str);
        }
    }
    /**
     * Add a card to this player's hand.
     * @param card card to add.
     */
    public void addCard(Card card) {
        Logger.write(name + " a reçu un " + card);
        this.hand.addCard(card);
    }

    public int getStake() {
        return stake;
    }

    public void setStake(int stake) {
        if(stake > tokens) {
            throw new RuntimeException(this + " ne peut pas miser plus que son total de jetons.");
        }
        this.stake = stake;
    }

    public int getTokens() {
        return tokens;
    }

    public void addTokens(int nb) {
        tokens += nb;
    }

    public void removeTokens(int nb) {
        tokens -= nb;
    }

    public boolean isHuman() {
        return is("HumanPlayer");
    }

    public boolean isBot() {
        return is("BotPlayer");
    }

    public boolean isDealer() {
        return is("Dealer");
    }
    /**
     * Used to know which kind of player this is.
     * @param classname the class to check, as a String (could have been a Class object, too)
     * @return true if *this* is a *classname*, false otherwise
     */
    private boolean is(String classname) {
        return (this.getClass().getSimpleName().equals(classname)) ? true : false;
    }

    @Override
    public String toString() {
        return name ;
    }

    /**
     * Asks the player to choose an action.
     * For now, actions are :
     * <ul>
     *  <li>Requesting a new card. {@link Player#CHOICE_CARD} </li>
     *  <li>End turn. {@link Player#CHOICE_STOP}</li>
     * </ul>
     * @return
     */
    public abstract int choose();

    /**
     * Asks the player to bet.
     */
    public abstract int bet();
}
