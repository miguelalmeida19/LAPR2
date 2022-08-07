package app.controller;

import app.algorithms.regression.LinearRegression;
import app.algorithms.regression.MultipleLinearRegression;
import app.algorithms.regression.Regression;
import app.domain.model.NHSReport;

import app.domain.model.Test;
import app.domain.store.TestStore;
import app.externalModule.ReportNHS;
import app.mappers.NHSReportValuesMapper;
import app.mappers.dto.NHSReportValuesDTO;
import app.mappers.dto.NhsReportDTO;

import java.io.FileInputStream;
import java.io.IOException;

import java.io.InputStream;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;


public class SendReportNhsController {
    private final TestStore testStore;

    /**
     * Constructor
     */
    public SendReportNhsController() {
        testStore = App.getInstance().getCompany().getTestStore();
    }

    /**
     * Generates the report to send to NHS.
     * @param nhsReportDTO dto with all information to generate the report.
     * @throws ParseException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public void generateReport(NhsReportDTO nhsReportDTO) throws ParseException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        checkIfTestsAdClientsAreISystem();

        //get the NHS Api
        NHSReportValuesMapper mapper = new NHSReportValuesMapper();
        Properties properties = new Properties();
        InputStream in = new FileInputStream("configuration.conf");
        properties.load(in);
        String api = properties.getProperty("Company.API.SendNHS.Class");
        Class<?> nhsAPI = Class.forName(api);
        ReportNHS reportNHS = (ReportNHS) nhsAPI.newInstance();

        //sets the data and creates the linear regression
        Date lowerLimit = nhsReportDTO.getIntervalOfDaysToFitTheModel()[0];
        Date upperLimit = nhsReportDTO.getIntervalOfDaysToFitTheModel()[1];
        List<Test> testsMadeInInterval = testStore.getCovidTestsForRegression(lowerLimit, upperLimit);
        double[] Y = testStore.getYList(lowerLimit, upperLimit, testsMadeInInterval);

        NHSReportValuesDTO nhsReportValuesDTO;
        String table = "";
        Regression regression;
        if (nhsReportDTO.getModelToUse().equals("multiple")) {
            List<double[]> x = new ArrayList<>();
            if (nhsReportDTO.getDaysOrWeeks().equals("days")) {
                x.add(testStore.getXlistNumberTestPerDay(lowerLimit, upperLimit, testsMadeInInterval));
                x.add(testStore.getXListAges(lowerLimit, upperLimit));
                regression = new MultipleLinearRegression(x, Y);

            } else {
                x.add(testStore.getXListNumberTestsByWeek(lowerLimit, upperLimit, testStore.getTestList()));
                x.add(testStore.getXListAgesByWeek(lowerLimit, upperLimit));
                Y = testStore.getYListWeek(lowerLimit, upperLimit);
                regression = new MultipleLinearRegression(x, Y);
            }
            table = testStore.getTableWithHistoricalDataToSendNHS(regression, nhsReportDTO);
        } else {
            if (nhsReportDTO.getDaysOrWeeks().equals("days")) {
                double[] x;
                if (nhsReportDTO.getIndependentVars()[0].equals("tests/day")) {

                    x = testStore.getXlistNumberTestPerDay(lowerLimit, upperLimit, testsMadeInInterval);
                } else {

                    x = testStore.getXListAges(lowerLimit, upperLimit);

                }
                regression = new LinearRegression(x, Y);
            } else {
                Y = testStore.getYListWeek(lowerLimit, upperLimit);

                double[] x;
                if (nhsReportDTO.getIndependentVars()[0].equals("tests/day")) {

                    x = testStore.getXListNumberTestsByWeek(lowerLimit, upperLimit, testsMadeInInterval);
                } else {

                    x = testStore.getXListAgesByWeek(lowerLimit, upperLimit);

                }
                regression = new LinearRegression(x, Y);
            }
            table = testStore.getTableWithHistoricalDataToSendNHS(regression, nhsReportDTO);

        }

        //creates the DTO with all the needed data to create the report
        nhsReportValuesDTO = mapper.toDTO(nhsReportDTO, regression, table);

        //creates and send the report
        NHSReport nhsReport = new NHSReport();
        String report = nhsReport.generateReportString(nhsReportValuesDTO);
        reportNHS.SendReport(report);

        //NHSReport report = new NHSReport();
        //report.generateReport(nhsReportDTO);
    }

    /**
     * Checks if the system has tests and clients.
     */
    private void checkIfTestsAdClientsAreISystem(){
        if(testStore.getTestList().size() == 0){
            throw new NullPointerException("There is no test registered in the system.");
        }
        if(App.getInstance().getCompany().getClientStore().getClients().size() == 0){
            throw new NullPointerException("There is no client in the system.");
        }
    }

}
