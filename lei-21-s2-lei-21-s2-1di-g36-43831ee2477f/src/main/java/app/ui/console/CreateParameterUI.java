package app.ui.console;

import app.controller.CreateParameterController;
import app.domain.model.Parameter;
import app.domain.model.ParameterCategory;
import app.ui.console.utils.Utils;

import java.util.Scanner;

public class CreateParameterUI implements Runnable {
    Scanner read = new Scanner(System.in);
    CreateParameterController parameterController = new CreateParameterController();

    public CreateParameterUI() {
    }

    public void run() {
        boolean controller = true;
        while (controller) {
            try {
                if (parameterController.getParameterCategories().size()==0){
                    throw new IllegalArgumentException("There are no parameter categories to associate with parameter");
                }
                Utils.print("Choose a parameter category: ");

                int count = 1;
                for (String pc : parameterController.getParameterCategories()) {
                    Utils.print(count + " - " + pc);
                    count++;
                }

                int option = read.nextInt();
                String parameterCategory = parameterController.getParameterCategories().get(option - 1);

                Utils.print("Enter the necessary data:\n");

                System.out.print("Code: ");
                String code = read.next();
                read.nextLine();

                System.out.print("Name: ");
                String name = read.next();

                System.out.print("Description: ");
                String description = read.next();

                Utils.print("A new parameter will be created with the following data: name: " + name + " code: " + code + " description: " + description);
                if(confirmation()){
                    parameterController.createNewParameter(code, name, description, parameterCategory);
                    parameterController.saveParameter();
                    Utils.print("Success!");

                }else {
                    throw new IllegalArgumentException("Could not create/save parameter");
                }
                controller = false;

            }catch (Exception e){
                controller = false;
                Utils.print("Error: "+e.getMessage());
            }
        }
    }

    private boolean confirmation(){
        Utils.print("Do you confirm?");
        Utils.print("[1] (Yes)\n[2] (No)");
        String answer = read.nextLine();
        answer = read.nextLine();
        if(answer.equals("2")){
            return false;
        }return true;
    }

}
