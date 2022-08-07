package app.domain.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.util.Objects;

import static org.junit.Assert.*;

public class ParameterTest {
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    public ParameterCategory pc;
    public Parameter p;
    @Before
    public void setUp() throws Exception {

        pc = new ParameterCategory("12345", "ola");

        p = new Parameter("12345","name",pc,"dexcription");

    }
    @Test
    public void checkNameCorrect() {
        p.checkName("aaaaaaaa");
        p.checkName("aaaa");
    }
    @Test
    public void checkNameIncorrect() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("The name of parameter cannot have more than 8 characters" );
        p.checkName("aaaaaaaaaa");

    }

    @Test
    public void checkDescriptionIncorrect() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("The description of parameter cannot have more than 20 characters." );
        p.checkDescription("aaaaaaaaaaaaaaaaaaaaa");
    }
    @Test
    public void checkDescriptionCorrect() {

        p.checkDescription("aaaaaaaaaaaaaaaaaaaa");
        p.checkDescription("aaaaaaaaaaaaaaa");
    }

    @Test
    public void checkCodeCorrect() {
        p.checkCode("aaaaa");
    }

    @Test
    public  void checkCodeIncorrectLess(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("The code of parameter must have 5 characters." );
        p.checkCode("aaa");
    }
    @Test
    public  void checkCodeIncorrectMore(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("The code of parameter must have 5 characters." );
        p.checkCode("aaaaasasdasd");
    }

    @Test
    public void checkConstructorChecks(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("The code of parameter must have 5 characters.");

        Parameter l = new Parameter("12","name",pc,"dexcription");

    }
    @Test
    public void checkConstructorChecks2(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("he description of parameter cannot have more than 20 characters.");
        Parameter l = new Parameter("12345","naae",pc,"dexcrsfgdddddddddddddddddddddddddddddiption");

    }
    @Test
    public void checkConstructorChecks3(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("The name of parameter cannot have more than 8 characters.");
        Parameter l = new Parameter("12345","naasddasasdasdasdasdme",pc,"dexcription");

    }
    @Test
    public void checkGets(){
        Parameter l = new Parameter("12345","naade",pc,"dexcription");
        Assert.assertEquals(pc,l.getCategory());
        Assert.assertEquals("dexcription",l.getDescription());
        Assert.assertEquals("12345",l.getCode());
        Assert.assertEquals("naade",l.getName());


    }

    @Test
    public void checkEquals(){
        Parameter l = new Parameter("12345","naadme",pc,"dexcription");
        Parameter l2 = new Parameter("12345","naadme",pc,"dexcription");
        Assert.assertEquals(true, l.equals(l2));
    }
    @Test
    public void checkEquals2(){
        Parameter l = new Parameter("12345","naadme",pc,"dexcription");
        Parameter l2 = new Parameter("12365","naadme",pc,"dexcription");
        Assert.assertEquals(false, l.equals(l2));
    }
    @Test
    public void checkEqualsNull(){
        Parameter l = new Parameter("12345","naadme",pc,"dexcription");

        Assert.assertEquals(false, l.equals(null));
    }
    @Test
    public void checkEqualsSameObject(){
        Parameter l = new Parameter("12345","naadme",pc,"dexcription");

        Assert.assertEquals(true, l.equals(l));
    }
    @Test
    public void checkEqualsDifferentTypeOfObject(){
        Parameter l = new Parameter("12345","naadme",pc,"dexcription");
        ParameterCategory pc = new ParameterCategory("12345","1234");
        Assert.assertEquals(false, l.equals(pc));
    }
    @Test
    public void checkHashCode(){
        Parameter l = new Parameter("12345","naadme",pc,"dexcription");
        assertEquals(l.hashCode(), Objects.hash("12345", "naadme", pc, "dexcription"));
    }

}