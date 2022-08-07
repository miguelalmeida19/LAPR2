package app.domain.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class TestParameterResultTest {
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    public TestParameterResult pc;
    @Before
    public void setUp() throws Exception {
        ReferenceValue ref = new ReferenceValue(100, 10);
        pc = new TestParameterResult("123", "mg", ref);
    }

    @Test
    public void getMetric() {
        Assert.assertEquals(true, this.pc.getMetric().equals("mg"));
    }

    @Test
    public void getRefValue() {
        ReferenceValue ref = new ReferenceValue(100, 10);
        Assert.assertEquals(true, this.pc.getRefValue().getRefValueMax() == (ref.getRefValueMax()) && this.pc.getRefValue().getRefValueMin() == (ref.getRefValueMin()));
    }

    @Test
    public void getResult() {
        Assert.assertEquals(true, this.pc.getResult().equals("123"));
    }
}