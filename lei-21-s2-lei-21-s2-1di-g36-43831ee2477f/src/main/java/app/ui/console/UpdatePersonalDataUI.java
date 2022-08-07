package app.ui.console;

import app.controller.UpdatePersonalDataController;
import app.domain.model.Client;
import app.mappers.dto.ClientDTO;
import app.ui.console.utils.Utils;
import auth.UserSession;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UpdatePersonalDataUI implements Runnable {
    static Scanner scanner = new Scanner(System.in);
    private UpdatePersonalDataController controller;
    private ClientDTO clientDTO;
    private int index;
    private List<String> options;

    public UpdatePersonalDataUI() {
        controller = new UpdatePersonalDataController();
        options = new ArrayList<>();
        options.add("Citizen Card Number");
        options.add("TIN");
        options.add("NHS");
        options.add("Birthdate");
        options.add("Password");
        options.add("Name");
        options.add("Sex");
        options.add("Email");
        options.add("Phone Number");
    }

    public void run() {

        try {
            selectOption();
        } catch (Exception e) {
            Utils.print(e.getMessage());
        }

    }

    public void selectOption() throws ParseException {

        index = Utils.showAndSelectIndex(options, "Personal Data");

        String email = controller.getEmail();

        clientDTO = controller.getClientByEmail(email);

        switch (index) {
            case 0:
                Utils.print("Change Citizen Card Number:");
                String citizenCardNumber = scanner.nextLine();
                clientDTO.setCitizenCardNumber(citizenCardNumber);
                confirmation(citizenCardNumber);
                break;
            case 1:
                Utils.print("Change TIN:");
                int tin = scanner.nextInt();
                clientDTO.setTin(tin);
                confirmation(String.valueOf(tin));
                break;
            case 2:
                Utils.print("Change NHS:");
                String nhs = scanner.nextLine();
                clientDTO.setNhsNumber(nhs);
                confirmation(nhs);
                break;
            case 3:
                Utils.print("Change Birthdate:");
                String birth = scanner.nextLine();
                clientDTO.setBirth(birth);
                confirmation(birth);
                break;
            case 4:
                Utils.print("Change Name:");
                String name = scanner.nextLine();
                clientDTO.setName(name);
                confirmation(name);
                break;
            case 5:
                Utils.print("Change Sex:");
                String sex = scanner.nextLine();
                clientDTO.setSex(sex);
                confirmation(sex);
                break;
            case 6:
                Utils.print("Change Email:");
                String mail = scanner.nextLine();
                confirmation(mail);
                clientDTO.setEmail(mail);
                controller.setClientData(clientDTO);
                break;
            case 7:
                Utils.print("Change Phone Number:");
                String phoneNumber = scanner.nextLine();
                clientDTO.setPhoneNumber(phoneNumber);
                confirmation(phoneNumber);
                break;
        }
    }

    public void confirmation(String word) throws ParseException {
        List<String> clientInfo = new ArrayList<>();
        clientInfo.add(clientDTO.getCitizenCardNumber());
        clientInfo.add(String.valueOf(clientDTO.getTin()));
        clientInfo.add(clientDTO.getNhsNumber());
        clientInfo.add(clientDTO.getBirth());
        clientInfo.add(clientDTO.getName());
        clientInfo.add(clientDTO.getSex());
        clientInfo.add(clientDTO.getEmail());
        clientInfo.add(clientDTO.getPhoneNumber());
        Utils.print("Current " + options.get(index) + " is: " + clientInfo.get(index));
        Utils.print("You selected: " + options.get(index));
        Utils.print("New: " + word);
        Utils.print("\nDo you confirm?");
        Utils.print("[1] - Yes\n[2] - No");
        String op = scanner.nextLine();
        if (op.equals("1")) {
            controller.setClientData(clientDTO);
            Utils.print("Success!");
        }
        if (op.equals("2")) {
            new ClientUI();
        } if (!op.equals("1") && !op.equals("2")) {
            throw new IllegalArgumentException("Enter a valid option (just the number 1 or 2)");
        }
    }
}