package app.algorithms.regression;

import app.algorithms.matrix.MatrixOperations;
import org.apache.commons.math3.distribution.FDistribution;
import org.apache.commons.math3.distribution.TDistribution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MultipleLinearRegression implements Regression{
    private final List<double[]> x;
    private double[][] Xready;
    private final double[][] Y;
    private final MatrixOperations matrixOperations = new MatrixOperations();
    private double critFD;
    private double[][] coeficientsOfEquation;

    public MultipleLinearRegression(List<double[]> x, double[] y) {


        this.x = x;


        MatrixOperations mo = new MatrixOperations();

        double[][] joinedMatrix = mo.joinXmatrix(x);
        Xready = joinedMatrix;
        double[][] transposeX = new double[joinedMatrix[0].length][joinedMatrix.length];

        mo.transpose(joinedMatrix, transposeX);

        double[][] temp = mo.multiplyMatrices(transposeX, joinedMatrix);

        double[][] temp2 = new double[temp.length][temp[0].length];
        mo.inverse(temp, temp2);

        double[][] y1 = new double[y.length][1];

        for (int n = 0; n < y.length; n++) {
            y1[n][0] = y[n];
        }
        this.Y = y1;
        double[][] temp4 = mo.multiplyMatrices(temp2, transposeX);
        coeficientsOfEquation = mo.multiplyMatrices(temp4, y1);


    }

    public double R2() {
        return calculateSqr() / calculateSqt();

    }

    public double calculateR() {
        return Math.sqrt(R2());
    }

    public String getEquationOfRegression() {
        String equation = "Y= " + coeficientsOfEquation[0][0] + " ";
        for (int n = 1; n < coeficientsOfEquation.length; n++) {
            if (String.valueOf(coeficientsOfEquation[n][0]).charAt(0) != '-') {
                equation = equation + "+";
            }
            equation = equation + String.valueOf(coeficientsOfEquation[n][0]) + "X" + n + " ";
        }
        return equation;
    }

    public double calculateR2Adjusted(double r2) {
        double a = Xready.length - 1;
        double b = Xready.length - (x.size() + 1);
        double c = 1 - r2;
        return (1 - ((a / b) * c));
    }

    public double calculateSqr() {
        double meanY = calculateMeanY(Y);
        double[][] Btrans = new double[coeficientsOfEquation[0].length][coeficientsOfEquation.length];
        matrixOperations.transpose(coeficientsOfEquation, Btrans);
        double[][] Xtrans = new double[Xready[0].length][Xready.length];

        matrixOperations.transpose(Xready, Xtrans);

        double[][] multiplication = matrixOperations.multiplyMatrices(Btrans, Xtrans);
        multiplication = matrixOperations.multiplyMatrices(multiplication, Y);
        double NminusMeanY = Xready.length * (meanY * meanY);

        return multiplication[0][0] - NminusMeanY;
    }

    public double calculateSqt() {
        double meanY = calculateMeanY(Y);
        double[][] Ytrans = new double[Y[0].length][Y.length];
        matrixOperations.transpose(Y, Ytrans);
        double[][] YtranstimesY = matrixOperations.multiplyMatrices(Ytrans, Y);
        double nTimesY2 = Xready.length * (meanY * meanY);
        return YtranstimesY[0][0] - nTimesY2;
    }

    public double calculateSqe() {

        double[][] Ytrans = new double[Y[0].length][Y.length];
        matrixOperations.transpose(Y, Ytrans);
        double[][] YtranstimsY = matrixOperations.multiplyMatrices(Ytrans, Y);
        double[][] Btrans = new double[coeficientsOfEquation[0].length][coeficientsOfEquation.length];
        double[][] Xtrans = new double[Xready[0].length][Xready.length];
        matrixOperations.transpose(coeficientsOfEquation, Btrans);
        matrixOperations.transpose(Xready, Xtrans);
        double[][] BtransTimesXtrans = matrixOperations.multiplyMatrices(Btrans, Xtrans);
        double[][] BtransTimesXtransTimesY = matrixOperations.multiplyMatrices(BtransTimesXtrans, Y);

        return YtranstimsY[0][0] - BtransTimesXtransTimesY[0][0];

    }

    private double calculateMeanY(double[][] Y) {
        double mean = 0;
        for (int n = 0; n < Y.length; n++) {
            mean = mean + Y[n][0];
        }
        return mean / Y.length;
    }

    public double calculateDFTot(){ //DF são as colunas da matriz -1
        return Xready.length-1;
    }

    public double calculateDF(){ // Linhas da matriz -1
        return Xready[0].length-1;
    }

    public double calculateDFError(){ // Subtração das 2 anteriores
        return calculateDFTot()-calculateDF();
    }

    public double calculateMean (){
        double sum=0;
        for(int i=0; i<Xready.length;i++){
            for(int j=0; j<Xready[0].length;j++){
                sum= sum + Xready[i][j];

            }
        }
       return sum/(Xready.length*Xready[0].length);

    }

    public double CalculateSSTot(){
        double mean= calculateMean();
        double sum=0;
        for(int i=0; i<Xready.length;i++){
            for(int j=0; j<Xready[0].length;j++){
                sum= sum + ((Xready[i][j]-mean)*(Xready[i][j]-mean));

            }
        }
        return sum;
    }

    public double calculateMQR(){
        return calculateSqr()/calculateDF();
    }

    public double calculateMQE(){
        return calculateSqe()/calculateDFError();
    }

    public double calculateFstat(){
        return calculateMQR()/calculateMQE();
    }

    public double predict(double[] x){
        if(x.length+1 != coeficientsOfEquation.length){
            throw new IllegalArgumentException("the size of the list x is not equal to the size of the list of coefficients in the equation. ");
        }
        double sum = coeficientsOfEquation[0][0];
        for(int n = 0; n < x.length; n++){
            sum = sum + (x[n] * coeficientsOfEquation[n+1][0]);
        }
        return sum;
    }

    /**
     * calculates the confidence interval for a beta.
     * @param alfa  significance level
     * @param j coefficient index.
     * @return array with upper and lower values of CI
     */
    public double[] confidenceIntervalsForRegressionCoefficients(double alfa, int j){
        alfa = 1 - ((1 - alfa)/2);

        TDistribution td= new TDistribution(Xready.length - (Xready[0].length));
        double critTD = 0.0;
        if(alfa> 0.5) {
            critTD = td.inverseCumulativeProbability(alfa);
        }
        else {
            critTD = td.inverseCumulativeProbability(1 - alfa);
        }
        double[][] C = calculateCmatrix();
        double resultMultiplication = critTD * Math.sqrt(calculateMQE()*C[j][j]);
        double[] interval = new double[2];


        interval[0] = coeficientsOfEquation[j][0] - resultMultiplication;
        interval[1] = coeficientsOfEquation[j][0] +resultMultiplication;
        return interval;
    }

    public double calculateTobs(int j){
        double[][] C = calculateCmatrix();
        return (coeficientsOfEquation[j][0] / Math.sqrt(calculateMQE() * C[j][j]));


    }

    public boolean rejectullHypotesis(double levelOfSig, int j ){
        levelOfSig = 1 - ((levelOfSig)/2);

        TDistribution td= new TDistribution(Xready.length - (Xready[0].length -1));
        double critTD = 0.0;
        if(levelOfSig> 0.5) {
            critTD = td.inverseCumulativeProbability(levelOfSig);
        }
        else {
            critTD = td.inverseCumulativeProbability(1 - levelOfSig);
        }
        double t0 = coeficientsOfEquation[j][0] / Math.sqrt(calculateMQE() * calculateCmatrix()[j][j]);
        if(Math.abs(t0) > critTD ){
            return true;
        }else{
            return false;
        }
    }

    private double[][] calculateCmatrix(){
        double[][] Xtrans = new double[Xready[0].length][Xready.length];

        matrixOperations.transpose(Xready,Xtrans);
        double[][] XtransPlusX = matrixOperations.multiplyMatrices(Xtrans, Xready);
        double[][] C = new double[XtransPlusX.length][XtransPlusX.length];
        matrixOperations.inverse(XtransPlusX, C);
        return  C;
    }

    public boolean significancy(double alphaFD){
        FDistribution fd= new FDistribution(calculateDF(),calculateDFError());

        critFD= fd.inverseCumulativeProbability(1-alphaFD);
        if(calculateFstat()>critFD){
            return true; //É significativo
        }else{
            return false; // Não é significativo
        }
    }

    public double[] confidenceIntervalForY(double predict,double alphaFD,double[]x){
        System.out.println();
        alphaFD = 1 - ((1-alphaFD)/2);

        TDistribution td= new TDistribution(calculateDFError());
        double critTD = 0.0;
        if(alphaFD> 0.5) {
            critTD = td.inverseCumulativeProbability(alphaFD);
        }
        else {
            critTD = td.inverseCumulativeProbability(1 - alphaFD);
        }
        double array[][]= new double[1][x.length+1];
        array[0][0]=1;
        for(int i=1; i< array[0].length;i++){
            System.out.println(x[i-1]);
            array[0][i]= x[i-1];
        }
        double [][]C= calculateCmatrix();
        double trans[][]= new double[array[0].length][array.length];
        matrixOperations.transpose(array,trans);
        double [][]Operation= matrixOperations.multiplyMatrices(array,C);
        Operation=matrixOperations.multiplyMatrices(Operation,trans);
        for(int i=0; i< Operation.length;i++){
            for(int j=0; j<Operation[0].length;j++){
                Operation[i][j]= (1+ Operation[i][j])*calculateMQE();
            }
        }
        double value= Math.sqrt(Operation[0][0]);
        double multiplication= critTD*value;
        double result[]= new double[2];
        result[0]= predict-multiplication;
        result[1]= predict+multiplication;

        return result;

    }

    public double getCritFD() {
        return critFD;
    }
}

