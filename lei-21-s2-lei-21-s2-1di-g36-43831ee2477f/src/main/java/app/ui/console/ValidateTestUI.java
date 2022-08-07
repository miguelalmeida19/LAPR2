package app.ui.console;

import app.controller.ValidateTestController;
import app.mappers.dto.ParameterDTO;
import app.mappers.dto.TestDTO;
import app.ui.console.utils.Utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class ValidateTestUI implements Runnable {
    static Scanner read = new Scanner(System.in);
    private ValidateTestController controller = new ValidateTestController();

    public ValidateTestUI() {
    }

    public void run() {
        boolean control = true;
        while (control) {
            try {
                showTestAvailable();
                TestDTO testDTO = null;
                while (testDTO == null) {
                    testDTO = selectTest();
                }
                validation(testDTO);
                control = menuValidate();
            } catch (Exception e) {
                Utils.print(e.getMessage());
                control = menuValidate();
            }
        }
    }

    private void showTestAvailable() {
        if (controller.getTestsAvailable().isEmpty()) {
            throw new IllegalArgumentException("There are no tests waiting for Validation");
        } else {
            Utils.print("Tests waiting for validation:");
            int counter = 1;
            for (TestDTO testDTO : controller.getTestsAvailable()) {
                Utils.print(counter + " - " + testDTO.getTestCode() + " : \n" + "Date of test registration: " + testDTO.getTestCreationDate() + "\nDate of sample analysis: " + testDTO.getSamplesAnalysedDate() + "\nDate of Report: " + testDTO.getReportMadeDate());
                counter++;
            }
        }
    }

    private TestDTO selectTest() {
        Utils.print("\nSelect the test:");
        String option = read.nextLine();
        try {
            TestDTO test = controller.getTestsAvailable().get(Integer.parseInt(option) - 1);
            Utils.print("Test Selected: ");
            String parameters = "";
            for (ParameterDTO parameterDTO : test.getParameterDTO()) {
                parameters = parameters + "\n- " + "Parameter Name: " + parameterDTO.getName() + " Parameter Code: " + parameterDTO.getCode() + " Parameter Description:" + parameterDTO.getDescription() + " Parameter Category: " + parameterDTO.getCategory().getName();
            }
            System.out.print("━━━━━━━━━━━━━━━━━━━━━━━━━▲━━━━━━━━━━━━━━━━━━━━━━━━━" +
                            "\nDate of test registration: " + test.getTestCreationDate() +
                            "\n\nDate of samples analysis: " + test.getSamplesAnalysedDate() +
                            "\n\nDate of report made: " + test.getReportMadeDate() +
                            "\n━━━━━━━━━━━━━━━━━━━━━━━━━▼━━━━━━━━━━━━━━━━━━━━━━━━━");
            Utils.print("\nDo you confirm?\n[1] - Yes\n[2] - No");
            option = read.nextLine();
            if (option.equals("1")) {
                return test;
            }
            if (option.equals("2")) {
                run();
            } else if (!option.contains("1") && !option.contains("2")) {
                throw new IllegalArgumentException("Please enter the option you want correctly!");
            }
        } catch (Exception e) {
            Utils.print("Error: " + e.getMessage());
            Utils.print("Please enter the option you want correctly!");
        }
        return null;
    }

    private void validation(TestDTO testDTO) throws IOException {
        Utils.print("You want to validate this test?\n[1] - Yes\n[2] - No");
        String option = read.nextLine();
        if (option.equals("1")) {
            controller.validateTest(testDTO);
            Utils.print("Test Validated Successfully!");
        }
        if (option.equals("2")) {
            new LaboratoryCoordinatorUI();
        } else {
            throw new IllegalArgumentException("Enter a valid option (just the number 1 or 2)");
        }
    }

    private Boolean menuValidate() {
        boolean control = false;
        Utils.print("You want to validate more tests?");
        Utils.print("[1] - Yes\n[2] - No");
        String option = read.nextLine();
        if (option.contains("1")) {
            control = true;
        }
        if (option.contains("2")) {
            control = false;
        } else if (!option.contains("1") && !option.contains("2")) {
            throw new IllegalArgumentException("Enter a valid option (just the number 1 or 2)");
        }
        return control;
    }
}