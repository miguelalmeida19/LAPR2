package app.domain.model;

import app.domain.store.*;
import auth.AuthFacade;
import org.apache.commons.lang3.StringUtils;


/**
 * @author Paulo Maio <pam@isep.ipp.pt>
 */
public class Company {

    private final String designation;
    private final AuthFacade authFacade;
    private final ParameterCategoryStore parameterCategoryStore;
    private final EmployeeStore employeeStore;
    private final ClientStore clientStore;
    private final ParameterStore parameterStore;
    private final LaboratoryStore laboratoryStore;
    private final TestTypeStore testTypeStore;
    private final TestStore testStore;

    public Company(String designation) {
        if (StringUtils.isBlank(designation))
            throw new IllegalArgumentException("Designation cannot be blank.");
        this.designation = designation;
        this.authFacade = new AuthFacade();
        this.parameterCategoryStore = new ParameterCategoryStore();
        this.employeeStore = new EmployeeStore();
        this.clientStore = new ClientStore();
        this.parameterStore = new ParameterStore();
        this.laboratoryStore = new LaboratoryStore();
        this.testTypeStore = new TestTypeStore();
        this.testStore = new TestStore();

    }


    public ParameterCategoryStore getParameterCategoryStore() {
        return this.parameterCategoryStore;
    }

    public ParameterStore getParameterStore() {
        return this.parameterStore;
    }

    public String getDesignation() {
        return designation;
    }

    public ClientStore getClientStore() {
        return clientStore;
    }

    public AuthFacade getAuthFacade() {
        return authFacade;
    }

    public EmployeeStore getEmployeeStore() {
        return employeeStore;
    }

    public LaboratoryStore getLaboratoryStore() {
        return this.laboratoryStore;
    }

    public TestTypeStore getTestTypeStore() {
        return this.testTypeStore;
    }

    public TestStore getTestStore(){
        return this.testStore;
    }
}
