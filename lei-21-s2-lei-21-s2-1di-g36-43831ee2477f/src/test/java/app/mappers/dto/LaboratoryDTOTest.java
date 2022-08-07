package app.mappers.dto;

import app.domain.model.Laboratory;
import app.domain.model.ParameterCategory;
import app.domain.model.TestType;
import app.mappers.LaboratoryMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class LaboratoryDTOTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    public Laboratory lab;
    public List<TestType> list = new ArrayList<>();
    public List<ParameterCategory> pc = new ArrayList<>();
    public LaboratoryDTO labDto;
    public LaboratoryMapper laboratoryMapper = new LaboratoryMapper();

    @Before
    public void setUp() throws Exception {
        pc.add(new ParameterCategory("12345", "ola"));
        TestType t = new TestType("123a5", "test1", "test", this.pc);
        list.add(t);
        lab = new Laboratory("lab1", "12345", "ola", "12345678901", "1234567890", list);
        labDto = laboratoryMapper.toDTO(lab);
    }

    @Test
    public void getPhoneNumber() {
        Assert.assertTrue(labDto.getPhoneNumber().equals("12345678901"));
    }

    @Test
    public void getAddress() {
        Assert.assertTrue(labDto.getAddress().equals("ola"));
    }

    @Test
    public void getId() {
        Assert.assertTrue(labDto.getId().equals("12345"));
    }

    @Test
    public void getTestsOfLab() {
        Assert.assertTrue(labDto.getTestsOfLab().equals(list));

    }

    @Test
    public void getNameOfLaboratory() {
        Assert.assertTrue(labDto.getNameOfLaboratory().equals("lab1"));

    }

    @Test
    public void getTin() {
        Assert.assertTrue(labDto.getTin().equals("1234567890"));
    }
}