package app.ui.console;

import app.controller.CreateNewClinicalLabController;
import app.controller.RecordTestSamplesController;
import app.mappers.dto.TestDTO;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.output.OutputException;
import app.ui.console.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RecordTestSamplesUI implements Runnable {
    static Scanner scanner = new Scanner(System.in);
    private final RecordTestSamplesController controller = new RecordTestSamplesController();
    private final CreateNewClinicalLabController createNewClinicalLabController = new CreateNewClinicalLabController();

    public RecordTestSamplesUI() {
    }

    public void run() {
        boolean control = true;
        while (control) {
            try {
                TestDTO testDTO = selectTest();
                System.out.print("Type how many barcodes you want: ");
                int barcode = scanner.nextInt();
                confirmation(testDTO, barcode);
                control = false;
            } catch (Exception e) {
                Utils.print("Error: " + e.getMessage());
                if (e.getMessage().equals("No tests are registered! :(")) {
                    control = false;
                    new MedicalLabTechnicianUI().run();
                } else {
                    Utils.print("Please enter again the data.");

                }
            }
        }
    }

    private TestDTO selectTest() {
        List<TestDTO> testDTOList = controller.getTestListDTO();
        List<TestDTO> testDTOListOfThisLab = new ArrayList<>();
        for (TestDTO testDTO : testDTOList) {
            if (testDTO.getLaboratory().getId().equals(createNewClinicalLabController.getCurrentLab().getId())) {
                testDTOListOfThisLab.add(testDTO);
            }
        }
        if (testDTOListOfThisLab.isEmpty()) {
            throw new IllegalArgumentException("No tests are registered! :(");
        }
        Utils.print("What is the test you want to associate the samples with?\n");
        int counter = 1;
        for (TestDTO testDTO : testDTOList) {
            Utils.print(counter + " - " + testDTO.getTestCode() + " : " + testDTO.getTestCreationDate());
            counter++;
        }
        Utils.print("\nSelect the test:");
        String option = scanner.nextLine();
        try {
            return testDTOList.get(Integer.parseInt(option) - 1);
        } catch (Exception e) {
            Utils.print("Error: " + e.getMessage());
            Utils.print("Please enter the option you want correctly!");
        }
        return null;
    }

    private void confirmation(TestDTO testDTO, int barcode) {
        Utils.print("A new Sample will be added with the following data: ");
        Utils.print("Test Associated Code: " + testDTO.getTestCode());
        Utils.print("Number of Barcodes: " + barcode);

        Utils.print("Do you confirm?");
        Utils.print("[1] (Yes)\n[2] (No)");
        String answer = scanner.next();
        scanner.nextLine();

        if (answer.equals("1")) {
            try {
                controller.registerSample(barcode, testDTO);
            } catch (OutputException | BarcodeException | IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            Utils.print("Success!");
            Utils.print("Barcodes Generated: ");
            for (String b : controller.getSampleCodes(testDTO)) {
                Utils.print("║█║▌║█║▌│║▌║▌█║");
                Utils.print("   " + b);
            }
        } else {
            new RecordTestSamplesUI().run();
        }
    }
}
