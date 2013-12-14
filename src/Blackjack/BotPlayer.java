package Blackjack;


public class BotPlayer extends Player {

    /**
     * An array of names for bots.
     */
    public static String[] botsNames = initializeBotsNames();

    public BotPlayer(String name) {
        super(name);
    }

    /**
     * Creates a new Bot with a random name, and remove the name so that it cannot be used twice.
     */
    public BotPlayer() {
        this(botsNames[Cards.Random.getInt(botsNames.length)]);
        // on supprime le nom du tableau pour qu'il ne soit pas choisi deux fois
        String[] newBotsNames = new String[botsNames.length - 1];
        for(int i = 0, j = 0; i < botsNames.length; i++) {
            if( ! botsNames[i].equals(this.getName())) {
                newBotsNames[j] = botsNames[i];
                j++;
            }
        }
        botsNames = newBotsNames;
    }

    private static String[] initializeBotsNames() {
        return new String[] {"John", "Kate", "Bob", "Sarah", "Bill", "Anna", "Jack", "Bonnie", "Will", "Jane"};
    }

    /**
     * Choose between asking a new card or stoping.
     * @return the choice (player constant)
     */
    @Override
    public int choose() {
        if (Game.total(this).max() >= 17) {
            return CHOICE_STOP;
        }
        return CHOICE_CARD;
    }

    @Override
    public int bet() {
        if(tokens <= 15) {
            return tokens;
        }
        return Cards.Random.getInt(1, this.tokens);
    }

}
