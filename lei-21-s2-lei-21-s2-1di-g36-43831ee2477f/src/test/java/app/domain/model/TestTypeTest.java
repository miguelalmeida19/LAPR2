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

import static org.junit.Assert.*;

public class TestTypeTest {
    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();
    public TestType test;
    public List<ParameterCategory> categoryList = new ArrayList<>();
    public List<TestType> testTypeList = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        categoryList.add(new ParameterCategory("12345", "Hemograma"));
        testTypeList.add(new TestType("23456", "Covid19", "Collected in Labs", categoryList));
        test = new TestType("12346", "Urine", "Collected in ManyLabs", categoryList);
    }

    @Test
    public void checkSizesCodeBig() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Code of test must have 5 characters.");
        test.checkCode("111111111111111111111111111111111111111111111111111111111");

    }

    @Test
    public void checkSizesCodeSmall() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Code of test must have 5 characters.");
        test.checkCode("111");
    }

    @Test
    public void checkSizesCodeCorrect() {
        test.checkCode("11111");
    }

    @Test
    public void checkDescriptionEmpty() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Description cannot be empty!");
        test.checkDescription("");
    }

    @Test
    public void checkDescription() {
        test.checkDescription("Covid19");
    }

    @Test
    public void checkMethodEmpty() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Collecting method cannot be empty.");
        test.checkCollectingMethod("");
    }

    @Test
    public void checkMethod() {
        test.checkCollectingMethod("Collected in ManyLabs");
    }

    @Test
    public void checkToString() {
        String expected = "TestType{code='12346', description='Urine', collectingMethod='Collected in ManyLabs', Category/categories selected: Hemograma}";
        Assert.assertEquals(true, expected.equals(test.toString()));
    }

    @Test
    public void checkEquals() {
        TestType e = new TestType("23456", "Covid19", "Collected in Labs", categoryList);
        TestType d = new TestType("23456", "Covid19", "Collected in Labs", categoryList);
        Assert.assertEquals(true, e.equals(d));
    }

    @Test
    public void checkEquals2() {
        TestType e = new TestType("23456", "Covid19", "Collected in Labs", categoryList);
        TestType l2 = new TestType("23456", "Urine", "Collected in Labs", categoryList);

        Assert.assertEquals(false, e.equals(l2));
    }

    @Test
    public void checkHashCode() {
        TestType l = new TestType("23456", "Covid19", "Collected in Labs", categoryList);
        assertEquals(l.hashCode(), Objects.hash("23456", "Covid19", "Collected in Labs", categoryList));
    }

    @Test
    public void checkGets() {
        TestType l = new TestType("23456", "Covid19", "Collected in Labs", categoryList);
        Assert.assertEquals(l.getCode(), "23456");
        Assert.assertEquals(l.getDescription(), "Covid19");
        Assert.assertEquals(l.getCollectingMethod(), "Collected in Labs");
    }

}