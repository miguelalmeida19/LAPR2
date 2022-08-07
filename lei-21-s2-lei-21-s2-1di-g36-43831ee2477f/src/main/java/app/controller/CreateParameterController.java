package app.controller;

import app.domain.model.Company;
import app.domain.model.Parameter;
import app.domain.model.ParameterCategory;
import app.domain.store.ParameterCategoryStore;
import app.domain.store.ParameterStore;

import java.util.ArrayList;
import java.util.List;

public class CreateParameterController {

    private Company company;
    private Parameter parameter;
    private ParameterStore parameterStore;
    private ParameterCategoryStore parameterCategoryStore ;

    /**
     * Constructor
     */
    public CreateParameterController() {
        this(App.getInstance().getCompany());

    }

    /**
     * Constructor
     * @param company company object
     */
    public CreateParameterController(Company company) {
        this.company = company;
        this.parameterCategoryStore = company.getParameterCategoryStore();
        this.parameterStore = company.getParameterStore();
    }

    /**
     * Creates the new parameter.
     * @param code code of parameter.
     * @param name name of parameter.
     * @param description description of parameter.
     * @param parameterCategoryDescription description of the category of parameter
     */
    public void createNewParameter(String code, String name, String description, String parameterCategoryDescription){
        ParameterCategory category = parameterCategoryStore.getParameterCategoryByDescription(parameterCategoryDescription);
        if(category == null){ //cannot create a parameter without category....
            throw  new IllegalArgumentException("Category is null");
        }

        parameter = parameterStore.createParameter(code,name,description, category);
        parameterStore.validateParameter(parameter);
    }

    /**
     * Saves the parameter created
     */
    public void saveParameter(){
        parameterStore.saveParameter(parameter);
    }

    /**
     * Gets the parameter categories used in the interface to create a parameter. Every parameter need an category.
     * @return list with all categories description.
     */
    public List<String> getParameterCategories(){
        parameterCategoryStore = company.getParameterCategoryStore();
        List<String> parameterCategoryString = new ArrayList<>();
        for (ParameterCategory pc : parameterCategoryStore.getParameterCategoryList()){
            parameterCategoryString.add(pc.getName());
        }
        return parameterCategoryString;
    }

}
