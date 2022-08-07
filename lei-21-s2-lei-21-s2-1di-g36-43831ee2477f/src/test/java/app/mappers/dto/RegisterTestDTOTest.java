package app.mappers.dto;

import app.domain.model.Parameter;
import app.domain.model.ParameterCategory;
import app.domain.model.TestType;
import app.mappers.ParameterMapper;
import app.mappers.TestTypeMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RegisterTestDTOTest {

    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();
    public RegisterTestDTO registerTestDTO;
    public TestType test;
    public TestTypeDTO testTypeDTO;
    public TestTypeMapper testTypeMapper = new TestTypeMapper();
    public ParameterMapper parameterMapper = new ParameterMapper();
    public List<ParameterCategory> categoryList = new ArrayList<>();
    public ParameterCategory pc;
    public LaboratoryDTO laboratoryDTO;
    public Parameter p;
    public List<TestType> testTypeList = new ArrayList<>();
    public List<Parameter> parameterList = new ArrayList<>();
    public List<ParameterDTO> parameterDTOList = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        test = new TestType("12346", "Urine", "Collected in ManyLabs", categoryList);
        testTypeDTO = testTypeMapper.toDTO(test);
        pc = new ParameterCategory("12345", "ola");
        p = new Parameter("51234","name",pc,"dexcription");
        testTypeList.add(test);
        laboratoryDTO = new LaboratoryDTO("Big Lab", "12345", "Rua Fixe", "12345678901", "1234567890", testTypeList);
        parameterList.add(p);
        parameterDTOList = parameterMapper.toDTO(parameterList);
        registerTestDTO = new RegisterTestDTO(testTypeDTO, parameterDTOList, "123456789012", laboratoryDTO, "1234567892");
    }

    @Test
    public void getParameterDTOS() {
        Assert.assertTrue(parameterDTOList.equals(registerTestDTO.getParameterDTOS()));
    }

    @Test
    public void getLaboratoryDTO() {
        Assert.assertTrue(laboratoryDTO.equals(registerTestDTO.getLaboratoryDTO()));
    }

    @Test
    public void getTIN() {
        Assert.assertTrue(("1234567892").equals(registerTestDTO.getTIN()));
    }

    @Test
    public void getNhsCode() {
        Assert.assertTrue("123456789012".equals(registerTestDTO.getNhsCode()));

    }

    @Test
    public void getTestType() {
        Assert.assertTrue((testTypeDTO).equals(registerTestDTO.getTestType()));
    }
}