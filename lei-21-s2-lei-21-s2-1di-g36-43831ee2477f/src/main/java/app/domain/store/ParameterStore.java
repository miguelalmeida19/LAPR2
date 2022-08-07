package app.domain.store;

import app.Persistence;
import app.controller.App;
import app.domain.model.Company;
import app.domain.model.Parameter;
import app.domain.model.ParameterCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ParameterStore {

    /**
     * List with all parameters created
     */
    private  List<Parameter> parameterList;

    /**
     * Constructor
     */
    public ParameterStore() {
        parameterList = new ArrayList<>();
        try{
            parameterList = (List<Parameter>) Persistence.readObjectFromFile("parameters.bin");
        }catch (Exception e){
            //System.out.println("The ParameterStore was not loaded :)");
        }
    }

    /**
     * Creates the parameter.
     *
     * @param code        code of parameter.
     * @param name        name of parameter.
     * @param description description of parameter.
     * @param category    category of parameter.
     * @return the parameter created.
     */
    public Parameter createParameter(String code, String name, String description, ParameterCategory category) {
        return new Parameter(code, name, category, description);
    }

    /**
     * Validates the parameter checking if it was already created before.
     *
     * @param parameter parameter to verify
     */
    public void validateParameter(Parameter parameter) {
        if (parameterList.contains(parameter)) {
            throw new IllegalArgumentException("The parameter has already been created.");
        }
    }

    public List<Parameter> getListOfParametersByCategoryList(List<ParameterCategory> parameterCategoryList) {
        List<Parameter> parameterList = new ArrayList<>();
        for (Parameter p1 : getListOfParameters()) {
            if (parameterCategoryList.contains(p1.getCategory())) {
                parameterList.add(p1);
            }
        }
        return parameterList;
    }

    /**
     * Saves the parameter in the store.
     *
     * @param parameter parameter to save
     */
    public void saveParameter(Parameter parameter) {
        validateParameter(parameter);
        addParameter(parameter);
    }

    /**
     * Method to add the parameter to store
     *
     * @param parameter parameter to add.
     */
    public void addParameter(Parameter parameter) {
        parameterList.add(parameter);
    }

    public List<Parameter> getListOfParameters() {
        return parameterList;
    }

    public String createParameterCode(String description) {
        char[] descriptionArray = description.toCharArray();
        String name = String.valueOf(descriptionArray[0]) + String.valueOf(descriptionArray[1]);
        String number = "1";
        for (Parameter p : parameterList) {
            if (p.getCode().contains(name)) {
                number = String.valueOf(Integer.parseInt(number) + 1);
            }
        }
        while (number.length() < 3) {
            number = "0" + number;
        }
        name = name + number;
        return name;
    }

    public String createParameterName(String description) {
        char[] descriptionArray = description.toCharArray();
        String name = (String.valueOf(descriptionArray[0]) + String.valueOf(descriptionArray[1])).toUpperCase(Locale.ROOT);
        return name;
    }
}
