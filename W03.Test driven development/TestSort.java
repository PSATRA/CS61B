import org.junit.Test; // Replace @org.junit.Test with @Test
import static org.junit.Assert.*; // Omit the prefix org.junit.Assert.

public class TestSort {
    @Test
    /**
     * Run the single test method independently and without main method.
     * Remember to delete static.
     * The result is on the left sidebar, green check or red X.
     */
    public void testFindSmallest() {
        String[] input = {"i", "have", "an", "egg"}; // Can also be put above as: private static final String[] input ;
        int expected = 2;

        int actual = Sort.findSmallest(input, 0);
        assertEquals(expected, actual);

        String[] input2 = {"there", "are", "many", "pigs"};
        int expected2 = 2;

        int actual2 = Sort.findSmallest(input2, 2);
        assertEquals(expected2, actual2);
    }

    @Test
    public void testSwap() {
        String[] input = {"i", "have", "an", "egg"};
        int a = 0;
        int b = 2;
        String[] expected = {"an", "have", "i", "egg"};

        Sort.swap(input, a, b);
        assertArrayEquals(expected, input);
    }

    @Test
    public void testSort() {
        String[] input = {"i", "have", "an", "egg"};
        String[] expected = {"an", "egg", "have", "i"};
        Sort.sort(input);
        assertArrayEquals(expected, input);
    }

}
