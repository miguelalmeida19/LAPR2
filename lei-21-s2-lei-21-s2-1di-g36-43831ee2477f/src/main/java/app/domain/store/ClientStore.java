package app.domain.store;

import app.Persistence;
import app.controller.App;
import app.domain.model.Client;
import app.domain.model.Company;
import app.domain.model.Test;
import app.mappers.ClientMapper;
import app.mappers.dto.ClientDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClientStore {
    private List<Client> clientList = new ArrayList<>();

    /**
     * Constructor
     */
    public ClientStore() {
        //not needed for now
        try{
            clientList = (List<Client>) Persistence.readObjectFromFile("clients.bin");
        }catch (Exception e){
            //System.out.println("The clientStore was not loaded :)");
        }
    }

    /**
     * This method creates a new Client

     */
    public Client createNewClient(String citizenCardNumber, String nhsNumber, int tin, Date birth, String sex, String phoneNumber, String name, String email, String address){
        Client c = new Client(citizenCardNumber, nhsNumber, tin, birth, sex, phoneNumber, name, email, address);
        return c;
    }

    /**
     * This method checks if a Client is valid or not
     */
    public boolean validateClient(Client c) {
        if (c == null)
            return false;
        int i = 0;
        for (Client client : clientList) {
            if (c.equals(clientList.get(i))) {
                throw new IllegalArgumentException("Client " + client.getName() + " already exists");
            }
            i++;
        }
        return !this.clientList.contains(c);
    }

    /**
     * This method saves a Client
     */
    public void saveClient(Client c) {
        validateClient(c);
        add(c);
    }

    /**
     * This method adds a new Client to the list
     */
    public boolean add(Client c){
        if (c != null) {
            if(!clientList.contains(c)){
                clientList.add(c);
                return true;
            }
        }
        return false;
    }

    /**
     * This method returns a client by the Tin
     */
    public ClientDTO getClientByTin(String TIN){
        ClientMapper clientMapper = new ClientMapper();
        for(Client c : clientList){
            if(String.valueOf(c.getTin()).equals(TIN) ){
                return clientMapper.toDTO(c);
            }
        }
        throw new IllegalArgumentException("The TIN provided does not match to any client in database.");
    }

    public ClientDTO getClientByName(String name){
        ClientMapper clientMapper = new ClientMapper();
        for(Client c : clientList){
            if(String.valueOf(c.getName()).equals(name) ){
                return clientMapper.toDTO(c);
            }
        }
        throw new IllegalArgumentException("The name provided does not match to any client in database.");
    }

    /**
     * This method returns a list of all the Clients that exist in memory
     */
    public List<Client> getClients() {
        return clientList;
    }

    public List<Client> checkClientTests(){
        Company company = App.getInstance().getCompany();
        TestStore testStore = company.getTestStore();
        List<Client> clientWithTestList = new ArrayList<>();
        for(Client client: clientList){
            for(Test test:testStore.getTestList()){
                if(test.getTINOfClient().equals(String.valueOf(client.getTin())) && test.isValidated()){
                    clientWithTestList.add(client);
                }
            }
        }
        return clientWithTestList;


    }

    public List<Test> getClientTests(String email){
        Company company = App.getInstance().getCompany();
        TestStore testStore = company.getTestStore();
        List<Test> testList = new ArrayList<>();
        for (Client client : clientList) {
            for (Test test : testStore.getTestList()) {
                if (test.isValidated() && test.getTINOfClient().equals(String.valueOf(client.getTin())) && email.equals(client.getEmail())){
                    testList.add(test);
                }
            }
        }
        return testList;
    }
}
