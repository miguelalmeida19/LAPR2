package app.domain.store;

import app.Persistence;
import app.controller.App;
import app.domain.model.*;
import app.mappers.ClientMapper;
import app.mappers.dto.ClientDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ClientStoreTest {
    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();
    public Client cli;
    public List<Client> clientList = new ArrayList<>();
    public Client cli2;


    @Before
    public void setUp() throws Exception {

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = df.parse("23-12-1998");
        clientList.add(new Client("1234567890123456","1234567890", 1234567890, date, "Male", "12345678910", "Miguel","miguel@ipp.pt", "Rua Fixe"));
        cli = new Client("1234567890123452","1234567860", 1234567891,  date, "Male", "12345678901", "Pedro","pedro@ipp.pt", "Rua Fixe");
         cli2 = new Client("1234567890123452","1234567860", 1234567999,  date, "Male", "12345678901", "Pedro","pedro@ipp.pt", "Rua Fixe");
    }

    @Test
    public void createNewClient() throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = df.parse("23-12-1998");
        Client client = new Client("1234567890123456","1234567890", 1234567891,  date, "Male", "12345678910", "Miguel","miguel@ipp.pt", "Rua Fixe");
        ClientStore clientStore = new ClientStore();
        Client client1 = clientStore.createNewClient("1234567890123456","1234567890", 1234567891,  date, "Male", "12345678910", "Miguel","miguel@ipp.pt", "Rua Fixe");
        Assert.assertEquals(true, client1.equals(client));
    }

    @Test
    public void validateClientDifferent() throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = df.parse("23-12-1998");
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Client " + cli.getName() + " already exists");
        Client cli2 = new Client("1234567890123452","1234567860", 1234567891,  date, "Male", "12345678901", "Pedro","pedro@ipp.pt", "Rua Fixe");
        ClientStore clientStore = new ClientStore();
        clientStore.add(cli);
        clientStore.validateClient(cli2);
    }

    @Test
    public void validateClientCorrect() throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = df.parse("14-05-2002");
        Client cli2 = new Client("9123436780123456","1234567690", 1234567891, date, "Male", "12345678910", "Henrique","henrique@ipp.pt", "Rua Fixe");
        ClientStore clientStore = new ClientStore();
        clientStore.add(cli);
        clientStore.validateClient(cli2);
    }

    @Test
    public void saveClient() {
        ClientStore clientStore = new ClientStore();
        clientStore.saveClient(cli);
    }

    @Test
    public void add() {
        clientList.add(cli);
    }

    @Test
    public void getClients() throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = df.parse("23-12-1998");
        clientList.add(cli);
        Client cli2 = new Client("1234567890123456","1234567890", 1234567891, date, "Female", "12345678910", "Isabel","isabel@ipp.pt", "Rua Fixe");
        ClientStore clientStore = new ClientStore();
        clientList.add(cli2);
        Assert.assertEquals(false, clientList.equals(clientStore.getClients()));
    }

    @Test
    public void checkConstructorChecks() throws ParseException {
        ClientStore clientStore = new ClientStore();
    }

    @Test
    public void getClientByTin() throws ParseException {
        ClientMapper clientMapper = new ClientMapper();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = df.parse("23-12-1998");
        Client client = new Client("1234567890123456","1234567890", 1234567891, date, "Male", "12345678910", "Miguel","miguel@ipp.pt", "Rua Fixe");
        clientList.add(client);
        ClientDTO clientDTO = clientMapper.toDTO(client);
        int tin = client.getTin();
        ClientStore clientStore = new ClientStore();
        clientStore.getClients().add(client);
        Assert.assertTrue(clientStore.getClientByTin(String.valueOf(tin)).getPhoneNumber().equals(clientDTO.getPhoneNumber()));
    }

    @Test
    public void getClientByTinFalse() throws ParseException {
        ClientMapper clientMapper = new ClientMapper();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = df.parse("23-12-1998");
        Client client = new Client("1234567890123456","1234567890", 1234567891, date, "Male", "12345678910", "Miguel","miguel@ipp.pt", "Rua Fixe");
        clientList.add(client);
        ClientDTO clientDTO = clientMapper.toDTO(client);
        int tin = client.getTin();
        ClientStore clientStore = new ClientStore();
        clientStore.getClients().remove(client);
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("The TIN provided does not match to any client in database.");
        Assert.assertTrue(clientStore.getClientByTin(String.valueOf(tin)).getPhoneNumber().equals(clientDTO.getPhoneNumber()));
    }
    @Test
    public void testGetClientByName() throws ParseException {
        ClientMapper clientMapper = new ClientMapper();

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = df.parse("23-12-1998");
        Client client = new Client("1234567890123456","1234567890", 1234567891, date, "Male", "12345678910", "Miguel","miguel@ipp.pt", "Rua Fixe");
        clientList.add(client);
        ClientDTO clientDTO = clientMapper.toDTO(client);
        String name= client.getName();
        ClientStore clientStore = new ClientStore();
        clientStore.getClients().add(client);
        Assert.assertTrue(clientStore.getClientByName(name).getName().equals(clientDTO.getName()));

    }
    @Test
    public void testGetClientByNameWrong() throws ParseException {
        ClientMapper clientMapper = new ClientMapper();

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = df.parse("23-12-1998");
        Client client = new Client("1234567890123456","1234567890", 1234567891, date, "Male", "12345678910", "Miguel","miguel@ipp.pt", "Rua Fixe");
        clientList.add(client);
        ClientDTO clientDTO = clientMapper.toDTO(client);
        String name= client.getName();
        ClientStore clientStore = new ClientStore();
        clientStore.getClients().remove(client);
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("The name provided does not match to any client in database.");
        Assert.assertTrue(clientStore.getClientByName(name).getName().equals(clientDTO.getName()));

    }
    @Test
    public void testCheckClientTests() throws ParseException {
        ClientStore clientStore = App.getInstance().getCompany().getClientStore();
        TestStore testStore = App.getInstance().getCompany().getTestStore();
        TestTypeStore testTypeStore = App.getInstance().getCompany().getTestTypeStore();
        clientStore.getClients().clear();
        clientStore.add(cli2);
        List<Parameter> parameterList = new ArrayList<>();
        parameterList.add(new Parameter("HB000", "HB", new ParameterCategory("he001", "Hemogram"), "Botar sangue"));
        List<ParameterCategory> parameterCategoryList = new ArrayList<>();
        parameterCategoryList.add(new ParameterCategory("he001", "Hemogram"));
        TestType testType = new TestType("12344", "Blood", "cheirete", parameterCategoryList);
        testTypeStore.saveTest(testType);
        List<TestType> testTypeList = new ArrayList<>();
        testTypeList.add(testType);
        Laboratory lab = new Laboratory("lab1", "12344", "address", "12345678901", "1234567890", testTypeList);
        app.domain.model.Test test = new app.domain.model.Test(testType, parameterList, "123456789012", lab, "1234567999");
        testStore.saveTest(test);
        test.validateTestState();
        Assert.assertTrue(clientStore.getClients().equals(clientStore.checkClientTests()));

    }
    @Test
    public void testGetClientTests() throws ParseException {
        ClientStore clientStore =App.getInstance().getCompany().getClientStore();
        clientStore.getClients().clear();
        TestStore testStore = App.getInstance().getCompany().getTestStore();
        testStore.getTestList().clear();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = df.parse("23-12-1998");
        Client person = new Client("1234567890123555","1234567855", 1234567855, date, "Male", "12345678955", "Miguel","person@ipp.pt", "Rua Fixe");
        TestTypeStore testTypeStore = App.getInstance().getCompany().getTestTypeStore();
        clientStore.add(person);
        clientList.add(person);
        List<Parameter> parameterList = new ArrayList<>();
        parameterList.add(new Parameter("HB000", "HB", new ParameterCategory("he001", "Hemogram"), "Botar sangue"));
        List<ParameterCategory> parameterCategoryList = new ArrayList<>();
        parameterCategoryList.add(new ParameterCategory("he001", "Hemogram"));
        TestType testType = new TestType("12345", "Blood", "cheirete", parameterCategoryList);
        testTypeStore.saveTest(testType);
        List<TestType> testTypeList = new ArrayList<>();
        testTypeList.add(testType);
        Laboratory lab = new Laboratory("lab1", "12345", "address", "12345678901", "1234567890", testTypeList);
        app.domain.model.Test test = new app.domain.model.Test(testType, parameterList, "123456789012", lab, "1234567855");
        testStore.saveTest(test);
        test.validateTestState();
        System.out.println(Arrays.deepToString(testStore.getTestList().toArray()));
        System.out.println(Arrays.deepToString(clientStore.getClientTests(person.getEmail()).toArray()));
        Assert.assertTrue(testStore.getTestList().equals(clientStore.getClientTests(person.getEmail())));

    }
}