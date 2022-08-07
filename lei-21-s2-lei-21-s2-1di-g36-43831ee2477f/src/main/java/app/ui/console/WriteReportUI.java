package app.ui.console;

import app.controller.WriteReportController;
import app.mappers.dto.ParameterDTO;
import app.mappers.dto.TestDTO;
import app.ui.console.utils.Utils;

import java.util.List;
import java.util.Scanner;

public class WriteReportUI implements Runnable {

    static Scanner read = new Scanner(System.in);
    private WriteReportController controller = new WriteReportController();

    public WriteReportUI(){}

    public void run() {
        boolean control = true;
        while (control) {
            try {
                TestDTO testDTO = selectTest();
                showTestInfo(testDTO);
                String diagnosis = enterDiagnosis();
                String report = enterReport();
                controller.createReport(report, diagnosis, testDTO);
                Utils.print("Success!");
                try {
                    control = menuReport();
                }catch (Exception e){
                    Utils.print(e.getMessage());
                    control = menuReport();
                }
                //
            } catch (Exception e) {
                Utils.print(e.getMessage());
                control = false;
            }
        }
    }

    private TestDTO selectTest() {
        List<TestDTO> testDTOList = controller.getTestListDTO();
        if(testDTOList.isEmpty()){
            throw new IllegalArgumentException("\nNo tests available to write report! :(");
        }
        Utils.print("What is the test you want to associate the samples with?\n");
        int counter = 1;
        for (TestDTO testDTO : testDTOList) {
            Utils.print(counter + " - " + testDTO.getTestCode() + " : \n"+ "Date of test registration: " + testDTO.getTestCreationDate() + "\nDate of sample analysis: " + testDTO.getSamplesAnalysedDate());
            counter++;
        }
        Utils.print("\nSelect the test:");
        String option = read.nextLine();
        try {
            TestDTO test = testDTOList.get(Integer.parseInt(option) - 1);
            return test;
        }
        catch (Exception e){
            Utils.print("Error: " + e.getMessage());
            Utils.print("Please enter the option you want correctly!");
        }
        return null;
    }

    private void showTestInfo(TestDTO testDTO) {
        Utils.print("Parameters and Results:");
        Utils.print("|-------------------------------------------------------------------------------------|");
        Utils.print("|    Parameter    |    Result    |    Max. Value    |    Min. Value    |    Metric    |");
        Utils.print("|-------------------------------------------------------------------------------------|");
        for (ParameterDTO parameterDTO : testDTO.getParameterDTO()){
            System.out.print("|    "+valueWithSpaces(9,parameterDTO.getName()) + "    ");
            System.out.print("|    "+valueWithSpaces(6,controller.getTestParameterResult(testDTO, parameterDTO).getResult()) + "    ");
            System.out.print("|    "+valueWithSpaces(10,String.valueOf(controller.getTestParameterResult(testDTO, parameterDTO).getRefValue().getRefValueMax())) + "    ");
            System.out.print("|    "+valueWithSpaces(10,String.valueOf(controller.getTestParameterResult(testDTO, parameterDTO).getRefValue().getRefValueMin())) + "    ");
            System.out.print("|    "+valueWithSpaces(6,controller.getTestParameterResult(testDTO, parameterDTO).getMetric()) + "    |");
            Utils.print("\n|-------------------------------------------------------------------------------------|");
        }
    }

    private String valueWithSpaces(int size, String value){
        String stringBuilder = value;
        for(int n = value.length(); n < size;n++){
            stringBuilder = stringBuilder + " ";
        }
        return stringBuilder;
    }

    private String enterDiagnosis(){
        Utils.print("\nType the Diagnosis based in the results:\n");
        String diagnosis = read.nextLine();
        return diagnosis;
    }

    private String enterReport(){
        Utils.print("\nType the Report:\n");
        String report = read.nextLine();

        return report;
    }

    private Boolean menuReport(){
        boolean control = false;
        Utils.print("You want to write more reports?");
        Utils.print("[1] - Yes\n[2] - No");
        String option = read.nextLine();
        if (option.contains("1")){
            control = true;
        }
        if (option.contains("2")){
            control = false;
        }
        else if (!option.contains("1") && !option.contains("2")){
            throw new IllegalArgumentException("Enter a valid option (just the number 1 or 2)");
        }
        return control;
    }
}
