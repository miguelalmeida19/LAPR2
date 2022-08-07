package app.ui.gui;

import app.controller.App;
import app.controller.ImportTestController;
import app.controller.OverviewController;
import app.controller.SendReportNhsController;
import app.domain.model.Company;
import app.mappers.NhsReportMapper;
import app.ui.console.*;
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
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class SendReportUI extends RoleUI implements Initializable {

    @FXML
    private ImageView success;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    private Date start;
    private Date end;
    private Date today;
    @FXML
    private DatePicker todayDate;
    @FXML
    private ComboBox sendDataFrom;
    @FXML
    private Spinner criticalPoints;
    @FXML
    private ComboBox typeOfModel;
    @FXML
    private ComboBox independentVariable;
    @FXML
    private Spinner significanceLevel;
    @FXML
    private Spinner confidenceInterval;
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
        success.setVisible(false);
        sendDataFrom.getItems().addAll("days", "weeks");
        typeOfModel.getItems().addAll("multiple", "simple");
        independentVariable.getItems().addAll("meanAge", "tests/day");
        SpinnerValueFactory<Integer> integerSpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 365, 1);
        this.criticalPoints.setValueFactory(integerSpinnerValueFactory);
        typeOfModel.setOnAction(actionEvent -> {
            if (String.valueOf(typeOfModel.getValue()).equals("multiple")) {
                independentVariable.setDisable(true);
            } else {
                independentVariable.setDisable(false);
            }
        });
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

    @FXML
    public void seeButton(ActionEvent actionEvent) {
        success.setVisible(false);
        try {
            setDates();

            SendReportNhsController controller = new SendReportNhsController();
            NhsReportMapper mapper = new NhsReportMapper();
            java.util.Date[] dates = new java.util.Date[2];
            dates[0] = start;
            dates[1] = end;
            String[] independentVars = new String[2];
            independentVars[0] = String.valueOf(independentVariable.getValue());
            controller.generateReport(mapper.toDto(today, dates, String.valueOf(sendDataFrom.getValue()), Integer.parseInt(String.valueOf(criticalPoints.getValue()).replace(".0", "")), String.valueOf(typeOfModel.getValue()), independentVars, (Double) significanceLevel.getValue(), (Double) confidenceInterval.getValue()));
            success.setVisible(true);
        } catch (Exception e) {
            Alert.AlertType type = Alert.AlertType.ERROR;
            Alert alert = new Alert(type, "");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(mainApp.getStage());

            alert.getDialogPane().setContentText(e.getMessage());
            alert.getDialogPane().setHeaderText("Error while doing the process");

            alert.showAndWait();
        }
    }

    private void setDates() {
        LocalDate startLocalDate = startDate.getValue();
        start = Date.valueOf(startLocalDate);
        LocalDate endLocalDate = endDate.getValue();
        end = Date.valueOf(endLocalDate);
        LocalDate todayLocalDate = todayDate.getValue();
        today = Date.valueOf(todayLocalDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);

        if (String.valueOf(sendDataFrom.getValue()).equals("weeks")) {
            calendar.add(Calendar.WEEK_OF_YEAR, 4);
            java.util.Date date = calendar.getTime();
            LocalDate localDate = new java.sql.Date(date.getTime()).toLocalDate();
            if (endLocalDate.isBefore(localDate)){
                throw new IllegalArgumentException("Start date and end date should be spaced 4 weeks at least");
            }
        }
        if (String.valueOf(sendDataFrom.getValue()).equals("days")) {
            calendar.add(Calendar.DAY_OF_YEAR, 4);
            java.util.Date date = calendar.getTime();
            LocalDate localDate = new java.sql.Date(date.getTime()).toLocalDate();
            if (endLocalDate.isBefore(localDate)){
                throw new IllegalArgumentException("Start date and end date should be spaced 4 days at least");
            }
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

    private java.util.Date parseDatesWeek(Date date) throws ParseException {
        LocalDate date1 = new java.sql.Date(date.getTime()).toLocalDate();
        if (date1.getDayOfWeek() != DayOfWeek.SUNDAY)
            throw new IllegalArgumentException("The date provided must be a sunday when the program is set to send week data");
        return date;
    }
}