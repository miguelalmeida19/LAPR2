package app.domain.model;

import org.junit.*;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

public class LaboratoryTest {
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

    //@Test(expected = IllegalArgumentException.class)

    @Test
    public void checkSizesNameSmall() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Name of lab cannot have more than 20 characters.");
        emp.checkSizes("lab1agfsrgaagaggfagfagfsgfasdfasdfadsfadsfdasfdasfasdfdas", "1234", "fal", "12345678901", "1234567890");

    }

    @Test
    public void checkSizesNameCorrect() {

        emp.checkSizes("lab1", "12345", "fal", "12345678901", "1234567890");
        emp.checkSizes("lab1asdfghjklzxcvbnm", "12345", "fal", "12345678901", "1234567890");

    }

    @Test
    public void checkSizesIDSmall() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("ID must have 5 characters.");
        emp.checkSizes("lab1", "1234", "fal", "12345678901", "1234567890");

    }

    @Test
    public void checkSizesIDBig() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("ID must have 5 characters.");
        emp.checkSizes("lab1", "123456", "fal", "12345678901", "1234567890");

    }

    @Test
    public void checkSizesIDCorrect() {

        emp.checkSizes("lab1", "12345", "fal", "12345678901", "1234567890");

    }

    @Test
    public void checkSizesPhoneNumberBig() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("The phone number must have 11 digits.");
        emp.checkSizes("lab1", "12345", "fal", "123456783031", "1234567890");


    }

    @Test
    public void checkSizesPhoneNumberSmall() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("The phone number must have 11 digits.");
        emp.checkSizes("lab1", "12345", "fal", "1234561", "1234567890");


    }

    @Test
    public void checkSizesPhoneNumberCorrect() {

        emp.checkSizes("lab1", "12345", "fal", "12345678901", "1234567890");

    }
    @Test
    public void checkSizesAddressBig() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Address cannot have more than 30 characters.");
        emp.checkSizes("lab1", "12345", "fafsegsgfdsgfgfsgfsdfsggfsfsgdfsgdfgsdfsgdfsgdfsgdsfgdl", "12345678901", "1234567890");


    }



    @Test
    public void checkSizesAddressCorrect() {

        emp.checkSizes("lab1", "12345", "fal", "12345678901", "1234567890");
        emp.checkSizes("lab1", "12345", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "12345678901", "1234567890");

    }

    @Test
    public void checkSizesTINBig() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("TIN number must have 10 digits.");
        emp.checkSizes("lab1", "12345", "fal", "12345678901", "12345678901");
    }

    @Test
    public void checkSizesTINSmall() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("TIN number must have 10 digits.");
        emp.checkSizes("lab1", "12345", "fal", "12345678901", "1234560");


    }

    @Test
    public void checkSizesTINCorrect() {

        emp.checkSizes("lab1", "12345", "fal", "12345678901", "1234567890");

    }


    @Test
    public void checkNumberTestsCorrect(){

        emp.checkNumberTests(list);
    }
    @Test
    public void checkNumberTestsIncorrect(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("The lab need to operate at least 1 test type.");
        emp.checkNumberTests(null);
        emp.checkNumberTests(new ArrayList<TestType>());
    }
    @Test
    public void checkNameIsEmpty(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Name of laboratory cannot be empty.");

        emp.checkSizes("", "12345", "fal", "12345678901", "1234567890");

    }
    @Test
    public void checkAdressIsEmpty(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Address of laboratory cannot be empty.");

        emp.checkSizes("a", "12345", "", "12345678901", "1234567890");

    }
    @Test
    public void checkConstructorChecks(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("ID must have 5 characters.");

        Laboratory l = new Laboratory("ola","1333333","1223","12345678901","1234567890",list);

    }
    @Test
    public void checkConstructorChecks2(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("The lab need to operate at least 1 test type.");
        List<TestType> t = new ArrayList<>();
        Laboratory l = new Laboratory("ola","12345","1223","12345678901","1234567890",t);

    }
    @Test
    public void checkGets(){
        Laboratory l = new Laboratory("ola","12345","1223","12345678901","1234567890",list);
        Assert.assertEquals(l.getAddress(),"1223");
        Assert.assertEquals(l.getID(),"12345");
        Assert.assertEquals(l.getPhoneNumber(),"12345678901");
        Assert.assertEquals(l.getTIN(),"1234567890");

    }

    @Test
    public void checkEquals(){
        Laboratory l = new Laboratory("ola","12345","1223","12345678901","1234567890",list);
        Laboratory l2 = new Laboratory("ola","12345","1223","12345678901","1234567890",list);
        Assert.assertEquals(true, l.equals(l2));
    }
    @Test
    public void checkEquals2(){
        Laboratory l = new Laboratory("ola","12345","1223","12345678901","1234567890",list);
        Laboratory l2 =  new Laboratory("ola","11111","1223","12345678901","1234567890",list);

        Assert.assertEquals(false, l.equals(l2));
    }
    @Test
    public void checkEqualsNull(){
        Laboratory l = new Laboratory("ola","12345","1223","12345678901","1234567890",list);

        Assert.assertEquals(false, l.equals(null));
    }
    @Test
    public void checkEqualsSameObject(){
        Laboratory l = new Laboratory("ola","12345","1223","12345678901","1234567890",list);

        Assert.assertEquals(true, l.equals(l));
    }
    @Test
    public void checkEqualsDifferentTypeOfObject(){
        Laboratory l = new Laboratory("ola","12345","1223","12345678901","1234567890",list);
        ParameterCategory pc = new ParameterCategory("12345","1234");
        Assert.assertEquals(false, l.equals(pc));
    }
    @Test
    public void checkHashCode(){
        Laboratory l = new Laboratory("ola","12345","1223","12345678901","1234567890",list);
        assertEquals(l.hashCode(), Objects.hash(list, "ola", "12345", "1223", "12345678901", "1234567890"));
    }


}