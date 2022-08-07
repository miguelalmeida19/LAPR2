package app.domain.model;

import java.io.Serializable;

public class SpecialistDoctor extends Employee  implements Serializable {
    private int doctorIndexNumber;

    /**
     * Constructor
     * @param name
     * @param role
     * @param address
     * @param phoneNumber
     * @param email
     * @param soc
     * @param id
     * @param doctorIndexNumber
     */
    public SpecialistDoctor(String name, String role, String address, String phoneNumber, String email, int soc, String id, int doctorIndexNumber){
        super(name,role,address,phoneNumber,email,soc,id);
        checkDoctorIndexNumber(doctorIndexNumber);
        this.doctorIndexNumber=doctorIndexNumber;
    }

    /**
     * This method returns the doctor index number of a Specialist Doctor
     * @return
     */
    public int getDoctorIndexNumber() {
        return doctorIndexNumber;
    }

    /**
     * This method returns a string with all the parameters of a Specialist Doctor
     * @return
     */
    @Override
    public String toString() {
        return "SpecialistDoctor{" +
                "name='" + getName() + '\'' +
                ", role=" + getRole() +
                ", address='" + getAddress() + '\'' +
                ", phoneNumber=" + getPhoneNumber() +
                ", email='" + getEmail() + '\'' +
                ", soc=" + getSoc() +
                ", id='" + getId() + '\'' +
                ", doctorIndexNumber=" + doctorIndexNumber +
                '}';
    }

    /**
     * This method checks if the doctor index number of a Specialist Doctor is valid or not
     * @return
     */
    public boolean checkDoctorIndexNumber() {
        if(String.valueOf(doctorIndexNumber).length()==6){
            return true;
        }else{
            throw new IllegalArgumentException("Doctor Index Number contains 6 digits");
        }
    }
}

