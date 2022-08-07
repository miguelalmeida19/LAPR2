package app.ui.console;

import app.controller.CreateNewClinicalLabController;
import app.controller.RegisterTestController;
import app.externalModule.ReferenceValuesExternalModule;
import app.mappers.RegisterTestMapper;
import app.mappers.dto.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;
import app.ui.console.utils.Utils;

public class RegisterTestUI implements Runnable {
    static Scanner scanner = new Scanner(System.in);
    private final RegisterTestController controller = new RegisterTestController();

    private final CreateNewClinicalLabController createNewClinicalLabController = new CreateNewClinicalLabController();
    private final RegisterTestMapper registerTestMapper;
    private String TIN;

    public RegisterTestUI() {
        registerTestMapper = new RegisterTestMapper();
    }

    public void run() {
        boolean control = true;
        while (control) {
            try {
                ClientDTO clientDTO = getClientInterface();
                Utils.print();
                Utils.print("▬▬ι════ Client Information ════ι▬▬");
                Utils.print();
                Utils.print("  Name: " + clientDTO.getName());
                Utils.print("  Email: " + clientDTO.getEmail());
                Utils.print("  Phone number: " + clientDTO.getPhoneNumber());
                Utils.print("  Birth date: " + clientDTO.getBirth());
                Utils.print();
                Utils.print("---------------------------------------\n");

                //selects the testType
                TestTypeDTO testTypeDTO = selectTestType();
                verifyIfTestTypeHasAPI(testTypeDTO);
                //selects the categories
                List<ParameterCategoryDTO> categoriesSelected = selectParameterCategories(testTypeDTO);

                LaboratoryDTO laboratoryDTO = createNewClinicalLabController.getCurrentLab();

                List<ParameterDTO> parametersSelected = selectParameters(categoriesSelected);
                System.out.print("Type the NHS Number: ");
                String nhsCode = scanner.nextLine();
                confirmation(testTypeDTO, categoriesSelected, parametersSelected, nhsCode, laboratoryDTO);
                control = false;
            } catch (Exception e) {
                Utils.print("Error: " + e.getMessage());
                control = false;
                Utils.print("Please enter again the data.");

            }

        }
    }

    private ClientDTO getClientInterface() {
        Utils.print("Please enter the TIN of the client:");
        TIN = scanner.nextLine();
        controller.checkIfClientHasAlreadyTestInSystem(TIN);

        return controller.getClientByTin(TIN);

    }

    private TestTypeDTO selectTestType() {
        List<TestTypeDTO> testTypeList = controller.getlistOfTestType();
        if (testTypeList.isEmpty()) {
            throw new IllegalArgumentException("No test type is available.");
        }
        Utils.print("What is the test type of the test you wish to register?\n");
        int counter = 1;
        for (TestTypeDTO testTypeDTO : testTypeList) {
            Utils.print(counter + " - " + testTypeDTO.getDescription());
            counter++;
        }
        Utils.print("\nSelect the test type:");
        String option = scanner.nextLine();

        return testTypeList.get(Integer.parseInt(option) - 1);

    }

    private List<ParameterCategoryDTO> selectParameterCategories(TestTypeDTO testTypeDTO) {
        List<ParameterCategoryDTO> categoriesSelected = new ArrayList<>();

        //get parameter categories
        //List<ParameterCategoryDTO> parameterCategoryDTOList = controller.getListOfParameterCategories();
        Utils.print("What are the parameter categories you want to register?\n");
        //shows the parameter categories
        int counter = 1;
        for (ParameterCategoryDTO parameterCategoryDTO : testTypeDTO.getCategoriesList()) {
            Utils.print(counter + " - " + parameterCategoryDTO.getname());
            counter++;
        }
        //asks to select
        Utils.print("\nSelect the parameter categories: (format: 1,2,3,4)");
        String option = scanner.nextLine();

        //validates the options
        validateOptions(option);

        //for each category selected it will add to the categoriesSelected
        //if 1 category introduced is invalid the program will throw and exception
        try {
            for (int n = 0; n < option.split(",").length; n++) {
                categoriesSelected.add(testTypeDTO.getCategoriesList().get(Integer.parseInt(option.split(",")[n]) - 1));
            }
        } catch (Exception e) {
            throw new InputMismatchException("The introduces options does not match to any parameter category.");
        }
        return categoriesSelected;
    }

    private void validateOptions(String options) {
        String optionsOnlyNumbers = "";
        for (int n = 0; n < options.split(",").length; n++) {
            optionsOnlyNumbers = optionsOnlyNumbers + String.valueOf(options.split(",")[n]);
        }
        if (!optionsOnlyNumbers.matches("[0-9]+")) {
            throw new IllegalArgumentException("Option introduced are invalid");
        }

    }

    private List<ParameterDTO> selectParameters(List<ParameterCategoryDTO> categoriesSelected) {
        List<ParameterDTO> parametersSelected = new ArrayList<>();
        //gets all parameters
        List<ParameterDTO> parameterDTOList = controller.getlistOfParameters(categoriesSelected);
        if (parameterDTOList.isEmpty()) {
            throw new IllegalArgumentException("No parameter is available.");
        }
        Utils.print("What are the parameters you want to register?\n");
        int counter = 1;
        for (ParameterDTO parameterDTO : parameterDTOList) {
            Utils.print(counter + " - " + parameterDTO.getName() + " : " + parameterDTO.getDescription());
            counter++;
        }
        Utils.print("\nSelect the parameters: (format: 1,2,3,4)");
        String option = scanner.nextLine();

        validateOptions(option);

        //for each parameter selected it will add to the parametersSelected
        //if 1 parameter introduced is invalid the program will throw and exception
        try {
            for (int n = 0; n < option.split(",").length; n++) {
                parametersSelected.add(parameterDTOList.get(Integer.parseInt(option.split(",")[n]) - 1));
            }
        } catch (Exception e) {
            Utils.print("erro: " + e.getMessage());
            throw new InputMismatchException("The introduces options does not match to any parameter shown.");
        }
        return parametersSelected;
    }

    private void confirmation(TestTypeDTO testTypeDTO, List<ParameterCategoryDTO> categoriesSelected, List<ParameterDTO> parametersSelected, String nhsCode, LaboratoryDTO laboratoryDTO) {
        String categories = "";

        for (ParameterCategoryDTO p : categoriesSelected) {
            categories = categories + " " + p.getname();
        }

        String parameters = "";

        for (ParameterDTO parameterDTO : parametersSelected) {
            parameters = parameters + " " + parameterDTO.getDescription();
        }

        Utils.print("A new Test will be created with the following data: ");
        Utils.print("Laboratory: " + laboratoryDTO.getNameOfLaboratory());
        Utils.print("Test Type: " + testTypeDTO.getDescription());
        Utils.print("Parameter Categories: " + categories);
        Utils.print("Parameters: " + parameters);
        Utils.print("NHS number: " + nhsCode);
        Utils.print("Do you confirm?");
        Utils.print("[1] (Yes)\n[2] (No)");
        String answer = scanner.next();
        scanner.nextLine();

        if (answer.equals("1")) {
            controller.registerTest(registerTestMapper.toDTO(testTypeDTO, parametersSelected, nhsCode, laboratoryDTO, TIN));
            controller.saveTest();
            Utils.print("Success");
        } else {
            new RegisterTestUI().run();
        }
    }

    private void verifyIfTestTypeHasAPI(TestTypeDTO testType) throws FileNotFoundException {
        try{
            Properties properties = new Properties();
            InputStream inputStream = new FileInputStream("configuration.conf");
            properties.load(inputStream);
            Class<?> c= Class.forName(properties.getProperty("Company.API."+testType.getDescription()+".Class"));
            ReferenceValuesExternalModule refValueAPI  = (ReferenceValuesExternalModule) c.newInstance();
            inputStream.close();
        }catch (Exception e){
            throw new FileNotFoundException("The API is not configured in the configuration file. Try to configure the API before use the TestType");
        }

    }
}