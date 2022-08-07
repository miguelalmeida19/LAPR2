package app.mappers.dto;

import app.domain.model.Parameter;
import app.domain.model.ParameterCategory;
import app.domain.model.TestType;
import app.mappers.ParameterCategoryMapper;
import app.mappers.TestTypeMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestTypeDTOTest {
    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();
    public TestType test;
    public List<ParameterCategory> categoryList = new ArrayList<>();
    public List<TestType> testTypeList = new ArrayList<>();
    TestTypeMapper testTypeMapper = new TestTypeMapper();
    ParameterCategoryMapper parameterCategoryMapper = new ParameterCategoryMapper();
    TestTypeDTO testTypeDTO;

    @Before
    public void setUp() throws Exception {
        categoryList.add(new ParameterCategory("12345", "Hemograma"));
        testTypeList.add(new TestType("23456", "Covid19", "Collected in Labs", categoryList));
        test = new TestType("12346", "Urine", "Collected in ManyLabs", categoryList);
        testTypeDTO = testTypeMapper.toDTO(test);
    }

    @Test
    public void getDescription() {
        Assert.assertTrue(testTypeDTO.getDescription().equals("Urine"));
    }

    @Test
    public void getCode() {
        Assert.assertTrue(testTypeDTO.getCode().equals("12346"));

    }

    @Test
    public void getCollectingMethod() {
        Assert.assertTrue(testTypeDTO.getCollectingMethod().equals("Collected in ManyLabs"));
    }
}