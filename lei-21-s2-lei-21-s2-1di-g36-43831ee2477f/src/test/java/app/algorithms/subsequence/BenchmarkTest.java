package app.algorithms.subsequence;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BenchmarkTest {

    @Test
    public void testGetSubsequence() {
        int[] actualSubsequence = (new Benchmark()).getSubsequence(new int[]{1, 1, 1, 1, 1, 1, 1, 1});
        assertEquals(8, actualSubsequence.length);
        assertEquals(1, actualSubsequence[0]);
        assertEquals(1, actualSubsequence[1]);
        assertEquals(1, actualSubsequence[2]);
        assertEquals(1, actualSubsequence[3]);
        assertEquals(1, actualSubsequence[4]);
        assertEquals(1, actualSubsequence[5]);
        assertEquals(1, actualSubsequence[6]);
        assertEquals(1, actualSubsequence[7]);
    }

    @Test
    public void testGetSubsequence2() {
        int[] actualSubsequence = (new Benchmark()).getSubsequence(new int[]{0, 1, 1, 1, 1, 1, 1, 1});
        assertEquals(8, actualSubsequence.length);
        assertEquals(0, actualSubsequence[0]);
        assertEquals(1, actualSubsequence[1]);
        assertEquals(1, actualSubsequence[2]);
        assertEquals(1, actualSubsequence[3]);
        assertEquals(1, actualSubsequence[4]);
        assertEquals(1, actualSubsequence[5]);
        assertEquals(1, actualSubsequence[6]);
        assertEquals(1, actualSubsequence[7]);
    }

    @Test
    public void testGetSubsequence3() {
        int[] actualSubsequence = (new Benchmark()).getSubsequence(new int[]{-1, 1, 1, 1, 1, 1, 1, 1});
        assertEquals(7, actualSubsequence.length);
        assertEquals(1, actualSubsequence[0]);
        assertEquals(1, actualSubsequence[1]);
        assertEquals(1, actualSubsequence[2]);
        assertEquals(1, actualSubsequence[3]);
        assertEquals(1, actualSubsequence[4]);
        assertEquals(1, actualSubsequence[5]);
        assertEquals(1, actualSubsequence[6]);
    }

    @Test
    public void testMax() {
        int[] actualMaxResult = (new Benchmark()).Max(new int[]{1, 1, 1, 1, 1, 1, 1, 1});
        assertEquals(8, actualMaxResult.length);
        assertEquals(1, actualMaxResult[0]);
        assertEquals(1, actualMaxResult[1]);
        assertEquals(1, actualMaxResult[2]);
        assertEquals(1, actualMaxResult[3]);
        assertEquals(1, actualMaxResult[4]);
        assertEquals(1, actualMaxResult[5]);
        assertEquals(1, actualMaxResult[6]);
        assertEquals(1, actualMaxResult[7]);
    }

    @Test
    public void testMax2() {
        int[] actualMaxResult = (new Benchmark()).Max(new int[]{0, 1, 1, 1, 1, 1, 1, 1});
        assertEquals(8, actualMaxResult.length);
        assertEquals(0, actualMaxResult[0]);
        assertEquals(1, actualMaxResult[1]);
        assertEquals(1, actualMaxResult[2]);
        assertEquals(1, actualMaxResult[3]);
        assertEquals(1, actualMaxResult[4]);
        assertEquals(1, actualMaxResult[5]);
        assertEquals(1, actualMaxResult[6]);
        assertEquals(1, actualMaxResult[7]);
    }

    @Test
    public void testMax3() {
        int[] actualMaxResult = (new Benchmark()).Max(new int[]{-1, 1, 1, 1, 1, 1, 1, 1});
        assertEquals(7, actualMaxResult.length);
        assertEquals(1, actualMaxResult[0]);
        assertEquals(1, actualMaxResult[1]);
        assertEquals(1, actualMaxResult[2]);
        assertEquals(1, actualMaxResult[3]);
        assertEquals(1, actualMaxResult[4]);
        assertEquals(1, actualMaxResult[5]);
        assertEquals(1, actualMaxResult[6]);
    }
}

