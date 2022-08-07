package app.mappers;

import app.controller.App;
import app.domain.model.Company;
import app.domain.model.TestType;
import app.domain.store.TestTypeStore;
import app.mappers.dto.TestDTO;
import app.mappers.dto.TestTypeDTO;

import java.util.ArrayList;
import java.util.List;

public class TestTypeMapper {
    private final Company company;
    private final TestTypeStore testTypeStore;

    /**
     * Constructor
     */
    public TestTypeMapper(){
        this.company = App.getInstance().getCompany();
        this.testTypeStore = company.getTestTypeStore();
    }
    /**
     * Method to create a list of DTOs with
     * @param testsList
     * @return
     */
    public List<TestTypeDTO> toDTO(List<TestType> testsList){
        List<TestTypeDTO> listDTOs = new ArrayList<>();
        for(TestType t : testsList){
            listDTOs.add(toDTO(t));
        }
        return listDTOs;
    }

    /**
     * This method converts a type of test to DTO
     * @param testType
     * @return
     */
    public TestTypeDTO toDTO(TestType testType){
        return new TestTypeDTO(testType);
    }

    /**
     *This method converts a type of test to DTO
     * @param test
     * @return
     */
    public TestType toTestType(TestTypeDTO test){
        for(TestType t : testTypeStore.getListOfTestType()){
            if(t.getCode().equals(test.getCode())){
                return t;
            }
        }
        return null;
    }
}
