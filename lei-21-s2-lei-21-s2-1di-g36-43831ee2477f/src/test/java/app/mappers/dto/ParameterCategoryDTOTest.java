package app.mappers.dto;

import app.domain.model.ParameterCategory;
import app.mappers.ParameterCategoryMapper;
import app.mappers.ParameterMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class ParameterCategoryDTOTest {

    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();
    public ParameterCategory pc;
    public ParameterCategoryDTO pcDTO;
    public ParameterCategoryMapper parameterMapper = new ParameterCategoryMapper();

    @Before
    public void setUp() throws Exception {
        pc = new ParameterCategory("12345", "Categoria");
        pcDTO = parameterMapper.toDTO(pc);
    }

    @Test
    public void getCode() {
        Assert.assertTrue(pcDTO.getCode().equals("12345"));
    }

    @Test
    public void getDescription() {
        Assert.assertTrue(pcDTO.getname().equals("Categoria"));
    }
}