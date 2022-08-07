package app.controller;

import app.domain.model.Company;
import app.domain.model.Test;
import app.domain.store.TestStore;
import app.mappers.ParameterResultMapper;
import app.mappers.TestMapper;
import app.mappers.dto.ParameterDTO;
import app.mappers.dto.TestDTO;
import app.ui.console.utils.EmailNotificationSender;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ValidateTestController {
    private Company company;
    private TestStore store;
    private TestMapper testMapper = new TestMapper();
    private ParameterResultMapper parameterResultMapper = new ParameterResultMapper();

    /**
     * Constructor
     */
    public ValidateTestController(){
        this(App.getInstance().getCompany());
    }

    /**
     * Constructor defining Company and store
     * @param company
     */
    public ValidateTestController(Company company){
        this.company = company;
        store = company.getTestStore();
    }

    /**
     * This method returns the tests that are Available to validate
     * @return list of tests
     */
    public List<TestDTO> getTestsAvailable(){
        List<TestDTO> testDTOList = new ArrayList<>();
        for (Test test: store.getTestList()){
            if (test.isDiagnosisMade()){
                testDTOList.add(testMapper.toDTO(test));
            }
        }
        return  testDTOList;
    }

    /**
     * This method validates a given Test
     * @param testDTO
     */
    public void validateTest(TestDTO testDTO) throws IOException {
        Test test = testMapper.toTest(testDTO);
        test.validateTest();
        EmailNotificationSender notificationSender = new EmailNotificationSender();
        notificationSender.sendTestDoneNotification();
    }

    /**
     * This method returns the Parameter Result Value for a specific parameter
     * @param testDTO
     * @param p
     * @return
     */
    public String getParameterResultValue(TestDTO testDTO, ParameterDTO p){
        return parameterResultMapper.toDTO(testDTO.getTestParameterFor(p.getCode()).getTestParameterResul()).getResult();
    }
}