package app.domain.model;
import auth.domain.model.UserRole;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

public class EmployeeTest {
    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();
    public Employee emp;
    public List<UserRole> roleList = new ArrayList<>();
    public List<Employee> employeeList = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        roleList.add(new UserRole("Receptionist", "Responsible for scheduling appointments, answer patient inquiries and handle patient requests."));
        employeeList.add(new Employee("Pedro", this.roleList.get(0).getId(), "Rua do Monte", "12345678910", "123@isep.ipp.pt", 1234, "P00001"));
        emp = new Employee("Pedro", "Receptionist", "Rua fixe", "12345678901", "1234567890@gmail.com", 1234, "P00002");
    }

    @Test
    public void checkSizesNameSmall() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Name can only be 35 characters long");
        emp.checkName("qwertyuiopqwertyuiopqwertyuiopqwertqwertryy");

    }

    @Test
    public void checkNameEmpty() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Name cannot be empty");
        emp.checkName("");

    }

    @Test
    public void checkNameNotEmpty() {

        emp.checkName("qwertyuiopqw");

    }

    @Test
    public void checkSizesNameCorrect() {

        emp.checkName("Pedro");
        emp.checkName("qwertyuiopqwertyuiopqwertyuiopqwert");

    }

    @Test
    public void checkSizeIDSmall() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("ID must contain at least 5 chars");
        emp.checkId("P001");
    }

    @Test
    public void checkSizeIDEmpty() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("ID cannot be empty");
        emp.checkId("");
    }

    @Test
    public void checkSizesIDCorrect() {
        emp.checkId("P00001");
    }

    @Test
    public void checkRolesEmpty() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Role cannot be empty");
        emp.checkRole("");

    }

    @Test
    public void checkRolesCorrect() {
        emp.checkRole("Receptionist");

    }

    @Test
    public void checkAddressEmpty() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Address cannot be empty");
        emp.checkAddress("");
    }

    @Test
    public void checkSizesAddressCorrect() {
        emp.checkAddress("Rua do Monte Gordo");
    }

    @Test
    public void checkEmailInvalidFormat() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Invalid Email");
        emp.checkEmail("isep.lan.pt");
    }

    @Test
    public void checkEmailInvalidFormat2() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Invalid Email");
        emp.checkEmail("isep@lanpt");
    }

    @Test
    public void checkEmailInvalidFormat3() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Invalid Email");
        emp.checkEmail("iseplanpt");
    }

    @Test
    public void checkEmailEmpty() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Email cannot be empty");
        emp.checkEmail("");
    }

    @Test
    public void checkEmailCorrect() {
        emp.checkEmail("1201115@isep.ipp.pt");
    }

    @Test
    public void checkPhoneNumberSize() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Invalid Phone Number! Must have 11 digits");
        emp.checkPhoneNumber("12345677542242241343");
    }

    @Test
    public void checkPhoneNumberSmall() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Invalid Phone Number! Must have 11 digits");
        emp.checkPhoneNumber("12");
    }

    @Test
    public void checkPhonesSizeCorrect() {
        emp.checkRole("12345678910");
    }

    @Test
    public void checkPhoneNumberLetters() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Invalid Phone Number");
        emp.checkPhoneNumber("123456789aa");
    }

    @Test
    public void checkSOCSize() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Invalid Standard Occupational Classification! Must have 4 digits");
        emp.checkSoc(12345566);
    }

    @Test
    public void checkSOCSizeSmall() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Invalid Standard Occupational Classification! Must have 4 digits");
        emp.checkSoc(123);
    }

    @Test
    public void checkSOCSizeRight() {
        emp.checkSoc(1234);
    }

    @Test
    public void checkDoctorIndexNumberSmall() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Invalid Doctor Index Number! Must have 6 digits");
        emp.checkDoctorIndexNumber(12);
    }

    @Test
    public void checkDoctorIndexNumberBig() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Invalid Doctor Index Number! Must have 6 digits");
        emp.checkDoctorIndexNumber(32131321);
    }

    @Test
    public void checkDoctorIndexNumberCorrect() {
        emp.checkDoctorIndexNumber(111111);
    }

    @Test
    public void checkGets() {
        Employee l = new Employee("Pedro", "Receptionist", "Rua fixe", "12345678901", "1234567890@gmail.com", 1234, "P00002");
        Assert.assertEquals(l.getName(), "Pedro");
        Assert.assertEquals(l.getRole(), "Receptionist");
        Assert.assertEquals(l.getPhoneNumber(), "12345678901");
        Assert.assertEquals(l.getAddress(), "Rua fixe");
        Assert.assertEquals(l.getEmail(), "1234567890@gmail.com");
        Assert.assertEquals(l.getSoc(), 1234);
        Assert.assertEquals(l.getId(), "P00002");


    }

    @Test
    public void checkConstructorChecks() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Name can only be 35 characters long");

        Employee l = new Employee("aaasaddaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "Receptionist", "Rua fixe", "12345678901", "1234567890@gmail.com", 1234, "P00002");

    }

    @Test
    public void ToStringcheck() {
        String expected="Employee{name='Pedro', role=Receptionist, address='Rua fixe', phoneNumber=12345678901, email='1234567890@gmail.com', soc=1234, id='P00002'}";
        Assert.assertEquals(true,expected.equals(emp.toString()));

    }
    @Test
    public void checkEquals(){
        Employee e = new Employee("Pedro", "Receptionist", "Rua fixe", "12345678901", "1234567890@gmail.com", 1234, "P00002");
        Employee d = new Employee("Pedro", "Receptionist", "Rua fixe", "12345678901", "1234567890@gmail.com", 1234, "P00002");
        Assert.assertEquals(true, e.equals(d));
    }
    @Test
    public void checkEquals2(){
        Employee e = new Employee("Pedro", "Receptionist", "Rua fixe", "12345678901", "1234567890@gmail.com", 1234, "P00002");
        Employee l2 =  new Employee("ola","Receptionist","1223","12345678901","1234567890@gmail.com",1234,"O00001");

        Assert.assertEquals(false, e.equals(l2));
    }
    @Test
    public void checkHashCode(){
        Employee l = new Employee("Pedro", "Receptionist", "Rua fixe", "12345678901", "1234567890@gmail.com", 1234, "P00002");
        assertEquals(l.hashCode(), Objects.hash("Pedro", "Receptionist", "Rua fixe", "12345678901", "1234567890@gmail.com", 1234, "P00002"));
    }

}