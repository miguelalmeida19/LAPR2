package app.domain.store;

import static org.junit.Assert.assertTrue;

import app.controller.App;
import app.domain.model.*;
import app.mappers.ClientMapper;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.output.OutputException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class TestStoreTest {
    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();
    public app.domain.model.Test test;
    private TestStore testStore;
    private ClientStore clientStore;

    private List<app.domain.model.Test> testList;
    private List<Parameter> parameterList = new ArrayList<>();
    private List<ParameterCategory> parameterCategoryList = new ArrayList<>();
    private List<TestType> testTypeList = new ArrayList<>();
    private Laboratory laboratory;
    private TestType testType = new TestType("12345", "Blood", "coletor", parameterCategoryList);

    @Before
    public void setUp() throws Exception {
        clientStore = App.getInstance().getCompany().getClientStore();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = df.parse("23-03-1998");
        clientStore.getClients().add(new Client("1234567890123456", "1234567890", 1234567890, date, "Male", "12345678910", "Miguel", "miguel@ipp.pt", "Rua Fixe"));
        clientStore.getClients().add(new Client("1234567890123455", "1234567893", 1234567893, date, "Male", "12345678913", "Miguel", "miguel@isep.pt", "Rua Top"));
        testStore = new TestStore();
        testList = new ArrayList<>();
        parameterList.add(new Parameter("HB000", "HB", new ParameterCategory("he001", "Hemogram"), "Botar sangue"));
        parameterCategoryList.add(new ParameterCategory("he001", "Hemogram"));
        TestType testType = new TestType("12345", "Blood", "coletor", parameterCategoryList);
        testTypeList.add(testType);
        laboratory = new Laboratory("Big Lab", "12345", "Road", "12345678901", "1234567890", testTypeList);
        test = new app.domain.model.Test(testType, parameterList, "123456789012", laboratory, "1234567890");
        testList.add(test);

    }

    @Test
    public void createTest() {
        app.domain.model.Test test = new app.domain.model.Test(testType, parameterList, "123456789111", laboratory, "1234567890");

        TestStore testStore = new TestStore();
        app.domain.model.Test test1 = testStore.createTest(testType, parameterList, "123456789111", laboratory, "1234567890");
        test1.setTestCode(test.getTestCode());
        Assert.assertEquals(true, test1.equals(test));
    }

    @Test
    public void validateTestCorrect() {
        app.domain.model.Test test = new app.domain.model.Test(testType, parameterList, "123456789111", laboratory, "1234567890");
        testStore.saveTest(test);
        app.domain.model.Test test1 = new app.domain.model.Test(testType, parameterList, "123456789112", laboratory, "1234567890");
        testStore.validateTest(test1);
    }


    @Test
    public void checkIfClientHasAlreadyTestInSystem() throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = df.parse("23-12-1998");
        Client client = new Client("1234567890123456", "1234567890", 1234567890, date, "Male", "12345678901", "Migel", "miguel@isep.ipp.pt", "Rua Fixe");
        app.domain.model.Test test = new app.domain.model.Test(testType, parameterList, "123456789111", laboratory, "1234567890");
        testStore.saveTest(test);
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("The Client with the TIN provided have already a test registered in the system.");
        testStore.checkIfClientHasAlreadyTestInSystem(String.valueOf(client.getTin()));
    }

    @Test
    public void checkIfClientCorrect() throws ParseException {
        //Checks that client does not have any test in System
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = df.parse("23-12-1998");
        Client client = new Client("1234567890123456", "1234567890", 1234567890, date, "Male", "12345678901", "Migel", "miguel@isep.ipp.pt", "Rua Fixe");
        testStore.checkIfClientHasAlreadyTestInSystem(String.valueOf(client.getTin()));
    }

    @Test
    public void saveTestInCorrect() {
        app.domain.model.Test test = new app.domain.model.Test(testType, parameterList, "123456789111", laboratory, "1234567890");
        Assert.assertFalse(testStore.getTestList().contains(test));
    }

    @Test
    public void saveTestCorrect() {
        app.domain.model.Test test = new app.domain.model.Test(testType, parameterList, "123456789111", laboratory, "1234567890");
        testStore.saveTest(test);
        Assert.assertTrue(testStore.getTestList().contains(test));
    }

    @Test
    public void getListRegisteredTest() {
        List<app.domain.model.Test> registeredList = new ArrayList<>();
        app.domain.model.Test test = new app.domain.model.Test(testType, parameterList, "123456789111", laboratory, "1234567890");
        testStore.saveTest(test);
        for (app.domain.model.Test t : testStore.getTestList()) {
            if (t.isRegistered()) {
                registeredList.add(t);
            }
        }
        Assert.assertTrue(registeredList.equals(testStore.getListRegisteredTest()));
    }


    @Test
    public void getTestByBarcode() throws OutputException, BarcodeException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        testStore.saveTest(test);
        test.addSample(1);
        for (app.domain.model.Test t : testList) {
            for (Sample s : t.getSampleList()) {
                app.domain.model.Test test1 = testStore.getTestByBarcode(s.getCode());
            }
        }
    }

    @Test
    public void getTestByBarcodeWrong() throws OutputException, BarcodeException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        app.domain.model.Test test = new app.domain.model.Test(testType, parameterList, "123456789012", laboratory, "1234567890");
        testStore.saveTest(test);
        test.addSample(1);
        String barcode = test.getTestCode();
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("The code provided does not match to any other code in database.");
        testStore.getTestByBarcode(barcode);
    }

    @Test
    public void testGetTestListCSV()
            throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, ParseException {
        TestStore testStore = new TestStore();
        exceptionRule.expect(FileNotFoundException.class);
        exceptionRule.expectMessage("File not found :(");
        testStore.getTestListCSV("foo.txt");
    }

    @Test
    public void testGetXListNumberTestsByWeek() throws ParseException {
        TestType testType1 = new TestType("12222", "Covid", "obtained", parameterCategoryList);
        app.domain.model.Test test = new app.domain.model.Test(testType1, parameterList, "123456789012", laboratory, "1234567890");
        testStore.saveTest(test);
        test.setCreationDate("19/05/2021");
        app.domain.model.Test test1 = new app.domain.model.Test(testType1, parameterList, "123456789011", laboratory, "1234567890");
        testStore.saveTest(test1);
        test1.setCreationDate("22/05/2021");
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date date = df.parse("18/05/2021");
        Date date1 = df.parse("25/05/2021");
        double[] res = {2.0};
        double[] obtained = testStore.getXListNumberTestsByWeek(date, date1, testStore.getTestList());
        Assert.assertTrue(Arrays.toString(obtained).equals(Arrays.toString(res)));


    }

    @Test
    public void testGetXListAges() throws ParseException {
        TestType testType1 = new TestType("12222", "Covid", "obtained", parameterCategoryList);
        app.domain.model.Test test = new app.domain.model.Test(testType1, parameterList, "123456789012", laboratory, "1234567890");
        testStore.saveTest(test);
        test.setCreationDate("18/05/2021");
        ClientMapper mapper = new ClientMapper();
        Client client1 = mapper.toClient(clientStore.getClientByTin("1234567890"));
        String age = client1.getBirth().split("-")[2];
        String month = client1.getBirth().split("-")[1];
        String day = client1.getBirth().split("-")[0];
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date date = df.parse("15/05/2021");
        Date date1 = df.parse("21/05/2021");
        String testyear = test.getCreationDate().split("/")[2];
        String testmonth = test.getCreationDate().split("/")[1];
        String testday = test.getCreationDate().split("/")[0];
        LocalDate ageDate = LocalDate.of(Integer.parseInt(age), Integer.parseInt(month), Integer.parseInt(day));
        LocalDate TestDate = LocalDate.of(Integer.parseInt(testyear), Integer.parseInt(testmonth), Integer.parseInt(testday));
        double years = ChronoUnit.YEARS.between(ageDate, TestDate);
        double[] x = {0.0, 0.0, 0.0, 23.0, 0.0, 0.0};
        Assert.assertTrue(Arrays.toString(testStore.getXListAges(date, date1)).equals(Arrays.toString(x)));

    }


    @Test
    public void testGetNumberTestsMadeOnDate() throws ParseException {
        TestType testType1 = new TestType("12222", "Covid", "obtained", parameterCategoryList);
        app.domain.model.Test test = new app.domain.model.Test(testType1, parameterList, "123456789012", laboratory, "1234567890");
        testStore.saveTest(test);
        test.setCreationDate("18/05/2021");
        app.domain.model.Test test1 = new app.domain.model.Test(testType1, parameterList, "123456789011", laboratory, "1234567890");
        testStore.saveTest(test1);
        test1.setCreationDate("18/05/2021");
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date date = df.parse("18/05/2021");
        Assert.assertTrue(testStore.getNumberTestsMadeOnDate(date, testStore.getTestList()) == 2);


    }

    @Test
    public void testGetCovidTestsForRegression() throws ParseException {
        TestType testType1 = new TestType("12222", "Covid", "obtained", parameterCategoryList);
        app.domain.model.Test test = new app.domain.model.Test(testType1, parameterList, "123456789012", laboratory, "1234567890");
        test.setCreationDate("18/05/2021");
        testStore.saveTest(test);
        test.validateTestState();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date date = df.parse("15/05/2021");
        Date date1 = df.parse("21/05/2021");
        Assert.assertTrue(testStore.getCovidTestsForRegression(date, date1).equals(testStore.getTestList()));
    }

    @Test
    public void getTableWithHistoricalDataToSendNHS() {
    }

    @Test
    public void getNumberPositiveCasesOnDate() throws ParseException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        parameterCategoryList.clear();
        testList.clear();
        ParameterCategory pam = new ParameterCategory("11111", "Covid");
        parameterCategoryList.add(pam);
        parameterList.clear();
        TestType testType1 = new TestType("12222", "Covid", "obtained", parameterCategoryList);
        parameterList.add(new Parameter("IgGAN", "IgGAN", pam, "Donated"));
        app.domain.model.Test test = new app.domain.model.Test(testType1, parameterList, "123456789012", laboratory, "1234567890");
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date date = df.parse("14/05/2021");
        TestParameter res = new TestParameter(parameterList.get(0));
        ReferenceValue ref = new ReferenceValue(100, 10);
        test.addTestResult("IgGAN", "2.4", "null");
        test.setCreationDate("14/05/2021");
        testStore.saveTest(test);
        Assert.assertTrue(testStore.getNumberPositiveCasesOnDate(date, testStore.getTestList()) == 1);
    }

    @Test
    public void getYListWeek() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, ParseException {
        parameterCategoryList.clear();
        testList.clear();
        ParameterCategory pam = new ParameterCategory("11111", "Covid");
        parameterCategoryList.add(pam);
        parameterList.clear();
        TestType testType1 = new TestType("12222", "Covid", "obtained", parameterCategoryList);
        parameterList.add(new Parameter("IgGAN", "IgGAN", pam, "Donated"));
        app.domain.model.Test test = new app.domain.model.Test(testType1, parameterList, "123456789012", laboratory, "1234567890");
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date date = df.parse("14/05/2021");
        Date date1 = df.parse("21/05/2021");
        TestParameter res = new TestParameter(parameterList.get(0));
        ReferenceValue ref = new ReferenceValue(100, 10);
        test.addTestResult("IgGAN", "2.4", "null");
        test.setCreationDate("20/05/2021");
        testStore.saveTest(test);
        double[] array = {1}; //Position added to the day of test
        double[] result = testStore.getYListWeek(date, date1);
        Assert.assertTrue(Arrays.toString(result).equals(Arrays.toString(array)));


    }

    @Test
    public void getYList() throws ParseException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        parameterCategoryList.clear();
        testList.clear();
        ParameterCategory pam = new ParameterCategory("11111", "Covid");
        parameterCategoryList.add(pam);
        parameterList.clear();
        TestType testType1 = new TestType("12222", "Covid", "obtained", parameterCategoryList);
        parameterList.add(new Parameter("IgGAN", "IgGAN", pam, "Donated"));
        app.domain.model.Test test = new app.domain.model.Test(testType1, parameterList, "123456789012", laboratory, "1234567890");
        test.setCreationDate("18/05/2021");
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date date = df.parse("15/05/2021");
        Date date1 = df.parse("21/05/2021");
        TestParameter res = new TestParameter(parameterList.get(0));
        ReferenceValue ref = new ReferenceValue(100, 10);
        test.addTestResult("IgGAN", "2.4", "null");
        testStore.saveTest(test);
        double[] array = {0, 0, 0, 1, 0, 0}; //Position added to the day of test
        double[] result = testStore.getYList(date, date1, testStore.getTestList());
        Assert.assertTrue(Arrays.toString(result).equals(Arrays.toString(array)));


    }

}