package app.controller;

import app.domain.model.Company;
import app.domain.model.Test;
import app.domain.store.TestStore;
import app.mappers.TestMapper;
import app.mappers.dto.TestDTO;

import java.io.IOException;

public class RecordTestResultsController {
    private Company company;
    private TestStore store;
    private Test test;
    private final TestMapper testMapper;

    public RecordTestResultsController() {
        this(App.getInstance().getCompany());
    }

    public RecordTestResultsController(Company company) {
        this.company = company;
        store = company.getTestStore();
        testMapper = new TestMapper();
    }

    public void addTestResult(TestDTO testDTO, String parameterCode, String result, String metric) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Test test = testMapper.toTest(testDTO);
        test.addTestResult(parameterCode,result,metric);
        this.test = test;
    }

    /**
     * This method will check if all parameters have a result
     */
    public void validateParameters(TestDTO test){
        testMapper.toTest(test).validateParameterResult();
    }

    public TestDTO getTestByBarcode(String barcode){
        try {
            Test test = store.getTestByBarcode(barcode);
            if(test.isSamplesCollected()){
                TestMapper mapper = new TestMapper();
                return mapper.toDTO(test);
            }
        }
        catch (Exception e){
            throw new IllegalArgumentException("The code introduces is does not match to a test waiting for results.");
        }
        return null;
    }
}
