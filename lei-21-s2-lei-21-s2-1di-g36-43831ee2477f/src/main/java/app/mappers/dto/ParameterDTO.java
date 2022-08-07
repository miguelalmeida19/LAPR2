package app.mappers.dto;

import app.domain.model.Parameter;
import app.domain.model.ParameterCategory;

public class ParameterDTO {
    private final String code;
    private final String name;
    private final ParameterCategory category;
    private final String description;

    /**
     * Constructor
     * @param parameter
     */
    public ParameterDTO(Parameter parameter){
        this.code = parameter.getCode();
        this.name = parameter.getName();
        this.category = parameter.getCategory();
        this.description = parameter.getDescription();
    }

    /**
     * This method returns the code of a parameter
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * This method returns the description of a parameter
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * this method returns the name of the parameter
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * this method returns the category of the parameter
     * @return category
     */
    public ParameterCategory getCategory() {
        return category;
    }

    /**
     * Returns a string with all information about parameter
     * @return
     */
    @Override
    public String toString() {
        return "ParameterDTO{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", category=" + category.getName() +
                ", description='" + description + '\'' +
                '}';
    }
}
