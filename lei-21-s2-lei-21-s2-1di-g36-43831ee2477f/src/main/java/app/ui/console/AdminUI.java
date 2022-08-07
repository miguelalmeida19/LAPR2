package app.ui.console;

import app.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Paulo Maio <pam@isep.ipp.pt>
 */

public class AdminUI implements Runnable{
    public AdminUI()
    {
    }

    public void run()
    {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Specify new Parameter Category ", new CreateParameterCategoryUI()));
        options.add(new MenuItem("Specify new Parameter ", new CreateParameterUI()));
        options.add(new MenuItem("Create new Employee ", new RegisterEmployeeUI()));
        options.add(new MenuItem("Create new Test Type ", new CreateTestTypeUI()));
        options.add(new MenuItem("Create new Laboratory ", new CreateNewClinicalLabUI()));
        //options.add(new MenuItem("Add new Client ", new CreateClientUI()));
        //options.add(new MenuItem("Register Test ", new RegisterTestUI()));
        //options.add(new MenuItem("Record Samples ", new RecordTestSamplesUI()));
        //options.add(new MenuItem("Record Test Results ", new RecordTestResultsUI()));
        //options.add(new MenuItem("Write Report ", new WriteReportUI()));
        //options.add(new MenuItem("Validate Test ", new ValidateTestUI()));

        int option = 0;
        do
        {
            option = Utils.showAndSelectIndex(options, "\n\nAdmin Menu:");

            if ( (option >= 0) && (option < options.size()))
            {
                options.get(option).run();
            }
        }
        while (option != -1 );
    }
}