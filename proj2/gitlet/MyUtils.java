package gitlet;

public class MyUtils {
    /**
     * Prints the message and exit.
     * @param error The error message to be printed
     */
    public static void exit(String error) {
        System.out.println(error);
        System.exit(0);
    }

    /**
     * Cuts out the first two bytes of SHA-1.
     * @param sha1 The SHA-1 to be cut out
     * @return the first two bytes as the dir name
     */
    public static String preCut(String sha1) {
        return sha1.substring(0, 2);
    }

    /**
     * Cuts out the left bytes of SHA-1.
     * @param sha1 The SHA-1 to be cut out
     * @return the left bytes as the file name
     */
    public static String postCut(String sha1) {
        return sha1.substring(2);
    }
}
