package app.mappers.dto;
import app.domain.model.Client;

public class ClientDTO {
    private String citizenCardNumber;
    private String nhsNumber;
    private int tin;
    private String birth;
    private String sex;
    private String phoneNumber;
    private String name;
    private String email;
    private String address;

    /**
     * Constructor
     * @param p
     */
    public ClientDTO(Client p){
        this.citizenCardNumber = p.getCitizenCardNumber();
        nhsNumber = p.getNhsNumber();
        tin = p.getTin();
        birth = p.getBirth();
        sex = p.getSex();
        phoneNumber = p.getPhoneNumber();
        name = p.getName();
        email = p.getEmail();
        address = p.getAddress();
    }

    public ClientDTO(String name, String tin){
        this.name = name;
        this.tin = Integer.parseInt(tin);
    }

    /**
     * This method returns the birth date
     * @return
     */
    public String getBirth() {
        return birth;
    }

    /**
     * This method returns the citizen card number
     * @return
     */
    public String getCitizenCardNumber() {
        return citizenCardNumber;
    }

    /**
     * This method returns the name of a client
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * This method returns the nhsNumber
     * @return
     */
    public String getNhsNumber() {
        return nhsNumber;
    }

    /**
     * This method returns the tin of a client
     * @return
     */
    public int getTin() {
        return tin;
    }

    /**
     * This method returns the email of the client
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method returns the phone number of a client
     * @return
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    /**
     * This method returns the sex of a client
     * @return
     */
    public String getSex() {
        return sex;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setTin(int tin) {
        this.tin = tin;
    }

    public void setNhsNumber(String nhsNumber) {
        this.nhsNumber = nhsNumber;
    }

    public void setCitizenCardNumber(String citizenCardNumber) {
        this.citizenCardNumber = citizenCardNumber;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}