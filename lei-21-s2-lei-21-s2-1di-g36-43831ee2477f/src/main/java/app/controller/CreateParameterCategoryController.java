package app.controller;

import app.domain.model.Company;
import app.domain.model.ParameterCategory;
import app.domain.store.ParameterCategoryStore;

public class CreateParameterCategoryController {
    private Company company;
    private ParameterCategory pc;
    private ParameterCategoryStore store;

    /**
     * Constructor
     */
    public CreateParameterCategoryController() {
        this(App.getInstance().getCompany());

    }

    /**
     * Constructor defining company and parameter category
     * @param company
     */
    public CreateParameterCategoryController(Company company) {
        this.company = company;
        this.pc = null;
    }

    /** This method creates a new Parameter Category
     * @param code
     * @param description
     * @return
     */
    public boolean createParameterCategory(String code, String description){
        store = company.getParameterCategoryStore();
        pc = store.createParametercategory(code, description);
        return store.validateParameterCategory(pc);
    }

    /**
     * This method saves a Parameter Category
     * @return
     */
    public boolean saveParameterCategory() {
        return this.store.saveParameterCategory(pc);
    }
}