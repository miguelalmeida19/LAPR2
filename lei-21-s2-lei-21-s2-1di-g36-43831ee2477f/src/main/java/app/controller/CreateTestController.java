package app.controller;
import app.domain.model.Company;
import app.domain.model.Parameter;
import app.domain.model.ParameterCategory;
import app.domain.model.TestType;
import app.domain.store.ParameterCategoryStore;
import app.domain.store.TestTypeStore;

import java.util.List;

public class CreateTestController {
    private Company company;
    private TestType c;
    private TestTypeStore store;

    /**
     * Constructor
     */
    public CreateTestController(){
        this(App.getInstance().getCompany());
    }

    /**
     * Constructor defining company
     * @param company
     */
    public CreateTestController(Company company){
        this.company = company;

    }

    /**
     * This method creates a new test type
     * @param code
     * @param description
     * @param collectionMethod
     * @param parameterCategories
     */
    public void createNewTestType(String code, String description, String collectionMethod, List<ParameterCategory> parameterCategories){
        store = company.getTestTypeStore();
        c = store.createTestType(code, description, collectionMethod, parameterCategories);
        store.validateTest(c);

    }

    /**
     * This method saves a new test type
     */
    public void saveTest(){
        store.saveTest(c);
    }

    /**
     * This method returns a list with all the Parameter Categories
     * @return
     */
    public ParameterCategoryStore getParameterCategoryStore() {
        return company.getParameterCategoryStore();
    }
}
