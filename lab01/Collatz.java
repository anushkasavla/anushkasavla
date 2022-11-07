/** Class that prints the Collatz sequence starting from a given number.
<<<<<<< HEAD
 *  @author Anushka Savla
 */
public class Collatz {

    /** Buggy implementation of nextNumber! */
    public static int nextNumber(int n) {
            while (n != 2) {
                if (n % 2 == 0) {
                    n = n / 2;
                } else {
                    n = (3 * n) + 1;
                } System.out.print(n + " ");
            }
            return 1;
    }

    public static void main(String[] args) {
        int n = 5;
        System.out.print(n + " ");

        // Some starter code to test
        while (n != 1) {          
            n = nextNumber(n);          
            System.out.print(n + " ");
        }
        System.out.println();

    }
}

