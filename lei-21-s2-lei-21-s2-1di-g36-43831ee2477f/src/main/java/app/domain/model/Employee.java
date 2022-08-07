package app.domain.model;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Pattern;

public class

Employee implements Serializable {
    private final String name;
    private final String role;
    private final String address;
    private final String phoneNumber;
    private final String email;
    private final int soc;
    private final String id;

    /**
     * Constructor
     * @param name
     * @param role
     * @param address
     * @param phoneNumber
     * @param email
     * @param soc
     * @param id
     */
    public Employee(String name, String role, String address, String phoneNumber, String email, int soc, String id) {
        checkName(name);
        checkRole(role);
        checkAddress(address);
        checkPhoneNumber(phoneNumber);
        checkEmail(email);
        checkSoc(soc);
        checkId(id);
        this.name = name;
        this.role = role;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.soc = soc;
        this.id = id;
    }

    /**
     * This method returns the phone number of an Employee
     * @return
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
    /**
     * This method returns the soc of an Employee
     * @return
     */
    public int getSoc() {
        return soc;
    }
    /**
     *
     * This method returns the address of an Employee
     * @return
     */
    public String getAddress() {
        return address;
    }
    /**
     * This method returns the email of an Employee
     * @return
     */
    public String getEmail() {
        return email;
    }
    /**
     * This method returns the Id of an Employee
     * @return
     */
    public String getId() {
        return id;
    }
    /**
     * This method returns the name of an Employee
     * @return
     */
    public String getName() {
        return name;
    }
    /**
     * This method returns the role of an Employee
     * @return
     */
    public String getRole() {
        return role;
    }
    /**
     * This method returns a string with all the parameters of an Employee
     * @return
     */
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", role=" + role +
                ", address='" + address + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", email='" + email + '\'' +
                ", soc=" + soc +
                ", id='" + id + '\'' +
                '}';
    }

    /**
     * This method checks if the name of an Employee is valid or not
     * @param name
     */
    public void checkName(String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if(name.length()>35){
            throw new IllegalArgumentException("Name can only be 35 characters long");
        }
    }

    /**
     * This method checks if the role of an Employee is valid or not
     * @param role
     */
    public void checkRole(String role) {
        if (StringUtils.isBlank(role)) {
            throw new IllegalArgumentException("Role cannot be empty");
        }
    }

    /**
     * This method checks if the address of an Employee is valid or not
     * @param adress
     */
    public void checkAddress(String adress) {
        if (StringUtils.isBlank(adress)) {
            throw new IllegalArgumentException("Address cannot be empty");
        }
        if (adress.length()>90){
            throw new IllegalArgumentException("Address cannot be longer than 90 char");
        }
    }

    /**
     * This method checks if the phone number of an Employee is valid or not
     * @param phoneNumber
     */
    public void checkPhoneNumber(String phoneNumber) {
        if (String.valueOf(phoneNumber).length()!=11) {
            throw new IllegalArgumentException("Invalid Phone Number! Must have 11 digits");
        }
        if (!phoneNumber.matches("[0-9]+")){
            throw new IllegalArgumentException("Invalid Phone Number");
        }
    }

    /**
     * This method checks if the email of an Employee is valid or not
     * @param email
     */
    public void checkEmail(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if(StringUtils.isBlank(email)){
            throw new IllegalArgumentException("Email cannot be empty");
        }
        else if (!pat.matcher(email).matches()){
            throw new IllegalArgumentException("Invalid Email");
        }
    }

    /**
     * This method checks if the soc of an Employee is valid or not
     * @param soc
     */
    public void checkSoc(int soc) {
        if (String.valueOf(soc).length()!=4) {
            throw new IllegalArgumentException(" Invalid Standard Occupational Classification! Must have 4 digits");
        }
    }

    /**
     * This method checks if the ID of an Employee is valid or not
     * @param id
     */
    public void checkId(String id) {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("ID cannot be empty");
        }
        if (id.length()<5){
            throw new IllegalArgumentException("ID must contain at least 5 chars");
        }
    }

    /**
     * This method checks if the doctor index number of a Specialist Doctor is valid or not
     * @param doctorIndexNumber
     */

    public void checkDoctorIndexNumber(int doctorIndexNumber) {
        if (String.valueOf(doctorIndexNumber).length()!=6) {
            throw new IllegalArgumentException("Invalid Doctor Index Number! Must have 6 digits");
        }
    }

    /**
     * This method checks if an Employee is equal to another one
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return soc == employee.soc && Objects.equals(name, employee.name) && Objects.equals(role, employee.role) && Objects.equals(address, employee.address) && Objects.equals(phoneNumber, employee.phoneNumber) && Objects.equals(email, employee.email) && Objects.equals(id, employee.id);
    }

    /**
     * This method returns the hashcode of an Employee
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, role, address, phoneNumber, email, soc, id);
    }
}
