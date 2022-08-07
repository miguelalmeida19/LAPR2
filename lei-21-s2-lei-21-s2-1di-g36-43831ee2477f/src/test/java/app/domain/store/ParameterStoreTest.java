package app.domain.store;

import app.domain.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ParameterStoreTest {
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    public ParameterCategory pc2;
    public ParameterCategory pc;
    public Parameter p;
    @Before
    public void setUp() throws Exception {
        pc = new ParameterCategory("12345", "ola");
        pc2 = new ParameterCategory("12346","hi");
        p = new Parameter("12345","name",pc,"description");

    }
    @Test
    public void createParameter() {
        Parameter p = new Parameter("12345","name",pc,"description");
        ParameterStore ParameterStore = new ParameterStore();
        Parameter p1= ParameterStore.createParameter("12345","name","description",pc);
        Assert.assertEquals(true, p1.equals(p1));
    }

    @Test
    public void validateParameterWrong() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("The parameter has already been created." );

        ParameterStore l = new ParameterStore();
        l.addParameter(p);
        Parameter l2 = new Parameter("12345", "name", pc, "description");;
        l.validateParameter(l2);
    }

   @Test
    public void validateParameterCorrect() {

        ParameterStore l = new ParameterStore();
        l.addParameter(p);
        Parameter l2 = new Parameter("12345", "name", pc2, "description");;
        l.validateParameter(l2);
    }

    @Test
    public void addParameter() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("The parameter has already been created." );
        ParameterStore l = new ParameterStore();
        l.addParameter(p);
        l.validateParameter(p);

    }
    @Test
    public void saveParameter(){
        ParameterStore ParameterStore = new ParameterStore();
        ParameterStore.saveParameter(p);
    }

    @Test
    public void getListOfParametersByCategoryList(){
        ParameterStore parameterStore = new ParameterStore();
        Parameter parameter = new Parameter("HB000", "HB", new ParameterCategory("he001", "Hemogram"), "Blood Cells");
        ParameterCategory parameterCategory = new ParameterCategory("he001", "Hemogram");
        List<Parameter> parameterList = new ArrayList<>();
        List<ParameterCategory> parameterCategoryList = new ArrayList<>();
        parameterList.add(parameter);
        parameterStore.addParameter(parameter);
        parameterCategoryList.add(parameterCategory);
        Assert.assertTrue(parameterStore.getListOfParametersByCategoryList(parameterCategoryList).equals(parameterList));
    }

    @Test
    public void getListOfParameters(){
        List<Parameter> parameterList = new ArrayList<>();
        ParameterStore parameterStore = new ParameterStore();
        Parameter parameter = new Parameter("HB000", "HB", new ParameterCategory("he001", "Hemogram"), "Blood Cells");
        parameterList.add(parameter);
        parameterStore.addParameter(parameter);
        Assert.assertTrue(parameterStore.getListOfParameters().equals(parameterList));
    }
}