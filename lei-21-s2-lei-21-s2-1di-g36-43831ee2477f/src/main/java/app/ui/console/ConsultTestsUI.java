package app.ui.console;

import app.controller.ConsultTestsController;
import app.mappers.dto.ClientDTO;
import app.mappers.dto.ParameterDTO;
import app.mappers.dto.TestDTO;
import app.ui.console.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsultTestsUI implements Runnable {
    static Scanner read = new Scanner(System.in);
    private final ConsultTestsController controller = new ConsultTestsController();

    public ConsultTestsUI() {

    }

    public void run() {
        boolean control = true;
        while (control) {
            try {
                List<String> clients = orderClients();
                ClientDTO clientDTO = selectClient(clients);
                TestDTO testDTO = selectTest(clientDTO);
                showTestResults(testDTO);
            } catch (Exception e) {
                Utils.print(e.getMessage());
            }

        }
    }

    public List<String> orderClients() {
        Utils.print("How would you like to order the clients?");
        List<String> options = new ArrayList<>();
        options.add("Order by TIN");
        options.add("Order by Name");
        int option = Utils.showAndSelectIndex(options, "Select the ordering method:");
        List<String> clientListNameTIN = new ArrayList<>();

        switch (option) {
            case 0:
                String[] clientDtoToStringTIN = clientDtoToStringTIN(controller.getClientsList());
                try {
                    String[] clientListIdOrdered = controller.sortList(clientDtoToStringTIN);
                    for (String line : clientListIdOrdered) {
                        clientListNameTIN.add(line);
                    }
                    return clientListNameTIN;
                } catch (Exception e) {
                    Utils.print(e.getMessage());
                }
                break;
            case 1:
                String[] clientListName = clientDtoToStringName(controller.getClientsList());
                try {
                    String[] clientListNameOrdered = controller.sortList(clientListName);
                    for (String line : clientListNameOrdered) {
                        clientListNameTIN.add(line);
                    }
                    return clientListNameTIN;
                } catch (Exception e) {
                    Utils.print(e.getMessage());
                }
                break;
        }
        return null;
    }

    private String[] clientDtoToStringName(List<ClientDTO> clientDTOList) {
        String[] array = new String[clientDTOList.size()];
        for (int n = 0; n < clientDTOList.size(); n++) {
            array[n] = clientDTOList.get(n).getName() + " - " + clientDTOList.get(n).getTin();
        }
        return array;
    }

    private String[] clientDtoToStringTIN(List<ClientDTO> clientDTOList) {
        String[] array = new String[clientDTOList.size()];
        for (int n = 0; n < clientDTOList.size(); n++) {
            array[n] = clientDTOList.get(n).getTin() + " - " + clientDTOList.get(n).getName();
        }
        return array;
    }

    private ClientDTO selectClient(List<String> clients) {
        int index = Utils.showAndSelectIndex(clients, "Select the Client you want");
        int column = 1;
        if (clients.get(index).split(" - ")[0].matches("[0-9]+")){
            column = 0;
        }
        Utils.print(column);
        return controller.getClientByTin(clients.get(index).split(" - ")[column]);
    }

    private TestDTO selectTest(ClientDTO clientDTO) throws
            IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        int counter = 1;
        Utils.print("Tests Available:");
        List<TestDTO> testDTOList = controller.getTestsAvailable(clientDTO.getEmail());
        try {
            for (TestDTO testDTO : testDTOList) {
                Utils.print(counter + " - " + testDTO.getTestCode());
                counter++;
            }
            Utils.print("\n0 - Cancel");
            Utils.print("\nSelect the test:");
            String option = read.nextLine();

            if (controller.getTestsAvailable(clientDTO.getEmail()).isEmpty()) {
                throw new IllegalArgumentException("There are no test results available");
            }
            if (option.equals("0")) {
                new ClientUI().run();
            } else {
                TestDTO test = testDTOList.get(Integer.parseInt(option) - 1);
                Utils.print("Test Selected: ");
                System.out.print("━━━━━━━━━━━━━━━━━━━━━━━━━▲━━━━━━━━━━━━━━━━━━━━━━━━━" +
                        "\nDate of test registration: " + test.getTestCreationDate() +
                        "\n\nDate of samples analysis: " + test.getSamplesAnalysedDate() +
                        "\n\nDate of report made: " + test.getReportMadeDate() +
                        "\n\nDate of validation: " + test.getValidationDate() +
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
            }
        } catch (Exception e) {
            Utils.print("Please enter the option you want correctly!");
        }
        return null;
    }

    private void showTestResults(TestDTO testDTO) {
        for (ParameterDTO parameterDTO : testDTO.getParameterDTO()) {
            Utils.print("▬▬ι════ " + testDTO.getTestParameterFor(parameterDTO.getCode()).getCode() + " ════ι▬▬");
            Utils.print("Result: " +
                    testDTO.getTestParameterFor(parameterDTO.getCode()).getTestParameterResul().getResult() + "\nMetric: " +
                    testDTO.getTestParameterFor(parameterDTO.getCode()).getTestParameterResul().getMetric() + "\n" +
                    testDTO.getTestParameterFor(parameterDTO.getCode()).getTestParameterResul().getRefValue());
            Utils.print("---------------------------------------\n");
        }
        Utils.print("\n\n");
    }
}