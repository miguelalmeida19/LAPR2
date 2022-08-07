package app.controller;

import app.domain.model.Client;
import app.domain.model.Company;
import app.domain.model.Test;
import app.domain.shared.Constants;
import app.domain.store.ClientStore;
import app.domain.store.TestStore;
import app.domain.store.TestTypeStore;
import app.mappers.ClientMapper;
import app.mappers.TestMapper;
import app.mappers.dto.ClientDTO;
import app.mappers.dto.TestDTO;
import app.ui.console.utils.EmailNotificationSender;
import auth.AuthFacade;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import static app.ui.console.utils.Utils.generatePassword;

public class CreateClientController {
    private Company company;
    private Client c;
    private ClientStore store;

    /**
     * Constructor
     */
    public CreateClientController() {
        this(App.getInstance().getCompany());
    }

    /**
     * Constructor defining company, store and client
     * @param company
     */
    public CreateClientController(Company company) {
        this.company = company;
        store = company.getClientStore();
        this.c = null;
    }

    /**
     * This method creates a new Client
     * @param citizenCardNumber
     * @param nhsNumber
     * @param tin
     * @param birth
     * @param sex
     * @param phoneNumber
     * @param name
     * @param email
     */
    public void createNewClient(String citizenCardNumber, String nhsNumber, int tin, Date birth, String sex, String phoneNumber, String name, String email, String address)  {
        c = store.createNewClient(citizenCardNumber, nhsNumber, tin, birth, sex, phoneNumber, name, email, address);
        store.validateClient(c);

    }

    /**
     * This method adds a new Client to the list
     * @param client
     * @return
     */
    public boolean add(Client client) {
        return store.add(client);
    }

    /**
     * This method saves a Client
     * @throws IOException
     */
    public void saveClient() throws IOException {
        String password = generatePassword();

        saveClientLogin(c.getName(), password, c.getEmail());
        AuthFacade PW = company.getAuthFacade();
        PW.addUserWithRole(c.getName(), c.getEmail(), password, Constants.ROLE_CLIENT);
        this.store.saveClient(c);
    }

    /**
     * This method returns a list of all the clients
     * @return
     */
    public List<Client> getClients() {
        return store.getClients();
    }

    /**
     * This method saves client login informations to a file
     * @param name
     * @param password
     * @param email
     * @throws IOException
     */
    public void saveClientLogin(String name, String password, String email) throws IOException {
        EmailNotificationSender notificationSender = new EmailNotificationSender();
        notificationSender.sendPasswordNotification(name, password, email);
    }

    /**
     * This method returns a string for usage in UI with all the information of a client
     * @param citizenCardNumber
     * @param nhsNumber
     * @param tin
     * @param birth
     * @param sex
     * @param phoneNumber
     * @param name
     * @param email
     * @return
     */
    public String getClientInfo(String citizenCardNumber, String nhsNumber, int tin, Date birth, String sex, String phoneNumber, String name, String email, String address){
        String info = new Client(citizenCardNumber, nhsNumber, tin, birth, sex, phoneNumber, name, email, address).toString();
        return info;
    }


}