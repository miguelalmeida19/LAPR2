package app.mappers.dto;

import app.domain.model.Parameter;
import app.domain.model.ParameterCategory;
import app.mappers.ParameterMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class ParameterDTOTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    public ParameterCategory pc;
    public ParameterDTO pDto;
    public Parameter p;
    public ParameterMapper parameterMapper = new ParameterMapper();
    @Before
    public void setUp() throws Exception {

        pc = new ParameterCategory("12345", "ola");

        p = new Parameter("51234","name",pc,"dexcription");

        pDto = parameterMapper.toDTO(p);

    }

    @Test
    public void getCode() {
        Assert.assertTrue(pDto.getCode().equals("51234"));

    }

    @Test
    public void getDescription() {
        Assert.assertTrue(pDto.getDescription().equals("dexcription"));
    }

    @Test
    public void getName() {
        Assert.assertTrue(pDto.getName().equals("name"));
    }

    @Test
    public void getCategory() {
        Assert.assertTrue(pDto.getCategory().equals(pc));
    }

    @Test
    public void testToString() {
        Assert.assertTrue(pDto.toString().equals("ParameterDTO{code='51234', name='name', category=ola, description='dexcription'}"));
    }
}