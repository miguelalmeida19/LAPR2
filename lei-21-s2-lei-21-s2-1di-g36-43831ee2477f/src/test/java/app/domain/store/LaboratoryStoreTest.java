package app.domain.store;

import app.domain.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class LaboratoryStoreTest {
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    public Laboratory emp;
    public List<TestType> list = new ArrayList<>();
    public List<ParameterCategory> pc = new ArrayList<>();
    @Before
    public void setUp() throws Exception {

        pc.add(new ParameterCategory("12345", "ola"));
        TestType t = new TestType("123a5", "test1", "test", this.pc);
        list.add(t);
        emp = new Laboratory("lab1", "12345", "ola", "12345678901", "1234567890", list);

    }
    @Test
    public void createNewLaboratory() {
        Laboratory lab = new Laboratory("lab1", "12345", "ola", "12345678901", "1234567890", list);
        LaboratoryStore LaboratoryStore = new LaboratoryStore();
        Laboratory lab1= LaboratoryStore.createNewLaboratory("lab1", "12345", "ola", "12345678901", "1234567890", list);
        Assert.assertEquals(true, lab1.equals(lab));
    }
    @Test
    public void validateLaboratoryError() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("laboratory has already been created" );
        LaboratoryStore l = new LaboratoryStore();
        l.add(emp);
        Laboratory l2 = new Laboratory("lab1", "12345", "ola", "12345678901", "1234567890", list);;
        l.validateLaboratory(l2);

    }
    @Test
    public void validateLaboratoryCorrect() {

        LaboratoryStore l = new LaboratoryStore();
        l.add(emp);
        Laboratory l2 = new Laboratory("lab2", "12765", "offgsdsgla", "12345378901", "1234247890", list);;
        l.validateLaboratory(l2);

    }

    @Test
    public void checkDuplicatedAdress(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("There is a lab with the same address already created.");
        Laboratory l2 = new Laboratory("lab2", "12346", "ola", "12325678901", "1214567890", list);;
        LaboratoryStore l = new LaboratoryStore();
        l.add(emp);
        l.checkDuplicatedData(l2);

    }
    @Test
    public void checkDuplicatedID(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("There is a lab with the same ID already created.");
        Laboratory l2 = new Laboratory("lab2", "12345", "ol1a", "12325678901", "1214567890", list);;
        LaboratoryStore l = new LaboratoryStore();
        l.add(emp);
        l.checkDuplicatedData(l2);

    }
     @Test
    public void checkDuplicatedPhoneNumber(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("There is a lab with the same phone number already created.");
        Laboratory l2 = new Laboratory("lab2", "1s345", "ol1a", "12345678901", "1214567890", list);;
        LaboratoryStore l = new LaboratoryStore();
        l.add(emp);
        l.checkDuplicatedData(l2);

    }
      @Test
    public void checkDuplicatedTIN(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("There is a lab with the same TIN already created.");
        Laboratory l2 = new Laboratory("lab2", "1s345", "ol1a", "12345378901", "1234567890", list);;
        LaboratoryStore l = new LaboratoryStore();
        l.add(emp);
        l.checkDuplicatedData(l2);

    }
    @Test
    public void check(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Address of laboratory cannot be empty.");

        emp.checkSizes("a", "12345", "", "12345678901", "1234567890");

    }
    @Test
    public void save(){
        Laboratory l2 = new Laboratory("lab2", "1s345", "ol1a", "12345378901", "1234567822", list);;
        LaboratoryStore LaboratoryStore = new LaboratoryStore();
        LaboratoryStore.saveLaboratory(l2);
    }
}