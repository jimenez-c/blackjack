/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Blackjack;

/**
 * Represents a player's total points, with min and max values.
 * @author 21108766
 */
public class Total {
    private int min;
    private int max;

    public Total(int min, int max) {
        min(min);
        max(max);
    }

    public int min() {
        return min;
    }
    public void min(int min) {
        this.min = min;
    }

    public int max() {
        return max;
    }
    public void max(int max) {
        this.max = max;
    }

    public String toString() {
        return "(" + min + " | " + max + ")";
    }
}
