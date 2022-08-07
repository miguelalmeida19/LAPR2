package app.mappers.dto;

import app.domain.model.ParameterCategory;

public class ParameterCategoryDTO {
    private final String code;
    private final String name;

    /**
     * Constructor
     * @param parameterCategory
     */
    public ParameterCategoryDTO(ParameterCategory parameterCategory){
        code = parameterCategory.getCode();
        name = parameterCategory.getName();
    }

    /**
     * This method returns the code of Parameter Category
     * @return
     */
    public String getCode() {
        return code;
    }

    /**
     * This method returns the name of a Parameter Category
     * @return
     */
    public String getname() {
        return name;
    }
}
