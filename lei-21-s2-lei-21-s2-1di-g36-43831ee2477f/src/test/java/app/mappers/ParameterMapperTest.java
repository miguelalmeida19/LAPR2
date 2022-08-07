package app.mappers;

import app.controller.App;
import app.domain.model.Parameter;
import app.domain.model.ParameterCategory;
import app.domain.store.ParameterStore;
import app.mappers.dto.ParameterDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

public class ParameterMapperTest {

    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();
    public Parameter pc;
    public ParameterCategory pcc;

    public ParameterDTO pcDTO;
    public ParameterMapper parameterMapper = new ParameterMapper();

    @Before
    public void setUp() throws Exception {
        pcc = new ParameterCategory("12345", "Categoria");
        pc = new Parameter("12345","par",pcc,"description");
        pcDTO = parameterMapper.toDTO(pc);
    }

    @Test
    public void toDTO() {
        Assert.assertTrue(parameterMapper.toDTO(pc).getName().equals(pc.getName()));
        Assert.assertTrue(parameterMapper.toDTO(pc).getCode().equals(pc.getCode()));
        Assert.assertTrue(parameterMapper.toDTO(pc).getCategory().equals(pc.getCategory()));
        Assert.assertTrue(parameterMapper.toDTO(pc).getDescription().equals(pc.getDescription()));
    }


    @Test
    public void ToDTOList() {
        Parameter pc1=  new Parameter("12222","par",pcc,"description");
        List<Parameter> parameterList = new ArrayList<>();
        parameterList.add(pc);
        parameterList.add(pc1);
        for (int n = 0; n < parameterList.size(); n++) {
            Assert.assertTrue(parameterMapper.toDTO(parameterList).get(n).getCode().equals(parameterList.get(n).getCode()));
            Assert.assertTrue(parameterMapper.toDTO(parameterList).get(n).getName().equals(parameterList.get(n).getName()));
            Assert.assertTrue(parameterMapper.toDTO(parameterList).get(n).getCategory().equals(parameterList.get(n).getCategory()));
            Assert.assertTrue(parameterMapper.toDTO(parameterList).get(n).getDescription().equals(parameterList.get(n).getDescription()));

        }

    }

    @Test
    public void toParameterList() {
        Parameter pc1=  new Parameter("12222","par",pcc,"description");
        List<Parameter> parameterList = new ArrayList<>();
        parameterList.add(pc);
        parameterList.add(pc1);
        ParameterStore parameterStore = App.getInstance().getCompany().getParameterStore();
        parameterStore.addParameter(pc);
        parameterStore.addParameter(pc1);

        List<ParameterDTO> parameterDTOList  = parameterMapper.toDTO(parameterList);
        for (int n = 0; n < parameterList.size(); n++) {
            Assert.assertTrue(parameterMapper.toParameterList(parameterDTOList).get(n).getCode().equals(parameterDTOList.get(n).getCode()));
            Assert.assertTrue(parameterMapper.toParameterList(parameterDTOList).get(n).getName().equals(parameterDTOList.get(n).getName()));
            Assert.assertTrue(parameterMapper.toParameterList(parameterDTOList).get(n).getCategory().equals(parameterDTOList.get(n).getCategory()));
            Assert.assertTrue(parameterMapper.toParameterList(parameterDTOList).get(n).getDescription().equals(parameterDTOList.get(n).getDescription()));

        }

    }
}