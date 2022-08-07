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

public class SubsequenceUI extends RoleUI implements Initializable {
    @FXML
    private ComboBox subsequenceAlgorithm;
    private Calendar startDateCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
    private Calendar endDateCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
    private OverviewController overviewController;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private Pane sequencePane;
    @FXML
    private Button seeButton;
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
        subsequenceAlgorithm.getItems().addAll(
                "Benchmark",
                "BruteForceAlgorithm"
        );
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

    private void setInterval() {
        LocalDate startLocalDate = startDate.getValue();

        startDateCal.setTime(Date.valueOf(startLocalDate));

        LocalDate endLocalDate = endDate.getValue();

        endDateCal.setTime(Date.valueOf(endLocalDate));

    }

    private int getStart(int[] parent, int[] children) {
        return overviewController.findArray(parent, children);
    }

    @FXML
    public void seeButton(ActionEvent actionEvent) {
        try {
            setConfig(String.valueOf(subsequenceAlgorithm.getValue()));
            sequencePane.getChildren().clear();
            overviewController = new OverviewController();
            setInterval();
            int[] sequence = overviewController.getSequence(startDateCal, endDateCal);
            int[] subSequence = overviewController.getSubSequence(sequence);
            //System.out.println(Arrays.toString(subSequence));
            int start = getStart(sequence, subSequence);
            int end = start + subSequence.length - 1;
            //System.out.println(overviewController.getIntervalOfDays(start, end));
            Label label = new Label(overviewController.getIntervalOfDays(start, end));
            label.setFont(new Font(15));
            sequencePane.getChildren().add(label);
        } catch (Exception e) {
            Alert.AlertType type = Alert.AlertType.WARNING;
            Alert alert = new Alert(type, "");

            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(mainApp.getStage());

            alert.getDialogPane().setContentText(e.getMessage());
            alert.getDialogPane().setHeaderText("Error while doing the process");

            alert.showAndWait();
        }
    }

    private void setConfig(String algorithm) throws IOException {
        if (subsequenceAlgorithm.getValue() == null) {
            throw new IllegalArgumentException("Please choose an algorithm so that the process can run.");
        } else {
            Properties prop =new Properties();
            prop.load(new FileInputStream("configuration.conf"));
            String value = "app.algorithms.subsequence." + algorithm;
            prop.setProperty("Company.Algorithms.subsequence", value);
            prop.store(new FileOutputStream("configuration.conf"),null);
        }
    }
}