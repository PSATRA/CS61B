package IntList;

import static org.junit.Assert.*;
import org.junit.Test;

public class SquarePrimesTest {

    /**
     * Here is a test for isPrime method. Try running it.
     * It passes, but the starter code implementation of isPrime
     * is broken. Write your own JUnit Test to try to uncover the bug!
     */
    @Test
    public void testSquarePrimesSimple() {
        IntList lst = IntList.of(17, 7, 5, 4);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("289 -> 49 -> 25 -> 4", lst.toString());
        assertTrue(changed);
    }

    @Test
    public void testSquarePrimesSimple2() {
        IntList lst = IntList.of(0);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("0", lst.toString());
        assertFalse(changed);
    }

    @Test
    public void testSquarePrimesSimple3() {
        IntList lst = IntList.of(2);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("4", lst.toString());
        assertTrue(changed);
    }
}
