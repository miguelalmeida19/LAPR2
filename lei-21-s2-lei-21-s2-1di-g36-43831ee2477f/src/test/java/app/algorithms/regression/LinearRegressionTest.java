package app.algorithms.regression;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;

import static org.junit.Assert.*;

public class LinearRegressionTest {
    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void intercept() {
        double[] y3 = {10.98,11.13,12.51,8.40,9.27,8.73,6.36,8.50,7.82,9.14,8.24,12.19,11.88,9.57,10.94,9.58,10.09,8.11,6.83,8.88,7.68,8.47,8.86,10.36,11.08};
        double[] x4 = {35.3,29.7,30.8,58.8,61.4,71.3,74.4,76.7,70.7,57.5,46.4,28.9,28.1,39.1,46.8,48.5,59.3,70.0,70.0,74.5,72.1,58.1,44.6,33.4,28.6};
       LinearRegression lr= new LinearRegression(x4,y3);
       double intercept= 13.622989268172681;
        Assert.assertTrue(lr.intercept()==intercept);
    }

    @Test
    public void slope() {
        double[] y3 = {10.98,11.13,12.51,8.40,9.27,8.73,6.36,8.50,7.82,9.14,8.24,12.19,11.88,9.57,10.94,9.58,10.09,8.11,6.83,8.88,7.68,8.47,8.86,10.36,11.08};
        double[] x4 = {35.3,29.7,30.8,58.8,61.4,71.3,74.4,76.7,70.7,57.5,46.4,28.9,28.1,39.1,46.8,48.5,59.3,70.0,70.0,74.5,72.1,58.1,44.6,33.4,28.6};
        LinearRegression lr= new LinearRegression(x4,y3);
        double slope= -0.07982869331126773;
        Assert.assertTrue(lr.slope()==slope);
    }

    @Test
    public void r2() {
        double[] y3 = {10.98,11.13,12.51,8.40,9.27,8.73,6.36,8.50,7.82,9.14,8.24,12.19,11.88,9.57,10.94,9.58,10.09,8.11,6.83,8.88,7.68,8.47,8.86,10.36,11.08};
        double[] x4 = {35.3,29.7,30.8,58.8,61.4,71.3,74.4,76.7,70.7,57.5,46.4,28.9,28.1,39.1,46.8,48.5,59.3,70.0,70.0,74.5,72.1,58.1,44.6,33.4,28.6};
        LinearRegression lr= new LinearRegression(x4,y3);
        double r2=0.7144375210132557;
        Assert.assertTrue(lr.R2()==r2);
    }

    @Test
    public void calculateR() {
        double[] y3 = {10.98,11.13,12.51,8.40,9.27,8.73,6.36,8.50,7.82,9.14,8.24,12.19,11.88,9.57,10.94,9.58,10.09,8.11,6.83,8.88,7.68,8.47,8.86,10.36,11.08};
        double[] x4 = {35.3,29.7,30.8,58.8,61.4,71.3,74.4,76.7,70.7,57.5,46.4,28.9,28.1,39.1,46.8,48.5,59.3,70.0,70.0,74.5,72.1,58.1,44.6,33.4,28.6};
        LinearRegression lr= new LinearRegression(x4,y3);
        double r= Math.sqrt(lr.R2());
        Assert.assertTrue(lr.calculateR()==r);
    }

    @Test
    public void calculateR2Adjusted() {
        double[] y3 = {10.98,11.13,12.51,8.40,9.27,8.73,6.36,8.50,7.82,9.14,8.24,12.19,11.88,9.57,10.94,9.58,10.09,8.11,6.83,8.88,7.68,8.47,8.86,10.36,11.08};
        double[] x4 = {35.3,29.7,30.8,58.8,61.4,71.3,74.4,76.7,70.7,57.5,46.4,28.9,28.1,39.1,46.8,48.5,59.3,70.0,70.0,74.5,72.1,58.1,44.6,33.4,28.6};
        LinearRegression lr= new LinearRegression(x4,y3);
    }

    @Test
    public void interceptStdErr() {
        double[] y3 = {10.98,11.13,12.51,8.40,9.27,8.73,6.36,8.50,7.82,9.14,8.24,12.19,11.88,9.57,10.94,9.58,10.09,8.11,6.83,8.88,7.68,8.47,8.86,10.36,11.08};
        double[] x4 = {35.3,29.7,30.8,58.8,61.4,71.3,74.4,76.7,70.7,57.5,46.4,28.9,28.1,39.1,46.8,48.5,59.3,70.0,70.0,74.5,72.1,58.1,44.6,33.4,28.6};
        LinearRegression lr= new LinearRegression(x4,y3);
        double StdErr= 0.5814634941477703;
        Assert.assertTrue(lr.interceptStdErr()==StdErr);

    }

    @Test
    public void slopeStdErr() {
        double[] y3 = {10.98,11.13,12.51,8.40,9.27,8.73,6.36,8.50,7.82,9.14,8.24,12.19,11.88,9.57,10.94,9.58,10.09,8.11,6.83,8.88,7.68,8.47,8.86,10.36,11.08};
        double[] x4 = {35.3,29.7,30.8,58.8,61.4,71.3,74.4,76.7,70.7,57.5,46.4,28.9,28.1,39.1,46.8,48.5,59.3,70.0,70.0,74.5,72.1,58.1,44.6,33.4,28.6};
        LinearRegression lr= new LinearRegression(x4,y3);
        double StdErr=0.010523580966436092;
        Assert.assertTrue(lr.slopeStdErr()==StdErr);
    }

    @Test
    public void predict() {
        double[] y3 = {10.98,11.13,12.51,8.40,9.27,8.73,6.36,8.50,7.82,9.14,8.24,12.19,11.88,9.57,10.94,9.58,10.09,8.11,6.83,8.88,7.68,8.47,8.86,10.36,11.08};
        double[] x4 = {35.3,29.7,30.8,58.8,61.4,71.3,74.4,76.7,70.7,57.5,46.4,28.9,28.1,39.1,46.8,48.5,59.3,70.0,70.0,74.5,72.1,58.1,44.6,33.4,28.6};
        double[] x1 = {35.3};

        LinearRegression lr= new LinearRegression(x4,y3);
        double predict= lr.slope()*35.3+ lr.intercept();
        Assert.assertTrue(lr.predict(x1)==predict);
    }

    @Test
    public void confidenceIntervalsForRegressionCoefficients() {
        double[] y3 = {10.98,11.13,12.51,8.40,9.27,8.73,6.36,8.50,7.82,9.14,8.24,12.19,11.88,9.57,10.94,9.58,10.09,8.11,6.83,8.88,7.68,8.47,8.86,10.36,11.08};
        double[] x4 = {35.3,29.7,30.8,58.8,61.4,71.3,74.4,76.7,70.7,57.5,46.4,28.9,28.1,39.1,46.8,48.5,59.3,70.0,70.0,74.5,72.1,58.1,44.6,33.4,28.6};
        double[] x1 = {35.3};

        LinearRegression lr= new LinearRegression(x4,y3);
        double[] interval = new double[2];
        interval[0]= -0.1015983791663468;
        interval[1]= -0.05805900745618865;
        Assert.assertTrue(lr.confidenceIntervalsForRegressionCoefficients(0.95,0)[0]==interval[0] && lr.confidenceIntervalsForRegressionCoefficients(0.95,0)[1]==interval[1]);
    }

    @Test
    public void rejectullHypotesis() {
        double[] y3 = {10.98, 11.13, 12.51, 8.40, 9.27, 8.73, 6.36, 8.50, 7.82, 9.14, 8.24, 12.19, 11.88, 9.57, 10.94, 9.58, 10.09, 8.11, 6.83, 8.88, 7.68, 8.47, 8.86, 10.36, 11.08};
        double[] x4 = {35.3, 29.7, 30.8, 58.8, 61.4, 71.3, 74.4, 76.7, 70.7, 57.5, 46.4, 28.9, 28.1, 39.1, 46.8, 48.5, 59.3, 70.0, 70.0, 74.5, 72.1, 58.1, 44.6, 33.4, 28.6};
        double[] x1 = {35.3};

        LinearRegression lr = new LinearRegression(x4, y3);
        double valueTStudent = 2.07;
        boolean reject = false;
        if (lr.calculateTobs(0) > valueTStudent) {
            reject = true;
        }
        Assert.assertTrue(lr.rejectullHypotesis(0.05, 0) == reject);
    }
    @Test
    public void calculateTobs() {
        double[] y3 = {10.98,11.13,12.51,8.40,9.27,8.73,6.36,8.50,7.82,9.14,8.24,12.19,11.88,9.57,10.94,9.58,10.09,8.11,6.83,8.88,7.68,8.47,8.86,10.36,11.08};
        double[] x4 = {35.3,29.7,30.8,58.8,61.4,71.3,74.4,76.7,70.7,57.5,46.4,28.9,28.1,39.1,46.8,48.5,59.3,70.0,70.0,74.5,72.1,58.1,44.6,33.4,28.6};
        double[] x1 = {35.3};
        LinearRegression lr= new LinearRegression(x4,y3);
        double tobs= 24.61065223293827; //there is a difference to the powerpoint related to rounding up
        Assert.assertTrue(lr.calculateTobs(0)==tobs);
    }

    @Test
    public void calculateStdDev() {
        double[] y3 = {10.98,11.13,12.51,8.40,9.27,8.73,6.36,8.50,7.82,9.14,8.24,12.19,11.88,9.57,10.94,9.58,10.09,8.11,6.83,8.88,7.68,8.47,8.86,10.36,11.08};
        double[] x4 = {35.3,29.7,30.8,58.8,61.4,71.3,74.4,76.7,70.7,57.5,46.4,28.9,28.1,39.1,46.8,48.5,59.3,70.0,70.0,74.5,72.1,58.1,44.6,33.4,28.6};
        double[] x1 = {35.3};
        LinearRegression lr= new LinearRegression(x4,y3);
        double std= 0.8901245161193015;
        Assert.assertTrue(lr.calculateStdDev()==std);
    }

    @Test
    public void getSsr() {
        double[] y3 = {10.98,11.13,12.51,8.40,9.27,8.73,6.36,8.50,7.82,9.14,8.24,12.19,11.88,9.57,10.94,9.58,10.09,8.11,6.83,8.88,7.68,8.47,8.86,10.36,11.08};
        double[] x4 = {35.3,29.7,30.8,58.8,61.4,71.3,74.4,76.7,70.7,57.5,46.4,28.9,28.1,39.1,46.8,48.5,59.3,70.0,70.0,74.5,72.1,58.1,44.6,33.4,28.6};
        double[] x1 = {35.3};
        LinearRegression lr= new LinearRegression(x4,y3);
        double ssr= 45.592401953477726;
        Assert.assertTrue(lr.getSsr()==ssr);
    }

    @Test
    public void getSse() {
        double[] y3 = {10.98,11.13,12.51,8.40,9.27,8.73,6.36,8.50,7.82,9.14,8.24,12.19,11.88,9.57,10.94,9.58,10.09,8.11,6.83,8.88,7.68,8.47,8.86,10.36,11.08};
        double[] x4 = {35.3,29.7,30.8,58.8,61.4,71.3,74.4,76.7,70.7,57.5,46.4,28.9,28.1,39.1,46.8,48.5,59.3,70.0,70.0,74.5,72.1,58.1,44.6,33.4,28.6};
        double[] x1 = {35.3};
        LinearRegression lr= new LinearRegression(x4,y3);
        double se= lr.calculateSqt()-lr.calculateSqr();
        Assert.assertTrue(lr.getSse()==se);
    }

    @Test
    public void getSst() {
        double[] y3 = {10.98,11.13,12.51,8.40,9.27,8.73,6.36,8.50,7.82,9.14,8.24,12.19,11.88,9.57,10.94,9.58,10.09,8.11,6.83,8.88,7.68,8.47,8.86,10.36,11.08};
        double[] x4 = {35.3,29.7,30.8,58.8,61.4,71.3,74.4,76.7,70.7,57.5,46.4,28.9,28.1,39.1,46.8,48.5,59.3,70.0,70.0,74.5,72.1,58.1,44.6,33.4,28.6};
        double[] x1 = {35.3};
        LinearRegression lr= new LinearRegression(x4,y3);
        double st= lr.getSsr()+lr.getSse();
        Assert.assertTrue(lr.getSst()==st);
    }

    @Test
    public void calculateDF() {
        double[] y3 = {10.98,11.13,12.51,8.40,9.27,8.73,6.36,8.50,7.82,9.14,8.24,12.19,11.88,9.57,10.94,9.58,10.09,8.11,6.83,8.88,7.68,8.47,8.86,10.36,11.08};
        double[] x4 = {35.3,29.7,30.8,58.8,61.4,71.3,74.4,76.7,70.7,57.5,46.4,28.9,28.1,39.1,46.8,48.5,59.3,70.0,70.0,74.5,72.1,58.1,44.6,33.4,28.6};
        double[] x1 = {35.3};
        LinearRegression lr= new LinearRegression(x4,y3);
        Assert.assertTrue(lr.calculateDF()==1);//Its always 1;
    }

    @Test
    public void calculateSqr() {
        double[] y3 = {10.98,11.13,12.51,8.40,9.27,8.73,6.36,8.50,7.82,9.14,8.24,12.19,11.88,9.57,10.94,9.58,10.09,8.11,6.83,8.88,7.68,8.47,8.86,10.36,11.08};
        double[] x4 = {35.3,29.7,30.8,58.8,61.4,71.3,74.4,76.7,70.7,57.5,46.4,28.9,28.1,39.1,46.8,48.5,59.3,70.0,70.0,74.5,72.1,58.1,44.6,33.4,28.6};
        double[] x1 = {35.3};
        LinearRegression lr= new LinearRegression(x4,y3);
    }

    @Test
    public void calculateSqt() {
        double[] y3 = {10.98,11.13,12.51,8.40,9.27,8.73,6.36,8.50,7.82,9.14,8.24,12.19,11.88,9.57,10.94,9.58,10.09,8.11,6.83,8.88,7.68,8.47,8.86,10.36,11.08};
        double[] x4 = {35.3,29.7,30.8,58.8,61.4,71.3,74.4,76.7,70.7,57.5,46.4,28.9,28.1,39.1,46.8,48.5,59.3,70.0,70.0,74.5,72.1,58.1,44.6,33.4,28.6};
        double[] x1 = {35.3};
        LinearRegression lr= new LinearRegression(x4,y3);
        double st= lr.getSsr()+lr.getSse();
        Assert.assertTrue(lr.calculateSqt()==st);
    }

    @Test
    public void calculateSqe() {
        double[] y3 = {10.98,11.13,12.51,8.40,9.27,8.73,6.36,8.50,7.82,9.14,8.24,12.19,11.88,9.57,10.94,9.58,10.09,8.11,6.83,8.88,7.68,8.47,8.86,10.36,11.08};
        double[] x4 = {35.3,29.7,30.8,58.8,61.4,71.3,74.4,76.7,70.7,57.5,46.4,28.9,28.1,39.1,46.8,48.5,59.3,70.0,70.0,74.5,72.1,58.1,44.6,33.4,28.6};
        double[] x1 = {35.3};
        LinearRegression lr= new LinearRegression(x4,y3);
        double se= lr.calculateSqt()-lr.calculateSqr();
        Assert.assertTrue(lr.calculateSqe()==se);
    }

    @Test
    public void calculateDFError() {
        double[] y3 = {10.98,11.13,12.51,8.40,9.27,8.73,6.36,8.50,7.82,9.14,8.24,12.19,11.88,9.57,10.94,9.58,10.09,8.11,6.83,8.88,7.68,8.47,8.86,10.36,11.08};
        double[] x4 = {35.3,29.7,30.8,58.8,61.4,71.3,74.4,76.7,70.7,57.5,46.4,28.9,28.1,39.1,46.8,48.5,59.3,70.0,70.0,74.5,72.1,58.1,44.6,33.4,28.6};
        double[] x1 = {35.3};
        LinearRegression lr= new LinearRegression(x4,y3);
        double df= y3.length-2;
        Assert.assertTrue(lr.calculateDFError()==df);
    }

    @Test
    public void calculateDFTot() {
        double[] y3 = {10.98,11.13,12.51,8.40,9.27,8.73,6.36,8.50,7.82,9.14,8.24,12.19,11.88,9.57,10.94,9.58,10.09,8.11,6.83,8.88,7.68,8.47,8.86,10.36,11.08};
        double[] x4 = {35.3,29.7,30.8,58.8,61.4,71.3,74.4,76.7,70.7,57.5,46.4,28.9,28.1,39.1,46.8,48.5,59.3,70.0,70.0,74.5,72.1,58.1,44.6,33.4,28.6};
        double[] x1 = {35.3};
        LinearRegression lr= new LinearRegression(x4,y3);
        double df= y3.length-1;
        Assert.assertTrue(lr.calculateDFTot()==df);
    }

    @Test
    public void calculateMQR() {
        double[] y3 = {10.98,11.13,12.51,8.40,9.27,8.73,6.36,8.50,7.82,9.14,8.24,12.19,11.88,9.57,10.94,9.58,10.09,8.11,6.83,8.88,7.68,8.47,8.86,10.36,11.08};
        double[] x4 = {35.3,29.7,30.8,58.8,61.4,71.3,74.4,76.7,70.7,57.5,46.4,28.9,28.1,39.1,46.8,48.5,59.3,70.0,70.0,74.5,72.1,58.1,44.6,33.4,28.6};
        double[] x1 = {35.3};
        LinearRegression lr= new LinearRegression(x4,y3);
        double Mqr= lr.calculateSqr();
        Assert.assertTrue(lr.calculateMQR()==Mqr);
    }

    @Test
    public void calculateMQE() {
        double[] y3 = {10.98,11.13,12.51,8.40,9.27,8.73,6.36,8.50,7.82,9.14,8.24,12.19,11.88,9.57,10.94,9.58,10.09,8.11,6.83,8.88,7.68,8.47,8.86,10.36,11.08};
        double[] x4 = {35.3,29.7,30.8,58.8,61.4,71.3,74.4,76.7,70.7,57.5,46.4,28.9,28.1,39.1,46.8,48.5,59.3,70.0,70.0,74.5,72.1,58.1,44.6,33.4,28.6};
        double[] x1 = {35.3};
        LinearRegression lr= new LinearRegression(x4,y3);
        double Mqe= lr.calculateSqe()/ (y3.length-2);
        Assert.assertTrue(lr.calculateMQE()==Mqe);
    }

    @Test
    public void getEquationOfRegression() {
        double[] y3 = {10.98,11.13,12.51,8.40,9.27,8.73,6.36,8.50,7.82,9.14,8.24,12.19,11.88,9.57,10.94,9.58,10.09,8.11,6.83,8.88,7.68,8.47,8.86,10.36,11.08};
        double[] x4 = {35.3,29.7,30.8,58.8,61.4,71.3,74.4,76.7,70.7,57.5,46.4,28.9,28.1,39.1,46.8,48.5,59.3,70.0,70.0,74.5,72.1,58.1,44.6,33.4,28.6};
        double[] x1 = {35.3};
        LinearRegression lr= new LinearRegression(x4,y3);
        String equation=("Y= 13.622989268172681 -0.07982869331126773X");
        Assert.assertTrue(lr.getEquationOfRegression().equals(equation));

    }

    @Test
    public void calculateFstat() {
        double[] y3 = {10.98,11.13,12.51,8.40,9.27,8.73,6.36,8.50,7.82,9.14,8.24,12.19,11.88,9.57,10.94,9.58,10.09,8.11,6.83,8.88,7.68,8.47,8.86,10.36,11.08};
        double[] x4 = {35.3,29.7,30.8,58.8,61.4,71.3,74.4,76.7,70.7,57.5,46.4,28.9,28.1,39.1,46.8,48.5,59.3,70.0,70.0,74.5,72.1,58.1,44.6,33.4,28.6};
        double[] x1 = {35.3};
        LinearRegression lr= new LinearRegression(x4,y3);
        double Fstat= lr.calculateMQR()/lr.calculateMQE();
        Assert.assertTrue(lr.calculateFstat()==Fstat);

    }



    @Test
    public void significancy() {
        double[] y3 = {10.98,11.13,12.51,8.40,9.27,8.73,6.36,8.50,7.82,9.14,8.24,12.19,11.88,9.57,10.94,9.58,10.09,8.11,6.83,8.88,7.68,8.47,8.86,10.36,11.08};
        double[] x4 = {35.3,29.7,30.8,58.8,61.4,71.3,74.4,76.7,70.7,57.5,46.4,28.9,28.1,39.1,46.8,48.5,59.3,70.0,70.0,74.5,72.1,58.1,44.6,33.4,28.6};
        double[] x1 = {35.3};
        LinearRegression lr= new LinearRegression(x4,y3);
        double valueTSnedecor= 4.2565;
        boolean reject=false;
        if(lr.calculateFstat()>valueTSnedecor){
            reject= true;
        }
        Assert.assertTrue(lr.significancy(0.05)==reject);
    }


}