package app.controller;

import app.domain.model.*;
import app.domain.store.*;
import app.mappers.LaboratoryMapper;
import app.mappers.ParameterCategoryMapper;
import app.mappers.ParameterMapper;
import app.mappers.TestTypeMapper;
import app.mappers.dto.*;

import java.util.List;

public class RegisterTestController {

    private final ClientStore clientStore;
    private final Company company;
    private final ParameterMapper parameterMapper;
    private final TestTypeMapper testTypeMapper;
    private final LaboratoryMapper laboratoryMapper;
    private final ParameterCategoryMapper parameterCategoryMapper;
    private final TestStore testStore;
    private final ParameterCategoryStore parameterCategoryStore;
    private Test test;
    /**
     * Constructor
     */
    public RegisterTestController(){
        this(App.getInstance().getCompany());
    }

    /**
     * Constructor
     * @param company Company
     */
    public RegisterTestController(Company company) {
        this.company = company;
        this.parameterMapper = new ParameterMapper();
        this.testTypeMapper = new TestTypeMapper();
        this.laboratoryMapper = new LaboratoryMapper();
        this.testStore = company.getTestStore();
        parameterCategoryMapper = new ParameterCategoryMapper();
        parameterCategoryStore = company.getParameterCategoryStore();
        clientStore = company.getClientStore();
    }

    /**
     * Bridge between UI and test store. cheks if the client has already a test registered in the system
     * @param TIN tin of client
     */
    public void  checkIfClientHasAlreadyTestInSystem(String TIN){
        testStore.checkIfClientHasAlreadyTestInSystem(TIN);
    }
    /**
     * Get the list of test type.
     * @return list of test type.
     */
    public List<TestTypeDTO> getlistOfTestType(){
        TestTypeStore testTypeStore = company.getTestTypeStore();

        return testTypeMapper.toDTO(testTypeStore.getListOfTestType());
    }

    /**
     * Get the list of parameters dto from a list of parameter categories.
     * @param parameterCategoryDTOList list of parameter categories.
     * @return ist of parameters.
     */
    public List<ParameterDTO> getlistOfParameters(List<ParameterCategoryDTO> parameterCategoryDTOList){
        ParameterStore parameterStore = company.getParameterStore();
        return parameterMapper.toDTO(parameterStore.getListOfParametersByCategoryList(parameterCategoryMapper.toParameterCategoryList(parameterCategoryDTOList)));
    }

    /**
     * Register the test.
     * @param registerTestDTO register test dto with all data needed.
     */
    public void registerTest(RegisterTestDTO registerTestDTO){
        test = testStore.createTest(testTypeMapper.toTestType(registerTestDTO.getTestType()), parameterMapper.toParameterList(registerTestDTO.getParameterDTOS()), registerTestDTO.getNhsCode(), laboratoryMapper.toLaboratory(registerTestDTO.getLaboratoryDTO()),registerTestDTO.getTIN());
        testStore.validateTest(test);
    }

    /**
     * Gets the client by TIN. bridge between UI and store.
     * @param TIN tin
     * @return client dto
     */
    public ClientDTO getClientByTin(String TIN){
        return clientStore.getClientByTin(TIN);
    }

    /**
     * Get the list of parameter categories in dto.
     * @return list of parameter category dto
     */
    public List<ParameterCategoryDTO> getListOfParameterCategories(){
        List<ParameterCategory> parameterCategoryList = parameterCategoryStore.getParameterCategoryList();
        return parameterCategoryMapper.toDTO(parameterCategoryList);
    }

    /**
     * Saves the test.
     */
    public void saveTest(){
        testStore.saveTest(test);
    }
}
