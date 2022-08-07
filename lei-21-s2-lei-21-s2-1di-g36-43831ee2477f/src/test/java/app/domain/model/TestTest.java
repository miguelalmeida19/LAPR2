package app.domain.model;

import app.controller.App;
import app.domain.store.ClientStore;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.output.OutputException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;


public class TestTest {


    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    private List<TestParameter> testParameterList = new ArrayList<>();
    private List<Parameter> parameterList = new ArrayList<>();
    private List<ParameterCategory> parameterCategoryList = new ArrayList<>();
    private app.domain.model.Test test;
    private ClientStore clientStore;
    private TestType testType;
    private Laboratory lab;
    private List<Sample> sampleList;

    /**
     * Constructor
     */


    @Before
    public void setUp() throws Exception {
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
    }

    @Test
    public void validateParameterResultCorrect() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        test.addTestResult("HB000", "12", "g/L");
        test.validateParameterResult();
    }


    @Test
    public void validateParameterResultMissing() {

        exceptionRule.expect(NullPointerException.class);
        exceptionRule.expectMessage("There is one or more parameters that have no result.");

        test.validateParameterResult();
    }

    @Test
    public void validateReportCorrect() {

        test.validateReport(new Report("result", "report"));
    }

    @Test
    public void validateReportTooBig() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("This report is invalid, you entered too many words (Max 400 words)");
        test.validateReport(new Report("r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a r a  ", "report"));
    }

    @Test
    public void getTestParameterList() {
        testParameterList.add(new TestParameter(parameterList.get(0)));
        Assert.assertEquals(test.getTestParameterList(), testParameterList);

    }

    @Test
    public void getTINOfClienCorrectt() {
        Assert.assertEquals(test.getTINOfClient(), "1234567890");
    }

    @Test
    public void getTINOfClientWrong() {
        Assert.assertNotEquals(test.getTINOfClient(), "1234567899");
    }

    @Test
    public void getReport() throws OutputException, BarcodeException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        test.addSample(1);
        test.addTestResult("HB000", "cheiro fétido", "g/m3");
        test.validateParameterResult();
        test.addReport(new Report("result", "report"));

        Assert.assertEquals(test.getReport(), new Report("result", "report"));
    }

    @Test
    public void getReportException() {
        exceptionRule.expect(IllegalAccessError.class);
        exceptionRule.expectMessage("You cannot add a report to a test that isn't in samples analysed state.");

        test.addReport(new Report("result", "report"));

        Assert.assertEquals(test.getReport(), new Report("result", "report"));
    }

    @Test
    public void getReportWrong() throws OutputException, BarcodeException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        test.addSample(1);
        test.addTestResult("HB000", "cheiro fétido", "g/m3");
        test.validateParameterResult();
        test.addReport(new Report("result", "report"));
        Assert.assertNotEquals(test.getReport(), new Report("result", "rport"));
    }


    @Test
    public void getLaboratory() {
        Assert.assertEquals(test.getLaboratory(), lab);
    }

    @Test
    public void getLaboratoryWrong() {
        List<TestType> testTypeList = new ArrayList<>();
        testTypeList.add(testType);
        Laboratory lab2 = new Laboratory("cheiro", "11111", "adsads", "12345678901", "1234567890", testTypeList);
        Assert.assertNotEquals(test.getLaboratory(), lab2);
    }

    @Test
    public void addSample() throws OutputException, BarcodeException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        test.addSample(2);
        Assert.assertEquals(test.getSampleList().size(), 2);
    }

    @Test
    public void addSampleWrong() throws OutputException, BarcodeException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        test.addSample(1);
        Assert.assertNotEquals(test.getSampleList().size(), 2);
    }

    @Test
    public void addSampleWrongException() throws OutputException, BarcodeException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        exceptionRule.expect(IllegalAccessError.class);
        exceptionRule.expectMessage("You cannot add samples to a test that isn't in registered state.");

        test.addSample(1);
        test.addTestResult("HB000", "cheiro fétido", "g/m3");
        test.validateParameterResult();
        test.addSample(1);
        Assert.assertNotEquals(test.getSampleList().size(), 2);
    }

    @Test
    public void addReport() throws OutputException, BarcodeException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        test.addSample(1);
        test.addTestResult("HB000", "cheiro fétido", "g/m3");
        test.validateParameterResult();
        Report report = new Report("result", "report");
        test.addReport(report);
        Assert.assertEquals(test.getReport(), report);

    }

    @Test
    public void addReportException() throws OutputException, BarcodeException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        exceptionRule.expect(IllegalAccessError.class);
        exceptionRule.expectMessage("You cannot add a report to a test that isn't in samples analysed state.");

        test.addReport(new Report("result", "report"));
        Assert.assertTrue(test.getReport().equals(new Report("result", "report")));

    }


    @Test
    public void getTestType() {
        Assert.assertEquals(test.getTestType(), testType);
    }

    @Test
    public void getParameterList() {
        Assert.assertEquals(test.getParameterList(), parameterList);
    }

    @Test
    public void getParameterListWrong() {
        Parameter p = new Parameter("12322", "cheiedor", new ParameterCategory("11111", "ola"), "asdasd");
        List<Parameter> p2 = new ArrayList<>();
        p2.add(p);
        Assert.assertNotEquals(test.getParameterList(), p2);
    }

    @Test
    public void getTestTypeWrong() {
        TestType testType1 = new TestType("11160", "12", "12", parameterCategoryList);
        Assert.assertNotEquals(test.getTestType(), testType1);
    }

    @Test
    public void addTestResultException() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("The code provided does not match to any parameter in this test.");
        test.addTestResult("CHEIRO", "pivete", "g/l");
    }

    @Test
    public void getNhsCode() {
        Assert.assertEquals(test.getNhsCode(), "123456789012");
    }

    @Test
    public void getNhsCodeWrong() {
        Assert.assertNotEquals(test.getNhsCode(), "123456789011");
    }

    @Test
    public void isRegistered() {
        assertTrue(test.isRegistered());
    }

    @Test
    public void isRegisteredFalse() {
        Assert.assertNotEquals(test.isRegistered(), false);
    }

    @Test
    public void isSamplesCollected() throws OutputException, BarcodeException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        test.addSample(2);
        Assert.assertEquals(test.isSamplesCollected(), true);
    }

    @Test
    public void isSamplesCollectedWrong() throws OutputException, BarcodeException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Assert.assertEquals(test.isSamplesCollected(), false);
    }

    @Test
    public void isSamplesAnalysed() {
    }

    @Test
    public void isDiagnosisMade() {
    }

    @Test
    public void isValidated() {
        test.validateTest();
        assertTrue(test.isValidated());
    }

    @Test
    public void isNotValidated() throws OutputException, BarcodeException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        test.addSample(2);
        assertFalse(test.isValidated());
    }

    @Test
    public void testEqualsTestTypeDifferent() {
        parameterList.add(new Parameter("HB000", "HB", new ParameterCategory("he001", "Hemogram"), "Botar sangue"));
        parameterCategoryList.add(new ParameterCategory("he001", "Hemogram"));
        testType = new TestType("12378", "Blood", "cheirete", parameterCategoryList);
        List<TestType> testTypeList = new ArrayList<>();
        testTypeList.add(testType);
        lab = new Laboratory("lab1", "12345", "address", "12345678901", "1234567890", testTypeList);
        app.domain.model.Test test2 = new app.domain.model.Test(testType, parameterList, "123456789012", lab, "1234567890");

        Assert.assertNotEquals(test.equals(test2), true);
    }

    @Test
    public void testEqualsParameterDifferent() {
        parameterList.add(new Parameter("HB001", "HB", new ParameterCategory("he001", "Hemogram"), "Botar sangue"));
        parameterCategoryList.add(new ParameterCategory("he001", "Hemogram"));
        testType = new TestType("12345", "Blood", "cheirete", parameterCategoryList);
        List<TestType> testTypeList = new ArrayList<>();
        testTypeList.add(testType);
        lab = new Laboratory("lab1", "12345", "address", "12345678901", "1234567890", testTypeList);
        app.domain.model.Test test2 = new app.domain.model.Test(testType, parameterList, "123456789012", lab, "1234567890");

        Assert.assertNotEquals(test.equals(test2), true);
    }

    @Test
    public void testEqualsParameterCategoryDifferent() {
        parameterList.add(new Parameter("HB000", "HB", new ParameterCategory("he001", "Hemogram"), "Botar sangue"));
        parameterCategoryList.add(new ParameterCategory("hi001", "Hemogram"));
        testType = new TestType("12345", "Blood", "cheirete", parameterCategoryList);
        List<TestType> testTypeList = new ArrayList<>();
        testTypeList.add(testType);
        lab = new Laboratory("lab1", "12345", "address", "12345678901", "1234567890", testTypeList);
        app.domain.model.Test test2 = new app.domain.model.Test(testType, parameterList, "123456789012", lab, "1234567890");

        Assert.assertNotEquals(test.equals(test2), true);
    }

    @Test
    public void testEqualsLabDifferent() {
        parameterList.add(new Parameter("HB000", "HB", new ParameterCategory("he001", "Hemogram"), "Botar sangue"));
        parameterCategoryList.add(new ParameterCategory("he001", "Hemogram"));
        testType = new TestType("12345", "Blood", "cheirete", parameterCategoryList);
        List<TestType> testTypeList = new ArrayList<>();
        testTypeList.add(testType);
        lab = new Laboratory("labtu", "12345", "address", "12345678901", "1234567890", testTypeList);
        app.domain.model.Test test2 = new app.domain.model.Test(testType, parameterList, "123456789012", lab, "1234567890");

        Assert.assertNotEquals(test.equals(test2), true);
    }

    @Test
    public void testEqualsNHSDifferent() {
        parameterList.add(new Parameter("HB000", "HB", new ParameterCategory("he001", "Hemogram"), "Botar sangue"));
        parameterCategoryList.add(new ParameterCategory("he001", "Hemogram"));
        testType = new TestType("12345", "Blood", "cheirete", parameterCategoryList);
        List<TestType> testTypeList = new ArrayList<>();
        testTypeList.add(testType);
        lab = new Laboratory("lab1", "12345", "address", "12345678901", "1234567890", testTypeList);
        app.domain.model.Test test2 = new app.domain.model.Test(testType, parameterList, "123456789011", lab, "1234567890");

        Assert.assertNotEquals(test.equals(test2), true);
    }

    @Test
    public void testEqualsTinDifferent() throws ParseException {
        parameterList.add(new Parameter("HB000", "HB", new ParameterCategory("he001", "Hemogram"), "Botar sangue"));
        parameterCategoryList.add(new ParameterCategory("he001", "Hemogram"));
        testType = new TestType("12345", "Blood", "cheirete", parameterCategoryList);
        List<TestType> testTypeList = new ArrayList<>();
        testTypeList.add(testType);
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = df.parse("23-12-1998");
        clientStore.getClients().add(new Client("1234567890123456", "1234567890", 1233567890, date, "Male", "12345678910", "Miguel", "miguel@ipp.pt", "Rua Fixe"));
        lab = new Laboratory("lab1", "12345", "address", "12345678901", "1234567890", testTypeList);
        app.domain.model.Test test2 = new app.domain.model.Test(testType, parameterList, "123456789012", lab, "1233567890");
        Assert.assertNotEquals(test.equals(test2), true);
    }


}