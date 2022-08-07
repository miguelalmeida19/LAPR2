package app.mappers.dto;

import app.algorithms.regression.Regression;

public class NHSReportValuesDTO {

    private final Regression regression;
    private final double R2;
    private final double R2Adj;
    private final String hypotesisTestsForRegressionCoefficients;
    private final double DF;
    private final double DFError;
    private final double DFTot;
    private final double SQR;
    private final double SQE;
    private final double SQT;
    private final double MQR;
    private final double MQE;
    private final double FStat;
    private final double significanceLevelForHipT;
    private final double significanceLevelForIC;
    private final String getEquationOfRegression;
    private final String table;
    private final double degreesOfFreedomSignificancy;
    private final double critFD;
    private final boolean significancyResul;
    public NHSReportValuesDTO(NhsReportDTO nhsReportDTO, Regression regression, String hypothesisTestsForRegressionCoefficients, String table) {
        this.regression = regression;
        this.getEquationOfRegression = regression.getEquationOfRegression();
        this.R2 = regression.R2();
        this.R2Adj = regression.calculateR2Adjusted(regression.R2());
        this.hypotesisTestsForRegressionCoefficients = hypothesisTestsForRegressionCoefficients;
        this.DF = regression.calculateDF();
        this.DFError = regression.calculateDFError();
        this.DFTot = regression.calculateDFTot();
        this.SQR = regression.calculateSqr();
        this.SQE = regression.calculateSqe();
        this.significancyResul = regression.significancy(nhsReportDTO.getSignificanceLevelForIC());
        this.SQT = regression.calculateSqt();
        ;
        this.MQR = regression.calculateMQR();

        this.MQE = regression.calculateMQE();
        ;this.degreesOfFreedomSignificancy = regression.calculateDFError();
        this.FStat = regression.calculateFstat();
        this.significanceLevelForHipT = nhsReportDTO.getSignificanceLevelForHipT();
        this.significanceLevelForIC = nhsReportDTO.getSignificanceLevelForIC();
        this.critFD = regression.getCritFD();

        this.table = table;
        ;
    }

    public boolean isSignificancyResul() {
        return significancyResul;
    }

    public String getTable() {
        return table;
    }

    public String getGetEquationOfRegression() {
        return getEquationOfRegression;
    }

    public double getSignificanceLevelForIC() {
        return significanceLevelForIC;
    }

    public double getSignificanceLevelForHipT() {
        return significanceLevelForHipT;
    }

    public double getDF() {
        return DF;
    }

    public double getDFError() {
        return DFError;
    }

    public double getDFTot() {
        return DFTot;
    }

    public double getFStat() {
        return FStat;
    }

    public double getR2Adj() {
        return R2Adj;
    }

    public double getSQR() {
        return SQR;
    }

    public double getMQE() {
        return MQE;
    }

    public double getMQR() {
        return MQR;
    }

    public double getSQE() {
        return SQE;
    }

    public double getSQT() {
        return SQT;
    }

    public Regression getRegression() {
        return regression;
    }

    public String getHypotesisTestsForRegressionCoefficients() {
        return hypotesisTestsForRegressionCoefficients;
    }

    public double getR2() {
        return R2;
    }

    public double getCritFD() {
        return critFD;
    }

    public double getDegreesOfFreedomSignificancy() {
        return degreesOfFreedomSignificancy;
    }

}
