package app.domain.model;

import auth.domain.model.UserRole;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SpecialistDoctorTest {
    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();
    public SpecialistDoctor spec;
    public List<UserRole> roleList = new ArrayList<>();
    public List<Employee> employeeList = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        spec = new SpecialistDoctor("Pedro", "Specialist Doctor", "Rua fixe", "12345678901", "1234567890@gmail.com", 1234, "P00002", 123456);
    }


    @Test
    public void checkDoctorIndexNumberCorrect() {
        Assert.assertEquals(true, spec.checkDoctorIndexNumber());
    }

    @Test
    public void checkDoctorIndexNumberIncorrect() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Invalid Doctor Index Number! Must have 6 digits");
        spec.checkDoctorIndexNumber(1234567);
    }

    @Test
    public void checkDoctorIndexNumberSmall() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Invalid Doctor Index Number! Must have 6 digits");
        spec.checkDoctorIndexNumber(321);
    }

    @Test

    public void checkGets() {
        SpecialistDoctor l = new SpecialistDoctor("Pedro", "Receptionist", "Rua fixe", "12345678901", "1234567890@gmail.com", 1234, "P00002", 123456);
        Assert.assertEquals(l.getDoctorIndexNumber(), 123456);
    }

    @Test
    public void ToStringcheck() {
        String expected= "SpecialistDoctor{name='Pedro', role=Specialist Doctor, address='Rua fixe', phoneNumber=12345678901, email='1234567890@gmail.com', soc=1234, id='P00002', doctorIndexNumber=123456}";
        Assert.assertEquals(true,expected.equals(spec.toString()));
    }
}