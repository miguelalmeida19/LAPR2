package app.mappers;

import app.controller.App;
import app.domain.model.TestParameterResult;
import app.domain.store.TestStore;
import app.mappers.dto.TestParameterResultDTO;

import java.util.ArrayList;
import java.util.List;

public class ParameterResultMapper {
    private final TestStore testStore;

    /**
     * Constructor
     */
    public ParameterResultMapper() {
        testStore = App.getInstance().getCompany().getTestStore();
    }

    /**
     * This method converts a list of test parameter results to DTO
     * @param testParameterResultList
     * @return
     */
    public List<TestParameterResultDTO> toDTO(List<TestParameterResult> testParameterResultList){
        List<TestParameterResultDTO> listDTOs = new ArrayList<>();
        for(TestParameterResult t : testParameterResultList){
            listDTOs.add(toDTO(t));
        }
        return listDTOs;
    }

    /**
     * This method converts a  parameter result to DTO
     * @param testParameterResult
     * @return
     */
    public TestParameterResultDTO toDTO(TestParameterResult testParameterResult){
        return new TestParameterResultDTO(testParameterResult);
    }

    /**
     * This method converts a  parameterResultDTO to parameter result
     * @param testParameterResult
     * @return
     */
    public TestParameterResult toTest(TestParameterResultDTO testParameterResult){
        return new TestParameterResult(testParameterResult.getResult(), testParameterResult.getMetric(), testParameterResult.getRefValue());
    }
}
