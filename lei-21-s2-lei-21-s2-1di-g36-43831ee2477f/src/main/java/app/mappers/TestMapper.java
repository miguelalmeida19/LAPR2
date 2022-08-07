package app.mappers;

import app.controller.App;
import app.domain.model.Company;
import app.domain.model.Test;
import app.domain.store.TestStore;
import app.mappers.dto.ParameterDTO;
import app.mappers.dto.TestDTO;
import app.mappers.dto.TestTypeDTO;

import java.util.ArrayList;
import java.util.List;

public class TestMapper {

    private final TestStore testStore;
    private final Company company;
    private final TestTypeMapper testTypeMapper;
    private final ParameterMapper parameterMapper;

    /**
     * Constructor
     */
    public TestMapper(){
        testTypeMapper = new TestTypeMapper();
        parameterMapper = new ParameterMapper();
        company = App.getInstance().getCompany();
        testStore = company.getTestStore();
    }

    /**
     * This method converts a Test to a TestDTO
     * @param test
     * @return
     */
    public TestDTO toDTO(Test test){
        TestTypeDTO testTypeDTO = testTypeMapper.toDTO(test.getTestType());
        List<ParameterDTO> parameterDTOList = parameterMapper.toDTO(test.getParameterList());
        return new TestDTO(testTypeDTO, parameterDTOList, test);
    }

    /**
     * This method converts a Test List to a TestDTO List
     * @param testList
     * @return
     */
    public List<TestDTO> toDTO(List<Test> testList){
        List<TestDTO> testDTOList = new ArrayList<>();
        for (Test t: testList){
            testDTOList.add(toDTO(t));
        }
        return testDTOList;
    }

    /**
     * This method converts a TestDTO to Test
     * @param testDTO
     * @return
     */
    public Test toTest(TestDTO testDTO){
        for (Test test : testStore.getTestList()){
            if(test.getTestCode().equals(testDTO.getTestCode())){
                return test;
            }

        }
        throw new IllegalArgumentException("Test DTO does not match to any test in memory.");
    }

    public TestDTO toDTOParam(Test test){
        return new TestDTO(test);
    }

    public List<TestDTO> toDTOParam(List<Test> testList){
        List<TestDTO> testDTOList = new ArrayList<>();
        for (Test t: testList){
            testDTOList.add(toDTOParam(t));
        }
        return testDTOList;
    }
}
