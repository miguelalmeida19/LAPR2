package app.mappers;

import app.controller.App;
import app.domain.model.ParameterCategory;
import app.domain.model.TestType;
import app.domain.store.TestTypeStore;
import app.mappers.dto.TestTypeDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestTypeMapperTest {
    ParameterCategory p = new ParameterCategory("12345", "pc1");
    List<ParameterCategory> parameterCategoryList = new ArrayList<>();

    @Before
    public void setUp() throws Exception {


    }

    @Test
    public void toDTO() {
        TestTypeMapper testTypeMapper = new TestTypeMapper();
        parameterCategoryList.add(p);

        TestType testType = new TestType("12345", "testType", "cm", parameterCategoryList);

        Assert.assertTrue(testTypeMapper.toDTO(testType).getCode().equals(testType.getCode()));
        Assert.assertTrue(testTypeMapper.toDTO(testType).getCollectingMethod().equals(testType.getCollectingMethod()));
        Assert.assertTrue(testTypeMapper.toDTO(testType).getDescription().equals(testType.getDescription()));
        for (int n = 0; n < parameterCategoryList.size(); n++) {
            // the parameter category don't have a .equals implemented...
            //np, just lets do the equals manually :))
            Assert.assertTrue(testTypeMapper.toDTO(testType).getCategoriesList().get(n).getCode().equals(testType.getCategoriesList().get(n).getCode()));
            Assert.assertTrue(testTypeMapper.toDTO(testType).getCategoriesList().get(n).getname().equals(testType.getCategoriesList().get(n).getName()));

        }
    }

    @Test
    public void toDTOList() {
        TestTypeMapper testTypeMapper = new TestTypeMapper();
        parameterCategoryList.add(p);

        TestType testType = new TestType("12345", "testType", "cm", parameterCategoryList);
        TestType testType1 = new TestType("14345", "testType1", "cm1", parameterCategoryList);
        List<TestType> testTypeList = new ArrayList<>();
        testTypeList.add(testType);
        testTypeList.add(testType1);
        for (int j = 0; j < testTypeList.size(); j++) {
            Assert.assertTrue(testTypeMapper.toDTO(testTypeList).get(j).getCode().equals(testTypeList.get(j).getCode()));
            Assert.assertTrue(testTypeMapper.toDTO(testTypeList).get(j).getCollectingMethod().equals(testTypeList.get(j).getCollectingMethod()));
            Assert.assertTrue(testTypeMapper.toDTO(testTypeList).get(j).getDescription().equals(testTypeList.get(j).getDescription()));
            for (int n = 0; n < parameterCategoryList.size(); n++) {
                // the parameter category don't have a .equals implemented...
                //np, just lets do the equals manually :))
                Assert.assertTrue(testTypeMapper.toDTO(testTypeList).get(j).getCategoriesList().get(n).getCode().equals(testTypeList.get(j).getCategoriesList().get(n).getCode()));
                Assert.assertTrue(testTypeMapper.toDTO(testTypeList).get(j).getCategoriesList().get(n).getname().equals(testTypeList.get(j).getCategoriesList().get(n).getName()));

            }
        }
    }

    @Test
    public void toTestType() {
        TestTypeMapper testTypeMapper = new TestTypeMapper();
        TestTypeStore testTypeStore = App.getInstance().getCompany().getTestTypeStore();

        parameterCategoryList.add(p);
        TestType testType253 = new TestType("12645", "testType", "cm", parameterCategoryList);
        testTypeStore.addTestType(testType253);

        TestTypeDTO testTypeDTO = testTypeMapper.toDTO(testType253);

        Assert.assertTrue(testTypeMapper.toTestType(testTypeDTO).getCode().equals(testType253.getCode()));
        System.out.println(testTypeMapper.toTestType(testTypeDTO).getCollectingMethod()+"  "+(testType253.getCollectingMethod()));
        System.out.println(testTypeMapper.toTestType(testTypeDTO));
        System.out.println();
        Assert.assertTrue(testTypeMapper.toTestType(testTypeDTO).getCollectingMethod().equals(testType253.getCollectingMethod()));
        Assert.assertTrue(testTypeMapper.toTestType(testTypeDTO).getDescription().equals(testType253.getDescription()));
        for (int n = 0; n < parameterCategoryList.size(); n++) {
            // the parameter category don't have a .equals implemented...
            //np, just lets do the equals manually :))
            Assert.assertTrue(testTypeMapper.toTestType(testTypeDTO).getCategoriesList().get(n).getCode().equals(testType253.getCategoriesList().get(n).getCode()));
            Assert.assertTrue(testTypeMapper.toTestType(testTypeDTO).getCategoriesList().get(n).getName().equals(testType253.getCategoriesList().get(n).getName()));

        }

    }
}