public class ifIsPower {
    /* decide if a number is the power of another, let's say 2. */
    public static boolean power(int num) {           // if a variable or method doesn't belong to an object, set static
        if (num == 2) {
            return true;
        }
        else if (num % 2 == 1) {
            return false;
        }

        return power(num / 2);
    }

    public static void main(String[] args) {
        System.out.println(power(3072));
    }
}
