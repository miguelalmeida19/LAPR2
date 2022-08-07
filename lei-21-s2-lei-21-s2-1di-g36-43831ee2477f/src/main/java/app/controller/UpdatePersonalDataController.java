package app.controller;

import app.domain.model.Client;
import app.domain.model.Company;
import app.domain.store.ClientStore;
import app.domain.store.TestStore;
import app.mappers.ClientMapper;
import app.mappers.TestMapper;
import app.mappers.dto.ClientDTO;
import auth.AuthFacade;
import auth.UserSession;
import auth.domain.model.Password;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdatePersonalDataController {
    private Company company;
    private ClientStore clientStore;
    private TestStore testStore;
    private ClientMapper clientMapper;
    private TestMapper testMapper;
    private AuthFacade authFacade;

    public UpdatePersonalDataController() {
        this(App.getInstance().getCompany());
    }

    public UpdatePersonalDataController(Company company) {
        this.company = company;
        clientStore = company.getClientStore();
        testStore = company.getTestStore();
        clientMapper = new ClientMapper();
        testMapper = new TestMapper();
        authFacade = company.getAuthFacade();
    }

    public String getEmail() {
        UserSession userSession = authFacade.getCurrentUserSession();
        return userSession.getUserId().getEmail();
    }

    public ClientDTO getClientByEmail(String email){
        for (Client client : clientStore.getClients()){
            if (client.getEmail().equals(email)){
                return clientMapper.toDTO(client);
            }
        }
        throw new IllegalArgumentException("The provided email does not match to any client");
    }

    public void setClientData(ClientDTO clientDTO) throws ParseException {
        Client client = clientMapper.toClient(clientStore.getClientByTin(String.valueOf(clientDTO.getTin())));
        client.setName(clientDTO.getName());
        client.setBirth(clientDTO.getBirth());
        client.setEmail(clientDTO.getEmail());
        client.setTin(clientDTO.getTin());
        client.setCitizenCardNumber(clientDTO.getCitizenCardNumber());
        client.setNhsNumber(clientDTO.getNhsNumber());
        client.setPhoneNumber(clientDTO.getPhoneNumber());
        client.setSex(clientDTO.getSex());
    }
}
