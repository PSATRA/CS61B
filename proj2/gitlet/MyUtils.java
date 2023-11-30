package gitlet;

public class MyUtils {
    /**
     * Print the message and exit.
     * @param error The error message to be printed
     */
    static void exit(String error) {
        System.out.println(error);
        System.exit(0);
    }
}
