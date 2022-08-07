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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
import javafx.stage.Stage;

import javax.management.relation.Role;
import java.io.File;
import java.net.URL;
import java.text.ParseException;
import java.util.*;

public class OverviewUI extends RoleUI implements Initializable {

    @FXML
    private Label welcome1;
    @FXML
    private Label welcome11;
    @FXML
    private Label welcome;
    @FXML
    private Button graphicsButton;
    private Company company;
    private AuthFacade authFacade;
    @FXML
    private AnchorPane lateralMenuPane;
    @FXML
    private Button button;
    @FXML
    private Button button1;
    @FXML
    private Button button2;
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
    }

    public void setMainApp(app.ui.gui.App mainApp) {
        super.setMainApp(mainApp);
        this.mainApp = mainApp;
    }

    public void setStage(Stage stage){
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

    @FXML
    public void subsequenceButton(ActionEvent actionEvent) {
        SubsequenceUI subsequenceUI = null;
        try {
            subsequenceUI = (SubsequenceUI) mainApp.replaceSceneContent("/fxml/Subsequence.fxml", stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        subsequenceUI.setMainApp(mainApp);
        subsequenceUI.setStage(stage);
    }

    @FXML
    public void graphicsButton(ActionEvent actionEvent) {
        GraphicsUI graphicsUI = null;
        try {
            graphicsUI = (GraphicsUI) mainApp.replaceSceneContent("/fxml/Graphics.fxml", stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        graphicsUI.setMainApp(mainApp);
        graphicsUI.setStage(stage);
    }

    @FXML
    public void testsButton(ActionEvent actionEvent) {
        TestsNumbersUI testsNumbersUI = null;
        try {
            testsNumbersUI = (TestsNumbersUI) mainApp.replaceSceneContent("/fxml/TestNumbers.fxml", stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        testsNumbersUI.setMainApp(mainApp);
        testsNumbersUI.setStage(stage);
    }
}
