package app.mappers.dto;

import app.controller.App;
import app.domain.model.*;
import app.domain.store.LaboratoryStore;
import app.mappers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestDTO {
    private Company company;
    private TestTypeDTO testTypeDTO;
    private List<ParameterDTO> parameterDTOS;
    private String nhsCode;
    private final String testCode;
    private String testCreationDate;
    private final LaboratoryMapper laboratoryMapper = new LaboratoryMapper();
    private final ParameterMapper parameterMapper = new ParameterMapper();
    private final TestTypeMapper testTypeMapper = new TestTypeMapper();
    private ReportMapper reportMapper;
    private LaboratoryDTO laboratory;
    private String validationDate;
    private String reportMadeDate;
    private String samplesAnalysedDate;
    private Report report;

    private List<TestParameter> testParameterList = new ArrayList<>();

    /**
     * Constructor
     * @param testTypeDTO
     * @param parameterDTOS
     * @param test
     */
    public TestDTO(TestTypeDTO testTypeDTO, List<ParameterDTO> parameterDTOS, Test test) {
        company = App.getInstance().getCompany();
        this.reportMapper = new ReportMapper();
        this.testTypeDTO = testTypeDTO;
        this.parameterDTOS = parameterDTOS;
        this.nhsCode = test.getNhsCode();
        this.testCode = test.getTestCode();
        this.report = test.getReport();
        this.reportMadeDate = test.getReportMadeDate();
        this.samplesAnalysedDate = test.getSamplesAnalysedDate();
        this.laboratory = laboratoryMapper.toDTO(test.getLaboratory());
        validationDate = test.getValidationDate();
        testCreationDate = test.getCreationDate();
        testParameterList = test.getTestParameterList();
    }

    public TestDTO(Test test){
        company = App.getInstance().getCompany();
        this.testCode = test.getTestCode();
        this.nhsCode = test.getNhsCode();
        this.laboratory = laboratoryMapper.toDTO(test.getLaboratory());
        this.parameterDTOS = parameterMapper.toDTO(test.getParameterList());
        this.testTypeDTO = testTypeMapper.toDTO(test.getTestType());
        this.samplesAnalysedDate = test.getSamplesAnalysedDate();
        testCreationDate = test.getCreationDate();
        reportMadeDate = test.getReportMadeDate();
        validationDate = test.getValidationDate();
    }

    public TestDTO(String testCode, String testCreationDate, String samplesAnalysedDate, String reportMadeDate, String validationDate){
        this.testCode = testCode;
        this.testCreationDate = testCreationDate;
        this.samplesAnalysedDate = samplesAnalysedDate;
        this.reportMadeDate = reportMadeDate;
        this.validationDate = validationDate;
    }

    /**
     * This method returns the NHS code.
     * @return nhsCode
     */
    public String getNhsCode() {
        return nhsCode;
    }

    /**
     * This method returns the testCode
     * @return testCode
     */
    public String getTestCode() {
        return testCode;
    }

    /**
     * This method returns a list with all the parameters
     * @return parameterDTOS
     */
    public List<ParameterDTO> getParameterDTO() {
        return parameterDTOS;
    }

    /**
     * This method returns the creation date of the test.
     * @return creation date of the test.
     */
    public String getTestCreationDate() {
        return testCreationDate;
    }

    /**
     * This method returns the date when the report was made.
     * @return reportMadeDate
     */
    public String getReportMadeDate() {
        return reportMadeDate;
    }

    /**
     * This method returns the date when the validation was done.
     * @return
     */
    public String getValidationDate() {
        return validationDate;
    }

    /**
     * This method returns the date when samples were analysed
     * @return samplesAnalysedDate
     */
    public String getSamplesAnalysedDate() {
        return samplesAnalysedDate;
    }

    /**
     * This method returns a TestParameter for a given Parameter Code
     * @param parameterCode
     * @return
     */
    public TestParameter getTestParameterFor(String parameterCode){
        for(TestParameter p : testParameterList){
            if(p.getParameter().getCode().equals(parameterCode)){
                return p;
            }
        }
        throw new IllegalArgumentException("The code provided does not match to any parameter in this test."); //this will never be executed =D
    }

    /**
     * This method returns the laboratory
     * @return
     */
    public LaboratoryDTO getLaboratory() {
        return laboratory;
    }

    /**
     * This method returns a String with all the information of a specific test
     * @return
     */
    public String toString() {
        return "TestDTO{" +
                "testTypeDTO=" + testTypeDTO.getDescription() +
                ", parameterDTOS=" + parameterDTOS +
                ", nhsCode='" + nhsCode + '\'' +
                ", testCode='" + testCode + '\'' +
                ", testCreationDate='" + testCreationDate + '\'' +
                '}';
    }
}