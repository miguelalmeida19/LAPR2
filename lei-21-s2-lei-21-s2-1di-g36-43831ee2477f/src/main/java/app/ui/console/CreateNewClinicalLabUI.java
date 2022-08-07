package app.ui.console;

import app.controller.CreateNewClinicalLabController;
import app.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CreateNewClinicalLabUI implements  Runnable {
    Scanner scanner = new Scanner(System.in);
    CreateNewClinicalLabController lb = new CreateNewClinicalLabController();
    public CreateNewClinicalLabUI() {
    }


    public  void run() {

        if(lb.getTestTypes() == null || lb.getTestTypes().size() == 0){
            Utils.print("No tests are available.");
        }else {
            boolean controll = true;
            while(controll) {
                try {


                    Utils.print("Tests available:");
                    List<String> testsList = lb.getTestTypes();


                    for (int n = 0; n < testsList.size(); n++) {
                        Utils.print(n + 1 + " - " + testsList.get(n));
                    }

                    List<String> tests = inputTests(testsList);
                    scanner.nextLine();
                    Utils.print("Type the required information to create a new lab:");
                    String name = inputText("name of lab:");
                    String ID = inputText("ID fo the lab:");
                    validateStringOnlyNumbers(ID);
                    String address = inputText("address:");
                    String phoneNumber = inputText("phone number:");
                    validateStringOnlyNumbers(phoneNumber);
                    String TIN = inputText("TIN:");
                    validateStringOnlyNumbers(TIN);
                    lb.createLaboratory(name, ID, address, phoneNumber, TIN, tests);
                    Utils.print("A new Laboratory will be created with the following data:" + "name: " + name + " ID: " + ID + " address: " + address + " phonenumber: " + phoneNumber + " TIN: " + TIN + " tests: " + tests);

                    Utils.print("Do you confirm?");
                    Utils.print("[1] (Yes)\n[2] (No)");
                    String input = scanner.nextLine();
                    while (!input.equals("1") && !input.equals("2")) {
                        Utils.print("Select a valid answer. ");
                        Utils.print("[1] (Yes)\n[2] (No)");
                        input = scanner.nextLine();
                    }
                    if (input.equals("1")) {
                        lb.saveLaboratory();
                        Utils.print("Success!");
                    }else{
                        new AdminUI().run();
                    }
                    controll = false;

                } catch (Exception e) {
                    Utils.print("Error: " + e.getMessage());
                    Utils.print("Please enter again the data.");
                }
            }
        }


    }

    private List<String> inputTests(List<String> tests) throws Exception {
        List<String> testsSelected = new ArrayList<>();
        Utils.print();
        Utils.print("Select the tests. Type -1 when you are done.");

        int input = scanner.nextInt();
        scanner.nextLine();
        while(input >= 0){
            testsSelected.add(tests.get(input-1));
            input = scanner.nextInt();

        }
        if(testsSelected.size() == 0){
            throw new Exception("No tests were selected!");
        }
        return testsSelected;
    }
    private String inputText(String message){
        Utils.print(message);
        return scanner.nextLine();
    }

    private void validateStringOnlyNumbers(String text){
        if(!text.matches("[0-9]+")){
            throw new InputMismatchException("The value introduced does not have only numbers.");
        }
    }
}
