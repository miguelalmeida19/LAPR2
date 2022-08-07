package app.algorithms.regression;

import java.util.Arrays;

public interface Regression {
    public String getEquationOfRegression();
    public double R2();
    public double calculateR2Adjusted(double r2);
    public double[] confidenceIntervalsForRegressionCoefficients(double alfa, int j);
    public double calculateTobs(int j);
    public boolean rejectullHypotesis(double levelOfSig, int j );
    public double predict(double[] x);
    public double calculateFstat();
    public double calculateDFTot();
    public double calculateDFError();
    public double calculateDF();
    public double calculateSqr();
    public double calculateSqt();
    public double calculateSqe();
    public double calculateMQR();
    public double calculateMQE();
    public boolean significancy(double alphaFD);
    public double[] confidenceIntervalForY(double predict,double levelOfSig,  double[] x0);
    public double getCritFD();
}
