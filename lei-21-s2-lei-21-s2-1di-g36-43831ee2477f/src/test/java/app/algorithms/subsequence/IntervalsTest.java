package app.algorithms.subsequence;

import app.controller.App;
import app.domain.model.*;
import app.domain.store.ClientStore;
import app.domain.store.TestStore;
import app.mappers.TestMapper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.*;

import static org.junit.Assert.*;

public class IntervalsTest {
    private Company company;
    private TestStore testStore;
    private ClientStore clientStore;
    private TestMapper testMapper;
    private List<app.domain.model.Test> testList;
    private SimpleDateFormat df;
    private String[][] intervals;
    private final int intervalsArraySize = 24;
    private int[] sequence;
    private int[] testsRegArray;
    private int[] testsValiArray;
    String[] allDatesInInterval;
    private LocalDate currentDate;
    public ExpectedException exceptionRule = ExpectedException.none();
    private List<TestParameter> testParameterList = new ArrayList<>();
    private List<Parameter> parameterList = new ArrayList<>();
    private List<ParameterCategory> parameterCategoryList = new ArrayList<>();
    private app.domain.model.Test test;
    private TestType testType;
    private Laboratory lab;
    private List<Sample> sampleList;
    private WeekFields weekFields = WeekFields.of(Locale.getDefault());
    private Intervals intervalsClass;

    @Before
    public void setUp() throws Exception {
        company = App.getInstance().getCompany();
        testStore = company.getTestStore();
        clientStore = company.getClientStore();
        testMapper = new TestMapper();
        testList = testStore.getTestList();
        intervalsClass = new Intervals();
        df = new SimpleDateFormat("HH:mm");
    }

    @Test
    public void getSequence() throws ParseException {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.set(2002, 11,31);
        c2.set(2003, 0,1);
        clientStore = App.getInstance().getCompany().getClientStore();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = df.parse("23-12-1998");
        clientStore.getClients().add(new Client("1234567890123456", "1234567890", 1234567890, date, "Male", "12345678910", "Miguel", "miguel@ipp.pt", "Rua Fixe"));
        parameterList.add(new Parameter("HB000", "HB", new ParameterCategory("he001", "Hemogram"), "Botar sangue"));
        parameterCategoryList.add(new ParameterCategory("he001", "Hemogram"));
        testType = new TestType("12345", "Blood", "cheirete", parameterCategoryList);
        List<TestType> testTypeList = new ArrayList<>();
        testTypeList.add(testType);
        lab = new Laboratory("lab1", "12345", "address", "12345678901", "1234567890", testTypeList);
        test = new app.domain.model.Test(testType, parameterList, "123456789012", lab, "1234567890");
        testStore.saveTest(test);
        test.validateTestState();
        test.setCreationDate("31/12/2002 08:30");
        test.setValidationDate("01/01/2003 19:30");
        int[] sequence = {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1};
        Assert.assertTrue(Arrays.toString(intervalsClass.getSequence(c1,c2)).equals(Arrays.toString(sequence)));
    }
    @Test
    public void getDays() throws ParseException {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.set(2002, 11,31);
        c2.set(2003, 0,1);
        Date input = c1.getTime();
        Date input1 = c2.getTime();
        LocalDate la = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dat = input1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long days= 1;
        clientStore = App.getInstance().getCompany().getClientStore();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = df.parse("23-12-1998");
        clientStore.getClients().add(new Client("1234567890123456", "1234567890", 1234567890, date, "Male", "12345678910", "Miguel", "miguel@ipp.pt", "Rua Fixe"));
        parameterList.add(new Parameter("HB000", "HB", new ParameterCategory("he001", "Hemogram"), "Botar sangue"));
        parameterCategoryList.add(new ParameterCategory("he001", "Hemogram"));
        testType = new TestType("12345", "Blood", "cheirete", parameterCategoryList);
        List<TestType> testTypeList = new ArrayList<>();
        testTypeList.add(testType);
        lab = new Laboratory("lab1", "12345", "address", "12345678901", "1234567890", testTypeList);
        test = new app.domain.model.Test(testType, parameterList, "123456789012", lab, "1234567890");
        testStore.saveTest(test);
        test.validateTestState();
        test.setCreationDate("31/12/2002 08:30");
        test.setValidationDate("01/01/2003 19:30");
        List<LocalDate> x=new ArrayList<>();
        x.add(la);
        x.add(dat);
        Assert.assertTrue(intervalsClass.getDays(c1,c2,1).equals(x));

    }

    @Test
    public void getSundays() {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.set(2020, 11,31);
        c2.set(2021, 0,3);
        Date input = c1.getTime();
        Date input1 = c2.getTime();
        LocalDate la = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dat = input1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        List<LocalDate> x=new ArrayList<>();
        x.add(la);
        x.add(dat);
        //C2 is sunday, so count must be 1
        int count=1;
        Assert.assertTrue(intervalsClass.getSundays(x)==count);
    }

    @Test
    public void getIntervalOfDays() throws ParseException {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.set(2002, 11,10);
        c2.set(2003, 11,11);
        clientStore = App.getInstance().getCompany().getClientStore();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = df.parse("23-12-1998");
        clientStore.getClients().add(new Client("1234567890123456", "1234567890", 1234567890, date, "Male", "12345678910", "Miguel", "miguel@ipp.pt", "Rua Fixe"));
        parameterList.add(new Parameter("HB000", "HB", new ParameterCategory("he001", "Hemogram"), "Botar sangue"));
        parameterCategoryList.add(new ParameterCategory("he001", "Hemogram"));
        testType = new TestType("12345", "Blood", "cheirete", parameterCategoryList);
        List<TestType> testTypeList = new ArrayList<>();
        testTypeList.add(testType);
        lab = new Laboratory("lab1", "12345", "address", "12345678901", "1234567890", testTypeList);
        test = new app.domain.model.Test(testType, parameterList, "123456789012", lab, "1234567890");
        testStore.saveTest(test);
        test.validateTestState();
        test.setCreationDate("10/12/2002 08:30");
        test.setValidationDate("11/12/2002 19:30");
        int[] sequence = intervalsClass.getSequence(c1, c2);
        int initialDate=0;
        int finalDate= 1;
        String intervalString = "10-12-2002 [08:00-08:30] to 10-12-2002 [08:30-09:00]";
        Assert.assertTrue(intervalString.equals(intervalsClass.getIntervalOfDays(initialDate, finalDate)));

    }

    @Test
    public void getTestsPerformedByDay() throws ParseException {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.set(2002, 10,18);
        c2.set(2002, 10,19);
        clientStore = App.getInstance().getCompany().getClientStore();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = df.parse("23-12-1998");
        clientStore.getClients().add(new Client("1234567890123456", "1234567890", 1234567890, date, "Male", "12345678910", "Miguel", "miguel@ipp.pt", "Rua Fixe"));
        parameterList.add(new Parameter("HB000", "HB", new ParameterCategory("he001", "Hemogram"), "Botar sangue"));
        parameterCategoryList.add(new ParameterCategory("he001", "Hemogram"));
        testType = new TestType("12345", "Blood", "cheirete", parameterCategoryList);
        List<TestType> testTypeList = new ArrayList<>();
        testTypeList.add(testType);
        lab = new Laboratory("lab1", "12345", "address", "12345678901", "1234567890", testTypeList);
        test = new app.domain.model.Test(testType, parameterList, "123456789012", lab, "1234567890");
        testStore.saveTest(test);
        test.validateTestState();
        test.setCreationDate("18/11/2002 08:30");
        test.setValidationDate("18/11/2002 19:30");
        Date input = c1.getTime();
        Date input1 = c2.getTime();
        LocalDate la = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dat = input1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        List<LocalDate> x=new ArrayList<>();
        x.add(la);
        x.add(dat);
        int[]tests= {1,0}; //Each position represents a day and the tests made
        Assert.assertTrue(Arrays.toString(intervalsClass.getTestsPerformedByDay(la,dat)).equals(Arrays.toString(tests)));

    }

    @Test
    public void getTestsPerformedByWeek() throws ParseException {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.set(2002, 9,18);
        c2.set(2002, 9,25);
        clientStore = App.getInstance().getCompany().getClientStore();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = df.parse("23-12-1998");
        clientStore.getClients().add(new Client("1234567890123456", "1234567890", 1234567890, date, "Male", "12345678910", "Miguel", "miguel@ipp.pt", "Rua Fixe"));
        parameterList.add(new Parameter("HB000", "HB", new ParameterCategory("he001", "Hemogram"), "Botar sangue"));
        parameterCategoryList.add(new ParameterCategory("he001", "Hemogram"));
        testType = new TestType("12345", "Blood", "cheirete", parameterCategoryList);
        List<TestType> testTypeList = new ArrayList<>();
        testTypeList.add(testType);
        lab = new Laboratory("lab1", "12345", "address", "12345678901", "1234567890", testTypeList);
        test = new app.domain.model.Test(testType, parameterList, "123456789012", lab, "1234567890");
        testStore.saveTest(test);
        test.validateTestState();
        test.setCreationDate("18/10/2002 08:30");
        test.setValidationDate("18/10/2002 19:30");
        Date input = c1.getTime();
        Date input1 = c2.getTime();
        LocalDate la = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dat = input1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        List<LocalDate> x=new ArrayList<>();
        x.add(la);
        x.add(dat);
        int[]tests= {1,0}; //Each position represents a week and the tests made
        Assert.assertTrue(Arrays.toString(intervalsClass.getTestsPerformedByWeek(la,dat)).equals(Arrays.toString(tests)));

    }

    @Test
    public void getTestsPerformedByMonth() throws ParseException {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.set(2002, 7,18);
        c2.set(2002, 8,21);
        clientStore = App.getInstance().getCompany().getClientStore();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = df.parse("23-12-1998");
        clientStore.getClients().add(new Client("1234567890123456", "1234567890", 1234567890, date, "Male", "12345678910", "Miguel", "miguel@ipp.pt", "Rua Fixe"));
        parameterList.add(new Parameter("HB000", "HB", new ParameterCategory("he001", "Hemogram"), "Botar sangue"));
        parameterCategoryList.add(new ParameterCategory("he001", "Hemogram"));
        testType = new TestType("12345", "Blood", "cheirete", parameterCategoryList);
        List<TestType> testTypeList = new ArrayList<>();
        testTypeList.add(testType);
        lab = new Laboratory("lab1", "12345", "address", "12345678901", "1234567890", testTypeList);
        test = new app.domain.model.Test(testType, parameterList, "123456789012", lab, "1234567890");
        testStore.saveTest(test);
        test.validateTestState();
        test.setCreationDate("19/08/2002 08:30");
        test.setValidationDate("19/08/2002 19:30");
        Date input = c1.getTime();
        Date input1 = c2.getTime();
        LocalDate la = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dat = input1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        List<LocalDate> x=new ArrayList<>();
        System.out.println(Arrays.toString(intervalsClass.getTestsPerformedByMonth(la,dat)));
        x.add(la);
        x.add(dat);
        int[]tests= {1,0}; //Each position represents a month and the tests made
        Assert.assertTrue(Arrays.toString(intervalsClass.getTestsPerformedByMonth(la,dat)).equals(Arrays.toString(tests)));
    }

    @Test
    public void getTestsPerformedByYear() throws ParseException {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.set(2005, 6,18);
        c2.set(2006, 8,21);
        clientStore = App.getInstance().getCompany().getClientStore();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = df.parse("23-12-1998");
        clientStore.getClients().add(new Client("1234567890123456", "1234567890", 1234567890, date, "Male", "12345678910", "Miguel", "miguel@ipp.pt", "Rua Fixe"));
        parameterList.add(new Parameter("HB000", "HB", new ParameterCategory("he001", "Hemogram"), "Botar sangue"));
        parameterCategoryList.add(new ParameterCategory("he001", "Hemogram"));
        testType = new TestType("12345", "Blood", "cheirete", parameterCategoryList);
        List<TestType> testTypeList = new ArrayList<>();
        testTypeList.add(testType);
        lab = new Laboratory("lab1", "12345", "address", "12345678901", "1234567890", testTypeList);
        test = new app.domain.model.Test(testType, parameterList, "123456789012", lab, "1234567890");
        testStore.saveTest(test);
        test.validateTestState();
        test.setCreationDate("19/08/2005 08:30");
        test.setValidationDate("19/08/2005 19:30");
        Date input = c1.getTime();
        Date input1 = c2.getTime();
        LocalDate la = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dat = input1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        List<LocalDate> x=new ArrayList<>();
        x.add(la);
        x.add(dat);
        int[]tests= {1,0}; //Each position represents a month and the tests made
        Assert.assertTrue(Arrays.toString(intervalsClass.getTestsPerformedByYear(la,dat)).equals(Arrays.toString(tests)));
    }
}