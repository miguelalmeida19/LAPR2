package app.controller;

import app.domain.model.Company;
import app.domain.model.Employee;
import app.domain.model.SpecialistDoctor;
import app.domain.store.EmployeeStore;
import app.domain.store.OrganizationRoleStore;
import app.ui.console.utils.EmailNotificationSender;
import auth.AuthFacade;
import auth.domain.model.UserRole;
import org.apache.commons.lang3.StringUtils;

import javax.management.relation.Role;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static app.ui.console.utils.Utils.generatePassword;

public class RegisterEmployeeController {
    private Company company;
    private Employee Emp;
    private EmployeeStore store;
    private OrganizationRoleStore roleList = new OrganizationRoleStore();

    /**
     * Constructor
     */
    public RegisterEmployeeController() {
        this(App.getInstance().getCompany());

    }

    /**
     * Constructor defining company, story and emp
     * @param company
     */
    public RegisterEmployeeController(Company company) {
        this.company = company;
        store = company.getEmployeeStore();
        this.Emp = null;
    }

    /**
     * This method creates a new Specialist Doctor
     * @param name
     * @param role
     * @param address
     * @param phoneNumber
     * @param email
     * @param soc
     * @param id
     * @param doctorIndexNumber
     */
    public void createNewSpecialistDoctor(String name, String role, String address, String phoneNumber, String email, int soc, String id, int doctorIndexNumber) {

        Emp = store.createNewSpecialistDoctor(name, role, address, phoneNumber, email, soc, id, doctorIndexNumber);
        store.validateEmployee(Emp);
    }

    /**
     * This method creates a new Employees
     * @param name
     * @param role
     * @param address
     * @param phoneNumber
     * @param email
     * @param soc
     * @param id
     */
    public void createNewEmployee(String name, String role, String address, String phoneNumber, String email, int soc, String id) {
        Emp = store.createNewEmployee(name, role, address, phoneNumber, email, soc, id);
        store.validateEmployee(Emp);
    }

    /**
     * This method adds a new Employee to the list of employees
     * @param employee
     * @return
     */
    public boolean add(Employee employee) {
        return store.add(employee);
    }

    /**
     * This method saves an Employee
     * @throws IOException
     */
    public void saveEmployee() throws IOException {
        this.store.saveEmployee(Emp);
        String password = generatePassword();
        System.out.println("Password: " + password);
        saveEmployeeLogin(Emp.getName(), password, Emp.getEmail());
        AuthFacade PW = company.getAuthFacade();
        PW.addUserWithRole(Emp.getName(), Emp.getEmail(), password, Emp.getRole());
    }

    /**
     * This method returns a list with all the roles that exist
     * @return
     */
    public List<String> getRoles() {
        List<String> userRoleString = new ArrayList<>();
        for (UserRole role : roleList.getRoles()){
            userRoleString.add(role.getId());
        }
        return userRoleString;
    }

    /**
     * This method returns a list with all the employees that exist
     * @return
     */
    public List<String> getEmployees() {
        return store.getEmployees();
    }

    /**
     * This method returns the ID of an Employee
     * @param name
     * @return
     */
    public String getId(String name) {
        return store.getId(name);
    }

    /**
     * This method returns a String that contains all the information about a Specialist Doctor
     * @param name
     * @param role
     * @param address
     * @param phoneNumber
     * @param email
     * @param soc
     * @param id
     * @param doctorIndexNumber
     * @return
     */
    public String getSpecialistDoctorInfo(String name, String role, String address, String phoneNumber, String email, int soc, String id, int doctorIndexNumber){
       String info = new SpecialistDoctor(name, role, address, phoneNumber, email, soc, id, doctorIndexNumber).toString();
       return info;
    }

    /**
     * This method returns a String that contains all the information about an Employee
     * @param name
     * @param role
     * @param address
     * @param phoneNumber
     * @param email
     * @param soc
     * @param id
     * @return
     */
    public String getEmployeeInfo(String name, String role, String address, String phoneNumber, String email, int soc, String id){
        String info = new Employee(name, role, address, phoneNumber, email, soc, id).toString();
        return info;
    }

    /**
     * This method saves the login data in a file
     * @param name
     * @param password
     * @param email
     * @throws IOException
     */
    public void saveEmployeeLogin(String name, String password, String email) throws IOException {
        EmailNotificationSender notificationSender = new EmailNotificationSender();
        notificationSender.sendPasswordNotification(name, password, email);
    }
}