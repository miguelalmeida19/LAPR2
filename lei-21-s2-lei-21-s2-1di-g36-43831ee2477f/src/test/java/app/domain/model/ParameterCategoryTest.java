package app.domain.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Objects;

import static org.junit.Assert.assertEquals;

public class ParameterCategoryTest {
    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();
    public ParameterCategory pc;

    @Before
    public void setUp() throws Exception {
        pc = new ParameterCategory("12345", "Categoria");
    }

    @Test
    public void getCode() {
        String code = "12345";
        Assert.assertEquals(true, code.equals(pc.getCode()));
    }

    @Test
    public void getName() {
        String name = "Categoria";
        Assert.assertEquals(true, name.equals(pc.getName()));
    }

    @Test
    public void testEqualsFalse() {
        ParameterCategory pc1 = new ParameterCategory("12346", "Category");
        ParameterCategory pc2 = new ParameterCategory("12332", "Hemograma");
        Assert.assertEquals(false, pc1.equals(pc2));
    }

    @Test
    public void testEqualsTrue() {
        ParameterCategory pc1 = new ParameterCategory("12346", "Category");
        ParameterCategory pc2 = new ParameterCategory("12346", "Category");
        Assert.assertEquals(true, pc1.equals(pc2));
    }

    @Test
    public void testEqualsFalse1() {
        ParameterCategory pc1 = new ParameterCategory("12346", "Hemograma");
        ParameterCategory pc2 = new ParameterCategory("12332", "Hemograma");
        Assert.assertEquals(false, pc1.equals(pc2));
    }

    @Test
    public void testEqualsFalse3() {
        ParameterCategory pc1 = new ParameterCategory("12346", "Category");
        ParameterCategory pc2 = new ParameterCategory("12346", "Hemograma");
        Assert.assertEquals(false, pc1.equals(pc2));
    }

    @Test
    public void checkHashCode(){
        ParameterCategory pc1 = new ParameterCategory("32423", "Categoria");
        assertEquals(pc1.hashCode(), Objects.hash("32423", "Categoria"));
    }

    @Test
    public void checkNameValid() {
        pc.checkNameRules("Categoria");
    }

    @Test
    public void checkNameBig() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Name cannot be longer than 15 characters");
        pc.checkNameRules("123111111111111111");
    }

    @Test
    public void checkNameEmpty() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Name must be entered");
        pc.checkNameRules("");
    }

    @Test
    public void checkCodeCorrect() {
        pc.checkCodeRules("aaaaa");
    }

    @Test
    public  void checkCodeIncorrectLess(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Code must have 5 chars." );
        pc.checkCodeRules("aaa");
    }
    @Test
    public  void checkCodeIncorrectMore(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Code must have 5 chars." );
        pc.checkCodeRules("aaaaasasdasd");
    }
    @Test
    public  void checkCodeBlank(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Code cannot be blank." );
        pc.checkCodeRules("");
    }
}