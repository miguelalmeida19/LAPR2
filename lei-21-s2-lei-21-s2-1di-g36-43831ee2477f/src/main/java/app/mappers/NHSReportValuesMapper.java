package app.mappers;

import app.algorithms.regression.Regression;
import app.domain.model.NHSReport;
import app.mappers.dto.NHSReportValuesDTO;
import app.mappers.dto.NhsReportDTO;

public class NHSReportValuesMapper {

    public NHSReportValuesDTO toDTO(NhsReportDTO nhsReport, Regression regression, String table){
        String hypothesisTestForRegressionCoefficients = hypothesisTestForRegressionCoefficients(regression, nhsReport);
        return new NHSReportValuesDTO(nhsReport, regression,hypothesisTestForRegressionCoefficients, table);
    }
    private String hypothesisTestForRegressionCoefficients(Regression regression, NhsReportDTO nhsReportDTO) {
        String decision = "";
        if (regression.rejectullHypotesis(nhsReportDTO.getSignificanceLevelForHipT(), 0)) {
            decision = "Reject";
        } else {
            decision = "No reject";
        }
        String report = "HO:a=0 H1: a<>0\n" +
                "t_obs= " + regression.calculateTobs(0) + "\n" +
                "Decision: " + decision + "\n" +
                "\n";
        String[] coefficients = regression.getEquationOfRegression().split(" ");

        for (int n = 2; n < coefficients.length; n++) {
            if (regression.rejectullHypotesis(nhsReportDTO.getSignificanceLevelForHipT(), n - 1)) {
                decision = "Reject";
            } else {
                decision = "No reject";
            }
            report = report + "HO:b" + (n - 2) + "=0 H1: b<>0\n" +
                    "t_obs= " + regression.calculateTobs(n - 1) + "\n" +
                    "Decision: \n" +
                    decision +
                    "\n";
        }
        return report;

    }
}
