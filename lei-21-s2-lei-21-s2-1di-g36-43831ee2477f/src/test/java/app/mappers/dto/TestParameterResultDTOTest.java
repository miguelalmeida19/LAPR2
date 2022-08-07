package app.mappers.dto;

import app.domain.model.Parameter;
import app.domain.model.ReferenceValue;
import app.domain.model.TestParameterResult;
import app.mappers.ParameterResultMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class TestParameterResultDTOTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    public TestParameterResult pc;
    public ParameterResultMapper parameterResultMapper = new ParameterResultMapper();
    public TestParameterResultDTO parameterResultDTO;
    public ReferenceValue ref;

    @Before
    public void setUp() throws Exception {
        ref = new ReferenceValue(100, 10);
        pc = new TestParameterResult("123", "mg", ref);
        parameterResultDTO = parameterResultMapper.toDTO(pc);
    }

    @Test
    public void getMetric() {
        Assert.assertTrue(parameterResultDTO.getMetric().equals("mg"));
    }

    @Test
    public void getResult() {
        Assert.assertTrue(parameterResultDTO.getResult().equals("123"));
    }

    @Test
    public void getRefValueString() {
        Assert.assertTrue(parameterResultDTO.getRefValueString().equals("ReferenceValue{refValueMax=100.0, refValueMin=10.0, metric='null'}"));
    }

    @Test
    public void getRefValue() {
        Assert.assertTrue(parameterResultDTO.getRefValue().equals(ref));

    }
}