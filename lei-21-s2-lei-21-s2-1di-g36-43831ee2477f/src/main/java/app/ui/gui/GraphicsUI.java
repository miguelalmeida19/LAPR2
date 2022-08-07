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
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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
import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;

public class GraphicsUI extends RoleUI implements Initializable {
    @FXML
    private NumberAxis y;
    @FXML
    private CategoryAxis x;
    @FXML
    private LineChart chart;
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

    private void setInterval(){
        LocalDate startLocalDate = startDate.getValue();

        startDateCal.setTime(Date.valueOf(startLocalDate));

        LocalDate endLocalDate = endDate.getValue();

        endDateCal.setTime(Date.valueOf(endLocalDate));

        if (endLocalDate.isBefore(startLocalDate)){
            Alert.AlertType type = Alert.AlertType.ERROR;
            Alert alert = new Alert(type, "");

            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(mainApp.getStage());

            alert.getDialogPane().setContentText("End date cannot be before start date");
            alert.getDialogPane().setHeaderText("Invalid Dates");

            alert.showAndWait();
        }
        if((startDate.getValue()==null || endDate.getValue()==null) || (startDate.getValue()==null && endDate.getValue()==null)){
            Alert.AlertType type = Alert.AlertType.WARNING;
            Alert alert = new Alert(type, "");

            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(mainApp.getStage());

            alert.getDialogPane().setContentText("Cannot load graphic, because dates are empty");
            alert.getDialogPane().setHeaderText("Dates empty");

            alert.showAndWait();
        }
    }

    private int getStart(int[] parent, int[] children){
        return overviewController.findArray(parent, children);
    }

    @FXML
    public void perDay(ActionEvent actionEvent) {
        try {
            chart.getData().clear();
            overviewController = new OverviewController();
            setInterval();
            LocalDate startDateLocalDate = startDate.getValue();
            LocalDate endDateLocalDate = endDate.getValue();

            int[] testsPerformedByDay = overviewController.getTestsPerformedByDay(startDateLocalDate, endDateLocalDate);
            XYChart.Series S = new XYChart.Series<>();
            int counter = 0;
            for (int test: testsPerformedByDay){
                S.getData().add(new XYChart.Data<>(String.valueOf(counter), test));
                counter++;
            }
            chart.getData().addAll(S);
        } catch (Exception e) {
            Alert.AlertType type = Alert.AlertType.WARNING;
            Alert alert = new Alert(type, "");

            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(mainApp.getStage());

            alert.getDialogPane().setContentText("Cannot load graphic, because dates are empty");
            alert.getDialogPane().setHeaderText("Dates empty");

            alert.showAndWait();
        }
    }

    @FXML
    public void perWeek(ActionEvent actionEvent) {
        try {
            chart.getData().clear();
            overviewController = new OverviewController();
            setInterval();
            LocalDate startDateLocalDate = startDate.getValue();
            LocalDate endDateLocalDate = endDate.getValue();

            int[] testsPerformedByWeek = overviewController.getTestsPerformedByWeek(startDateLocalDate, endDateLocalDate);
            XYChart.Series S = new XYChart.Series<>();
            int counter = 0;
            for (int test: testsPerformedByWeek){
                S.getData().add(new XYChart.Data<>(String.valueOf(counter), test));
                counter++;
            }
            chart.getData().addAll(S);
        } catch (Exception e) {
            Alert.AlertType type = Alert.AlertType.WARNING;
            Alert alert = new Alert(type, "");

            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(mainApp.getStage());

            alert.getDialogPane().setContentText("Cannot load graphic, because dates are empty");
            alert.getDialogPane().setHeaderText("Dates empty");

            alert.showAndWait();
        }
    }

    @FXML
    public void perMonth(ActionEvent actionEvent) {
        try {
            chart.getData().clear();
            overviewController = new OverviewController();
            setInterval();
            LocalDate startDateLocalDate = startDate.getValue();
            LocalDate endDateLocalDate = endDate.getValue();

            int[] testsPerformedByMonth = overviewController.getTestsPerformedByMonth(startDateLocalDate, endDateLocalDate);
            XYChart.Series S = new XYChart.Series<>();
            int counter = 0;
            List<String> monthList= new ArrayList<>();
            monthList.add("Jan");
            monthList.add("Feb");
            monthList.add("Mar");
            monthList.add("Apr");
            monthList.add("May");
            monthList.add("Jun");
            monthList.add("Jul");
            monthList.add("Aug");
            monthList.add("Sep");
            monthList.add("Oct");
            monthList.add("Nov");
            monthList.add("Dec");
            for (int test: testsPerformedByMonth){
                S.getData().add(new XYChart.Data<>(monthList.get(counter), test));
                counter++;
            }
            chart.getData().addAll(S);
        } catch (Exception e) {
            Alert.AlertType type = Alert.AlertType.WARNING;
            Alert alert = new Alert(type, "");

            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(mainApp.getStage());

            alert.getDialogPane().setContentText("Cannot load graphic, because dates are empty");
            alert.getDialogPane().setHeaderText("Dates empty");

            alert.showAndWait();
        }
    }

    @FXML
    public void perYear(ActionEvent actionEvent) {
        try {
            chart.getData().clear();
            overviewController = new OverviewController();
            setInterval();
            LocalDate startDateLocalDate = startDate.getValue();
            LocalDate endDateLocalDate = endDate.getValue();

            int[] testsPerformedByYear = overviewController.getTestsPerformedByYear(startDateLocalDate, endDateLocalDate);
            XYChart.Series S = new XYChart.Series<>();
            int counter = 0;
            for (int test: testsPerformedByYear){
                S.getData().add(new XYChart.Data<>(String.valueOf(counter), test));
                counter++;
            }
            chart.getData().addAll(S);
        } catch (Exception e) {
            Alert.AlertType type = Alert.AlertType.WARNING;
            Alert alert = new Alert(type, "");

            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(mainApp.getStage());

            alert.getDialogPane().setContentText("Because the interval defined is less than one year");
            alert.getDialogPane().setHeaderText("Fail to load graphic");

            alert.showAndWait();
        }
    }
}