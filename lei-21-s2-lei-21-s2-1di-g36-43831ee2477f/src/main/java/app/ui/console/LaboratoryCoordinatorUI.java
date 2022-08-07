package app.ui.console;

import app.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class LaboratoryCoordinatorUI implements Runnable {
    public LaboratoryCoordinatorUI(){
    }

    public void run() {
        List<MenuItem> options = new ArrayList<>();
        options.add(new MenuItem("Validate Test ", new ValidateTestUI()));
        options.add(new MenuItem("Import Tests ", new ImportTestUI()));
        options.add(new MenuItem("Analyse the overall performance of the company  ", new OverviewUI()));
        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\n\nLaboratory Coordinator Menu:");

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        }
        while (option != -1);
    }
}