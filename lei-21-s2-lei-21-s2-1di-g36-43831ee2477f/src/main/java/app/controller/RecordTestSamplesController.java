package app.controller;

import app.domain.model.Company;
import app.domain.model.Sample;
import app.domain.model.Test;
import app.domain.store.TestStore;
import app.mappers.TestMapper;
import app.mappers.dto.TestDTO;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.output.OutputException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecordTestSamplesController {
    private Company company;
    private TestStore store;

    /**
     * Constructor
     */
    public RecordTestSamplesController() {
        this(App.getInstance().getCompany());

    }

    /**
     * Constructor defining company and store
     * @param company
     */
    public RecordTestSamplesController(Company company) {
        this.company = company;
        store = company.getTestStore();
    }

    /**
     * This method returns the list with all tests
     * @return testDTOList
     */
    public List<TestDTO> getTestListDTO() {
        List<TestDTO> testDTOList = new ArrayList<>();
        List<Test> listTest = store.getListRegisteredTest();
        TestMapper mapper = new TestMapper();
        for (Test t : listTest) {
            testDTOList.add(mapper.toDTO(t));
        }
        return testDTOList;
    }

    /**
     * This method regists a new Sample
     * @param numberOfBarcode
     * @param testDTO
     * @throws OutputException
     * @throws BarcodeException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public void registerSample(int numberOfBarcode, TestDTO testDTO) throws OutputException, BarcodeException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        TestMapper mapper = new TestMapper();
        Test test = mapper.toTest(testDTO);
        test.addSample(numberOfBarcode);
    }

    /**
     * This method returns a list with all the samples codes
     * @param testDTO
     * @return SamplesCodes
     */
    public List<String> getSampleCodes(TestDTO testDTO){
        TestMapper mapper = new TestMapper();
        Test test = mapper.toTest(testDTO);
        List<String> SamplesCodes = new ArrayList<>();
        for (Sample sample: test.getSampleList()){
            SamplesCodes.add(sample.getCode());
        }
        return SamplesCodes;
    }
}
