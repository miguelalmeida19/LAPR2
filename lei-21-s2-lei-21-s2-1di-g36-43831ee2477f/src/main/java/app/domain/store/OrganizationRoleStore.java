package app.domain.store;

import app.controller.App;
import app.domain.model.Company;
import auth.AuthFacade;
import auth.domain.model.UserRole;

import java.util.ArrayList;
import java.util.List;

public class OrganizationRoleStore {
    private List<UserRole> roles;

    public OrganizationRoleStore(){
        roles = new ArrayList<UserRole>();
        roles.add(new UserRole("Receptionist", "Responsible for scheduling appointments, answer patient inquiries and handle patient requests."));
        roles.add(new UserRole("Specialist Doctor", "Doctor who has completed advanced education and training in a specific field of medicine."));
        roles.add(new UserRole("Medical Lab Technician", "Medical laboratory technicians assist physicians in the diagnosis and treatment of diseases by performing tests on tissue, blood, and other body fluids."));
        roles.add(new UserRole("Clinical Chemistry Technologist", "Prepare specimens and analyze the chemical and hormonal contents of body fluids."));
        roles.add(new UserRole("Laboratory Coordinator", "Responsible for coordinating activities in the science labs."));
        roles.add(new UserRole("Administrator", "Responsible for coordinating the software use"));
        roles.add(new UserRole("Client", "Client person"));

        //
        // add here the new roles.
        //
        addUserRolesToSystem(); //this method is necessary to register the roles in auth mechanism.

    }

    /**
     * This method register the user roles on "store" in the authentication module.
     */
    private void addUserRolesToSystem(){
        AuthFacade authFacade =App.getInstance().getCompany().getAuthFacade();

        for(UserRole u : roles){
            authFacade.addUserRole(u.getId(),u.getDescription());
        }
    }

    /**
     * This method returns a list with all the roles
     * @return
     */
    public List<UserRole> getRoles() {
        return roles;
    }
}
