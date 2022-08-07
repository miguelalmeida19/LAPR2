package app.ui.gui;

import app.Persistence;
import app.controller.App;
import app.controller.ImportTestController;
import app.domain.model.Company;
import app.ui.console.*;
import auth.AuthFacade;
import auth.UserSession;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class RoleUI implements Initializable {

    @FXML
    private Button button5;
    @FXML
    private Button button4;
    @FXML
    private Button button3;
    @FXML
    private Button button2;
    @FXML
    private Button button1;
    @FXML
    private VBox buttons;
    @FXML
    private Button session;
    private AuthFacade authFacade;
    private Company company;
    @FXML
    private AnchorPane lateralMenuPane;
    @FXML
    private Button button;
    @FXML
    private AnchorPane titleBarContainer;
    @FXML
    private Button exitButton;
    @FXML
    private Button minimizeButton;
    @FXML
    private Circle circle;
    @FXML
    private Label email;
    @FXML
    private Pane anchorPane;
    @FXML
    private Label name;
    private app.ui.gui.App mainApp;
    private Stage stage;
    private List<Image> imageList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imageList = new ArrayList<>();
        imageList.add(new Image("/images/laboratoryCoordinatorAvatar.png", false));
        imageList.add(new Image("/images/receptionistAvatar.png", false));
        imageList.add(new Image("/images/specialistDoctorAvatar.png", false));
        imageList.add(new Image("/images/clientAvatar.png", false));
        imageList.add(new Image("/images/clinicalChemistryTechnologistAvatar.png", false));
        imageList.add(new Image("/images/administratorAvatar.png", false));
        imageList.add(new Image("/images/medicalLabTechnicianAvatar.png", false));
        company = App.getInstance().getCompany();
        authFacade = company.getAuthFacade();
        Image image = getImageForUser();
        circle.setFill(new ImagePattern(image));
        UserSession userSession = authFacade.getCurrentUserSession();
        email.setText(userSession.getUserId().getEmail());
        name.setText(userSession.getUserRoles().get(0).getId());
        button.setText("Import Tests");
        session.setText("Logout");
        getUserOptions();
    }

    public void setMainApp(app.ui.gui.App mainApp) {
        this.mainApp = mainApp;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void buttonHandlerAction(ActionEvent event) throws Exception {
        ImportTestsUI importTestsUI = (ImportTestsUI) mainApp.replaceSceneContent("/fxml/ImportTests.fxml", stage);
        importTestsUI.setMainApp(mainApp);
    }

    @FXML
    public void exitAction(ActionEvent event) {
        //System.out.println("exit");
        //System.out.println("guardando");

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

    @FXML
    public void minimizeAction(ActionEvent event) {
        //System.out.println("minimize");
        Stage stage = (Stage) minimizeButton.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void sessionClicked(ActionEvent actionEvent) throws Exception {
        AuthUI authUI = (AuthUI) mainApp.replaceSceneContent("/fxml/Auth.fxml", mainApp.getStage());
        authUI.setMainApp(mainApp);
        authUI.setStage(mainApp.getStage());
        authFacade.getCurrentUserSession().doLogout();
    }

    public Image getImageForUser() {
        UserSession userSession = authFacade.getCurrentUserSession();
        for (Image image : imageList) {
            //System.out.println(image.getUrl().toLowerCase(Locale.ROOT));
            //System.out.println(userSession.getUserRoles().get(0).getId().toLowerCase(Locale.ROOT));
            if (image.getUrl().toLowerCase(Locale.ROOT).contains(userSession.getUserRoles().get(0).getId().toLowerCase(Locale.ROOT).replace(" ", ""))) {
                return image;
            }
        }
        return null;
    }

    public void getUserOptions() {
        UserSession userSession = authFacade.getCurrentUserSession();
        String role = userSession.getUserRoles().get(0).getId().toLowerCase(Locale.ROOT);
        switch (role) {
            case "receptionist":
                button.setText("Add new Client");
                button1.setText("Register Test");
                button.setOnAction(actionEvent -> {
                    Stage Stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Stage.hide();
                    new CreateClientUI().run();
                    Stage.show();
                });
                button1.setOnAction(actionEvent -> {
                    Stage Stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Stage.hide();
                    new RegisterTestUI().run();
                    Stage.show();
                });
                break;
            case "laboratory coordinator":
                button.setText("Import Tests");
                button1.setText("Overview of all the tests");
                button2.setText("Validate Tests");
                button.setOnAction(actionEvent -> {
                    ImportTestsUI importTestsUI = null;
                    try {
                        importTestsUI = (ImportTestsUI) mainApp.replaceSceneContent("/fxml/ImportTests.fxml", stage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    importTestsUI.setMainApp(mainApp);
                    importTestsUI.setStage(stage);
                });
                button1.setOnAction(actionEvent -> {
                    OverviewUI overviewUI = null;
                    try {
                        overviewUI = (OverviewUI) mainApp.replaceSceneContent("/fxml/Overview.fxml", stage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    overviewUI.setMainApp(mainApp);
                    overviewUI.setStage(stage);
                });
                button2.setOnAction(actionEvent -> {
                    Stage Stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Stage.hide();
                    new ValidateTestUI().run();
                    Stage.show();
                });
                break;
            case "clinical chemistry technologist":
                button.setText("Consult Tests");
                button1.setText("Record Test Results");
                button.setOnAction(actionEvent -> {
                    ConsultTestsUI consultTestsUI = null;
                    try {
                        consultTestsUI = (ConsultTestsUI) mainApp.replaceSceneContent("/fxml/ConsultTests.fxml", stage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    consultTestsUI.setMainApp(mainApp);
                });
                button1.setOnAction(actionEvent -> {
                    Stage Stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Stage.hide();
                    new RecordTestResultsUI().run();
                    Stage.show();
                });
                break;
            case "administrator":
                button.setText("Send Report");
                button1.setText("Specify new Parameter Category");
                button2.setText("Specify new Parameter");
                button3.setText("Create new Employee");
                button4.setText("Create new Test Type");
                button5.setText("Create new Laboratory");
                button.setOnAction(actionEvent -> {
                    SendReportUI sendReportUI = null;
                    try {
                        sendReportUI = (SendReportUI) mainApp.replaceSceneContent("/fxml/SendReport.fxml", stage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    sendReportUI.setMainApp(mainApp);
                });
                button1.setOnAction(actionEvent -> {
                    Stage Stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Stage.hide();
                    new CreateParameterCategoryUI().run();
                    Stage.show();
                });
                button2.setOnAction(actionEvent -> {
                    Stage Stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Stage.hide();
                    new CreateParameterUI().run();
                    Stage.show();
                });
                button3.setOnAction(actionEvent -> {
                    Stage Stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Stage.hide();
                    new RegisterEmployeeUI().run();
                    Stage.show();
                });
                button4.setOnAction(actionEvent -> {
                    Stage Stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Stage.hide();
                    new CreateTestTypeUI().run();
                    Stage.show();
                });
                button5.setOnAction(actionEvent -> {
                    Stage Stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Stage.hide();
                    new CreateNewClinicalLabUI().run();
                    Stage.show();
                });
                break;
            case "client":
                button.setText("See Test Results");
                button.setOnAction(actionEvent -> {
                    TestResultsUI testResultsUI = null;
                    try {
                        testResultsUI = (TestResultsUI) mainApp.replaceSceneContent("/fxml/TestResults.fxml", stage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    testResultsUI.setMainApp(mainApp);
                });
                button1.setText("Update Personal Data");
                button1.setOnAction(actionEvent -> {
                    UpdatePersonalDataUI updatePersonalDataUI = null;
                    try {
                        updatePersonalDataUI = (UpdatePersonalDataUI) mainApp.replaceSceneContent("/fxml/UpdatePersonalData.fxml", stage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    updatePersonalDataUI.setMainApp(mainApp);
                });
                break;
            case "medical lab technician":
                button.setText("Record Samples");
                button.setOnAction(actionEvent -> {
                    Stage Stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Stage.hide();
                    new RecordTestSamplesUI().run();
                    Stage.show();
                });
                break;
            case "specialist doctor":
                button.setText("Write Report");
                button.setOnAction(actionEvent -> {
                    Stage Stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Stage.hide();
                    new WriteReportUI().run();
                    Stage.show();
                });
                break;
        }
    }

    @FXML
    public void buttonHandlerAction1(ActionEvent actionEvent) {
    }

    @FXML
    public void buttonHandlerAction2(ActionEvent actionEvent) {
    }

    @FXML
    public void buttonHandlerAction3(ActionEvent actionEvent) {
    }

    @FXML
    public void buttonHandlerAction4(ActionEvent actionEvent) {
    }

    @FXML
    public void buttonHandlerAction5(ActionEvent actionEvent) {
    }
}