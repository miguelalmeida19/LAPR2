package app.domain.model;

import java.io.Serializable;
import java.util.Objects;

public class Parameter  implements Serializable {

    private final String code;
    private final String name;
    private final ParameterCategory category;
    private final String description;

    /**
     * Constructor
     * @param code code of parameter
     * @param name name of parameter
     * @param category category of parameter
     * @param description description of parameter
     */
    public Parameter(String code, String name, ParameterCategory category, String description ){
        checkName(name);
        checkCode(code);
        checkDescription(description);
        this.code = code;
        this.name = name;
        this.category = category;
        this.description = description;
    }

    public ParameterCategory getCategory() {
        return category;
    }

    /**
     * Check if the name is valid. throws an exception if the name has a problem,
     * @param name name to check
     */
    public void checkName(String name){
        if(name.length() > 8){
            throw new IllegalArgumentException("The name of parameter cannot have more than 8 characters.");
        }
    }

    /**
     * Check if the description is valid. throws an exception if the description has a problem,
     * @param description
     */
    public void checkDescription(String description){
        if (description.length()>20){
            throw new IllegalArgumentException("The description of parameter cannot have more than 20 characters.");
        }
    }

    /**
     * Check if the code is valid. throws an exception if the code has a problem,
     * @param code
     */
    public void checkCode(String code){
        if( !(code.length() == 5)){
            throw new IllegalArgumentException("The code of parameter must have 5 characters.");
        }
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parameter parameter;
        parameter = (Parameter) o;
        return this.code.equals(parameter.code) && this.name.equals(parameter.name) &&this.category.equals(parameter.category) && this.description.equals(parameter.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, category, description);
    }
}
