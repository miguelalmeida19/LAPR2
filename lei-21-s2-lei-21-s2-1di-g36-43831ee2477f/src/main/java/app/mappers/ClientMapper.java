package app.mappers;

import app.controller.App;
import app.domain.model.Client;
import app.domain.model.Company;
import app.domain.store.ClientStore;
import app.mappers.dto.ClientDTO;

import java.util.ArrayList;
import java.util.List;

public class ClientMapper {
    private Company company;
    private ClientStore clientStore;

    /**
     * Constructor
     */
    public ClientMapper(){
        company = App.getInstance().getCompany();
        clientStore = company.getClientStore();
    }

    /**
     * This method converts a client to DTO
     * @param p
     * @return
     */
    public ClientDTO toDTO(Client p){
        return new ClientDTO(p);
    }

    public List<ClientDTO> toDTO (List<Client> clientList){
        List<ClientDTO> clientDTOS = new ArrayList<>();
        for (Client client : clientList){
            clientDTOS.add(toDTO(client));
        }
        return clientDTOS;
    }

    /**
     * This method converts a clientDTO to client
     * @param p
     * @return
     */
   public Client toClient(ClientDTO p){
        for(Client c : clientStore.getClients()){
            if(c.getTin() == p.getTin()){
                return c;
            }
        }
        throw new IllegalArgumentException("The client provided does not match to any client in database.");
   }
}