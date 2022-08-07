package app.domain.model;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class Client implements Serializable {
    private String citizenCardNumber;
    private String nhsNumber;
    private int tin;
    private Date birth;
    private String sex;
    private String phoneNumber;
    private String name;
    private String email;
    private String address;

    public Client(String citizenCardNumber, String nhsNumber, int tin, Date birth, String sex, String phoneNumber, String name, String email, String address) {
        checkCitizenCardNumber(citizenCardNumber);
        checkNhsNumber(nhsNumber);
        checkTin(tin);
        checkDate(birth);
        checkSex(sex);
        checkPhoneNumber(phoneNumber);
        checkName(name);
        checkEmail(email);
        checkAddress(address);
        this.citizenCardNumber = citizenCardNumber;
        this.nhsNumber = nhsNumber;
        this.tin = tin;
        this.birth = birth;
        this.sex = sex;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.email = email;
        this.address = address;
    }

    /**
     * This method returns the name of a Client
     */
    public String getName() {
        return name;
    }

    /**
     * This method returns the nhs number of a Client
     */
    public String getNhsNumber() {
        return nhsNumber;
    }

    /**
     * This method returns the tin of a Client

     */
    public int getTin() {
        return tin;
    }

    public String getBirth() {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String birthDate = df.format(birth);
        return birthDate;
    }

    /**
     * This method returns the email of a Client

     */
    public String getEmail() {
        return email;
    }

    /**
     * This method returns the phone number of a Client

     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * This method returns the sex of a Client

     */
    public String getSex() {
        return sex;
    }

    public String getAddress() {
        return address;
    }

    public String getCitizenCardNumber() {
        return citizenCardNumber;
    }

    public void setSex(String sex) {
        checkSex(sex);
        this.sex = sex;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(String name) {
        checkName(name);
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        checkPhoneNumber(phoneNumber);
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        checkEmail(email);
        this.email = email;
    }

    public void setBirth(String birth) throws ParseException {

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date Date = df.parse(birth);
        checkDate(Date);
        this.birth = Date;
    }

    public void setCitizenCardNumber(String citizenCardNumber) {
        checkCitizenCardNumber(citizenCardNumber);
        this.citizenCardNumber = citizenCardNumber;
    }

    public void setNhsNumber(String nhsNumber) {
        checkNhsNumber(nhsNumber);
        this.nhsNumber = nhsNumber;
    }

    public void setTin(int tin) {
        checkTin(tin);
        this.tin = tin;
    }

    /**
     * This method returns a string with all the information of a Client

     */
    @Override
    public String toString() {
        return "Client{" +
                "citizenCardNumber=" + citizenCardNumber +
                ", nhsNumber=" + nhsNumber +
                ", tin=" + tin +
                ", birth=" + String.valueOf(birth).split(" ")[2] + "/" + String.valueOf(birth).split(" ")[1] + "/" + String.valueOf(birth).split(" ")[5] +
                ", sex='" + sex + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    /**
     * This method checks if the citizen card number is valid or not

     */
    public void checkCitizenCardNumber(String citizenCardNumber) {
        if (StringUtils.isBlank(citizenCardNumber)) {
            throw new IllegalArgumentException("You must enter a valid Citizen Card Number");
        }
        if (String.valueOf(citizenCardNumber).length() != 16) {
            throw new IllegalArgumentException("Invalid Citizen Card Number, it must contain 16 digits");
        }
    }

    /**
     * This method checks if the nhs number is valid or not

     */
    public void checkNhsNumber(String nhsNumber) {
        if (nhsNumber.equals("0")) {
            throw new IllegalArgumentException("You must enter a valid NHS Number");
        }
        if (String.valueOf(nhsNumber).length() != 10) {
            throw new NumberFormatException("Invalid NHS Number, it must contain 10 digits");
        }
    }

    /**
     * This method checks if the sex is valid or not

     */
    public void checkSex(String sex) {
        List<String> sexList = new ArrayList<String>();
        sexList.add("Male");
        sexList.add("Female");
        sexList.add("Undefined");
        if (!sexList.contains(sex)) {
            throw new IllegalArgumentException("You must select a valid Sex [Male/Female]");
        }
    }

    /**
     * This method checks if the phone number is valid or not

     */
    public void checkPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() != 11) {
            throw new IllegalArgumentException("Invalid Phone Number! Must have 11 digits");
        }
        if (!phoneNumber.matches("[0-9]+")) {
            throw new IllegalArgumentException("Invalid Phone Number");
        }
    }

    public void checkTin(int tin){
        if(String.valueOf(tin).length()!=10){
            throw new IllegalArgumentException("Invalid TIN! Must have 10 digits");
        }
    }

    /**
     * This method checks if the email is valid or not

     */
    public void checkEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (!pat.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid Email");
        }
    }

    /**
     * This method checks if the name is valid or not

     */
    public void checkName(String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (name.length()>35) {
            throw new IllegalArgumentException("Name can only have 35 characters");
        }
    }

    /**
     * This method checks if the date is valid or not

     */
    public void checkDate(Date date) {
        if (Integer.parseInt(date.toString().split(" ")[5]) < 1871) {
            throw new IllegalArgumentException("The maximum acceptable age is 150 years old");
        }
    }

    public void checkAddress(String address) {
        if (StringUtils.isBlank(address)) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (address.length()>90) {
            throw new IllegalArgumentException("Name can only have 90 characters");
        }
    }

    /**
     * This method returns true or false depending on if a Client is equal to another one
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return citizenCardNumber == client.citizenCardNumber && nhsNumber == client.nhsNumber && tin == client.tin && Objects.equals(birth, client.birth) && Objects.equals(sex, client.sex) && Objects.equals(phoneNumber, client.phoneNumber) && Objects.equals(name, client.name) && Objects.equals(email, client.email);
    }
}
