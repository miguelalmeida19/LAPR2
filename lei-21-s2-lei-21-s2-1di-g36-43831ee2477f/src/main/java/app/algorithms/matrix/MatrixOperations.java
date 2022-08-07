package app.algorithms.matrix;

import java.util.Arrays;
import java.util.List;

public class MatrixOperations {
    private int N = 4;

    public MatrixOperations() {

    }

    public double[][] joinXmatrix(List<double[]> x) {
        double[][] joinMatrix = new double[x.get(0).length][x.size() + 1];

        for (int n = 0; n < joinMatrix.length; n++) {
            joinMatrix[n][0] = 1;
        }
        int col = 1;
        for (double[] x1 : x) {
            for (int n = 0; n < x1.length; n++) {
                joinMatrix[n][col] = x1[n];
            }
            col++;
        }
        return joinMatrix;
    }

    public double[][] multiplyMatrices(double[][] firstMatrix, double[][] secondMatrix) {
        double[][] result = new double[firstMatrix.length][secondMatrix[0].length];


        if (firstMatrix[0].length != secondMatrix.length) {
            throw new IllegalArgumentException("the two matrices given cannot be multiplied.");
        }

        for (int n = 0; n < result.length; n++) {
            for (int m = 0; m < result[0].length; m++) {
                double sum = 0;

                for (int j = 0; j < secondMatrix.length; j++) {
                    sum = sum + (firstMatrix[n][j] * secondMatrix[j][m]);


                }
                result[n][m] = sum;
            }

        }

        return result;
    }


    public void transpose(double A[][], double B[][]) {
        int i, j;
        for (i = 0; i < A[0].length; i++)
            for (j = 0; j < A.length; j++)
                B[i][j] = A[j][i];
    }

    public void inverse(double[][] squareMatrix,
                               double[][] inverseMatrix) {
        int size = squareMatrix.length;
        if (squareMatrix[0].length != size || inverseMatrix.length != size
                || inverseMatrix[0].length != size) {
            throw new IllegalArgumentException(
                    "--- invalid length. column should be 2 times larger than row.");
        }
        for (int i = 0; i < size; ++i) {
            Arrays.fill(inverseMatrix[i], 0.0f);
            inverseMatrix[i][i] = 1.0f;
        }
        for (int i = 0; i < size; ++i) {
            findPivotAndSwapRow(i, squareMatrix, inverseMatrix, size);
            sweep(i, squareMatrix, inverseMatrix, size);
        }
    }

    /**
     * A utility function to inverse matrix.
     * Find a pivot and swap the row of squareMatrix0 and squareMatrix1
     */
    private static void findPivotAndSwapRow(final int row,
                                            final double[][] squareMatrix0, double[][] squareMatrix1,
                                            final int size) {
        int ip = row;
        double pivot = Math.abs(squareMatrix0[row][row]);
        for (int i = row + 1; i < size; ++i) {
            if (pivot < Math.abs(squareMatrix0[i][row])) {
                ip = i;
                pivot = Math.abs(squareMatrix0[i][row]);
            }
        }
        if (ip != row) {
            for (int j = 0; j < size; ++j) {
                double  temp0 = squareMatrix0[ip][j];
                squareMatrix0[ip][j] = squareMatrix0[row][j];
                squareMatrix0[row][j] = temp0;
                double temp1 = squareMatrix1[ip][j];
                squareMatrix1[ip][j] = squareMatrix1[row][j];
                squareMatrix1[row][j] = temp1;
            }
        }
    }

    /**
     * A utility function to inverse matrix. This function calculates answer for each row by
     * sweeping method of Gauss Jordan elimination
     */
    private static void sweep(int row, double[][] squareMatrix0, double[][] squareMatrix1, int size) {
        double pivot = squareMatrix0[row][row];
        if (pivot == 0) {
            throw new IllegalArgumentException(
                    "Inverse failed. Invalid pivot");
        }
        for (int j = 0; j < size; ++j) {
            squareMatrix0[row][j] /= pivot;
            squareMatrix1[row][j] /= pivot;
        }
        for (int i = 0; i < size; i++) {
             double sweepTargetValue = squareMatrix0[i][row];
            if (i != row) {
                for (int j = row; j < size; ++j) {
                    squareMatrix0[i][j] -= sweepTargetValue
                            * squareMatrix0[row][j];
                }
                for (int j = 0; j < size; ++j) {
                    squareMatrix1[i][j] -= sweepTargetValue
                            * squareMatrix1[row][j];
                }
            }
        }
    }


}
