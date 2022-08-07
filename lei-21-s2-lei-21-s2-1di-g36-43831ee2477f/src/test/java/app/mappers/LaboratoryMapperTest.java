package app.mappers;

import app.domain.model.Laboratory;
import app.domain.model.ParameterCategory;
import app.domain.model.TestType;
import app.mappers.dto.ClientDTO;
import app.mappers.dto.LaboratoryDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class LaboratoryMapperTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    public Laboratory lab;
    public List<TestType> list = new ArrayList<>();
    public List<ParameterCategory> pc = new ArrayList<>();
    public LaboratoryDTO labDto;
    public LaboratoryMapper laboratoryMapper;

    @Before
    public void setUp() throws Exception {
        laboratoryMapper = new LaboratoryMapper();
        pc.add(new ParameterCategory("12345", "ola"));
        TestType t = new TestType("123a5", "test1", "test", this.pc);
        list.add(t);
        lab = new Laboratory("lab1", "12345", "ola", "12345678901", "1234567890", list);
        labDto = laboratoryMapper.toDTO(lab);
    }

    @Test
    public void toDTO() {
        LaboratoryDTO laboratoryDTO = new LaboratoryDTO("lab1", "12345", "ola", "12345678901", "1234567890", list);
        Assert.assertTrue(laboratoryMapper.toDTO(lab).getPhoneNumber().equals(laboratoryDTO.getPhoneNumber()));
        Assert.assertTrue(laboratoryMapper.toDTO(lab).getNameOfLaboratory().equals(laboratoryDTO.getNameOfLaboratory()));
        Assert.assertTrue(laboratoryMapper.toDTO(lab).getAddress().equals(laboratoryDTO.getAddress()));
        Assert.assertTrue(laboratoryMapper.toDTO(lab).getId().equals(laboratoryDTO.getId()));
        Assert.assertTrue(laboratoryMapper.toDTO(lab).getTin().equals(laboratoryDTO.getTin()));
        Assert.assertTrue(laboratoryMapper.toDTO(lab).getTestsOfLab().equals(laboratoryDTO.getTestsOfLab()));
    }

    @Test
    public void toDTOList() {
        Laboratory lab2 = new Laboratory("lab2","12345","test","12345678901","1234567890",list);
        List<Laboratory> laboratoryList = new ArrayList<>();
        laboratoryList.add(lab);
        laboratoryList.add(lab2);
        for (int n = 0; n < laboratoryList.size(); n++){
                Assert.assertTrue(laboratoryMapper.toDTO(laboratoryList.get(n)).getPhoneNumber().equals(laboratoryMapper.toDTO(laboratoryList).get(n).getPhoneNumber()));
                Assert.assertTrue(laboratoryMapper.toDTO(laboratoryList.get(n)).getAddress().equals(laboratoryMapper.toDTO(laboratoryList).get(n).getAddress()));
                Assert.assertTrue(laboratoryMapper.toDTO(laboratoryList.get(n)).getId().equals(laboratoryMapper.toDTO(laboratoryList).get(n).getId()));
                Assert.assertTrue(laboratoryMapper.toDTO(laboratoryList.get(n)).getTin().equals(laboratoryMapper.toDTO(laboratoryList).get(n).getTin()));
                Assert.assertTrue(laboratoryMapper.toDTO(laboratoryList.get(n)).getTestsOfLab().equals(laboratoryMapper.toDTO(laboratoryList).get(n).getTestsOfLab()));
                Assert.assertTrue(laboratoryMapper.toDTO(laboratoryList.get(n)).getNameOfLaboratory().equals(laboratoryMapper.toDTO(laboratoryList).get(n).getNameOfLaboratory()));

            }
        }



    @Test
    public void toLaboratory() {
        LaboratoryDTO laboratoryDTO = laboratoryMapper.toDTO(lab);
        Assert.assertTrue(laboratoryMapper.toLaboratory(laboratoryDTO).getPhoneNumber().equals(lab.getPhoneNumber()));
        Assert.assertTrue(laboratoryMapper.toLaboratory(laboratoryDTO).getNameOfLaboratory().equals(lab.getNameOfLaboratory()));
        Assert.assertTrue(laboratoryMapper.toLaboratory(laboratoryDTO).getAddress().equals(lab.getAddress()));
        Assert.assertTrue(laboratoryMapper.toLaboratory(laboratoryDTO).getID().equals(lab.getID()));
        Assert.assertTrue(laboratoryMapper.toLaboratory(laboratoryDTO).getTIN().equals(lab.getTIN()));
        Assert.assertTrue(laboratoryMapper.toLaboratory(laboratoryDTO).getTestsOfLab().equals(lab.getTestsOfLab()));
    }
}