package app.mappers.dto;

import app.domain.model.Client;
import app.mappers.ClientMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class ClientDTOTest {
    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();
    public Client cli;
    public ClientDTO cliDto;
    public ClientMapper clientMapper = new ClientMapper();
    public List<Client> clientList = new ArrayList<>();
    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    Date date = df.parse("23-12-1998");

    public ClientDTOTest() throws ParseException {
    }

    @Before
    public void setUp() throws Exception {
        clientList.add(new Client("1234567890123456", "1234567890", 1234567891, date, "Male", "12345678910", "Miguel", "miguel@ipp.pt", "Rua Fixe"));
        cli = new Client("1234567890123452", "1234567860", 1234547891, date, "Male", "12345678901", "Miguel", "Miguel@ipp.pt", "Rua Fixe");
        cliDto = clientMapper.toDTO(cli);
    }

    @Test
    public void getBirth() {
        Assert.assertTrue(cliDto.getBirth().equals("23-12-1998"));
    }

    @Test
    public void getCitizenCardNumber() {
        Assert.assertTrue(cliDto.getCitizenCardNumber().equals("1234567890123452"));
    }

    @Test
    public void getName() {
        Assert.assertTrue(cliDto.getName().equals("Miguel"));

    }

    @Test
    public void getNhsNumber() {
        Assert.assertTrue(cliDto.getNhsNumber().equals("1234567860"));
    }

    @Test
    public void getTin() {
        Assert.assertTrue(cliDto.getTin()==1234547891);
    }

    @Test
    public void getEmail() {
        Assert.assertTrue(cliDto.getEmail().equals("Miguel@ipp.pt"));

    }

    @Test
    public void getPhoneNumber() {
        Assert.assertTrue(cliDto.getPhoneNumber().equals("12345678901"));
    }

    @Test
    public void getSex() {
        Assert.assertTrue(cliDto.getSex().equals("Male"));
    }
}