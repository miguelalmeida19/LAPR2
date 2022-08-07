package app.controller;

import app.domain.model.Company;
import app.domain.model.Report;
import app.domain.model.Test;
import app.domain.store.TestStore;
import app.mappers.ParameterResultMapper;
import app.mappers.TestMapper;
import app.mappers.dto.ParameterDTO;
import app.mappers.dto.TestDTO;
import app.mappers.dto.TestParameterResultDTO;

import java.util.ArrayList;
import java.util.List;

public class WriteReportController {
    private Company company;
    private TestStore store;
    private ParameterResultMapper parameterResultMapper = new ParameterResultMapper();
    private TestMapper testMapper = new TestMapper();

    /**
     * Constructor
     */
    public WriteReportController(){
        this(App.getInstance().getCompany());
    }

    /**
     * Constructor defining company and store
     * @param company
     */
    public WriteReportController(Company company){
        this.company = company;
        store = company.getTestStore();
    }

    /**
     * This method returns the tests that are Available to write report
     * @return list of tests
     */
    public List<TestDTO> getTestListDTO() {
        List<TestDTO> testDTOList = new ArrayList<>();
        List<Test> listTest = store.getTestList();
        TestMapper mapper = new TestMapper();
        for (Test t : listTest) {
            if (t.isSamplesAnalysed()) {
                testDTOList.add(mapper.toDTO(t));
            }
        }
        return testDTOList;
    }

    /**
     * This method returns a test parameter result from a testDTO and a given ParameterDTO
     * @return TestParameterResult
     */
    public TestParameterResultDTO getTestParameterResult(TestDTO testDTO, ParameterDTO parameterDTO){
        return parameterResultMapper.toDTO(testDTO.getTestParameterFor(parameterDTO.getCode()).getTestParameterResul());
    }

    /**
     * this method creates a report
     * @param report
     * @param diagnosis
     * @param testDTO
     */
    public void createReport(String report, String diagnosis, TestDTO testDTO){
        Report fullReport = new Report(report, diagnosis);
        Test test = testMapper.toTest(testDTO);
        test.validateReport(fullReport);
        test.addReport(fullReport);
    }
}
