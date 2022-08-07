package app.domain.model;


import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Objects;

public class ParameterCategory  implements Serializable {
    private final String code;
    private final String name;

    /**
     * Constructor
     * @param code
     * @param name
     */
    public ParameterCategory(String code, String name) {
        checkCodeRules(code);
        checkNameRules(name);
        this.code = code;
        this.name = name;
    }

    /**
     * This method checks if the code is valid or not
     * @param code
     */
    public void checkCodeRules(String code) {
        if (StringUtils.isBlank(code))
            throw new IllegalArgumentException("Code cannot be blank.");
        if ((code.length()!=5))
            throw new IllegalArgumentException("Code must have 5 chars.");
    }

    /**
     * This method returns the code of a Parameter Category
     * @return
     */
    public String getCode() {
        return code;
    }

    /**
     * This method returns the name of a Parameter Category
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * This method checks if the name is valid or not
     * @param name
     */
    public void checkNameRules(String name) {
        if (name.length()>15){
            throw new IllegalArgumentException("Name cannot be longer than 15 characters");
        }
        else if(name.isEmpty()){
            throw new IllegalArgumentException("Name must be entered");
        }
    }

    /**
     * This method returns true or false depending on if a Parameter Category is equal to another one
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParameterCategory that = (ParameterCategory) o;
        return code.equals(that.code) && name.equals(that.name);
    }

    /**
     * This method returns the hashCode of a Parameter Category
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(code, name);
    }
}