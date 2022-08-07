package app.ui;

import app.Persistence;
import app.algorithms.matrix.MatrixOperations;

import app.controller.App;
import app.controller.SendReportNhsController;
import app.domain.store.TestStore;
import app.mappers.NhsReportMapper;
import app.ui.console.utils.Utils;

import java.io.IOException;
import java.text.ParseException;

import java.util.Date;


public class TesteMain {
    public static void main(String[] args) throws ParseException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException {
        MatrixOperations o = new MatrixOperations();
        //double[][] a = {{1,2,3},{1,7,5}};
        //double[][] b = {{1,2},{1,7},{9,0}};

        /*
        List<double[]> a = new ArrayList<>();
        double[]x1 = {89,66,78,111,44,77,80,66,109,76};
        double[] x2 = {4,1,3,6,1,3,3,2,5,3};
        double[] x3 = {3.84,3.19,3.78,3.89,3.57,3.57,3.03,3.51,3.54,3.25};
        double[]y={7,5.4,6.6,7.4,4.8,6.4,7,5.6,7.3,6.4};
        //####################################################################
        double[] y3 = {2256,2340,2426,2293,2330,2368,2250,2409,2364,2379,2440,2364};
        double[] x4 = {80,93,100,82,90,99,81,96,94,93,97,95};
        double[] x5 = {8,9,10,12,11,8,8,10,12,11,13,11};
        //####################################################################
        double[] y4 = {3.5,1,4,2,1,3,4.5,1.5,3,5};
        double[] x9 = {825,215,1070, 550,480,920,1350,325,670,1215};

        a.add(x4);
        a.add(x5);
       // a.add(x2);
        Regression r  = new LinearRegression(x9,y4);
        Regression n = new MultipleLinearRegression(a,y3);


        System.out.println(r.rejectullHypotesis(0.05,0));
        */
        /*
        Date today = Utils.readDateFromConsole("Type the day of today: ");
        //String daysOrWeeks = Utils.readLineFromConsole("days or weeks?");
        String daysOrWeeks ="days";
        //int numberOfHistoricalPoints = Utils.readIntegerFromConsole("Number of historical points:");
        int numberOfHistoricalPoints = 15;
        Date lowerDateIntervalToFitRegression = Utils.readDateFromConsole("Type the day inicial para fit do modelo e essas cenas: ");
        Date upperDateIntervalToFitRegression = Utils.readDateFromConsole("Type the day final para fit do modelo e essas cenas: ");
        //String modeloAUsar = Utils.readLineFromConsole("Simple or multiple model?? : ");
        String modeloAUsar = "multiple";
        String[] independentVars = {"numTests", "meanAge"};
        TestStore testStore = App.getInstance().getCompany().getTestStore();
        testStore.getTestListCSV("tests_CovidMATCPCSV.csv");
        SendReportNhsController controller = new SendReportNhsController();
        NhsReportMapper mapper = new NhsReportMapper();
        Date[] dates = new Date[2];
        dates[0] = lowerDateIntervalToFitRegression;
        dates[1] = upperDateIntervalToFitRegression;
        controller.generateReport(mapper.toDto(today, dates,daysOrWeeks, numberOfHistoricalPoints,modeloAUsar,independentVars, 0.05,0.95, 1));
*/


        Date today = Utils.readDateFromConsole("Type the day of today: ");
        //String daysOrWeeks = Utils.readLineFromConsole("days or weeks?");
        String daysOrWeeks ="weeks"; //days or weeks
        //int numberOfHistoricalPoints = Utils.readIntegerFromConsole("Number of historical points:");
        int numberOfHistoricalPoints = 5; //numero dias ou semanas q queremos enviar
        Date lowerDateIntervalToFitRegression = Utils.readDateFromConsole("Type the day inicial para fit do modelo e essas cenas: ");
        Date upperDateIntervalToFitRegression = Utils.readDateFromConsole("Type the day final para fit do modelo e essas cenas: "); //é necessário ser, em caso de semanas, 7 dias depois desta data introduzida
        //String modeloAUsar = Utils.readLineFromConsole("Simple or multiple model?? : ");
        String modeloAUsar = "multiple"; //ou simple
        String[] independentVars = {"meanAge","tests/day"}; // ou {"tests/day" , "meanAge"}
        TestStore testStore = App.getInstance().getCompany().getTestStore();
       testStore.getTestListCSV("tests_Covid15.csv");
        SendReportNhsController controller = new SendReportNhsController();
        NhsReportMapper mapper = new NhsReportMapper();
        Date[] dates = new Date[2];
        dates[0] = lowerDateIntervalToFitRegression;
        dates[1] = upperDateIntervalToFitRegression;
        controller.generateReport(mapper.toDto(today, dates,daysOrWeeks, numberOfHistoricalPoints,modeloAUsar,independentVars, 0.05,0.95));
        Persistence.saveAllData();


    }


}
