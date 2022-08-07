package app.mappers.dto;

import app.controller.App;
import app.domain.model.*;
import app.domain.store.ClientStore;
import app.mappers.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class TestDTOTest {
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    private List<Parameter> parameterList = new ArrayList<>();
    private List<ParameterCategory> parameterCategoryList = new ArrayList<>();
    private app.domain.model.Test test;
    private TestType testType;
    private Laboratory lab;
    private List<Sample> sampleList;
    private ClientStore clientStore;
    TestMapper testMapper = new TestMapper();
    LaboratoryMapper laboratoryMapper = new LaboratoryMapper();
    TestDTO testDTO;
    List<TestType> testTypeList = new ArrayList<>();
    Report report;

    @Before
    public void setUp() throws Exception {
        clientStore = App.getInstance().getCompany().getClientStore();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = df.parse("23-12-1998");
        clientStore.getClients().add(new Client("1234567890123456", "1234567890", 1234567890, date, "Male", "12345678910", "Miguel", "miguel@ipp.pt", "Rua Fixe"));
        parameterList.add(new Parameter("HB000", "HB", new ParameterCategory("he001", "Hemogram"), "Botar sangue"));
        parameterCategoryList.add(new ParameterCategory("he001", "Hemogram"));
        testType = new TestType("12345", "Blood", "cheirete", parameterCategoryList);
        testTypeList.add(testType);
        lab = new Laboratory("lab1", "12345", "address", "12345678901", "1234567890", testTypeList);
        test = new app.domain.model.Test(testType, parameterList, "123456789012", lab, "1234567890");
        testDTO = testMapper.toDTO(test);
        report = new Report("dia", "report");
    }

    @Test
    public void getNhsCode() {
        Assert.assertTrue(testDTO.getNhsCode().equals("123456789012"));
    }

    @Test
    public void getTestParameterFor() {
        TestParameter testParameter = new TestParameter(new Parameter("HB000", "HB", new ParameterCategory("he001", "Hemogram"), "Botar sangue"));
        Assert.assertTrue(testDTO.getTestParameterFor("HB000").equals(testParameter));

    }
}