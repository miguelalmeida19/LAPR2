package app.domain.model;

import app.controller.App;
import app.domain.store.*;
import auth.AuthFacade;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class CompanyTest {
    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();
    public Company com;
    public String designation;
    public AuthFacade authFacade;
    public ParameterCategoryStore parameterCategoryStore;
    public EmployeeStore employeeStore;
    public ClientStore clientStore;
    public ParameterStore parameterStore;
    public LaboratoryStore laboratoryStore;
    public TestTypeStore testTypeStore;

    @Before
    public void setUp() throws Exception {
        String designation = "ManyLabs";
        this.com = new Company(designation);
        this.designation = designation;
        this.authFacade = new AuthFacade();
        this.parameterCategoryStore = new ParameterCategoryStore();
        this.employeeStore = new EmployeeStore();
        this.clientStore = new ClientStore();
        this.parameterStore = new ParameterStore();
        this.laboratoryStore = new LaboratoryStore();
        this.testTypeStore = new TestTypeStore();
    }
    @Test
    public void testCompanyConstructor(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Designation cannot be blank.");
        Company company = new Company("");
    }

    @Test
    public void getParameterCategoryStore() {
        Assert.assertEquals(true, this.com.getParameterCategoryStore().getClass().equals(this.parameterCategoryStore.getClass()));
    }

    @Test
    public void getParameterStore() {
        Assert.assertEquals(true, this.com.getParameterStore().getClass().equals(this.parameterStore.getClass()));
    }

    @Test
    public void getDesignation() {
        Assert.assertEquals(true, this.com.getDesignation().equals("ManyLabs"));
    }

    @Test
    public void getClientStore() {
        Assert.assertEquals(true, com.getClientStore().getClients().equals(this.clientStore.getClients()));
    }

    @Test
    public void getAuthFacade() {
        Assert.assertEquals(true, this.com.getAuthFacade().getClass().equals(this.authFacade.getClass()));
    }

    @Test
    public void getEmployeeStore() {
        Assert.assertEquals(true, this.com.getEmployeeStore().getClass().equals(this.employeeStore.getClass()));
    }

    @Test
    public void getLaboratoryStore() {
        Assert.assertEquals(true, this.com.getLaboratoryStore().getClass().equals(this.laboratoryStore.getClass()));
    }

    @Test
    public void getTestTypeStore() {
        Assert.assertEquals(true, this.com.getTestTypeStore().getClass().equals(this.testTypeStore.getClass()));
    }
}