package app.ui.console;

import app.controller.CreateParameterCategoryController;
import app.domain.model.ParameterCategory;
import app.ui.console.utils.Utils;

import java.util.Scanner;

public class CreateParameterCategoryUI implements Runnable {
    Scanner read = new Scanner(System.in);
    CreateParameterCategoryController pc = new CreateParameterCategoryController();

    public CreateParameterCategoryUI() {
    }

    public void run() {
        Utils.print("Enter the necessary data:\n");

        System.out.print("Code: ");
        String code = read.next();
        read.nextLine();

        System.out.print("Description: ");
        String description = read.next();
        Utils.print("A parameter category will be created with the following data: code: " + code + " description: " + description);
        try {
            confirmation();
            pc.createParameterCategory(code, description);
            pc.saveParameterCategory();
            Utils.print("Success!");
        }catch (Exception e){
            Utils.print(e.getMessage());
        }

    }

    private boolean confirmation() {
        Utils.print("Do you confirm?");
        Utils.print("[1] (Yes)\n[2] (No)");
        String answer = read.nextLine();
        answer = read.nextLine();
        if (answer.equals("2")) {
            return false;
        }
        return true;
    }
}
