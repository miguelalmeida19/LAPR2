package app.ui.console;

import app.controller.CreateTestController;
import app.domain.model.ParameterCategory;
import app.domain.model.TestType;
import app.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CreateTestTypeUI implements Runnable {
    static Scanner read = new Scanner(System.in);
    private final CreateTestController testController = new CreateTestController();

    public CreateTestTypeUI() {
    }

    public void run() {
        try {


            Utils.print("Enter the required information:\n");

            System.out.print("Code: ");
            String code = read.nextLine();

            System.out.print("Description: ");
            String description = read.nextLine();

            System.out.print("Collecting Method: ");
            String collectingMethod = read.nextLine();

            Utils.print("Choose the category/categories of the test type");
            List<ParameterCategory> parameterCategoryList = testController.getParameterCategoryStore().getParameterCategoryList();
            int i = 0;
            for (ParameterCategory pc : parameterCategoryList) {
                System.out.println((i + 1) + " - " + parameterCategoryList.get(i).getName());
                i++;
            }

            Utils.print("Enter the number of the category/categories of the test type\nIf you want more than one category, enter like this (1,2,3,4)");
            String option = read.nextLine();
            List<ParameterCategory> categoriesSelected = new ArrayList<>();
            if (option.matches("[0-9]+")) {
                categoriesSelected.add(parameterCategoryList.get(Integer.parseInt(option) - 1));
                Utils.print(parameterCategoryList.get(Integer.parseInt(option) - 1).getName());
            } else {
                String[] categoriesArray = option.split(",");
                for (int f = 0; f < categoriesArray.length; f++) {
                    categoriesSelected.add(parameterCategoryList.get(Integer.parseInt(categoriesArray[f]) - 1));
                }
            }

            String categories = "";

            for (ParameterCategory p : categoriesSelected) {
                categories = categories + " " + p.getName();
            }

            Utils.print("A new Test Type will be created with the following data: " + "\ncode: " + code + "\ndescription: " + description + "\ncollecting method: " + collectingMethod + "\ncategory/categories selected: " + categories);

            Utils.print("Do you confirm?");
            Utils.print("[1] (Yes)\n[2] (No)");
            String answer = read.next();
            read.nextLine();

            if (answer.equals("1")) {
                testController.createNewTestType(code, description, collectingMethod, categoriesSelected);
                Utils.print("\ncode: " + code + "\ndescription: " + description + "\ncollecting method: " + collectingMethod + "\ncategory/categories selected: " + categories);
                testController.saveTest();
                Utils.print("Success");
            } else {
                new AdminUI().run();
            }
        }catch (Exception e){
            Utils.print("Error: "+e.getMessage());
        }
    }
}
