package app.domain.store;

import app.Persistence;
import app.domain.model.Employee;
import app.domain.model.Laboratory;
import app.domain.model.TestType;
import app.mappers.dto.LaboratoryDTO;

import java.util.ArrayList;
import java.util.List;

public class LaboratoryStore {

    /**
     * List of all labs.
     */
    private static Laboratory currentLaboratory;
    private List<Laboratory> store = new ArrayList<>();
    private TestTypeStore testTypeStore = new TestTypeStore();

    /**
     * Constructor
     */
    public LaboratoryStore() {
        store.add(new Laboratory("Oeira's Lab", "001DO", "Rua Oeiras 123", "12345678901", "1234567890", testTypeStore.getListOfTestType()));
        store.add(new Laboratory("Loure's Lab", "001LN", "Rua Loures 123", "12345678902", "1234567891", testTypeStore.getListOfTestType()));
        store.add(new Laboratory("Aveiro's Lab", "001LR", "Rua Aveiro 123", "12345678903", "1234567892", testTypeStore.getListOfTestType()));
        store.add(new Laboratory("Coimbra's Lab", "001MA", "Rua Coimbra 123", "12345678904", "1234567893", testTypeStore.getListOfTestType()));
        store.add(new Laboratory("Marroco's Lab", "001SO", "Rua Marrocos 123", "12345678905", "1234567894", testTypeStore.getListOfTestType()));
        store.add(new Laboratory("Angola's Lab", "001WA", "Rua Angola 123", "12345678906", "1234567895", testTypeStore.getListOfTestType()));
        try{
            store = (List<Laboratory>) Persistence.readObjectFromFile("labs.bin");
        }catch (Exception e){
            //System.out.println("The LaboratoryStore was not loaded :)");
        }
    }

    /**
     * @param nameOfLaboratory name of the lab
     * @param ID               id of the lab.
     * @param address          address of the lab.
     * @param phoneNumber      phone number used to contact the lab.
     * @param TIN              TIN of the lab
     * @return laboratory object created in this method
     */
    public Laboratory createNewLaboratory(String nameOfLaboratory, String ID, String address, String phoneNumber, String TIN, List<TestType> testType) {

        return new Laboratory(nameOfLaboratory, ID, address, phoneNumber, TIN, testType);
    }

    /**
     * Validates the laboratory.
     *
     * @param lab laboratory
     */
    public void validateLaboratory(Laboratory lab) {
        if (store.contains(lab)) {
            throw new IllegalArgumentException("laboratory has already been created");
        }
        checkDuplicatedData(lab);
    }

    /**
     * Check for duplicated data in the labs (AC10)
     * @param lab laboratory to check.
     */
    public void checkDuplicatedData(Laboratory lab){
        for(Laboratory laboratory : store){
            if(laboratory.getAddress().equals(lab.getAddress())){
                throw new IllegalArgumentException("There is a lab with the same address already created.");
            }
            if(laboratory.getID().equals(lab.getID())){
                throw new IllegalArgumentException("There is a lab with the same ID already created.");
            }
            if(laboratory.getPhoneNumber().equals(lab.getPhoneNumber())){
                throw new IllegalArgumentException("There is a lab with the same phone number already created.");
            }
            if(laboratory.getTIN().equals(lab.getTIN())){
                throw new IllegalArgumentException("There is a lab with the same TIN already created.");
            }
        }
    }

    public boolean add(Laboratory lab) {
        if (lab != null) {
            if (!store.contains(lab)) {
                store.add(lab);
                return true;
            }
        }
        return false;
    }

    /**
     * Saves the laboratory in the list of all labs created.
     *
     * @param lab laboratory to save.
     */
    public void saveLaboratory(Laboratory lab) {
        validateLaboratory(lab);
        add(lab);
    }

    public List<Laboratory> getLaboratories() {
        return store;
    }

    public Laboratory getCurrentLaboratory() {
        return currentLaboratory;
    }

    public Laboratory getLabByID(String id){
        for (Laboratory laboratory: store){
            if (laboratory.getID().equals(id)){
                return laboratory;
            }
        }
        return null;
    }

    public void setCurrentLaboratory(Laboratory lab){
        this.currentLaboratory = lab;
    }
}