package app.algorithms.subsequence;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BruteForceAlgorithmTest {

    @Test
    public void testGetSubsequence() {
        int[] actualSubsequence = (new BruteForceAlgorithm()).getSubsequence(new int[]{1, 1, 1, 1, 1, 1, 1, 1});
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
    public void testSubsequence() {
        int[] actualSubsequenceResult = (new BruteForceAlgorithm()).Subsequence(new int[]{1, 1, 1, 1, 1, 1, 1, 1});
        assertEquals(8, actualSubsequenceResult.length);
        assertEquals(1, actualSubsequenceResult[0]);
        assertEquals(1, actualSubsequenceResult[1]);
        assertEquals(1, actualSubsequenceResult[2]);
        assertEquals(1, actualSubsequenceResult[3]);
        assertEquals(1, actualSubsequenceResult[4]);
        assertEquals(1, actualSubsequenceResult[5]);
        assertEquals(1, actualSubsequenceResult[6]);
        assertEquals(1, actualSubsequenceResult[7]);
    }
}

