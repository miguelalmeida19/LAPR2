package app.domain.model;

import app.controller.App;
import app.domain.store.ClientStore;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.output.OutputException;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

public class Test implements Serializable {

    private static double counter = 1.0;
    private final String TINOfClient;

    private final List<TestParameter> testParameterList = new ArrayList<>();
    private final TestType testType;
    private final String nhsCode;
    private String testCode;
    private Report report;
    private Laboratory laboratory;
    private final List<Sample> sampleList = new ArrayList<>();

    private List<String> testCodesList = new ArrayList<>();

    private String validationDate;
    private String reportMadeDate;
    private String samplesAnalysedDate;
    private String creationDate;
    private String status;

    /**
     * Constructor
     *
     * @param testType      test type.
     * @param parameterList parameter list.
     * @param nhsCode       nhs code.
     */
    public Test(TestType testType, List<Parameter> parameterList, String nhsCode, Laboratory laboratory, String TINOfClient) {
        resetCodeNumber(); // code cannot have more than 12 digits
        this.testType = testType;
        this.nhsCode = nhsCode;
        this.testCode = generateCode();
        checkTestCode(testCode);
        this.laboratory = laboratory;
        counter++;
        checkTin(TINOfClient);
        this.TINOfClient = TINOfClient;
        createTestParameter(parameterList);
        checkNhsCode(nhsCode);

        creationDate = getDate();
        this.status = "REGISTERED";
    }

    public void setSamplesAnalysedDate(String samplesAnalysedDate) {
        this.samplesAnalysedDate = samplesAnalysedDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public void setValidationDate(String validationDate) {
        this.validationDate = validationDate;
    }

    public void setReportMadeDate(String reportMadeDate) {
        this.reportMadeDate = reportMadeDate;
    }

    /**
     * Validates the result. this method verifies if all parameters have a result.
     */
    public void validateParameterResult() {
        for (TestParameter t : testParameterList) {
            if (t.getTestParameterResul() == null) {
                throw new NullPointerException("There is one or more parameters that have no result.");
            }
        }
        status = "SAMPLES ANALYSED";
        samplesAnalysedDate = getDate();
    }

    /**
     * This method changes the status of the test after its validation.
     */
    public void validateTest() {
        status = "TEST VALIDATED";
        validationDate = getDate();
    }

    public void validateTestState() {
        status = "TEST VALIDATED";
    }

    /**
     * Validates the report
     *
     * @param report report
     */
    public void validateReport(Report report) {
        if (report.getReport().split(" ").length + report.getdiagnosis().split(" ").length > 400) {
            throw new IllegalArgumentException("This report is invalid, you entered too many words (Max 400 words)");
        }
    }

    /**
     * This method returns a list with parameters from testParameters
     *
     * @return
     */
    private List<Parameter> getParameterListFomTestParameterList() {
        List<Parameter> parameterList = new ArrayList<>();
        for (TestParameter p : testParameterList) {
            parameterList.add(p.getParameter());
        }
        return parameterList;
    }

    /**
     * get the test parameter list.
     *
     * @return test parameter list.
     */
    public List<TestParameter> getTestParameterList() {
        return testParameterList;
    }

    /**
     * This method creates a TestParameter
     *
     * @param parameterList
     */
    private void createTestParameter(List<Parameter> parameterList) {
        for (Parameter p : parameterList) {
            this.testParameterList.add(new TestParameter(p));
        }
    }

    //  CODE GENERATION AND CONTROLLING (SIZE)

    /**
     * This method generates the barcode
     *
     * @return
     */
    public String generateCode() {
        StringBuilder finalString = new StringBuilder();
        for (int n = String.valueOf(String.format("%.0f", counter)).length(); n < 12; n++) {
            finalString.append("0");
        }
        testCodesList.add(finalString + String.format("%.0f", Double.valueOf(String.valueOf(counter))));
        return finalString + String.format("%.0f", Double.valueOf(String.valueOf(counter)));

    }

    /**
     * This method returns the barcode number
     */
    private void resetCodeNumber() {
        if (String.valueOf(String.valueOf(String.format("%.0f", counter)).length()).equals("999999999999")) {
            counter = 1.0;
        }
    }

    // ---------------------------------------------

    // VALIDATIONS

    /**
     * Verifies if the code is right.
     *
     * @param nhsCode code
     */
    private void checkNhsCode(String nhsCode) {
        if (nhsCode.length() != 12) {
            throw new IllegalArgumentException("The size of NHS code must be a code with 12 digits.");
        }
    }

    private void checkTestCode(String testCode) {
        if (testCodesList.contains(testCode)) {
            testCode = generateCode();
        }
    }

    private void checkTin(String tin) {
        ClientStore clientStore = App.getInstance().getCompany().getClientStore();
        List<String> tinsList = new ArrayList<>();
        for (Client client : clientStore.getClients()) {
            tinsList.add(String.valueOf(client.getTin()));
        }
        if (!tinsList.contains(tin)){
            throw new IllegalArgumentException("Provided tin does not match to any client in System, please register the client first");
        }
}

    /**
     * This method gets the tin of a client
     *
     * @return TINOfClient
     */
    public String getTINOfClient() {
        return TINOfClient;
    }

    /**
     * This method gets the date when it is called
     *
     * @return date
     */
    private String getDate() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.UK);
        return dateFormat.format(date);
    }

    /**
     * Gets the report.
     *
     * @return report.
     */
    public Report getReport() {
        return report;
    }

    /**
     * This method returns the date when the samples were analysed
     *
     * @return samplesAnalysedDate
     */
    public String getSamplesAnalysedDate() {
        return samplesAnalysedDate;
    }

    /**
     * This method returns the date when the validation was done
     *
     * @return
     */
    public String getValidationDate() {
        return validationDate;
    }

    /**
     * This method returns the date when the report was made
     *
     * @return reportMadeDate
     */
    public String getReportMadeDate() {
        return reportMadeDate;
    }

    /**
     * This method gets the labpratory
     *
     * @return laboratory
     */
    public Laboratory getLaboratory() {
        return laboratory;
    }

    /**
     * This method adds a sample to the sample list
     *
     * @param numberBarcodes
     * @throws OutputException
     * @throws BarcodeException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public void addSample(int numberBarcodes) throws OutputException, BarcodeException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        if (!isRegistered()) {
            throw new IllegalAccessError("You cannot add samples to a test that isn't in registered state.");
        }
        for (int n = 0; n < numberBarcodes; n++) {
            Sample sample = new Sample(getDate());
            sampleList.add(sample);
        }

        status = "SAMPLES COLLECTED";

    }

    /**
     * This method saves a report and changes status to "DIAGNOSIS MADE"
     *
     * @param report
     */
    public void addReport(Report report) {
        if (!isSamplesAnalysed()) {
            throw new IllegalAccessError("You cannot add a report to a test that isn't in samples analysed state.");
        } else {
            this.report = report;
            status = "DIAGNOSIS MADE";
            reportMadeDate = getDate();
        }
    }

    /**
     * get the sample list.
     *
     * @return sample list
     */
    public List<Sample> getSampleList() {
        return sampleList;
    }

    /**
     * get the creation date of the test.
     *
     * @return creation date of the test.
     */
    public String getCreationDate() {
        return creationDate;
    }

    /**
     * Get the test code
     *
     * @return test code.
     */
    public String getTestCode() {
        return testCode;
    }

    /**
     * Get the test type.
     *
     * @return test type.
     */
    public TestType getTestType() {
        return testType;
    }

    /**
     * get the parameter list
     *
     * @return parameter list
     */
    public List<Parameter> getParameterList() {
        return getParameterListFomTestParameterList();
    }

    /**
     * adds the test result.
     *
     * @param parameterCode parameter code
     * @param result        result
     * @param metric        metric
     */
    public void addTestResult(String parameterCode, String result, String metric) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        TestParameter testParameter = getTestParameterFor(parameterCode);
        testParameter.addResult(result, metric, testType.getReferenceValues(testParameter));
    }

    /**
     * This method returns a TestParameter for a given Parameter Code
     *
     * @param parameterCode
     * @return
     */
    private TestParameter getTestParameterFor(String parameterCode) {
        for (TestParameter p : testParameterList) {
            if (p.getParameter().getCode().equals(parameterCode)) {
                return p;
            }
        }
        throw new IllegalArgumentException("The code provided does not match to any parameter in this test."); //this will never be executed =D
    }

    /**
     * Get the nhs code.
     *
     * @return nhs code.
     */
    public String getNhsCode() {
        return nhsCode;
    }

    /**
     * checks if the test in registered state
     *
     * @return true/false
     */
    public boolean isRegistered() {
        return status.equals("REGISTERED");
    }

    /**
     * checks if the test in 'samples colected' state
     *
     * @return true/false
     */
    public boolean isSamplesCollected() {
        return status.equals("SAMPLES COLLECTED");
    }

    /**
     * checks if the test in "samples analysed" state
     *
     * @return true/false
     */
    public boolean isSamplesAnalysed() {
        return status.equals("SAMPLES ANALYSED");
    }

    /**
     * checks if the test in "diagnosis made" state
     *
     * @return true/false
     */
    public boolean isDiagnosisMade() {
        return status.equals("DIAGNOSIS MADE");
    }

    /**
     * checks if the test in "test validated" state
     *
     * @return true/false
     */
    public boolean isValidated() {
        return status.equals("TEST VALIDATED");
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    /**
     * This method checks if a test is equal to another
     *
     * @param o object
     * @return is equals?
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Test test = (Test) o;
        return TINOfClient.equals(test.TINOfClient) && nhsCode.equals(test.nhsCode) && testCode.equals(test.testCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(TINOfClient, nhsCode, testCode);
    }
}