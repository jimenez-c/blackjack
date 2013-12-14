/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Cards;

/**
 *
 * @author cyril
 */
public class Random {
    public static int getInt(int lower, int higher) {
        return (int)(Math.random() * (higher-lower)) + lower;
    }

    public static int getInt(int higher) {
        return getInt(0, higher);
    }
}
