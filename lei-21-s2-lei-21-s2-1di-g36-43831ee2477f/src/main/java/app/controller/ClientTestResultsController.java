package app.controller;

import app.algorithms.sorting.SortAlgorithm;
import app.domain.model.Client;
import app.domain.model.Company;
import app.domain.model.Test;
import app.domain.store.ClientStore;
import app.domain.store.TestStore;
import app.mappers.ClientMapper;
import app.mappers.TestMapper;
import app.mappers.dto.TestDTO;
import auth.AuthFacade;
import auth.UserSession;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class ClientTestResultsController {
    private Company company;
    private ClientStore clientStore;
    private TestStore testStore;
    private ClientMapper clientMapper;
    private TestMapper testMapper;
    private AuthFacade authFacade;

    public ClientTestResultsController() {
        this(App.getInstance().getCompany());
    }

    public ClientTestResultsController(Company company) {
        this.company = company;
        clientStore = company.getClientStore();
        testStore = company.getTestStore();
        clientMapper = new ClientMapper();
        testMapper = new TestMapper();
        authFacade = company.getAuthFacade();
    }

    /**
     * This method returns a list with all tests
     *
     * @return
     */

    public List<TestDTO> getTestsAvailable(String email) {
        return testMapper.toDTO(clientStore.getClientTests(email));
    }

    /**
     * This method returns an email of a user
     * @return
     */

    public String getEmail() {
        UserSession userSession = authFacade.getCurrentUserSession();
        return userSession.getUserId().getEmail();
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
     * This method returns a test by its code
     * @param code
     * @return
     */
    public TestDTO getTestByTestCode(String code){
        return testMapper.toDTO(testStore.getTestByTestCode(code));
    }
}
