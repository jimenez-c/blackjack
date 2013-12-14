package Blackjack;


import java.util.ArrayList;

/**
 *
 * @author cyril
 */
public class Logger {
    private static ArrayList<String> messages;

    static {
        flush();
    }

    /**
     * Write a message on standard output and add it to the messages list.
     */
    public static void write(String msg) {
        messages.add(msg);
        System.out.println(msg);
    }

    /**
     * Returns the messages.
     */
    public ArrayList<String> getMessages() {
        return messages;
    }

    /**
     * Flush messages.
     */
    public static void flush() {
        messages = new ArrayList<>();
    }
}
