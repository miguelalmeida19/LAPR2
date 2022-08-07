package app.algorithms.sorting;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class QuickSortTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testPartition() {
        thrown.expect(ArrayIndexOutOfBoundsException.class);
        QuickSort.partition(new String[]{"String Array"}, 1, 1);
    }

    @Test
    public void testPartition2() {
        assertEquals(1, QuickSort.partition(new String[]{"String Array", "String Array"}, 1, 1));
    }

    @Test
    public void testPartition3() {
        thrown.expect(ArrayIndexOutOfBoundsException.class);
        QuickSort.partition(new String[]{"String Array"}, 0, 1);
    }
}

