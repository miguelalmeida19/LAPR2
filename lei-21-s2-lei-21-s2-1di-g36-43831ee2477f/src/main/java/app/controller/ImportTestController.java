package app.controller;

import app.domain.model.Company;
import app.domain.model.Test;
import app.domain.store.TestStore;
import app.mappers.TestMapper;
import app.mappers.dto.TestDTO;
import app.ui.gui.RoleUI;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

public class ImportTestController {

    private Company company;
    private TestStore testStore;
    private TestMapper testMapper = new TestMapper();

    /**
     * Comstructor
     */
    public ImportTestController() {
        this(App.getInstance().getCompany());
        testStore = App.getInstance().getCompany().getTestStore();
    }

    /**
     * Constructor defining Company
     * @param company
     */
    public ImportTestController(Company company){
        this.company = company;
        this.testStore = company.getTestStore();
    }

    /**
     * This method gets a test list
     * @param filename
     * @throws ParseException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public void getTestList(String filename) throws ParseException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        testStore.getTestListCSV(filename);
    }

    /**
     * This method returns a list of Tests DTO
     * @return
     */
    public List<TestDTO> getTestDTOList(){
        return testMapper.toDTOParam(testStore.getTestList());
    }

    /**
     * This method returns a list of files
     * @return
     */
    public List<File> getFiles(){
        File diretory = new File("./testStorage");
        return Arrays.asList(diretory.listFiles());
    }
}
