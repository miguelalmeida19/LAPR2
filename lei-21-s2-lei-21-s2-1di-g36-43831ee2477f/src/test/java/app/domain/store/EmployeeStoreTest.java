package app.domain.store;

import app.domain.model.*;
import auth.domain.model.UserRole;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;


public class EmployeeStoreTest {
    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();
    public Employee emp;
    public SpecialistDoctor spec;
    public List<UserRole> roleList = new ArrayList<>();
    public List<Employee> employeeList = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        roleList.add(new UserRole("RECEPTIONIST", "Responsible for scheduling appointments, answer patient inquiries and handle patient requests."));
        employeeList.add(new Employee("Pedro", this.roleList.get(0).getId(), "Rua do Monte", "12345678910", "123@isep.ipp.pt", 1234, "P00001"));
        emp = new Employee("Pedro", "RECEPTIONIST", "Rua do Monte", "12345678901", "1234567890@gmail.com", 1234, "P00002");
        spec = new SpecialistDoctor("Pedro", this.roleList.get(0).getId(), "Rua do Monte", "12345678910", "123@isep.ipp.pt", 1234, "P00001", 234561);
    }

    @Test
    public void createNewSpecialistDoctor() {
        SpecialistDoctor spec = new SpecialistDoctor("Pedro","RECEPTIONIST" , "Rua do Monte", "12345678910", "123@isep.ipp.pt", 1234, "P00001", 234561);
        EmployeeStore EmployeeStore = new EmployeeStore();
        Employee spec1= EmployeeStore.createNewSpecialistDoctor("Pedro","RECEPTIONIST" , "Rua do Monte", "12345678910", "123@isep.ipp.pt", 1234, "P00001", 234561);
        Assert.assertEquals(true, spec1.equals(spec));
    }

    @Test
    public void createNewEmployee() {
        Employee employee = new Employee("Pedro", "RECEPTIONIST", "Rua do Monte", "12345678910", "123@isep.ipp.pt", 1234, "P00001");
    }

    @Test
    public void validateEmployeeDifferent() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Employee " + emp.getName() + " already exists");
        Employee emp2 = new Employee("Pedro", "RECEPTIONIST", "Rua do Monte", "12345678901", "1234567890@gmail.com", 1234, "P00002");;
        EmployeeStore employeeStore = new EmployeeStore();
        employeeStore.add(emp);
        employeeStore.validateEmployee(emp2);
    }
    @Test
    public void validateEmployeeCorrect(){
        Employee emp2 = new Employee("André", "MEDICAL LAB TECHNICIAN", "Rua Fixe", "12345678902", "1234567890@gmail.com", 1234, "P00002");;
        EmployeeStore employeeStore = new EmployeeStore();
        employeeStore.add(emp);
        employeeStore.validateEmployee(emp2);
    }

    @Test
    public void add(){
        employeeList.add(emp);
    }

    @Test
    public void save(){
        EmployeeStore employeeStore = new EmployeeStore();
        employeeStore.saveEmployee(emp);
    }

    @Test
    public void getEmployees() {
        EmployeeStore employeeStore = new EmployeeStore();
        Employee emp2 = new Employee("André", "MEDICAL LAB TECHNICIAN", "Rua Fixe", "12345678902", "1234567890@gmail.com", 1234, "P00002");;
        employeeStore.add(emp);
        employeeStore.add(emp2);

        List<String> employeeTestList = new ArrayList<>();
        employeeTestList.add(emp.getName());
        employeeTestList.add(emp2.getName());

        employeeStore.getEmployees().equals(employeeTestList);
    }

    @Test
    public void getInitialsWithoutName() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Could not create id, because name field is empty");
        EmployeeStore store = new EmployeeStore();
        String name = "";
        String inicitials = "PMHM";
        Assert.assertEquals(false, inicitials.equals(store.getInitials(name)));
    }

    @Test
    public void getInitials() {
        EmployeeStore store = new EmployeeStore();
        String name = "Pedro Moreira Henriques Machado";
        String inicitials = "PMHM";
        Assert.assertEquals(true, inicitials.equals(store.getInitials(name)));
    }
    @Test
    public void getQuantity() {
        EmployeeStore store = new EmployeeStore();
        store.createNewEmployee("Pedro", "MEDICAL LAB TECHNICIAN", "Rua Fixe", "12345678902", "1234567890@gmail.com", 1234, "P00002");
        int count = 1;
        System.out.println(store.getEmployeeQuantity());
        Assert.assertEquals(true, String.valueOf(count).equals(String.valueOf(store.getEmployeeQuantity())));
    }
    @Test
    public void getID() {
        EmployeeStore store = new EmployeeStore();
        String name = "Pedro Moreira Henriques Machado";
        String inicitials = "PMHM";
        int count=1;
        String ID="PMHM00001";
        Assert.assertEquals(true, ID.equals(store.getId(name)));
    }
}