package app.mappers;

import app.controller.App;
import app.domain.model.Client;
import app.domain.store.ClientStore;
import app.mappers.dto.ClientDTO;
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

public class ClientMapperTest {

    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();

    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    Date date = df.parse("23-12-1998");

    public Client cli = new Client("1234567890123452", "1234567860", 1234547891, date, "Male", "12345678901", "Miguel", "Miguel@ipp.pt", "Rua Fixe");;
    public ClientStore clientStore = App.getInstance().getCompany().getClientStore();

    public ClientMapperTest() throws ParseException {
    }


    @Test
    public void toDTO() {
        ClientMapper clientMapper= new ClientMapper();
        clientStore.saveClient(cli);
        Assert.assertTrue(clientMapper.toDTO(cli).getTin()==cli.getTin());
        Assert.assertTrue(clientMapper.toDTO(cli).getName().equals(cli.getName()));
        Assert.assertTrue(clientMapper.toDTO(cli).getBirth().equals(cli.getBirth()));
        Assert.assertTrue(clientMapper.toDTO(cli).getEmail().equals(cli.getEmail()));
        Assert.assertTrue(clientMapper.toDTO(cli).getSex().equals(cli.getSex()));
        Assert.assertTrue(clientMapper.toDTO(cli).getCitizenCardNumber().equals(cli.getCitizenCardNumber()));
        Assert.assertTrue(clientMapper.toDTO(cli).getNhsNumber().equals(cli.getNhsNumber()));
    }
    @Test
    public void toClient(){
        ClientMapper clientMapper= new ClientMapper();
        //cli is already saved
        ClientDTO clientDTO = clientMapper.toDTO(cli);
        System.out.println(cli);
        Assert.assertTrue(clientMapper.toClient(clientDTO).getTin()==cli.getTin());
        Assert.assertTrue(clientMapper.toClient(clientDTO).getName().equals(cli.getName()));
        Assert.assertTrue(clientMapper.toClient(clientDTO).getBirth().equals(cli.getBirth()));
        Assert.assertTrue(clientMapper.toClient(clientDTO).getEmail().equals(cli.getEmail()));
        Assert.assertTrue(clientMapper.toClient(clientDTO).getSex().equals(cli.getSex()));
        Assert.assertTrue(clientMapper.toClient(clientDTO).getCitizenCardNumber().equals(cli.getCitizenCardNumber()));
        Assert.assertTrue(clientMapper.toDTO(cli).getNhsNumber() ==cli.getNhsNumber());
    }
}