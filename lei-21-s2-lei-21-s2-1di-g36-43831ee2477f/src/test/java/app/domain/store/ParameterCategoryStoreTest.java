package app.domain.store;

import app.domain.model.Employee;
import app.domain.model.ParameterCategory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Categories;
import org.junit.rules.ExpectedException;
import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ParameterCategoryStoreTest {
    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();
    public ParameterCategory pc;
    public ParameterCategoryStore store;

    @Before
    public void setUp() throws Exception {
        pc = new ParameterCategory("12345", "Categoria");
        store = new ParameterCategoryStore();
    }

    @Test
    public void createParametercategory() {
        ParameterCategory pc1 = store.createParametercategory("21313", "Category");
        ParameterCategory pc2 = new ParameterCategory("21313", "Category");
        Assert.assertEquals(true, pc1.equals(pc2));
    }

    @Test
    public void validateParameterCategoryDifferent() {
        ParameterCategory pc1 = new ParameterCategory("12345", "Categoria");
        ParameterCategory pc2 = new ParameterCategory("12345", "Categoria");
        ParameterCategoryStore parameterCategoryStore = new ParameterCategoryStore();
        parameterCategoryStore.add(pc1);
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Parameter Category already exists");
        parameterCategoryStore.validateParameterCategory(pc2);
    }
    @Test
    public void validateParameterCategoryCorrect(){
        ParameterCategory pc1 = new ParameterCategory("12313", "Hemograma");
        ParameterCategory pc2 = new ParameterCategory("12321", "Hemograma");
        ParameterCategoryStore parameterCategoryStore = new ParameterCategoryStore();
        parameterCategoryStore.add(pc1);
        Assert.assertTrue(parameterCategoryStore.validateParameterCategory(pc2));
    }
    @Test
    public void validateParameterCategoryNull(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Parameter Category cannot be null");
        ParameterCategory pc1 = null;
        ParameterCategoryStore parameterCategoryStore = new ParameterCategoryStore();
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Parameter Category cannot be null");
        parameterCategoryStore.validateParameterCategory(pc1);
    }

    @Test
    public void saveParameterCategory() {
        store.saveParameterCategory(pc);
    }

    @Test
    public void getParameterCategoryByDescription() {
        store.add(pc);
        store.getParameterCategoryByDescription(pc.getName());
        //System.out.println(store.getParameterCategoryByDescription(pc.getName()));
        Assert.assertEquals(true, store.getParameterCategoryByDescription(pc.getName()).equals(pc));
    }

    @Test
    public void add() {
      store.add(pc);
      store.getParameterCategoryList().contains(pc);
    }
    @Test
    public void addWrong() {
        store.add(pc);
        ParameterCategory pc1 = new ParameterCategory("12345", "Cat");
        Assert.assertEquals(false,store.getParameterCategoryList().contains(pc1));
    }
    @Test
    public void addWrongcode() {
        store.add(pc);
        ParameterCategory pc1 = new ParameterCategory("12344", "Categoria");
        Assert.assertEquals(false,store.getParameterCategoryList().contains(pc1));
    }

    @Test
    public void getParameterCategoryList() {
        ParameterCategoryStore parameterCategoryStore = new ParameterCategoryStore();

        ParameterCategory pc1 = new ParameterCategory("12344", "Categoria");
        ParameterCategory pc2 = new ParameterCategory("12345", "Cat");
        parameterCategoryStore.add(pc1);
        parameterCategoryStore.add(pc2);

        List<ParameterCategory> parameterCategoryList = new ArrayList<>();
        parameterCategoryList.add(pc1);
        parameterCategoryList.add(pc2);


        Assert.assertEquals(true, parameterCategoryStore.getParameterCategoryList().equals(parameterCategoryList));
    }
}