package app.ui.console;

import app.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ClinicalChemistryTechnologistUI implements Runnable{
    public ClinicalChemistryTechnologistUI()
    {
    }

    public void run()
    {
        List<MenuItem> options = new ArrayList<>();
        options.add(new MenuItem("Record Test Results ", new RecordTestResultsUI()));
        options.add(new MenuItem("Consult Historical Tests ", new ConsultTestsUI()));
        int option = 0;
        do
        {
            option = Utils.showAndSelectIndex(options, "\n\nClinical Chemistry Technologist Menu:");

            if ( (option >= 0) && (option < options.size()))
            {
                options.get(option).run();
            }
        }
        while (option != -1 );
    }
}


