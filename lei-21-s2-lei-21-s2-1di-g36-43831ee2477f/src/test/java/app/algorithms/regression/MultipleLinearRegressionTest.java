package app.algorithms.regression;

import app.algorithms.matrix.MatrixOperations;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class MultipleLinearRegressionTest {
    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();
    public List<double[]> x = new ArrayList<>();
    private double[][] Xready;
    private double[][] Y;
    private double[] y3;
    private final MatrixOperations matrixOperations = new MatrixOperations();

    @Before
    public void setUp() throws Exception {


    }

//values were obtained on the power point 8 of Matcp
    @Test
    public void r2() {
        double[] y3 = {2256,2340,2426,2293,2330,2368,2250,2409,2364,2379,2440,2364};
        double[] x4 = {80,93,100,82,90,99,81,96,94,93,97,95};
        double[] x5 = {8,9,10,12,11,8,8,10,12,11,13,11};
        x.add(x4);
        x.add(x5);
        MultipleLinearRegression mlr= new MultipleLinearRegression(x,y3);
        double r2= 0.9217400316525068;
        System.out.println(mlr.R2());
        Assert.assertTrue(mlr.R2()==r2);

    }
//values were obtained on the power point 8 of Matcp

    @Test
    public void calculateR() {
        double[] y3 = {2256,2340,2426,2293,2330,2368,2250,2409,2364,2379,2440,2364};
        double[] x4 = {80,93,100,82,90,99,81,96,94,93,97,95};
        double[] x5 = {8,9,10,12,11,8,8,10,12,11,13,11};
        x.add(x4);
        x.add(x5);
        MultipleLinearRegression mlr= new MultipleLinearRegression(x,y3);
        double r = Math.sqrt(mlr.R2());
        Assert.assertTrue(mlr.calculateR()==r);
    }
//values were obtained on the power point 8 of Matcp

    @Test
    public void getEquationOfRegression() {
        double[] y3 = {2256,2340,2426,2293,2330,2368,2250,2409,2364,2379,2440,2364};
        double[] x4 = {80,93,100,82,90,99,81,96,94,93,97,95};
        double[] x5 = {8,9,10,12,11,8,8,10,12,11,13,11};
        x.add(x4);
        x.add(x5);
        MultipleLinearRegression mlr= new MultipleLinearRegression(x,y3);
        double[][] coeficientsOfEquation= {{1562.7301},{7.5084},{9.8131}};
        String equation= new String("Y= 1562.7301332839545 +7.508385485713173X1 +9.813124929351332X2 ");
        Assert.assertTrue(mlr.getEquationOfRegression().equals(equation));

    }
//values were obtained on the power point 8 of Matcp

    @Test
    public void calculateR2Adjusted() {
        double[] y3 = {2256,2340,2426,2293,2330,2368,2250,2409,2364,2379,2440,2364};
        double[] x4 = {80,93,100,82,90,99,81,96,94,93,97,95};
        double[] x5 = {8,9,10,12,11,8,8,10,12,11,13,11};
        x.add(x4);
        x.add(x5);
        MultipleLinearRegression mlr= new MultipleLinearRegression(x,y3);
        double R2Adj=  0.9043489275752862;

        Assert.assertTrue(mlr.calculateR2Adjusted(mlr.R2())==(R2Adj));
    }
//values were obtained on the power point 8 of Matcp

    @Test
    public void calculateSqr() {
        double[] y3 = {2256,2340,2426,2293,2330,2368,2250,2409,2364,2379,2440,2364};
        double[] x4 = {80,93,100,82,90,99,81,96,94,93,97,95};
        double[] x5 = {8,9,10,12,11,8,8,10,12,11,13,11};
        x.add(x4);
        x.add(x5);
        MultipleLinearRegression mlr= new MultipleLinearRegression(x,y3);
        double SQR= 38223.560560919344;
        Assert.assertTrue(mlr.calculateSqr()==(SQR));

    }
    //values were obtained on the power point 8 of Matcp

    @Test
    public void calculateSqrWrong() {
        double[] y3 = {2256,2340,2426,2293,2330,2368,2250,2409,2364,2379,2440,2364};
        double[] x4 = {80,93,100,82,90,99,81,96,94,93,97,95};
        double[] x5 = {8,9,10,12,11,8,8,10,12,11,13,11};
        x.add(x4);
        x.add(x5);
        MultipleLinearRegression mlr= new MultipleLinearRegression(x,y3);
        double SQR= 15;
        Assert.assertFalse(mlr.calculateSqr()==(SQR));

    }
//values were obtained on the power point 8 of Matcp

    @Test
    public void calculateSqt() {
        double[] y3 = {2256,2340,2426,2293,2330,2368,2250,2409,2364,2379,2440,2364};
        double[] x4 = {80,93,100,82,90,99,81,96,94,93,97,95};
        double[] x5 = {8,9,10,12,11,8,8,10,12,11,13,11};
        x.add(x4);
        x.add(x5);
        MultipleLinearRegression mlr= new MultipleLinearRegression(x,y3);
        double SQT= 41468.91666665673;
        Assert.assertTrue(mlr.calculateSqt()==(SQT));
    }
    //values were obtained on the power point 8 of Matcp

    @Test
    public void calculateSqtWrong() {
        double[] y3 = {2256,2340,2426,2293,2330,2368,2250,2409,2364,2379,2440,2364};
        double[] x4 = {80,93,100,82,90,99,81,96,94,93,97,95};
        double[] x5 = {8,9,10,12,11,8,8,10,12,11,13,11};
        x.add(x4);
        x.add(x5);
        MultipleLinearRegression mlr= new MultipleLinearRegression(x,y3);
        double SQT= 10;
        Assert.assertFalse(mlr.calculateSqt()==(SQT));
    }
//values were obtained on the power point 8 of Matcp

    @Test
    public void calculateSqe() {
        double[] y3 = {2256,2340,2426,2293,2330,2368,2250,2409,2364,2379,2440,2364};
        double[] x4 = {80,93,100,82,90,99,81,96,94,93,97,95};
        double[] x5 = {8,9,10,12,11,8,8,10,12,11,13,11};
        x.add(x4);
        x.add(x5);
        MultipleLinearRegression mlr= new MultipleLinearRegression(x,y3);
        double SQE= 3245.356105737388;
        System.out.println(mlr.calculateSqe());
        Assert.assertTrue(mlr.calculateSqe()==(SQE));
    }
//values were obtained on the power point 8 of Matcp

    @Test
    public void calculateDFTot() {
        double[] y3 = {2256,2340,2426,2293,2330,2368,2250,2409,2364,2379,2440,2364};
        double[] x4 = {80,93,100,82,90,99,81,96,94,93,97,95};
        double[] x5 = {8,9,10,12,11,8,8,10,12,11,13,11};
        x.add(x4);
        x.add(x5);
        MultipleLinearRegression mlr= new MultipleLinearRegression(x,y3);
        double df= 11;
        Assert.assertTrue(mlr.calculateDFTot()==(df));
    }
//values were obtained on the power point 8 of Matcp

    @Test
    public void calculateDF() {
        double[] y3 = {2256,2340,2426,2293,2330,2368,2250,2409,2364,2379,2440,2364};
        double[] x4 = {80,93,100,82,90,99,81,96,94,93,97,95};
        double[] x5 = {8,9,10,12,11,8,8,10,12,11,13,11};
        x.add(x4);
        x.add(x5);
        MultipleLinearRegression mlr= new MultipleLinearRegression(x,y3);
        double df= 2;
        Assert.assertTrue(mlr.calculateDF()==(df));
    }
//values were obtained on the power point 8 of Matcp

    @Test
    public void calculateDFError() {
        double[] y3 = {2256,2340,2426,2293,2330,2368,2250,2409,2364,2379,2440,2364};
        double[] x4 = {80,93,100,82,90,99,81,96,94,93,97,95};
        double[] x5 = {8,9,10,12,11,8,8,10,12,11,13,11};
        x.add(x4);
        x.add(x5);
        MultipleLinearRegression mlr= new MultipleLinearRegression(x,y3);
        double df= 9;
        Assert.assertTrue(mlr.calculateDFError()==(df));
    }
//values were obtained on the power point 8 of Matcp

    @Test
    public void calculateMean() {
        double[] y3 = {2256,2340,2426,2293,2330,2368,2250,2409,2364,2379,2440,2364};
        double[] x4 = {80,93,100,82,90,99,81,96,94,93,97,95};
        double[] x5 = {8,9,10,12,11,8,8,10,12,11,13,11};
        x.add(x4);
        x.add(x5);
        MultipleLinearRegression mlr= new MultipleLinearRegression(x,y3);
        double mean= 34.30555555555556;
        Assert.assertTrue(mlr.calculateMean()==(mean));
    }
//values were obtained on the power point 8 of Matcp

    @Test
    public void calculateSSTot() {
        double[] y3 = {2256,2340,2426,2293,2330,2368,2250,2409,2364,2379,2440,2364};
        double[] x4 = {80,93,100,82,90,99,81,96,94,93,97,95};
        double[] x5 = {8,9,10,12,11,8,8,10,12,11,13,11};
        x.add(x4);
        x.add(x5);
        MultipleLinearRegression mlr= new MultipleLinearRegression(x,y3);
        double SSt= 60307.638888888905;
        Assert.assertTrue(mlr.CalculateSSTot()==SSt);


    }
//values were obtained on the power point 8 of Matcp

    @Test
    public void calculateMQR() {
        double[] y3 = {2256,2340,2426,2293,2330,2368,2250,2409,2364,2379,2440,2364};
        double[] x4 = {80,93,100,82,90,99,81,96,94,93,97,95};
        double[] x5 = {8,9,10,12,11,8,8,10,12,11,13,11};
        x.add(x4);
        x.add(x5);
        MultipleLinearRegression mlr= new MultipleLinearRegression(x,y3);
        double MQR= 19111.780280459672;
        Assert.assertTrue(mlr.calculateMQR()==MQR);
    }
//values were obtained on the power point 8 of Matcp

    @Test
    public void calculateMQE() {
        double[] y3 = {2256,2340,2426,2293,2330,2368,2250,2409,2364,2379,2440,2364};
        double[] x4 = {80,93,100,82,90,99,81,96,94,93,97,95};
        double[] x5 = {8,9,10,12,11,8,8,10,12,11,13,11};
        x.add(x4);
        x.add(x5);
        MultipleLinearRegression mlr= new MultipleLinearRegression(x,y3);
        double MQE= 360.5951228597098;
        Assert.assertTrue(mlr.calculateMQE()==MQE);
    }
//values were obtained on the power point 8 of Matcp

    @Test
    public void calculateFstat() {
        double[] y3 = {2256,2340,2426,2293,2330,2368,2250,2409,2364,2379,2440,2364};
        double[] x4 = {80,93,100,82,90,99,81,96,94,93,97,95};
        double[] x5 = {8,9,10,12,11,8,8,10,12,11,13,11};
        x.add(x4);
        x.add(x5);
        MultipleLinearRegression mlr= new MultipleLinearRegression(x,y3);
        double Fs= 53.00066215231409;
        System.out.println(mlr.calculateFstat());
        Assert.assertTrue(mlr.calculateFstat()==Fs);
    }
//values were obtained on the power point 8 of Matcp

    @Test
    public void predict() {
        double[] y3 = {2256,2340,2426,2293,2330,2368,2250,2409,2364,2379,2440,2364};
        double[] x4 = {80,93,100,82,90,99,81,96,94,93,97,95};
        double[] x2 = {80,93};
        double[] x5 = {8,9,10,12,11,8,8,10,12,11,13,11};
        x.add(x4);
        x.add(x5);
        MultipleLinearRegression mlr= new MultipleLinearRegression(x,y3);
        String equation= new String("Y= 1562.7301332839545 +7.508385485713173X1 +9.813124929351332X2 ");
        double pre= 1562.7301332839545+ (7.508385485713173*80)+ (9.813124929351332*93);
        Assert.assertTrue(mlr.predict(x2)==pre);
    }
    //values were obtained on the power point 8 of Matcp

    @Test
    public void predictWrong() {
        double[] y3 = {2256,2340,2426,2293,2330,2368,2250,2409,2364,2379,2440,2364};
        double[] x4 = {80,93,100,82,90,99,81,96,94,93,97,95};
        double[] x2 = {80};
        double[] x5 = {8,9,10,12,11,8,8,10,12,11,13,11};
        x.add(x4);
        x.add(x5);
        MultipleLinearRegression mlr= new MultipleLinearRegression(x,y3);
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("the size of the list x is not equal to the size of the list of coefficients in the equation. ");
        mlr.predict(x2);
    }
//values were obtained on the power point 8 of Matcp

    @Test
    public void confidenceIntervalsForRegressionCoefficients() {
        double[] y3 = {2256,2340,2426,2293,2330,2368,2250,2409,2364,2379,2440,2364};
        double[] x4 = {80,93,100,82,90,99,81,96,94,93,97,95};
        double[] x5 = {8,9,10,12,11,8,8,10,12,11,13,11};
        x.add(x4);
        x.add(x5);
        MultipleLinearRegression mlr= new MultipleLinearRegression(x,y3);
        double[] interval = new double[2];


        interval[0]= 1.9990123038290522;
        interval[1]= 17.627237554873613;
        Assert.assertTrue(mlr.confidenceIntervalsForRegressionCoefficients(0.95,2)[0]==interval[0] && mlr.confidenceIntervalsForRegressionCoefficients(0.95,2)[1]==interval[1]) ;
    }
//values were obtained on the power point 8 of Matcp

    @Test
    public void calculateTobs() {
        double[] y3 = {2256,2340,2426,2293,2330,2368,2250,2409,2364,2379,2440,2364};
        double[] x4 = {80,93,100,82,90,99,81,96,94,93,97,95};
        double[] x5 = {8,9,10,12,11,8,8,10,12,11,13,11};
        x.add(x4);
        x.add(x5);
        MultipleLinearRegression mlr= new MultipleLinearRegression(x,y3);
        double tOBS= 2.840863948628121;
        Assert.assertTrue(mlr.calculateTobs(2)==tOBS);

    }
//values were obtained on the power point 8 of Matcp

    @Test
    public void rejectullHypotesis() {
        double[] y3 = {2256,2340,2426,2293,2330,2368,2250,2409,2364,2379,2440,2364};
        double[] x4 = {80,93,100,82,90,99,81,96,94,93,97,95};
        double[] x5 = {8,9,10,12,11,8,8,10,12,11,13,11};
        x.add(x4);
        x.add(x5);
        MultipleLinearRegression mlr= new MultipleLinearRegression(x,y3);
        double valueTStudent= 2.262;
        boolean reject=false;
        if(mlr.calculateTobs(2)>valueTStudent){
            reject= true;
        }
        Assert.assertTrue(mlr.rejectullHypotesis(0.05,2)==reject);
    }
//values were obtained on the power point 8 of Matcp

    @Test
    public void confidenceIntervalForY() {
        double[] y3 = {2256,2340,2426,2293,2330,2368,2250,2409,2364,2379,2440,2364};
        double[] x2 = {80,93};
        double[] x4 = {80,93,100,82,90,99,81,96,94,93,97,95};
        double[] x5 = {8,9,10,12,11,8,8,10,12,11,13,11};
        x.add(x4);
        x.add(x5);
        MultipleLinearRegression mlr= new MultipleLinearRegression(x,y3);
        double[] interval = new double[2];
        interval[0]= 2421.9094470357777;
        interval[1]= 3730.133734105587;
        Assert.assertTrue(mlr.confidenceIntervalForY(mlr.predict(x2), 0.95,x2)[0]==interval[0] && mlr.confidenceIntervalForY(mlr.predict(x2), 0.95,x2)[1]==interval[1]) ;
    }
    //values were obtained on the power point 8 of Matcp

    @Test
    public void testSignificancy() {
        double[] y3 = {2256,2340,2426,2293,2330,2368,2250,2409,2364,2379,2440,2364};
        double[] x2 = {80,93};
        double[] x4 = {80,93,100,82,90,99,81,96,94,93,97,95};
        double[] x5 = {8,9,10,12,11,8,8,10,12,11,13,11};
        x.add(x4);
        x.add(x5);
        MultipleLinearRegression mlr= new MultipleLinearRegression(x,y3);
        double valueTSnedecor= 4.2565;
        boolean reject=false;
        if(mlr.calculateFstat()>valueTSnedecor){
            reject= true;
        }
        Assert.assertTrue(mlr.significancy(0.05)==reject);

    }
}