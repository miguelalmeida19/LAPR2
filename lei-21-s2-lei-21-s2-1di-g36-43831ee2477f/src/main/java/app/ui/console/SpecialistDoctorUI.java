package app.ui.console;

import app.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class SpecialistDoctorUI implements Runnable{
    public SpecialistDoctorUI()
    {
    }

    public void run()
    {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Write Report ", new WriteReportUI()));
        int option = 0;
        do
        {
            option = Utils.showAndSelectIndex(options, "\n\nSpecialist Doctor Menu:");

            if ( (option >= 0) && (option < options.size()))
            {
                options.get(option).run();
            }
        }
        while (option != -1 );
    }
}


