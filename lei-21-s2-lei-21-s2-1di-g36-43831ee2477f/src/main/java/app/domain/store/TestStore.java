package app.domain.store;
import app.Persistence;
import app.algorithms.regression.MultipleLinearRegression;
import app.algorithms.regression.Regression;
import app.controller.App;
import app.controller.ConsultTestsController;
import app.domain.model.*;
import app.domain.shared.Constants;
import app.mappers.dto.NhsReportDTO;
import app.ui.console.utils.EmailNotificationSender;
import app.ui.console.utils.Utils;
import auth.AuthFacade;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.output.OutputException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;


public class TestStore {

    private List<Test> testList;
    private final ParameterStore parameterStore;
    private final ParameterCategoryStore parameterCategoryStore;
    private final LaboratoryStore laboratoryStore;
    private final TestTypeStore testTypeStore;
    private final double valueMaxForCovidTest = 1.4;


    /**
     * constructor
     */
    public TestStore() {
        this.testList = new ArrayList<>();
        parameterStore = new ParameterStore();
        testTypeStore = new TestTypeStore();
        parameterCategoryStore = new ParameterCategoryStore();
        laboratoryStore = new LaboratoryStore();
        try {
            testList = (List<Test>) Persistence.readObjectFromFile("tests.bin");
        } catch (Exception e) {
            //System.out.println("The testStore was not loaded :)");
        }
    }

    /**
     * Creates the table with the historical points to send in the report
     *
     * @param regression   regression model
     * @param nhsReportDTO dto
     * @return table with all data
     * @throws ParseException
     */
    public String getTableWithHistoricalDataToSendNHS(Regression regression, NhsReportDTO nhsReportDTO) throws ParseException {
        if (regression instanceof MultipleLinearRegression) {
            if (nhsReportDTO.getDaysOrWeeks().equals("days")) {
                return getHistoryOfPointsMultiLinearDays(nhsReportDTO.getNumberOfHistoricalPoints(), nhsReportDTO.getToday(), regression, nhsReportDTO);

            } else {
                return getHistoryOfPointsMultiLinearWeeks(nhsReportDTO.getNumberOfHistoricalPoints(), nhsReportDTO.getToday(), regression, nhsReportDTO);
            }

        } else {
            if (nhsReportDTO.getDaysOrWeeks().equals("days")) {
                return getHistoryOfPointsSimpleDays(nhsReportDTO.getNumberOfHistoricalPoints(), nhsReportDTO.getToday(), regression, nhsReportDTO);

            } else {
                return getHistoryOfPointsSimpleWeek(nhsReportDTO.getNumberOfHistoricalPoints(), nhsReportDTO.getToday(), regression, nhsReportDTO);
            }
        }
    }

    /**
     * Creates the table with the historical points to send in the report. This method is for simple linear regression nad gives a table with week data.
     *
     * @param numberOfHistoricalPoints number of historical points.
     * @param today                    today.
     * @param regression               regression model
     * @param nhsReportDTO             dto
     * @return table
     * @throws ParseException
     */
    private String getHistoryOfPointsSimpleWeek(int numberOfHistoricalPoints, Date today, Regression regression, NhsReportDTO nhsReportDTO) throws ParseException {
        String report = "";
        LocalDate todayLocalDate = new java.sql.Date(today.getTime()).toLocalDate();

        while (todayLocalDate.getDayOfWeek() != DayOfWeek.SUNDAY) {
            todayLocalDate = todayLocalDate.minusDays(1);
        }

        for (LocalDate week =todayLocalDate; week.isAfter(todayLocalDate.minusDays(numberOfHistoricalPoints * 7)); week = week.minusDays(7)) {
            double Y = 0;

            Date upperLimitWeek = Date.from(week.atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant());
            Date lowerLimitWeek = Date.from(week.minusDays(7).atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant());
            double x = 0;
            if (nhsReportDTO.getIndependentVars()[0].equals("meanAge")) {
                x = x + getXListAgesByWeek(lowerLimitWeek, upperLimitWeek)[0];

            } else {
                x = x + getXListNumberTestsByWeek(lowerLimitWeek, upperLimitWeek, getTestList())[0];
            }

            Y = Y + getYListWeek(lowerLimitWeek, upperLimitWeek)[0];


            double[] x1 = new double[1];
            x1[0] = x;
            double[] IC = regression.confidenceIntervalForY(regression.predict(x1), nhsReportDTO.getSignificanceLevelForIC(), x1);


            report = report + week + "                    " + String.valueOf(Y) + "                                      " +
                    regression.predict(x1) + "                            [ " + IC[0] + " - " + IC[1] + " ]" + "\n";

        }

        return report;
    }

    private String getHistoryOfPointsSimpleDays(int number, Date today, Regression r, NhsReportDTO nhsReportDTO) throws ParseException {
        String report = "";
        LocalDate todayLocalDate = new java.sql.Date(today.getTime()).toLocalDate();
        for (LocalDate date = todayLocalDate.minusDays(1); date.isAfter(todayLocalDate.minusDays(number + 1)); date = date.minusDays(1)) {
            if (date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                Date date1 = Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

                double[] x = new double[1];
                if (nhsReportDTO.getIndependentVars()[0].equals("tests/day")) {
                    x[0] = (double) getNumberTestsMadeOnDate(date1, getTestList());

                } else {
                    x[0] = getMeanAgesPerDay(date1, date1).get(0);

                }
                double[] IC = r.confidenceIntervalForY(r.predict(x), nhsReportDTO.getSignificanceLevelForIC(), x);

                report = report + date + "                    " + String.valueOf(getNumberPositiveCasesOnDate(date1, getTestList())) + "                                      " +
                        r.predict(x) + "                            [ " + IC[0] + " - " + IC[1] + " ]" + "\n";
            }else{
                number++;
            }

        }
        return report;
    }

    private String getHistoryOfPointsMultiLinearDays(int number, Date today, Regression r, NhsReportDTO nhsReportDTO) throws ParseException {
        String report = "";
        LocalDate todayLocaldate = new java.sql.Date(today.getTime()).toLocalDate();

            for (LocalDate date = todayLocaldate.minusDays(1); date.isAfter(todayLocaldate.minusDays(number + 1)); date = date.minusDays(1)) {

            if (date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                Date date1 = Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

                double[] x = new double[2];
                x[0] = (double) getNumberTestsMadeOnDate(date1, getTestList());
                x[1] = getMeanAgesPerDay(date1, date1).get(0);

                double[] IC = r.confidenceIntervalForY(r.predict(x), nhsReportDTO.getSignificanceLevelForIC(), x);

                report = report + date + "                    " + String.valueOf(getNumberPositiveCasesOnDate(date1, getTestList())) + "                                      " +
                        r.predict(x) + "                            [ " + IC[0] + " - " + IC[1] + " ]" + "\n";
            }else{
                number++;
            }

        }
        return report;
    }

    private String getHistoryOfPointsMultiLinearWeeks(int number, Date today, Regression r, NhsReportDTO nhsReportDTO) throws ParseException {
        LocalDate todate = new java.sql.Date(today.getTime()).toLocalDate();

        while (todate.getDayOfWeek() != DayOfWeek.SUNDAY) {
            todate = todate.minusDays(1);
        }
        today = Date.from(todate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        String report = "";
        LocalDate todayLocalDate = new java.sql.Date(today.getTime()).toLocalDate();

        for (LocalDate week = todayLocalDate; week.isAfter(todayLocalDate.minusDays(number * 7)); week = week.minusDays(7)) {
            double sumNumeTests = 0;
            double sumMeanAges = 0;
            double sumPositiveCases = 0;

            Date upperLimitWeek = Date.from(week.atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant());
            Date lowerLimitWeek = Date.from(week.minusDays(7).atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant());

            sumNumeTests = getXListNumberTestsByWeek(lowerLimitWeek, upperLimitWeek, getTestList())[0];

            sumMeanAges = sumMeanAges + getXListAgesByWeek(lowerLimitWeek, upperLimitWeek)[0];


            sumPositiveCases += getYListWeek(lowerLimitWeek, upperLimitWeek)[0];

            double[] x = new double[2];
            x[0] = sumNumeTests;
            x[1] = sumMeanAges;
            double[] IC = r.confidenceIntervalForY(r.predict(x), nhsReportDTO.getSignificanceLevelForIC(), x);
            report = report + week + "                    " + String.valueOf(sumPositiveCases) + "                                      " +
                    r.predict(x) + "                            [ " + IC[0] + " - " + IC[1] + " ]" + "\n";
            //report = report + week + "                    " + String.valueOf(sumPositiveCases) + "                                      " +
            //       r.predict(x) + "\n";

        }

        return report;

    }

    /**
     * Gets the X variable list "number of tests made in the week"
     *
     * @param lowerLimit lower limit (date)
     * @param upperLimit upper limit (date)
     * @param testList   list of tests
     * @return the X list
     * @throws ParseException
     */
    public double[] getXListNumberTestsByWeek(Date lowerLimit, Date upperLimit, List<Test> testList) throws ParseException {
        int counter = 0;
        double numTestsWeek = 0;
        List<Double> x = new ArrayList<>();
        LocalDate upperLimitLocal = new java.sql.Date(upperLimit.getTime()).toLocalDate();
        LocalDate lowerLimitLocal = new java.sql.Date(lowerLimit.getTime()).toLocalDate();

        for (LocalDate date =upperLimitLocal; date.isAfter(lowerLimitLocal.minusDays(1)); date = date.minusDays(1)) {
            if (date.getDayOfWeek() != DayOfWeek.SUNDAY) {

                numTestsWeek = numTestsWeek + getNumberTestsMadeOnDate(Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()), testList);
                counter++;
                if (counter == 6) {
                    x.add(numTestsWeek);
                    counter = 0;
                    numTestsWeek = 0;
                }


            }
        }
        double[] a = new double[x.size()];
        for (int n = 0; n < x.size(); n++) {
            a[n] = x.get(n);
        }
        return a;
    }

    /**
     * Gets the X variable list "Mean ages of poeple testes in the week"
     *
     * @param lowerLimit lower limit (date)
     * @param upperLimit upper limit (date)
     * @return the X list
     * @throws ParseException
     */
    public double[] getXListAgesByWeek(Date lowerLimit, Date upperLimit) throws ParseException {
        int counter = 0;
        double ages = 0;
        List<Double> x = getMeanAgesPerWeek(lowerLimit, upperLimit);
        double a[] = new double[x.size()];
        for (int n = 0; n < x.size(); n++) {
            a[n] = x.get(n);
        }
        return a;
    }

    /**
     * Gets the number of positive cases on a specific date.
     *
     * @param date                date
     * @param testsMadeInInterval test list where this method will search
     * @return number of positive cases on the date given.
     * @throws ParseException
     */
    public int getNumberPositiveCasesOnDate(Date date, List<Test> testsMadeInInterval) throws ParseException {
        int counter = 0;
        for (Test t : testList) {

            for (TestParameter p : t.getTestParameterList()) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.UK);
                if (dateFormat.parse(t.getCreationDate().split(" ")[0]).equals(date) && Double.parseDouble(p.getTestParameterResul().getResult().replace(",", ".")) >= valueMaxForCovidTest && t.getTestType().getDescription().equals("Covid")) {
                    counter++;
                }
            }
        }
        return counter;
    }

    /**
     * Gets the Y list for the regression model. It gives the week data.
     *
     * @param lowerLimit lower limit (date)
     * @param upperLimit upper limit (date)
     * @return Y list
     * @throws ParseException
     */
    public double[] getYListWeek(Date lowerLimit, Date upperLimit) throws ParseException {

        double[] casesPerDay = getYList(lowerLimit, upperLimit, testList);
        int numWeeks = casesPerDay.length / 6;
        final double[] weeks = new double[numWeeks];
        double cases = casesPerDay[0];
        int counter = 0;
        for (int n = 1; n <= casesPerDay.length; n++) {
            if ((numWeeks != 1 && n % 6 != 0)) {
                cases = cases + casesPerDay[n - 1];
            } else if (numWeeks == 1) {
                for (int j = 1; j < 6; j++) {
                    double a = casesPerDay[j];
                    cases = cases + a;

                }
                weeks[0] = cases;
                return weeks;

            } else {
                weeks[counter] = cases;
                counter++;
                cases = 0;
            }

        }
        return weeks;
    }

    /**
     * Gets the Y list to create the regression model. It gives day data
     *
     * @param lowerLimit          lower limit (date)
     * @param upperLimit          upper limit (date)
     * @param testsMadeInInterval test list.
     * @return Y list
     * @throws ParseException
     */
    public double[] getYList(Date lowerLimit, Date upperLimit, List<Test> testsMadeInInterval) throws ParseException {
        List<Double> positiveCasesList = new ArrayList<>();
        LocalDate upperLimitLocal = new java.sql.Date(upperLimit.getTime()).toLocalDate();
        LocalDate lowerLimitLocal = new java.sql.Date(lowerLimit.getTime()).toLocalDate();
        for (LocalDate date = upperLimitLocal; date.isAfter(lowerLimitLocal.minusDays(1)); date = date.minusDays(1)) {
            if (date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                double cases = 0;

                for (Test t : testsMadeInInterval) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.UK);
                    Date date1 = dateFormat.parse(t.getCreationDate().split(" ")[0]);
                    if (date1.equals(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()))) {
                        for (TestParameter p : t.getTestParameterList()) {
                            int controll = 0;
                            if (Double.parseDouble(p.getTestParameterResul().getResult().replace(",", ".")) >= valueMaxForCovidTest && controll == 0) {
                                cases++;
                                controll = 1;
                            }
                        }

                    }


                }
                positiveCasesList.add(cases);


            }
        }
        double[] y = new double[positiveCasesList.size()];
        int counter2 = 0;
        for (Double d : positiveCasesList) {

            y[counter2] = d;
            counter2++;
        }
        return y;
    }

    /**
     * Gets the X list "number of tests per day"
     *
     * @param lowerLimit          lower limit (date)
     * @param upperLimit          upper limit (date)
     * @param testsMadeInInterval test list.
     * @return X list
     * @throws ParseException
     */
    public double[] getXlistNumberTestPerDay(Date lowerLimit, Date upperLimit, List<Test> testsMadeInInterval) throws ParseException {
        List<Double> numberTestsPerDay = new ArrayList<>();
        LocalDate upperLimitLocal = new java.sql.Date(upperLimit.getTime()).toLocalDate();
        LocalDate lowerLimitLocal = new java.sql.Date(lowerLimit.getTime()).toLocalDate();
        for (LocalDate date =upperLimitLocal; date.isAfter(lowerLimitLocal.minusDays(1)); date = date.minusDays(1)) {
            if (date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                numberTestsPerDay.add((double) getNumberTestsMadeOnDate(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()), testsMadeInInterval));

            }

        }
        double[] x = new double[numberTestsPerDay.size()];
        for (int n = 0; n < numberTestsPerDay.size(); n++) {
            x[n] = numberTestsPerDay.get(n);
        }
        return x;

    }

    /**
     * Gets the X list "mean of ages"
     *
     * @param lowerLimit lower limit (date)
     * @param upperLimit upper limit (date)
     * @return X list
     * @throws ParseException
     */
    public double[] getXListAges(Date lowerLimit, Date upperLimit) throws ParseException {
        List<Double> meanAges = getMeanAgesPerDay(lowerLimit, upperLimit);

        double[] x = new double[meanAges.size()];
        for (int n = 0; n < meanAges.size(); n++) {
            x[n] = meanAges.get(n);
        }
        return x;
    }

    /**
     * Gets the mean ages per day
     *
     * @param lowerLimit lower limit (date)
     * @param upperLimit upper limit (date)
     * @return list with the mean ages per day
     * @throws ParseException
     */
    public List<Double> getMeanAgesPerDay(Date lowerLimit, Date upperLimit) throws ParseException {
        List<Double> meanAgesPerDay = new ArrayList<>();
        LocalDate upperLimitLocal = new java.sql.Date(upperLimit.getTime()).toLocalDate();
        LocalDate lowerLimitLocal = new java.sql.Date(lowerLimit.getTime()).toLocalDate();
        for (LocalDate date = upperLimitLocal; date.isAfter(lowerLimitLocal.minusDays(1)); date = date.minusDays(1)) {
            if (date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                meanAgesPerDay.add(getMeanAgesOfTestsMade(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant())));
            }
        }
        return meanAgesPerDay;
    }

    /**
     * Gets the mean ages per week
     *
     * @param lowerLimit lower limit (date)
     * @param upperLimit upper limit (date)
     * @return list with the mean ages per day
     * @throws ParseException
     */
    public List<Double> getMeanAgesPerWeek(Date lowerLimit, Date upperLimit) throws ParseException {
        List<Double> x = new ArrayList<>();
        int counter = 0;
        double age = 0;
        int numAges = 0;
        LocalDate upperLimitLocal = new java.sql.Date(upperLimit.getTime()).toLocalDate();
        LocalDate lowerLimitLocal = new java.sql.Date(lowerLimit.getTime()).toLocalDate();
        for (LocalDate date =upperLimitLocal; date.isAfter(lowerLimitLocal.minusDays(1)); date = date.minusDays(1)) {
            if (date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                double ageGet = getMeanAgesOfTestsMade(Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
                if (ageGet != 0.0) numAges++;
                age = age + ageGet;
                counter++;
                if (counter == 6) {
                    if (numAges == 0) numAges = 1;
                    x.add(age / numAges);
                    age = 0;
                    counter = 0;
                    numAges = 0;
                }

            }
        }
        return x;
    }


    /**
     * Gets the number of tests made on a specific date.
     *
     * @param date     date
     * @param testList list of tests
     * @return number of tests made on a specific date
     * @throws ParseException
     */
    public int getNumberTestsMadeOnDate(Date date, List<Test> testList) throws ParseException {
        int counter = 0;
        for (Test t : testList) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.UK);

            if (dateFormat.parse(t.getCreationDate().split(" ")[0]).equals(date) && t.getTestType().getDescription().equals("Covid")) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * Gets the mean ages of tests made on a specific date.
     *
     * @param date date
     * @return mean ages
     * @throws ParseException
     */
    public double getMeanAgesOfTestsMade(Date date) throws ParseException {
        int counter = 0;
        double mean = 0;

        ClientStore clientStore = App.getInstance().getCompany().getClientStore();
        for (Test t : testList) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.UK);
            SimpleDateFormat clientFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.UK);

            if (clientFormat.parse(t.getCreationDate().split(" ")[0]).getYear() == date.getYear() && clientFormat.parse(t.getCreationDate().split(" ")[0]).getDay() == date.getDay() && clientFormat.parse(t.getCreationDate().split(" ")[0]).getMonth() == date.getMonth() && t.getTestType().getDescription().equals("Covid")) {

                {
                    //Period date1 = Period.between(dateFormat.parse(clientStore.getClientByTin(t.getTINOfClient()).getBirth().split(" ")[0].replace("-","/")).toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), Calendar.getInstance().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

                    //mean = mean + date1.getYears();
                    int clientYearBirth = Integer.parseInt(clientStore.getClientByTin(t.getTINOfClient()).getBirth().split(" ")[0].replace("-", "/").split("/")[2]);
                    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                    mean = mean + currentYear - clientYearBirth;
                    counter++;

                }
            }
        }
        if (counter == 0.0) {
            return 0;
        }
        return mean / counter;
    }

    /**
     * Gets the list of covid tests to create the regression model.
     *
     * @param lowerLimit  lower limit (date)
     * @param upperLimmit upper limit (date)
     * @return List of tests.
     * @throws ParseException
     */
    public List<Test> getCovidTestsForRegression(Date lowerLimit, Date upperLimmit) throws ParseException {
        List<Test> testList2 = new ArrayList<>();
        for (Test t : testList) {
            if (t.getTestType().getDescription().equals("Covid") && t.isValidated()) {

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.UK);
                Date date = dateFormat.parse(t.getCreationDate().split(" ")[0]);
                if (lowerLimit.getTime() <= date.getTime() && upperLimmit.getTime() >= date.getTime()) {
                    //if(Double.parseDouble(p.getTestParameterResul().getResult().replace(",", ".")) > maxValueCovid && (lowerLimit.before(date) &&  upperLimmit.after(date))){
                    testList2.add(t);
                }

            }

        }
        return testList2;
        /*Calendar c = Calendar.getInstance();
        for (LocalDate date = lowerLimit.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); date.isBefore(upperLimmit.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()); date = date.plusDays(1)){
            if(date.getDayOfWeek() !=DayOfWeek.SUNDAY){

            }
        }*/


    }


    /**
     * creates the test.
     *
     * @param testType      test type
     * @param parameterList parameter list
     * @param nhsCode       nhs code
     * @return test created
     */
    public Test createTest(TestType testType, List<Parameter> parameterList, String nhsCode, Laboratory laboratory, String TINOfClient) {
        return new Test(testType, parameterList, nhsCode, laboratory, TINOfClient);
    }

    /**
     * validate the test. checks if the test is duplicated.
     *
     * @param test test
     */
    public void validateTest(Test test) {
        if (testList.contains(test)) {
            throw new IllegalArgumentException("Test with the provided values already exist!");
        }
    }

    /**
     * This method checks if a certain client already has a test in the system
     *
     * @param TIN
     */
    public void checkIfClientHasAlreadyTestInSystem(String TIN) {
        for (Test t : testList) {
            if (t.getTINOfClient().equals(TIN) && !t.isValidated()) {
                throw new IllegalArgumentException("The Client with the TIN provided have already a test registered in the system.");
            }
        }
    }

    /**
     * saves the test.
     *
     * @param test test
     */
    public void saveTest(Test test) {
        validateTest(test);
        addTest(test);
    }

    /**
     * this method gets a list of registered tests.
     *
     * @return list of tests in the sate "registered"
     */
    public List<Test> getListRegisteredTest() {
        List<Test> listTest = new ArrayList<>();
        for (Test test : testList) {
            if (test.isRegistered()) {
                listTest.add(test);
            }
        }
        return listTest;
    }

    /**
     * This method adds a test to the testList
     *
     * @param test
     */
    private void addTest(Test test) {
        testList.add(test);
    }

    /**
     * This method returns a list with all the tests in System
     *
     * @return testList
     */
    public List<Test> getTestList() {
        return testList;
    }

    /**
     * gets the test that have the barcode provided.
     *
     * @param barcode barcode
     * @return test
     */
    public Test getTestByBarcode(String barcode) {
        for (Test t : testList) {
            for (Sample s : t.getSampleList()) {
                if (s.getCode().equals(barcode)) {
                    return t;
                }
            }

        }
        throw new IllegalArgumentException("The code provided does not match to any other code in database.");
    }

    public Test getTestByTestCode(String code) {
        for (Test t : testList) {
            if (t.getTestCode().equals(code)) {
                return t;
            }
        }
        throw new IllegalArgumentException("The code provided does not match to any test  in database.");
    }

    public void getTestListCSV(String fileName) throws ParseException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String path = "./testStorage/" + fileName;
        File file = new File(path);
        String line;
        int counter = 0;
        try {
            Scanner read = new Scanner(file);
            String firstLine = read.nextLine();
            String[] firstLineArray = firstLine.split(";");
            createCategories(firstLineArray, file);
            createParameters(firstLineArray, file);
            System.out.println("\n******************************\nLoading: ");
            while (read.hasNext()) {
                line = read.nextLine();
                try {
                    ConsultTestsController consultTestsController = new ConsultTestsController();
                    try {
                        consultTestsController.getClientByTin((line.split(";")[5]));
                    } catch (Exception e) {
                        createClient(line);
                    }
                    createTest(firstLine, line);
                    counter++;
                } catch (Exception e) {
                    System.out.println("\r" + e.getMessage());
                }
                System.out.print("\rTotal tests imported: " + counter);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found :(");
        }
    }

    private void createCategories(String[] array, File file) throws FileNotFoundException {
        Scanner read = new Scanner(file);
        String line = "";
        read.nextLine();
        for (int i = 0; i < array.length; i++) {
            if (array[i].contains("Category")) {
                Scanner scanner = new Scanner(file);
                scanner.nextLine();
                while (scanner.hasNextLine()) {
                    line = scanner.nextLine();
                    if (!parameterCategoryStore.getParameterCategoryList().contains(parameterCategoryStore.getParameterCategoryByDescription(line.split(";")[i])) && !line.split(";")[i].equals("NA")) {
                        parameterCategoryStore.saveParameterCategory(parameterCategoryStore.createParametercategory(parameterCategoryStore.createParameterCategoryCode(line.split(";")[i]), line.split(";")[i]));
                        break;
                    }
                }
            }
        }
    }

    private void createParameters(String[] array, File file) throws FileNotFoundException {
        int j = 0;
        String line;
        Scanner read = new Scanner(file);
        ParameterCategory parameterCategory = null;
        Boolean control = false;
        read.nextLine();
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals("Category")) {
                Scanner scanner = new Scanner(file);
                scanner.nextLine();
                while (scanner.hasNextLine()) {
                    line = scanner.nextLine();
                    if (!line.split(";")[i].equals("NA")) {
                        parameterCategory = parameterCategoryStore.getParameterCategoryByDescription(line.split(";")[i]);
                        control = true;
                        break;
                    } else {
                        control = false;
                    }
                }
                j = i + 1;
                if (control == true) {
                    while (!array[j].equals("Category") && !array[j].equals("Test_Reg_DateHour")) {
                        Parameter p = parameterStore.createParameter(array[j], array[j], array[j], parameterCategory);
                        parameterStore.saveParameter(p);
                        j++;
                    }
                }
            }
        }
    }

    private void createTest(String firstLine, String actualLine) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, BarcodeException, OutputException, OutputException, BarcodeException {
        Company company = App.getInstance().getCompany();
        ClientStore clientStore = company.getClientStore();
        List<Client> clients = clientStore.getClients();
        String testCode = actualLine.split(";")[0];
        checkTestCode(testCode);
        String nhsCode = actualLine.split(";")[1];
        String tin = actualLine.split(";")[5];
        Laboratory laboratory = laboratoryStore.getLabByID(actualLine.split(";")[2]);
        TestType testType = null;
        try {
            testType = testTypeStore.getTestTypeByName(actualLine.split(";")[11]);
        } catch (Exception e) {
        }
        List<ParameterCategory> parameterCategoryList = new ArrayList<>();
        for (int i = 12; i < firstLine.length(); i++) {
            if (firstLine.split(";")[i].equals("Test_Reg_DateHour")) {
                break;
            }
            if (firstLine.split(";")[i].equals("Category") && !actualLine.split(";")[i].equals("NA")) {
                parameterCategoryList.add(parameterCategoryStore.getParameterCategoryByDescription(actualLine.split(";")[i]));
            }
        }
        if (testType == null) {
            testType = new TestType("12345", actualLine.split(";")[11], "not specified", parameterCategoryList);
            testTypeStore.saveTest(testType);
        }
        List<Parameter> parameterList = parameterStore.getListOfParametersByCategoryList(parameterCategoryList);
        Test test = new Test(testType, parameterList, nhsCode, laboratory, tin);
        int parameterIndex = 0;
        for (Parameter p : parameterList) {
            parameterIndex = findParameterIndex(firstLine, p);
            String code = p.getCode();
            String result = actualLine.split(";")[parameterIndex];
            test.addTestResult(p.getCode(), actualLine.split(";")[parameterIndex], "no metric");
        }

        test.setCreationDate(actualLine.split(";")[firstLine.split(";").length - 4]);
        test.setSamplesAnalysedDate(actualLine.split(";")[firstLine.split(";").length - 3]);
        if (!test.getSamplesAnalysedDate().equals("")) {
            test.addSample(1);
        }
        test.setReportMadeDate(actualLine.split(";")[firstLine.split(";").length - 2]);
        test.setValidationDate(actualLine.split(";")[firstLine.split(";").length - 1]);
        test.validateTestState();
        saveTest(test);
    }

    private void createClient(String line) throws ParseException, IOException {
        Company company = App.getInstance().getCompany();
        ClientStore clientStore = company.getClientStore();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = df.parse(line.split(";")[6].replace("/", "-"));
        Client c = clientStore.createNewClient(line.split(";")[3], line.split(";")[4], Integer.parseInt(line.split(";")[5]), date, "Undefined", line.split(";")[7], line.split(";")[8], line.split(";")[9].toLowerCase(Locale.ROOT), line.split(";")[10]);
        clientStore.saveClient(c);
        String password = Utils.generatePassword();
        AuthFacade authFacade = company.getAuthFacade();
        authFacade.addUserWithRole(line.split(";")[8], line.split(";")[9].toLowerCase(Locale.ROOT), password, Constants.ROLE_CLIENT);
        EmailNotificationSender emailNotificationSender = new EmailNotificationSender();
        emailNotificationSender.sendPasswordNotification(line.split(";")[8], line.split(";")[9].toLowerCase(Locale.ROOT), password);
    }

    private int findParameterIndex(String firstLine, Parameter parameter) {
        String[] firstLineArray = firstLine.split(";");
        for (int i = 0; i < firstLineArray.length; i++) {
            if (firstLineArray[i].equals(parameter.getCode())) {
                return i;
            }
        }
        return 0;
    }

    private void checkTestCode(String code) {
        for (Test test : testList) {
            if (test.getTestCode().equals(code)) {
                test.generateCode();
            }
        }
    }
}