package app.domain.model;

import app.algorithms.regression.LinearRegression;
import app.algorithms.regression.MultipleLinearRegression;
import app.algorithms.regression.Regression;
import app.controller.App;
import app.domain.store.TestStore;
import app.externalModule.ReportNHS;
import app.mappers.dto.NHSReportValuesDTO;
import app.mappers.dto.NhsReportDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class NHSReport  implements Serializable {
    private String reportString;

    /**
     * Constructor
     */
    public NHSReport() {
    }




    /**
     * Generates the report.
     *
     */
    public String generateReportString(NHSReportValuesDTO nhsReportValuesDTO) throws ParseException {
        String decision = "";
        if(nhsReportValuesDTO.isSignificancyResul()){
            decision = "The model is significant";
        }else{
            decision = "The model isn't significant.";
        }

        String report = "The regression model fitted using data from the interval \n" +
                nhsReportValuesDTO.getGetEquationOfRegression() + "\n\n" +
                "Other statistics\n" +
                "R2 = " + nhsReportValuesDTO.getR2() + "\n" +
                "R2adjusted= " + nhsReportValuesDTO.getR2Adj() + "\n" +
                "R = " + Math.sqrt(nhsReportValuesDTO.getR2()) + "\n" +
                "\n" +
                "Hypothesis tests for regression coefficients\n" +
                nhsReportValuesDTO.getHypotesisTestsForRegressionCoefficients() +
                "Significance model with Anova\n" +
                "H0: b=0  H1:b<>0 \n" +
                "\t\tdf\tSS\t\tMS\t\tF\t\t\n" +
                "Regression\t" + nhsReportValuesDTO.getDF() + "\t" + nhsReportValuesDTO.getSQR() + "\t" + nhsReportValuesDTO.getMQR() + "\t" + nhsReportValuesDTO.getFStat() + "\t\n" +
                "Residual\t" + nhsReportValuesDTO.getDFError() + "\t " + nhsReportValuesDTO.getSQE() + "\t " + nhsReportValuesDTO.getMQE() + "\t\t\n" +
                "Total\t\t" + nhsReportValuesDTO.getDFTot() + "\t" + nhsReportValuesDTO.getSQT() + "\n" +
                "\n" +
                "Decision:\n" +
                "f0 > f" + nhsReportValuesDTO.getSignificanceLevelForIC() + ",("+nhsReportValuesDTO.getDF()+","+nhsReportValuesDTO.getDFError()+")="+nhsReportValuesDTO.getCritFD()+"\n" +
                "\n" +
                decision+"\t\t\t\n" +
                "\n" +
                "// Prediction values" + "\n" +
                "Date           Number of OBSERVED positive cases        Number of ESTIMATED/EXPECTED positive cases \t\t95% intervals \n"+
                nhsReportValuesDTO.getTable();

        reportString = report;
        return report;

    }


}
