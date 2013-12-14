package Blackjack;

/**
 * Represents a black jack human player.
 * @author cyril
 */
public class HumanPlayer extends Player {

    public HumanPlayer(String name) {
        super(name);
    }

    /**
     * Asks the player to choose between getting a new card or stopping.
     * @return The player's choice.
     */
    @Override
    public int choose() {
        Logger.write("Que faire ?");
        Logger.write(CHOICE_CARD + ". Demander une autre carte.");
        Logger.write(CHOICE_STOP + ". S'arrêter là.");

        int choice;
        choice = Lecture.lireInt();

        if (choice == CHOICE_CARD || choice == CHOICE_STOP) {
            return choice;
        } else {
            Logger.write("Choix incorrect.");
            return choose();
        }
    }

    @Override
    public int bet() {
        Logger.write("Vous avez " + tokens + " jetons. Combien voulez-vous miser ?");
        int bet = Lecture.lireInt();
        if(bet <= 0 || bet > tokens) {
            Logger.write("Mise incorrecte.");
            return bet();
        }
        return bet;
    }
}
