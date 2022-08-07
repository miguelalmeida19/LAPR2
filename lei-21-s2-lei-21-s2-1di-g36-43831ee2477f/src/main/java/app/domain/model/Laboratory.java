package app.domain.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Laboratory  implements Serializable {
    private List<TestType> testsOfLab;
    private String nameOfLaboratory;
    private String id;
    private String address;
    private String phoneNumber;
    private String tin;

    /**
     * Constructor
     *
     * @param nameOfLaboratory name of lab
     * @param id               id of lab
     * @param address          address of lab
     * @param phoneNumber      phone number used to contact the lab
     * @param tin              TIN of the lab
     * @param testsOfLab       tests operated by this lab
     */
    public Laboratory(String nameOfLaboratory, String id, String address, String phoneNumber, String tin, List<TestType> testsOfLab) {
        checkNumberTests(testsOfLab);
        checkSizes(nameOfLaboratory, id, address, phoneNumber, tin);
        this.nameOfLaboratory = nameOfLaboratory;
        this.id = id;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.tin = tin;
        this.testsOfLab = testsOfLab;
    }

    /**
     * Check if lab operates at least 1 test
     *
     * @throws IllegalArgumentException indicating the cause of failure.
     */
    public void checkNumberTests(List<TestType> testsOfLab) {

        if (testsOfLab == null || testsOfLab.size() == 0) {
            throw new IllegalArgumentException("The lab need to operate at least 1 test type.");
        }

    }

    /**
     * Check if sizes of parameters are valid
     *
     * @throws IllegalArgumentException indicating the cause of failure.
     */
    public void checkSizes(String nameOfLaboratory, String id, String address, String phoneNumber, String tin) {
        if (nameOfLaboratory.isEmpty()) {
            throw new IllegalArgumentException("Name of laboratory cannot be empty.");
        }
        if (address.isEmpty()) {
            throw new IllegalArgumentException("Address of laboratory cannot be empty.");
        }

        if (!(nameOfLaboratory.length() <= 20))
            throw new IllegalArgumentException("Name of lab cannot have more than 20 characters.");

        if (!(id.length() == 5))
            throw new IllegalArgumentException("ID must have 5 characters.");
        if (!(address.length() <= 30))
            throw new IllegalArgumentException("Address cannot have more than 30 characters.");

        if (!(phoneNumber.length() == 11))
            throw new IllegalArgumentException("The phone number must have 11 digits.");
        if (!(tin.length() == 10))
            throw new IllegalArgumentException("TIN number must have 10 digits.");

    }

    public String getID() {
        return id;
    }

    public String getNameOfLaboratory() {
        return nameOfLaboratory;
    }

    public List<TestType> getTestsOfLab() {
        return testsOfLab;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getTIN() {
        return tin;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Laboratory that = (Laboratory) o;
        return testsOfLab.equals(that.testsOfLab) && nameOfLaboratory.equals(that.nameOfLaboratory) && id.equals(that.id) && address.equals(that.address) && phoneNumber.equals(that.phoneNumber) && tin.equals(that.tin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(testsOfLab, nameOfLaboratory, id, address, phoneNumber, tin);
    }
}