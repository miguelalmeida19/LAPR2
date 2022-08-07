package app.ui.console;

import app.controller.RecordTestResultsController;
import app.mappers.dto.ParameterDTO;
import app.mappers.dto.TestDTO;
import app.ui.console.utils.Utils;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class RecordTestResultsUI implements Runnable {
    static Scanner read = new Scanner(System.in);
    private final RecordTestResultsController controller = new RecordTestResultsController();

    public void run() {
        try {

            TestDTO testDTO = getTestByBarcode();
            addTestResults(testDTO);
            controller.validateParameters(testDTO);
        }catch (Exception e){
            Utils.print("Error: "+e.getMessage());


        }
    }

    public TestDTO getTestByBarcode(){
        Utils.print("Enter the barcode of the test you wish to record results for.");
        try {
            String barcode = read.nextLine();
            TestDTO test;

            test = controller.getTestByBarcode(barcode);
            Utils.print("Test corresponding to the barcode: ");
            System.out.print(test.getTestCode());
            return test;
        }
        catch (Exception e){
            Utils.print("Error:");
            Utils.print(e.getMessage());
        }
        throw new IllegalArgumentException("Barcode already used or not exist!");
    }

    public void addTestResults(TestDTO testDTO){
        List<ParameterDTO> parameterDTOList = testDTO.getParameterDTO();
        for (ParameterDTO p: parameterDTOList){
            Utils.print("\n\n┎┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┒");
            Utils.print("        Parameter: " + p.getName());
            Utils.print("        ParameterCode: " + p.getCode());
            System.out.print("        Result: ");
            String result = read.nextLine();
            try{
                Double.parseDouble(result);
            }catch(Exception e){
                throw new InputMismatchException("The result must be a number");
            }
            System.out.print("        Metric: ");
            String metric = read.nextLine();
            Utils.print("┖┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┚");
            try {
                controller.addTestResult(testDTO, p.getCode(), result, metric);
                Utils.print("▬▬ι════ Result Added Successfully ════ι▬▬");
            } catch (Exception e) {
                Utils.print("Error!");
                Utils.print(e.getMessage());
            }
        }
    }
}