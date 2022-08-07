package app.controller;

import app.domain.model.Company;
import app.domain.model.Laboratory;
import app.domain.model.TestType;
import app.domain.store.LaboratoryStore;
import app.domain.store.TestTypeStore;
import app.mappers.LaboratoryMapper;
import app.mappers.dto.LaboratoryDTO;

import java.util.List;

public class CreateNewClinicalLabController {

    /**
     * Lab created. is used to priori validation
     */
    private Laboratory lab;
    private LaboratoryStore labStore;
    private LaboratoryMapper laboratoryMapper = new LaboratoryMapper();
    private Company company;

    /**
     * Constructor
     */
    public CreateNewClinicalLabController() {
        this(App.getInstance().getCompany());

    }

    /**
     * Constructor
     */
    public CreateNewClinicalLabController(Company company) {
        this.company = company;
        this.labStore = company.getLaboratoryStore();
    }
    /**
     * This method is the high-level method to create the new lab.
     * @param name name of the new lab.
     * @param ID ID of the lab
     * @param address address of the lab
     * @param phoneNumber phone number used to contact the lab.
     * @param TIN TIN number of the lab
     */
    public void createLaboratory(String name, String ID, String address, String phoneNumber, String TIN, List<String> testTypeCodes){
        labStore = company.getLaboratoryStore();
        TestTypeStore testStore = company.getTestTypeStore();
        List<TestType> testTypes = testStore.getTestTypeByCodes(testTypeCodes);
        lab = labStore.createNewLaboratory(name,ID,address,phoneNumber,TIN,testTypes);
        labStore.validateLaboratory(lab);
    }

    /**
     * This method is the high-level method to get all test codes on
     * @return list of all test type codes
     */
    public List<String> getTestTypes(){
        TestTypeStore testTypeStore = company.getTestTypeStore();
        return testTypeStore.getTestTypes();
    }

    /**
     * High level method to save the laboratory previously created.
     */
    public void saveLaboratory(){
        labStore.saveLaboratory(lab);
    }

    public List<LaboratoryDTO> getLaboratories(){
        return laboratoryMapper.toDTO(labStore.getLaboratories());
    }

    public void currentLab(LaboratoryDTO laboratoryDTO){
        labStore.setCurrentLaboratory(laboratoryMapper.toLaboratory(laboratoryDTO));
    }

    public LaboratoryDTO getCurrentLab(){
        return laboratoryMapper.toDTO(labStore.getCurrentLaboratory());
    }
}