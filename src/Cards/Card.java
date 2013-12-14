package Cards;

public class Card {

    protected int height;
    protected char family;
    protected boolean visible;

    public Card() {
        this(Random.getInt(1, 13), Random.getInt(1, 4));
    }

    public Card(int height, char family) {
        this.height = height;
        this.family = family;
        this.visible = true;
    }

    public Card(int height, int family) {
        this(height, newFamily(family));
    }

    public Card(Card card) {
        this(card.height, card.family);
    }

    public void hide() {
        visible = false;
    }

    public void show() {
        visible = true;
    }

    public boolean isVisible() {
        return visible;
    }

    public static char newFamily(int i) {
        switch (i) {
            case 0:
                return 'S';
            case 1:
                return 'H';
            case 2:
                return 'C';
            case 3:
                return 'D';
        }
        throw new IllegalArgumentException("L'indice " + i + " ne correspond à aucune famille.");
    }

    public static String familyToString(char family) {
        if (family == 'S') {
            return "Pique";
        }
        else if (family == 'H') {
            return "Coeur";
        }
        else if (family == 'C') {
            return "Trèfle";
        }
        else if (family == 'D') {
            return "Carreau";
        }
        throw new IllegalArgumentException("La famille " + family + "n'existe pas.");
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        if( ! visible) {
            return "Carte cachée.";
        }
        String output;
        switch(height) {
            case 'K' : output = "Roi"; break;
            case 'Q' : output = "Reine"; break;
            case 'J' : output = "Valet"; break;
            default :
                output = height + "";
        }

        output += " de " + familyToString(family);
        return output;
    }
}
