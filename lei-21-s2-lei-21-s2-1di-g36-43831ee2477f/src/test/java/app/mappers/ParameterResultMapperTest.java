package app.mappers;

import app.domain.model.ReferenceValue;
import app.domain.model.TestParameterResult;
import app.mappers.dto.TestParameterResultDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ParameterResultMapperTest {
    TestParameterResult testParameterResult = new TestParameterResult("18","g/l",new ReferenceValue(18.2,19.0));
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void toDTO() {
        ParameterResultMapper parameterResultMapper = new ParameterResultMapper();

        Assert.assertTrue(parameterResultMapper.toDTO(testParameterResult).getResult().equals(testParameterResult.getResult()));
        Assert.assertTrue(parameterResultMapper.toDTO(testParameterResult).getMetric().equals(testParameterResult.getMetric()));
        Assert.assertTrue(parameterResultMapper.toDTO(testParameterResult).getRefValue().equals(testParameterResult.getRefValue()));
    }

    @Test
    public void toDtoList() {
        ParameterResultMapper parameterResultMapper = new ParameterResultMapper();
        TestParameterResult testParameterResult2 = new TestParameterResult("19","g/ml",new ReferenceValue(18.3,15.0));
        List<TestParameterResult> testParameterResultList = new ArrayList<>();
        testParameterResultList.add(testParameterResult2);
        testParameterResultList.add(testParameterResult);

        for(int n= 0; n < testParameterResultList.size();n++ ){
            Assert.assertTrue(parameterResultMapper.toDTO(testParameterResultList).get(n).getResult().equals(testParameterResultList.get(n).getResult()));
            Assert.assertTrue(parameterResultMapper.toDTO(testParameterResultList).get(n).getMetric().equals(testParameterResultList.get(n).getMetric()));
            Assert.assertTrue(parameterResultMapper.toDTO(testParameterResultList).get(n).getRefValue().equals(testParameterResultList.get(n).getRefValue()));
        }

    }

    @Test
    public void toTest() {
        ParameterResultMapper parameterResultMapper = new ParameterResultMapper();
        TestParameterResultDTO parameterResultDTO = parameterResultMapper.toDTO(testParameterResult);
        Assert.assertTrue(parameterResultMapper.toTest(parameterResultDTO).getResult().equals(testParameterResult.getResult()));
        Assert.assertTrue(parameterResultMapper.toTest(parameterResultDTO).getMetric().equals(testParameterResult.getMetric()));
        Assert.assertTrue(parameterResultMapper.toTest(parameterResultDTO).getRefValue().equals(testParameterResult.getRefValue()));
    }
}