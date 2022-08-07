package app.ui.console;

import app.controller.AuthController;
import app.domain.shared.Constants;
import app.ui.console.utils.Utils;
import auth.mappers.dto.UserRoleDTO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Paulo Maio <pam@isep.ipp.pt>
 */

public class AuthUI implements Runnable{
    private final AuthController ctrl;

    public AuthUI()
    {
        ctrl = new AuthController();
    }

    public void run()
    {
        boolean success = doLogin();

        if (success)
        {
            List<UserRoleDTO> roles = this.ctrl.getUserRoles();
            if ( (roles == null) || (roles.isEmpty()) )
            {
                Utils.print("User has not any role assigned.");
            }
            else
            {
                UserRoleDTO role = selectsRole(roles);
                if (!Objects.isNull(role))
                {
                    List<MenuItem> rolesUI = getMenuItemForRoles();
                    this.redirectToRoleUI(rolesUI,role);
                }
                else
                {
                    Utils.print("User did not select a role.");
                }
            }
        }
        this.logout();
    }

    private List<MenuItem> getMenuItemForRoles()
    {
        List<MenuItem> rolesUI = new ArrayList<>();
        rolesUI.add(new MenuItem(Constants.ROLE_ADMIN, new AdminUI()));
        rolesUI.add(new MenuItem(Constants.ROLE_RECEPTIONIST, new ReceptionistUI()));
        rolesUI.add(new MenuItem(Constants.ROLE_MEDICAL_LAB_TECHNICIAN, new MedicalLabTechnicianUI()));
        rolesUI.add(new MenuItem(Constants.ROLE_LABORATORY_COORDINATOR, new LaboratoryCoordinatorUI()));
        rolesUI.add(new MenuItem(Constants.ROLE_SPECIALIST_DOCTOR, new SpecialistDoctorUI()));
        rolesUI.add(new MenuItem(Constants.ROLE_CLINICAL_CHEMISTRY_TECHNOLOGIST, new ClinicalChemistryTechnologistUI()));
        rolesUI.add(new MenuItem(Constants.ROLE_CLIENT, new ClientUI()));

        return rolesUI;
    }

    private boolean doLogin()
    {
        Utils.print("\nLogin UI:");

        int maxAttempts = 3;
        boolean success = false;
        do
        {
            maxAttempts--;
            String id = Utils.readLineFromConsole("Enter UserId/Email: ");
            String pwd = Utils.readLineFromConsole("Enter Password: ");

            success = ctrl.doLogin(id, pwd);
            if (!success)
            {
                Utils.print("Invalid UserId and/or Password. \n You have  " + maxAttempts + " more attempt(s).");
            }

        } while (!success && maxAttempts > 0);
        return success;
    }

    private void logout()
    {
        ctrl.doLogout();
    }

    private void redirectToRoleUI(List<MenuItem> rolesUI, UserRoleDTO role)
    {
        boolean found = false;
        Iterator<MenuItem> it = rolesUI.iterator();
        while (it.hasNext() && !found)
        {
            MenuItem item = it.next();
            found = item.hasDescription(role.getDescription());
            if (found)
                item.run();
        }
        if (!found)
            Utils.print("There is no UI for users with role '" + role.getDescription() + "'");
    }

    private UserRoleDTO selectsRole(List<UserRoleDTO> roles)
    {
        if (roles.size() == 1)
            return roles.get(0);
        else
            return (UserRoleDTO) Utils.showAndSelectOne(roles, "Select the role you want to adopt in this session:");
    }


}