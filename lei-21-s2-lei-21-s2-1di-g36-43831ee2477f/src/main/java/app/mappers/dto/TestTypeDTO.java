package app.mappers.dto;

import app.domain.model.ParameterCategory;
import app.domain.model.TestType;
import app.mappers.ParameterCategoryMapper;

import java.util.List;

public class TestTypeDTO {
    private final String code;
    private final String description;
    private final String collectingMethod;
    private final List<ParameterCategoryDTO> categoriesList;

    /**
     * Constructor
     * @param testType test type
     */
    public TestTypeDTO(TestType testType){
        ParameterCategoryMapper mapper = new ParameterCategoryMapper();
        this.code = testType.getCode();
        this.categoriesList = mapper.toDTO(testType.getCategoriesList());
        this.description = testType.getDescription();
        this.collectingMethod = testType.getCollectingMethod();
    }

    /**
     * returns the category list of the test Type
     * @return category list
     */
    public List<ParameterCategoryDTO> getCategoriesList() {
        return categoriesList;
    }

    /**
     * returns the description of the test Type
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * returns the code of the test Type
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * returns the collecting method of the test Type
     * @return collecting method
     */
    public String getCollectingMethod() {
        return collectingMethod;
    }
}
