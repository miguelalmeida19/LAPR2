package app.ui.console;

import app.controller.CreateClientController;
import app.ui.console.utils.Utils;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Date;
import java.util.Scanner;

public class CreateClientUI implements Runnable {
    static Scanner read = new Scanner(System.in);
    private final CreateClientController clientController = new CreateClientController();

    public CreateClientUI() {
    }

    public void run() {
        Utils.print("Enter the required information:\n");

        System.out.print("Citizen Card Number: ");
        String citizenCardNumber = read.nextLine();
        try{
            Double.parseDouble(citizenCardNumber);

        }catch (Exception e){
            Utils.print("The citizen card number must have only numbers.");
        }
        System.out.print("Name: ");
        String name = read.nextLine();

        System.out.print("NHS Number: ");
        String nhsNumber = "";
        try {
            nhsNumber = read.nextLine();
        }
        catch (Exception e){
            Utils.print("NHS number must have 10 digits");
        }

        System.out.print("TIN: ");
        int tin = 0;
        try {
            tin = read.nextInt();
        }
        catch (Exception e){
            Utils.print("TIN must have 10 digits");
        }
        Date birth = null;
        try {
             birth = Utils.readDateFromConsole("Birth Date: (DD-MM-YYYY)");
        }
        catch (Exception e){
            Utils.print(e.getMessage());
        }

        System.out.print("Sex: ");
        Utils.print("\n[1] Male\n[2] Female\n[3] I prefer not to say");
        String sex = "";
        try {
            int option = 0;
            option = read.nextInt();
            if (option == 1) {
                sex = "Male";
            } if (option == 2) {
                sex = "Female";
            } else if (option== 3) {
                sex = "Undefined";
            }
        }
        catch (Exception e){
            Utils.print("Select a valid option (1 or 2 or 3)");
        }

        System.out.print("Phone Number: ");
        read.nextLine();
        String phoneNumber = read.nextLine();
        try{
            Double.parseDouble(phoneNumber);

        }catch (Exception e){
            Utils.print("The phone number must have only numbers.");
        }
        System.out.print("Email: ");
        String email = read.nextLine();
        System.out.print("Address: ");
        String address = read.nextLine();
        try {
            clientController.getClientInfo(citizenCardNumber, nhsNumber, tin, birth, sex, phoneNumber, name, email, address);
            Utils.print("A new client will be created with the following data:" + clientController.getClientInfo(citizenCardNumber, nhsNumber, tin, birth, sex, phoneNumber, name, email, address));

            Utils.print("Do you confirm?");
            Utils.print("[1] (Yes)\n[2] (No)");
            String answer = read.next();
            read.nextLine();

            confirmation(answer, citizenCardNumber, nhsNumber, tin, birth, sex, phoneNumber, name, email, address);
        }
        catch (Exception e){
            Utils.print("\nError:");
            Utils.print(e.getMessage());
        }
    }

    private void confirmation(String answer, String citizenCardNumber, String nhsNumber, int tin, Date birth,String sex,String phoneNumber,String name,String email, String address){
        if (answer.equals("1")) {
            clientController.createNewClient(citizenCardNumber, nhsNumber, tin, birth, sex, phoneNumber, name, email, address);
            try {
                clientController.saveClient();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Utils.print("Success!");
            Utils.print("Client's notification successfully exported to <emailAndSMSMessages.txt>!");
        }
        if (answer.equals("2")){
            new ReceptionistUI().run();
        }
        else if (!answer.equals("1") && !answer.equals("2")) {
            throw new IllegalArgumentException("Enter a valid option (1 or 2)");
        }
    }
}
