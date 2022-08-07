package app.domain.model;

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
import java.util.Objects;

import static org.junit.Assert.*;

public class ClientTest {
    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();
    public Client cli;
    public List<Client> clientList = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = df.parse("23-12-1998");
        clientList.add(new Client("1234567890123456", "1234567890", 1234567891, date, "Male", "12345678910", "Miguel", "miguel@ipp.pt", "Rua Fixe"));
        cli = new Client("1234567890123452", "1234567860", 1234547891, date, "Male", "12345678901", "Miguel", "Miguel@ipp.pt", "Rua Fixe");
    }

    @Test
    public void checkCitizenNumberSmall() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Invalid Citizen Card Number, it must contain 16 digits");
        cli.checkCitizenCardNumber("1234567");

    }

    @Test
    public void checkCitizenNumberBig() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Invalid Citizen Card Number, it must contain 16 digits");
        cli.checkCitizenCardNumber("12345678193391331313131311431");
    }

    @Test
    public void checkCitizenNumberEmpty() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("You must enter a valid Citizen Card Number");
        cli.checkCitizenCardNumber("");

    }

    @Test
    public void checkCitizenNumberCorrect() {
        cli.checkCitizenCardNumber("1234567890123452");
    }

    @Test
    public void checkNHSSmall() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Invalid NHS Number, it must contain 10 digits");
        cli.checkNhsNumber("1234567");

    }

    @Test
    public void checkNHSEmpty() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("You must enter a valid NHS Number");
        cli.checkNhsNumber("0");

    }

    @Test
    public void checkNHSCorrect() {
        cli.checkNhsNumber("1234567860");
    }

    @Test
    public void checkPhoneNumberSize() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Invalid Phone Number! Must have 11 digits");
        cli.checkPhoneNumber("12345677542242241343");
    }

    @Test
    public void checkPhoneNumberSmall() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Invalid Phone Number! Must have 11 digits");
        cli.checkPhoneNumber("12");
    }

    @Test
    public void checkPhonesSizeCorrect() {
        cli.checkPhoneNumber("12345678910");
    }

    @Test
    public void checkPhoneNumberLetters() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Invalid Phone Number");
        cli.checkPhoneNumber("123456789aa");
    }

    @Test
    public void checkEmailInvalidFormat() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Invalid Email");
        cli.checkEmail("isep.lan.pt");
    }

    @Test
    public void checkEmailEmpty() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Invalid Email");
        cli.checkEmail("");
    }

    @Test
    public void checkEmailCorrect() {
        cli.checkEmail("1201115@isep.ipp.pt");

    }

    @Test
    public void checkNameEmpty() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Name cannot be empty");
        cli.checkName("");
    }

    @Test
    public void checkNameBig() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Name can only have 35 characters");
        cli.checkName("sdadasasasasasasasasasadsdgdfgdfgddfdgd");
    }

    @Test
    public void checkNameNotEmpty() {
        cli.checkName("qwertyuiopqw");
    }

    @Test
    public void checkDateEmpty() {
        exceptionRule.expect(NullPointerException.class);
        cli.checkDate(null);
    }

    @Test
    public void checkDateBig() throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = df.parse("23-12-1800");
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("The maximum acceptable age is 150 years old");
        cli.checkDate(date);
    }

    @Test
    public void checkDateNull() throws ParseException {
        Date date = null;
        exceptionRule.expect(NullPointerException.class);
        cli.checkDate(date);
    }

    @Test
    public void checkSexInvalid() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("You must select a valid Sex [Male/Female]");
        String sex = "Trans";
        cli.checkSex(sex);
    }

    @Test
    public void checkSexValid() {
        String sex = "Male";
        cli.checkSex(sex);
    }

    @Test
    public void checkGets() {
        Assert.assertEquals(cli.getName(), "Miguel");
        Assert.assertEquals(cli.getEmail(), "Miguel@ipp.pt");
        Assert.assertEquals(cli.getPhoneNumber(), "12345678901");
        Assert.assertEquals(cli.getSex(), "Male");
        Assert.assertEquals(cli.getTin(), 1234547891);
        Assert.assertEquals(cli.getNhsNumber(), "1234567860");
    }

    @Test
    public void checkConstructorChecks() throws ParseException {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Invalid NHS Number, it must contain 10 digits");
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = df.parse("23-12-1998");

        Client client = new Client("1234567890123456", "213", 1234567891, date, "Male", "12345678910", "Miguel", "miguel@ipp.pt", "Rua Fixe");

    }

    @Test
    public void checkConstructorChecks2() throws ParseException {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Invalid Email");
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = df.parse("23-12-1998");

        Client client = new Client("1234567890123456", "2132222222", 1234567891, date, "Male", "12345678910", "Miguel", "", "Rua Fixe");

    }

    @Test
    public void checkEquals() throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = df.parse("23-12-1998");
        Client cli2 = new Client("1234567890123452", "1234567860", 1234547891, date, "Male", "12345678901", "Miguel", "Miguel@ipp.pt", "Rua Fixe");
        Assert.assertEquals(true, cli.equals(cli2));
    }

    @Test
    public void checkEquals2() throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = df.parse("23-11-1998");
        Client cli2 = new Client("1234567890123452", "1234567860", 1234547891, date, "Male", "12345678901", "Miguel", "Miguel@ipp.pt", "Rua Fixe");
        Assert.assertEquals(false, cli.equals(cli2));
    }
}