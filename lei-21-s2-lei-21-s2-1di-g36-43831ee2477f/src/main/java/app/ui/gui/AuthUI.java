package app.ui.gui;

import app.Persistence;
import app.domain.model.Company;
import app.domain.store.OrganizationRoleStore;
import auth.AuthFacade;
import auth.UserSession;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.management.relation.Role;
import java.net.URL;
import java.util.*;

public class AuthUI implements Initializable {

    private Company company;

    private List<String> fxmlList = new ArrayList<>();

    private Stage stage;

    private AuthFacade authFacade;
    @FXML
    private PasswordField passowrd;
    @FXML
    private TextField email;
    @FXML
    private ImageView rocketEmail;
    @FXML
    private ImageView rocketPass;
    @FXML
    private AnchorPane page;

    private App mainApp;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setMainApp(App mainApp) {
        fxmlList.clear();
        fxmlList.add("/fxml/ReceptionistDashboard.fxml");
        fxmlList.add("/fxml/SpecialistDoctorDashboard.fxml");
        fxmlList.add("/fxml/MedicalLabTechnicianDashboard.fxml");
        fxmlList.add("/fxml/ClinicalChemistryTechnologistDashboard.fxml");
        fxmlList.add("/fxml/LaboratoryCoordinatorDashboard.fxml");
        fxmlList.add("/fxml/AdminDashboard.fxml");
        fxmlList.add("/fxml/ClientDashboard.fxml");

        this.mainApp = mainApp;
        company = app.controller.App.getInstance().getCompany();
        authFacade = company.getAuthFacade();
        page.requestFocus();
        rocketEmail.setVisible(false);
        rocketPass.setVisible(false);
    }

    public void minimize(MouseEvent event) {
        stage.setIconified(true);
    }

    public void close(MouseEvent event) throws InterruptedException {
        try {
            //System.out.println("guardando");
            Persistence.saveAllData();
            Platform.exit();

            //System.out.println("exit");
            System.exit(0);

        } catch (Exception e) {
            System.out.println("The data was not saved correctly :(( " + e.getMessage());
            Platform.exit();

            System.exit(0);

        }
    }

    public void passwordClicked(MouseEvent event) throws InterruptedException {
        rocketPass.setVisible(true);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    rocketPass.setVisible(false);
                });
            }
        }, 900);
    }

    public void emailClicked(MouseEvent event) throws InterruptedException {
        rocketEmail.setVisible(true);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    rocketEmail.setVisible(false);
                });
            }
        }, 900);
    }

    public void keypressed(KeyEvent keyEvent) throws Exception {
        if (keyEvent.getCode().isArrowKey()){
            if (keyEvent.getCode() == KeyCode.DOWN){
                passowrd.requestFocus();
            }
            if (keyEvent.getCode() == KeyCode.UP){
                email.requestFocus();
            }
        }
        if (keyEvent.getCode() == KeyCode.ENTER){

                if (!email.getText().isEmpty() && !passowrd.getText().isEmpty()) {
                    try {
                        doLogin(email.getText(), passowrd.getText());
                        if (authFacade.getCurrentUserSession().getUserRoles().isEmpty()){
                            Alert.AlertType type = Alert.AlertType.ERROR;
                            Alert alert = new Alert(type, "");

                            alert.initModality(Modality.APPLICATION_MODAL);
                            alert.initOwner(email.getScene().getWindow());

                            alert.getDialogPane().setContentText("Data entered does not correspond to any user of the system.");
                            alert.getDialogPane().setHeaderText("Invalid email/password");

                            alert.showAndWait();
                        }
                        else {
                            getUIForRoles(authFacade.getCurrentUserSession().getUserRoles().get(0).getId());
                        }
                    } catch (Exception e) {
                        Alert.AlertType type = Alert.AlertType.ERROR;
                        Alert alert = new Alert(type, "");

                        alert.initModality(Modality.APPLICATION_MODAL);
                        alert.initOwner(email.getScene().getWindow());

                        alert.getDialogPane().setContentText("Data entered does not correspond to any user of the system.");
                        alert.getDialogPane().setHeaderText("Invalid email/password");

                        alert.showAndWait();
                    }
                }
                else{
                    Alert.AlertType type = Alert.AlertType.ERROR;
                    Alert alert = new Alert(type, "");

                    alert.initModality(Modality.APPLICATION_MODAL);
                    alert.initOwner(email.getScene().getWindow());

                    alert.getDialogPane().setContentText("Data entered does not correspond to any user of the system.");
                    alert.getDialogPane().setHeaderText("Invalid email/password");

                    alert.showAndWait();
                }
        }
    }

    public void doLogin(String email1, String password1){
        authFacade.doLogin(email1, password1);
    }

    private void getUIForRoles(String role) throws Exception {

        OrganizationRoleStore organizationRoleStore = new OrganizationRoleStore();
        for (int i=0; i<organizationRoleStore.getRoles().size(); i++){
            if (organizationRoleStore.getRoles().get(i).getId().toLowerCase(Locale.ROOT).equals(role.toLowerCase(Locale.ROOT))){
                //System.out.println(fxmlList.get(i));
                RoleUI roleUI = (RoleUI) mainApp.replaceSceneContent(fxmlList.get(i), stage);
                roleUI.setMainApp(mainApp);
                roleUI.setStage(stage);
            }
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}