package app.ui.console;

import app.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ClientUI implements Runnable {

    public ClientUI(){
    }

    public void run() {


        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("See Test Results ", new TestResultsUI()));
        options.add(new MenuItem("Update Personal Data ", new UpdatePersonalDataUI()));

        int option = 0;
        do
        {
            option = Utils.showAndSelectIndex(options, "\n\nClient Menu:");

            if ( (option >= 0) && (option < options.size()))
            {
                options.get(option).run();
            }
        }
        while (option != -1 );
    }
}
