package app.ui.console;
import app.ui.console.utils.Utils;

/**
 *
 * @author Paulo Maio <pam@isep.ipp.pt>
 */
public class DevTeamUI implements Runnable{

    public DevTeamUI()
    {

    }
    public void run()
    {
        Utils.print("\n");
        System.out.print("Development Team:\n");
        System.out.print("\t Pedro Costa - 1200949@isep.ipp.pt \n");
        System.out.print("\t José Gonçalves - 1201114@isep.ipp.pt \n");
        System.out.print("\t Miguel Almeida - 1201115@isep.ipp.pt \n");
        System.out.print("\t José Pereira - 1201627@isep.ipp.pt \n");
        Utils.print("\n");
    }
}
