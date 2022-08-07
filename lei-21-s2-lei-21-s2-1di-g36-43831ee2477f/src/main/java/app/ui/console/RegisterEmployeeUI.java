package app.ui.console;

import app.controller.RegisterEmployeeController;
import app.ui.console.utils.Utils;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class RegisterEmployeeUI implements Runnable {
    static Scanner read = new Scanner(System.in);
    private final RegisterEmployeeController emp = new RegisterEmployeeController();

    public RegisterEmployeeUI() {
    }

    public void run() {
        Utils.print("What is the role of the employee you wish to register:\n");
        try {
            for (String role : emp.getRoles()) {
                Utils.print(" - " + role);
            }

            System.out.print("\nEnter the role you want: ");
            String option = read.next();
            String role = "";

            for (String r : emp.getRoles()) {
                if (r.contains(option.toUpperCase())){
                    role = r;
                }
            }


            Utils.print("Role: " + role);

            System.out.print("Name: ");
            read.nextLine();
            String name = read.nextLine();

            System.out.print("Email: ");
            String email = read.nextLine();

            System.out.print("Address: ");
            String address = read.nextLine();

            System.out.print("PhoneNumber: ");
            String phoneNumber = read.nextLine();

            System.out.print("SOC: ");
            int soc = 0;
            try {
                soc = read.nextInt();
            } catch (InputMismatchException inputMismatchException) {
                Utils.print("Soc contains only 4 digits");
            }
            String id = emp.getId(name);
            Utils.print("Id: " + id);

            int doctorIndexNumber = 0;
            if (role.equals("SPECIALIST DOCTOR")) {
                System.out.print("DoctorIndexNumber: ");
                doctorIndexNumber = read.nextInt();
            }

            for (int i = 0; i < 20; i++) {
                Utils.print("\n");
            }
            if (doctorIndexNumber != 0) {
                String docInfo = emp.getSpecialistDoctorInfo(name, role, address, phoneNumber, email, soc, id, doctorIndexNumber);
                Utils.print("A new worker will be created with the following data:");
                Utils.print(docInfo);
            } else {
                String empInfo = emp.getEmployeeInfo(name, role, address, phoneNumber, email, soc, id);
                Utils.print("A new worker will be created with the following data:");
                Utils.print(empInfo);
            }
            Utils.print("Do you confirm?");
            Utils.print("[1] (Yes)\n[2] (No)");
            String answer = read.next();
            read.nextLine();
            if (answer.equals("1")) {
                if (doctorIndexNumber != 0) {
                    try {
                        emp.createNewSpecialistDoctor(name, role, address, phoneNumber, email, soc, id, doctorIndexNumber);
                    } catch (Exception e) {
                        Utils.print("Error:");
                        Utils.print(e.getMessage());
                    }
                    try {
                        emp.saveEmployee();
                    } catch (IOException e) {
                        Utils.print("Error:");
                        Utils.print(e.getMessage());
                    }
                    Utils.print("Success!");
                } else {
                    try {
                        emp.createNewEmployee(name, role, address, phoneNumber, email, soc, id);
                    } catch (Exception e) {
                        Utils.print("Error:");
                        Utils.print(e.getMessage());
                    }
                    try {
                        emp.saveEmployee();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Utils.print("Employee's notification successfully exported to <emailAndSMSMessages.txt>!");
                    Utils.print("Success!");
                }
                int count = 1;
                Utils.print("\nLista de Trabalhadores Atuais: \n");
                for (String employeeName : emp.getEmployees()) {
                    Utils.print(count + " - " + employeeName);
                    count++;
                }
            } else {
                new AdminUI().run();
            }
        } catch (Exception exception) {
            Utils.print("Error:");
            Utils.print(exception.getMessage());
            read.nextLine();
        }
    }
}