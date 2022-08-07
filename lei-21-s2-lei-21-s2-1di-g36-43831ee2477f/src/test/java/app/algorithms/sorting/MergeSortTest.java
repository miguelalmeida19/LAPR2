package app.algorithms.sorting;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MergeSortTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testSort() {
        String[] actualSortResult = (new MergeSort()).sort(new String[]{"foo", "foo", "foo"});
        assertEquals(3, actualSortResult.length);
        assertEquals("foo", actualSortResult[0]);
        assertEquals("foo", actualSortResult[1]);
        assertEquals("foo", actualSortResult[2]);
    }

    @Test
    public void testSort2() {
        String[] actualSortResult = (new MergeSort()).sort(new String[]{"42", "foo", "foo"});
        assertEquals(3, actualSortResult.length);
        assertEquals("42", actualSortResult[0]);
        assertEquals("foo", actualSortResult[1]);
        assertEquals("foo", actualSortResult[2]);
    }

    @Test
    public void testMergeSort3() {
        thrown.expect(ArrayIndexOutOfBoundsException.class);
        MergeSort.mergeSort(new String[]{}, 0, 1);
    }

    @Test
    public void testMerge4() {
        thrown.expect(NegativeArraySizeException.class);
        MergeSort.merge(new String[]{"foo"}, Integer.MIN_VALUE, 1, 1);
    }

    @Test
    public void testMerge5() {
        thrown.expect(ArrayIndexOutOfBoundsException.class);
        MergeSort.merge(new String[]{"foo"}, 1, 0, 1);
    }

    @Test
    public void testMerge6() {
        thrown.expect(ArrayIndexOutOfBoundsException.class);
        MergeSort.merge(new String[]{"foo"}, 1, -1, 1);
    }

    @Test
    public void testMerge7() {
        thrown.expect(ArrayIndexOutOfBoundsException.class);
        MergeSort.merge(new String[]{"foo"}, 1, 1, 2);
    }

    @Test
    public void testMerge8() {
        thrown.expect(ArrayIndexOutOfBoundsException.class);
        MergeSort.merge(new String[]{"foo", "foo"}, 1, -1, 1);
    }

    @Test
    public void testMerge9() {
        thrown.expect(ArrayIndexOutOfBoundsException.class);
        MergeSort.merge(new String[]{"foo", "foo"}, 1, 1, 0);
    }
}