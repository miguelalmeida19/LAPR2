package app.ui.console;

import app.controller.ClientTestResultsController;

import app.mappers.dto.ParameterDTO;
import app.mappers.dto.TestDTO;
import app.ui.console.utils.Utils;


import java.io.IOException;
import java.util.List;
import java.util.Scanner;


public class TestResultsUI implements Runnable {
    static Scanner scanner = new Scanner(System.in);
    private ClientTestResultsController controller;

    public TestResultsUI(){
        controller = new ClientTestResultsController();
    }

    public void run() {
        boolean control = true;
        while (control) {
            try {
                TestDTO testDTO = selectTest();
                showTestResults(testDTO);
                control = true;
            } catch (Exception e) {
                Utils.print(e.getMessage());
                control = false;
            }
        }


    }

    private TestDTO selectTest() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String email = controller.getEmail();
        int counter = 1;
        Utils.print("Tests Available:");
        List<TestDTO> testDTOList = controller.getTestsAvailable(email);
        String[] arrayToSort = testDtoToString(testDTOList);
        try {

            String[] sortedArray = controller.sortList(arrayToSort);
        for (int i=sortedArray.length-1; i>=0; i--){
            Utils.print(counter + ":  "+ sortedArray[i]);
            counter++;
        }
        Utils.print("\n0 - Cancel");
        Utils.print("\nSelect the test:");
        String option = scanner.nextLine();

            if (controller.getTestsAvailable(email).isEmpty()){
                throw new IllegalArgumentException("There are no test results available");
            }
            if (option.equals("0")){
                new ClientUI().run();
            }
            else {
                TestDTO test = controller.getTestByTestCode(sortedArray[Integer.parseInt(option) - 1].split(" - ")[1]);
                Utils.print("Test Selected: ");
                System.out.print("━━━━━━━━━━━━━━━━━━━━━━━━━▲━━━━━━━━━━━━━━━━━━━━━━━━━" +
                        "\nDate of test registration: " + test.getTestCreationDate() +
                        "\n\nDate of samples analysis: " + test.getSamplesAnalysedDate() +
                        "\n\nDate of report made: " + test.getReportMadeDate() +
                        "\n\nDate of validation: " + test.getValidationDate() +
                        "\n━━━━━━━━━━━━━━━━━━━━━━━━━▼━━━━━━━━━━━━━━━━━━━━━━━━━");
                Utils.print("\nDo you confirm?\n[1] - Yes\n[2] - No");
                option = scanner.nextLine();
                if (option.equals("1")) {
                    return test;
                }
                if (option.equals("2")) {
                    run();
                } else if (!option.contains("1") && !option.contains("2")) {
                    throw new IllegalArgumentException("Please enter the option you want correctly!");
                }
            }
        } catch (Exception e) {
            Utils.print("Please enter the option you want correctly!");
        }
        return null;
    }

    private void showTestResults(TestDTO testDTO){
        for (ParameterDTO parameterDTO : testDTO.getParameterDTO()){
            Utils.print("▬▬ι════ " + testDTO.getTestParameterFor(parameterDTO.getCode()).getCode() + " ════ι▬▬");
            Utils.print("Result: " +
                    testDTO.getTestParameterFor(parameterDTO.getCode()).getTestParameterResul().getResult() + "\nMetric: " +
                    testDTO.getTestParameterFor(parameterDTO.getCode()).getTestParameterResul().getMetric() + "\n" +
                    testDTO.getTestParameterFor(parameterDTO.getCode()).getTestParameterResul().getRefValue());
            Utils.print("---------------------------------------\n");
        }
        Utils.print("\n\n");
    }

    private String[] testDtoToString(List<TestDTO> testDTOList){
        String[] array = new String[testDTOList.size()];
        for(int n = 0; n <  testDTOList.size(); n++){
            array[n] = testDTOList.get(n).getTestCreationDate() + " - " + testDTOList.get(n).getTestCode();
        }
        return array;
    }
}