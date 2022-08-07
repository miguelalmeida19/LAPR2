package app.algorithms.regression;
/******************************************************************************
 *  Compute least squares solution to y = beta * x + alpha.
 *  Simple linear regression.
 ******************************************************************************/

import org.apache.commons.math3.distribution.FDistribution;
import org.apache.commons.math3.distribution.TDistribution;

/**
 *  The code LinearRegression class performs a simple linear regression
 *  on an set of n data points (y_i, x_i).
 *  That is, it fits a straight line y = alpha + beta * x,
 *  (where y is the response variable, x is the predictor variable,
 *  alpha is the y-intercept, and beta is the slope)
 *  that minimizes the sum of squared residuals of the linear regression model.
 *  It also computes associated statistics, including the coefficient of
 *  determination R^2 and the standard deviation of the
 *  estimates for the slope and y-intercept.
 *
 */
public class LinearRegression implements Regression {
    private final double intercept, slope;
    private final double r2;
    private final double svar0, svar1;
    private final double MeanX;
    private double rss; //SSE
    private double ssr;
    private double sst;
    private double sxx;
    private int N;
    private double critFD;
    /**
     * Performs a linear regression on the data points (y[i], x[i]).
     *
     * @param x the values of the predictor variable
     * @param y the corresponding values of the response variable
     * @throws IllegalArgumentException if the lengths of the two arrays are not equal
     */
    public LinearRegression(double[] x, double[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("array lengths are not equal");
        }
        N = x.length;

        // first pass
        double sumx = 0.0, sumy = 0.0, sumx2 = 0.0;
        for (int i = 0; i < N; i++) {
            sumx += x[i];
            sumx2 += x[i] * x[i];
            sumy += y[i];
        }
        double xbar = sumx / N;
        double ybar = sumy / N;

        // second pass: compute summary statistics
        double xxbar = 0.0, yybar = 0.0, xybar = 0.0;
        for (int i = 0; i < N; i++) {
            xxbar += (x[i] - xbar) * (x[i] - xbar);
            yybar += (y[i] - ybar) * (y[i] - ybar);
            xybar += (x[i] - xbar) * (y[i] - ybar);
        }
        slope = xybar / xxbar;
        intercept = ybar - slope * xbar;

        sxx = xxbar;

        // more statistical analysis
        rss = 0.0;      // residual sum of squares. SSE
        ssr = 0.0;      // regression sum of squares
        for (int i = 0; i < N; i++) {
            double fit = slope * x[i] + intercept;
            rss += (fit - y[i]) * (fit - y[i]);
            ssr += (fit - ybar) * (fit - ybar);
        }

        for (int h = 0; h < y.length; h++) {
            sst = sst + (y[h] - ybar) * (y[h] - ybar);
        }

        int degreesOfFreedom = N - 2;
        r2 = ssr / yybar;
        double svar = rss / degreesOfFreedom;
        svar1 = svar / xxbar;
        svar0 = svar / N + xbar * xbar * svar1;

        double sum = 0;
        for (int i = 0; i < x.length; i++) {
            sum = sum + x[i];

        }
        MeanX = sum / x.length;

    }


    /**
     * Returns the y-intercept alpha of the best of the best-fit line y = alpha + beta * x.
     *
     * @return the y-intercept alpha of the best-fit line y = alpha + beta * x
     */
    public double intercept() {
        return intercept;
    }

    /**
     * Returns the slope beta of the best of the best-fit line y = alpha + beta * x.
     *
     * @return the slope beta of the best-fit line y = alpha + beta * x
     */
    public double slope() {
        return slope;
    }

    /**
     * Returns the coefficient of determination R^2.
     *
     * @return the coefficient of determination R^2,
     * which is a real number between 0 and 1
     */
    public double R2() {
        return r2;
    }

    public double calculateR() {
        return Math.sqrt(R2());
    }

    public double calculateR2Adjusted(double r2) {
        return (1 - (((1 - R2()) * (N - 1)) / (N - 2)));
    }

    /**
     * Returns the standard error of the estimate for the intercept.
     *
     * @return the standard error of the estimate for the intercept
     */
    public double interceptStdErr() {
        return Math.sqrt(svar0);
    }

    /**
     * Returns the standard error of the estimate for the slope.
     *
     * @return the standard error of the estimate for the slope
     */
    public double slopeStdErr() {
        return Math.sqrt(svar1);
    }

    /**
     * Returns the expected response y given the value of the predictor
     * variable x.
     *
     * @param x the value of the predictor variable
     * @return the expected response y given the value of the predictor
     * variable x
     */
    public double predict(double[] x) {
        if (x.length != 1) {
            throw new IllegalArgumentException("The prediction x must be an array with size 1");
        }
        return slope * x[0] + intercept;
    }

    public double[] confidenceIntervalsForRegressionCoefficients(double alfa, int j) {
        alfa = 1 - ((1 - alfa) / 2);
        TDistribution td = new TDistribution(N - 2);
        double critTD = 0.0;
        if (alfa > 0.5) {
            critTD = td.inverseCumulativeProbability(alfa);
        } else {
            critTD = td.inverseCumulativeProbability(1 - alfa);
        }
        double error = critTD * calculateStdDev() * Math.sqrt(1 / sxx);
        double[] IC = new double[2];
        IC[0] = slope - error;
        IC[1] = slope + error;
        return IC;
    }

    public boolean rejectullHypotesis(double levelOfSig,int j) {
        levelOfSig = 1 - ((levelOfSig) / 2);

        TDistribution td = new TDistribution(N - 2);
        double critTD = 0.0;
        if (levelOfSig > 0.5) {
            critTD = td.inverseCumulativeProbability(levelOfSig);
        } else {
            critTD = td.inverseCumulativeProbability(1 - levelOfSig);
        }
        double t0 = calculateTobs(0);
        if (Math.abs(t0) > critTD) {
            return true;
        } else {
            return false;
        }
    }

    public double calculateTobs(int j) {
        double denominator= ((1/N)+((MeanX*MeanX)/sxx));
        return intercept / (calculateStdDev() * (Math.sqrt(denominator)));
    }

    public double calculateStdDev() {
        return Math.sqrt(calculateMQE());
    }

    public double getSsr() {
        return ssr;
    }

    public double getSse() {
        return rss;
    }

    public double getSst() {
        return sst;
    }

    public double calculateDF() {
        return 1;
    }


    @Override
    public double calculateSqr() {
        return ssr;
    }

    @Override
    public double calculateSqt() {
        return ssr + rss;
    }

    @Override
    public double calculateSqe() {
        return rss;
    }

    public double calculateDFError() {
        return N - 2;
    }

    public double calculateDFTot() {
        return N - 1;
    }

    public double calculateMQR() {
        return ssr / 1;
    }

    public double calculateMQE() {
        return rss / (N - 2);
    }

    public String getEquationOfRegression() {
        String equation = "Y= " + intercept + " ";

        if (String.valueOf(slope).charAt(0) != '-') {
            equation = equation + "+";
        }
        equation = equation + String.valueOf(slope + "X");

        return equation;
    }

    public double calculateFstat() {
        return calculateMQR() / calculateMQE();
    }

    public boolean significancy(double alphaFD){
        FDistribution fd= new FDistribution(calculateDF(),calculateDFError());

        critFD= fd.inverseCumulativeProbability(1-alphaFD);
        System.out.println("Fisher–Snedecor critical value:" + critFD);
        if(calculateFstat()>critFD){
            return true; //É significativo
        }else{
            return false; // Não é significativo
        }
    }

    public double[] confidenceIntervalForY( double predict, double levelOfSig, double x[]) {
        double x0 = x[0];
        levelOfSig = 1 - ((levelOfSig) / 2);

        TDistribution td = new TDistribution(N - 2);
        double critTD = 0.0;
        if (levelOfSig > 0.5) {
            critTD = td.inverseCumulativeProbability(levelOfSig);
        } else {
            critTD = td.inverseCumulativeProbability(1 - levelOfSig);
        }
        double S = calculateStdDev();
        double calculation = (x0 -MeanX)*(x0 -MeanX);
        double Operation = Math.sqrt(1+(1/N)+(calculation/S));
        double multiplication= critTD*Operation;
        double result []= new double[2];
        result[0]= predict-multiplication;
        result[1]= predict+multiplication;
        return result;
    }


        /**
         * Returns a string representation of the simple linear regression model.
         *
         * @return a string representation of the simple linear regression model,
         *         including the best-fit line and the coefficient of determination
         *         R^2
         */
        public String toString(){
            StringBuilder s = new StringBuilder();
            s.append(String.format("%.2f n + %.2f", slope(), intercept()));
            s.append("  (R^2 = " + String.format("%.3f", R2()) + ")");
            return s.toString();
        }


    public double getCritFD() {
        return critFD;
    }
}

