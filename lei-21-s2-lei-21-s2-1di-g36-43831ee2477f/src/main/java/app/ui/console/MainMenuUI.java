package app.ui.console;

import app.ui.console.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Paulo Maio <pam@isep.ipp.pt>
 */
public class MainMenuUI {

    public MainMenuUI()
    {
    }
    private int controller = 0;
    public void run() throws IOException
    {
        Utils.print("Many Labs \n|-███═─");
        // ################# clears the notification file. it gets too big over the time. ############################
        if(controller == 0){
            File a = new File("emailAndSMSMessages.txt");
            a.delete();
        }
        controller++;
        // ######################################
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Do Login", new AuthUI()));
        options.add(new MenuItem("Know the Development Team",new DevTeamUI()));
        int option = 0;
        do
        {
            option = Utils.showAndSelectIndex(options, "\n\nMain Menu");

            if ( (option >= 0) && (option < options.size()))
            {
                options.get(option).run();
            }
        }
        while (option != -1 );
    }
}
