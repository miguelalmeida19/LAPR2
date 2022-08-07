package app.controller;


import app.algorithms.sorting.SortAlgorithm;
import app.domain.model.Client;
import app.domain.model.Company;
import app.domain.model.Test;
import app.domain.store.ClientStore;
import app.domain.store.TestStore;
import app.mappers.ClientMapper;
import app.mappers.TestMapper;
import app.mappers.dto.ClientDTO;
import app.mappers.dto.TestDTO;
import auth.AuthFacade;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConsultTestsController {
    private Company company;
    private ClientStore clientStore;
    private TestStore testStore;
    private ClientMapper clientMapper;
    private TestMapper testMapper;

    /**
     * Constructor defining company, stores and mappers
     */
    public ConsultTestsController(){
        company = App.getInstance().getCompany();
        clientStore = company.getClientStore();
        testStore = company.getTestStore();
        clientMapper = new ClientMapper();
        testMapper = new TestMapper();
    }

    /**
     * This method returns a client list in DTO
     * @return list of clients DTO
     */
    public List<ClientDTO> getClientsList(){
        return clientMapper.toDTO(clientStore.checkClientTests());
    }

    /**
     * It creates a  instance of the algorithm to be used
     * @param array
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */

    public String[] sortList(String[] array) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Properties properties = new Properties();
        InputStream in = new FileInputStream("configuration.conf");
        properties.load(in);
        Class<?> sortClass = Class.forName(properties.getProperty("Company.Algorithms.sorting"));
        SortAlgorithm sortAlgorithm = (SortAlgorithm)  sortClass.newInstance();
        in.close();
        return sortAlgorithm.sort(array);
    }

    /**
     * Returns a client list by name
     * @param name
     * @return
     */
    public ClientDTO getClientByName(String name){
        return clientStore.getClientByName(name);
    }

    /**
     *  Returns a client list by Tin
     * @param tin
     * @return
     */
    public ClientDTO getClientByTin(String tin){
        return clientStore.getClientByTin(tin);
    }

    /**
     * Returns a list of tests from a client
     * @param email
     * @return
     */
    public List<TestDTO> getTestsAvailable(String email) {
        return testMapper.toDTO(clientStore.getClientTests(email));
    }

    /**
     * This method returns a list with all dates from a test
     * @param testDTOS
     * @return
     */

    public List<TestDTO> getTestCodeDatesList(List<TestDTO> testDTOS){
        List<TestDTO> testDTOList = new ArrayList<>();
        for (TestDTO testDTO : testDTOS){
            testDTOList.add(new TestDTO(testDTO.getTestCode(), testDTO.getTestCreationDate(), testDTO.getSamplesAnalysedDate(), testDTO.getReportMadeDate(), testDTO.getValidationDate()));
        }
        return testDTOList;
    }

    /**
     * This method returns a list of tests with all information
     * @param testDTO
     * @return
     */

    public TestDTO getFullTestDTO(TestDTO testDTO){
        for (Test test : testStore.getTestList()){
            if (test.getTestCode().equals(testDTO.getTestCode())){
                return testMapper.toDTO(test);
            }
        }
        throw new IllegalArgumentException("There are no tests matching provided testCode");
    }

    /**
     * Returns an array of clients with his name first and the the TIN
     * @param clientDTOList
     * @return
     */

    public String[] getClientNameTin(List<ClientDTO> clientDTOList){
        String[] array = new String[clientDTOList.size()];
        for (int n = 0; n < clientDTOList.size(); n++) {
            array[n] = clientDTOList.get(n).getName() + " - " + clientDTOList.get(n).getTin();
        }
        return array;
    }

    /**
     * Returns an array of clients with his TIN first and his name
     * @param clientDTOList
     * @return
     */
    public String[] getClientTinName(List<ClientDTO> clientDTOList){
        String[] array = new String[clientDTOList.size()];
        for (int n = 0; n < clientDTOList.size(); n++) {
            array[n] = clientDTOList.get(n).getTin() + " - " + clientDTOList.get(n).getName();
        }
        return array;
    }

}
