package app.mappers;

import app.controller.App;
import app.domain.model.ParameterCategory;
import app.domain.store.ParameterCategoryStore;
import app.mappers.dto.ParameterCategoryDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ParameterCategoryMapperTest {

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
    public void toDTO() {

        Assert.assertTrue(parameterMapper.toDTO(pc).getname().equals(pc.getName()));
        Assert.assertTrue(parameterMapper.toDTO(pc).getCode().equals(pc.getCode()));
    }

    @Test
    public void ToDTOList() {
        ParameterCategory pc1 = new ParameterCategory("11111", "cheiroso");
        List<ParameterCategory> parameterCategoryList = new ArrayList<>();
        parameterCategoryList.add(pc);
        parameterCategoryList.add(pc1);
        for (int n = 0; n < parameterCategoryList.size(); n++) {
            Assert.assertTrue(parameterMapper.toDTO(parameterCategoryList).get(n).getCode().equals(parameterCategoryList.get(n).getCode()));
            Assert.assertTrue(parameterMapper.toDTO(parameterCategoryList).get(n).getname().equals(parameterCategoryList.get(n).getName()));

        }

    }

    @Test
    public void toParameterCategoryList() {
        ParameterCategory pc1 = new ParameterCategory("11111", "cheiroso");
        List<ParameterCategory> parameterCategoryList = new ArrayList<>();
        parameterCategoryList.add(pc);
        parameterCategoryList.add(pc1);
        ParameterCategoryStore parameterCategoryStore = App.getInstance().getCompany().getParameterCategoryStore();
        parameterCategoryStore.add(pc);
        parameterCategoryStore.add(pc1);

        List<ParameterCategoryDTO> parameterCategoryDTOList  = parameterMapper.toDTO(parameterCategoryList);
        for (int n = 0; n < parameterCategoryList.size(); n++) {
            Assert.assertTrue(parameterMapper.toParameterCategoryList(parameterCategoryDTOList).get(n).getCode().equals(parameterCategoryDTOList.get(n).getCode()));

            Assert.assertTrue(parameterMapper.toParameterCategoryList(parameterCategoryDTOList).get(n).getName().equals(parameterCategoryDTOList.get(n).getname()));

        }

    }
}