package app.ui.console;

import app.controller.CreateNewClinicalLabController;
import app.mappers.dto.LaboratoryDTO;
import app.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MedicalLabTechnicianUI implements Runnable{
    static Scanner scanner = new Scanner(System.in);
    private final CreateNewClinicalLabController controller = new CreateNewClinicalLabController();

    public MedicalLabTechnicianUI() {
    }

    public void run() {
        try {
            LaboratoryDTO laboratoryDTO = selectLab();
            Utils.print("Laboratory Chosen: " + laboratoryDTO.getNameOfLaboratory());
        }
        catch (Exception e){
            Utils.print("\n" + e.getMessage());
            new AuthUI().run();
        }

        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Record Samples ", new RecordTestSamplesUI()));

        int option = 0;
        do
        {
            option = Utils.showAndSelectIndex(options, "\n\nMedical Lab Technician Menu:");

            if ( (option >= 0) && (option < options.size()))
            {
                options.get(option).run();
            }
        }
        while (option != -1 );
    }

    private LaboratoryDTO selectLab() {
        List<LaboratoryDTO> laboratoryDTOList = controller.getLaboratories();
        if(laboratoryDTOList.isEmpty()){
            throw new IllegalArgumentException("No laboratories are registered! :(");
        }
        Utils.print("\nWhat is your laboratory?\n");
        int counter = 1;
        for (LaboratoryDTO laboratoryDTO : laboratoryDTOList) {
            Utils.print(counter + " - " + laboratoryDTO.getNameOfLaboratory());
            counter++;
        }
        Utils.print("\nSelect the Laboratory:");
        String option = scanner.nextLine();
        try {
            LaboratoryDTO laboratoryDTO = laboratoryDTOList.get(Integer.parseInt(option) - 1);
            controller.currentLab(laboratoryDTO);
            return laboratoryDTO;
        }
        catch (Exception e){
            Utils.print("Error: " + e.getMessage());
            Utils.print("Please enter the option you want correctly!");
        }
        return null;
    }
}