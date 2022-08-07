package app.ui.gui;

import app.controller.App;
import app.controller.ImportTestController;
import app.controller.OverviewController;
import app.domain.model.Company;
import auth.AuthFacade;
import auth.UserSession;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.management.relation.Role;
import java.io.*;
import java.net.URL;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;

public class TestsNumbersUI extends RoleUI implements Initializable {

    @FXML
    private Label numberOfTests;
    @FXML
    private Label waitingForResults;
    @FXML
    private Label testsWaitingForDiagnosis;
    @FXML
    private Label numberOfClients;
    @FXML
    private Label welcome1;
    @FXML
    private Label welcome11;
    @FXML
    private Label welcome;
    private Company company;
    private AuthFacade authFacade;
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
    private Label name;

    private app.ui.gui.App mainApp;

    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        try {
            setLabels();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setMainApp(app.ui.gui.App mainApp) {
        super.setMainApp(mainApp);
        this.mainApp = mainApp;
    }

    public void setStage(Stage stage) {
        super.setStage(stage);
        this.stage = stage;
    }

    @FXML
    public void exitAction(ActionEvent event) {
        super.exitAction(event);
    }

    @FXML
    public void minimizeAction(ActionEvent event) {
        super.minimizeAction(event);
    }

    @FXML
    public void sessionClicked(ActionEvent actionEvent) throws Exception {
        super.sessionClicked(actionEvent);
    }

    private void setLabels() throws ParseException {
        OverviewController overviewController = new OverviewController();
        numberOfTests.setText("Nº of Tests in System: " + String.valueOf(overviewController.getTestsNumber()));
        testsWaitingForDiagnosis.setText("Tests waiting for results: " + String.valueOf(overviewController.getTestWaitingDiagnosis()));
        waitingForResults.setText("Tests waiting for diagnosis: " + String.valueOf(overviewController.getTestWaitingResults()));
        numberOfClients.setText("Nº of Clients: " + String.valueOf(overviewController.getClientsNumber()));
    }
}