package app.mappers.dto;

import app.domain.model.Laboratory;
import app.domain.model.TestType;
import app.mappers.ClientMapper;

import java.util.List;

public class LaboratoryDTO {
    private List<TestType> testsOfLab;
    private String nameOfLaboratory;
    private String id;
    private String address;
    private String phoneNumber;
    private String tin;

    /**
     * Constructor
     *
     * @param nameOfLaboratory
     * @param id
     * @param address
     * @param phoneNumber
     * @param tin
     * @param testsOfLab
     */
    public LaboratoryDTO(String nameOfLaboratory, String id, String address, String phoneNumber, String tin, List<TestType> testsOfLab) {
        this.nameOfLaboratory = nameOfLaboratory;
        this.id = id;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.tin = tin;
        this.testsOfLab = testsOfLab;
    }

    public LaboratoryDTO(String address, String id) {
        this.address = address;
        this.id = id;
    }

    /**
     * This method returns the phone number of a Laboratory
     *
     * @return
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * This method returns the address of a Laboratory
     *
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method returns returns the id of a laboratory
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * This method returns the tests that exist in the lab
     *
     * @return
     */
    public List<TestType> getTestsOfLab() {
        return testsOfLab;
    }

    /**
     * This method returns the name of the Laboratory
     *
     * @return
     */
    public String getNameOfLaboratory() {
        return nameOfLaboratory;
    }

    /**
     * This method returns the tin of a Laboratory
     *
     * @return
     */
    public String getTin() {
        return tin;
    }
}
