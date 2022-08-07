package app.domain.store;

import app.domain.model.ParameterCategory;
import app.domain.model.TestType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestTypeStoreTest {
    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();
    public TestType test;
    public List<ParameterCategory> categoryList = new ArrayList<>();
    public List<TestType> testTypeList = new ArrayList<>();
    public TestTypeStore store;
    @Before
    public void setUp() throws Exception {
        categoryList.add(new ParameterCategory("12345","Hemograma"));
        testTypeList.add(new TestType("23456", "Covid19","Collected in Labs",categoryList));
        test = new TestType("12346", "Urine","Collected in ManyLabs",categoryList);
    }
    @Test
    public void createNewTestType() {
        TestType test = new TestType("12345","Urine","InManyLabs",categoryList);
        TestTypeStore TestTypeStore = new TestTypeStore();
        TestType t1= TestTypeStore.createTestType("12345","Urine","InManyLabs",categoryList);
        Assert.assertEquals(true, t1.equals(test));
    }
    @Test
    public void validateTestEquals()  {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("invalid arguments for test creation.");
        TestType t2= new TestType("12346", "Urine","Collected in ManyLabs",categoryList);
        TestTypeStore TestTypeStore = new TestTypeStore();
        TestTypeStore.addTestType(test);
        TestTypeStore.validateTest(t2);
    }
    @Test
    public void save(){
        TestTypeStore TestTypeStore = new TestTypeStore();
        TestTypeStore.saveTest(test);
    }
    @Test
    public void getTestTypes() {
        TestTypeStore TestTypeStore = new TestTypeStore();
        TestType test2 = new TestType("12346", "Urine","Collected in ManyLabs",categoryList);;
        TestTypeStore.addTestType(test);
        TestTypeStore.addTestType(test2);

        List<String> TestList = new ArrayList<>();
        TestList.add(test.getCode()+ " - "+test.getDescription());
        TestList.add(test2.getCode()+ " - "+test2.getDescription());

        TestTypeStore.getTestTypes().equals(TestList);
    }
    @Test
    public void getTestTypeByCodes(){
        List<TestType> testTypes = new ArrayList<>();
        TestTypeStore testTypeStore = new TestTypeStore();
        List<ParameterCategory> parameterCategoryListBlood = new ArrayList<>();
        parameterCategoryListBlood.add(new ParameterCategory("he001", "Hemogram"));
        parameterCategoryListBlood.add(new ParameterCategory("co001", "Cholesterol"));
        List<ParameterCategory> parameterCategoryListCovid = new ArrayList<>();
        parameterCategoryListCovid.add(new ParameterCategory("sw001", "Swab"));
        List<ParameterCategory> parameterCategoryListFezes = new ArrayList<>();
        parameterCategoryListFezes.add(new ParameterCategory("fe001", "Fezes"));
        testTypes.add(new TestType("12345", "Fezes", "Analysis", parameterCategoryListFezes));

        List<String> testTypeCodesList = new ArrayList<>();
        for (TestType t : testTypeStore.getListOfTestType()){
            testTypeCodesList.add(t.getCode() + " - " + t.getDescription());
        }
        Assert.assertTrue(testTypeStore.getTestTypeByCodes(testTypeCodesList).equals(testTypes));
    }
}