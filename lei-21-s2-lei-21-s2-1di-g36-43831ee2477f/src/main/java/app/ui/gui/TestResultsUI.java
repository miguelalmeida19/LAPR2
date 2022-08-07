package app.ui.gui;

import app.controller.ClientTestResultsController;
import app.controller.ConsultTestsController;
import app.controller.ImportTestController;
import app.domain.model.Company;
import app.mappers.dto.ClientDTO;
import app.mappers.dto.ParameterDTO;
import app.mappers.dto.TestDTO;
import auth.AuthFacade;
import auth.UserSession;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TestResultsUI extends RoleUI implements Initializable {

    @FXML
    private TableView<TestDTO> testsTable;
    @FXML
    private TableColumn<TestDTO, String> testCodeCol;
    @FXML
    private TableColumn<TestDTO, String> testRegCol;
    @FXML
    private TableColumn<TestDTO, String> testChemCol;
    @FXML
    private TableColumn<TestDTO, String> testDoctorCol;
    @FXML
    private TableColumn<TestDTO, String> testValiCol;
    @FXML
    private VBox resultsBoard;
    private ClientTestResultsController controller = new ClientTestResultsController();
    private Company company;
    private UserSession userSession;
    private AuthFacade authFacade;
    @FXML
    private Button testResultsButton;
    @FXML
    private AnchorPane lateralMenuPane;
    @FXML
    private Label success;
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

    private List<TestDTO> testAvailable = new ArrayList<>();

    private String fileName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        testsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        super.initialize(url, resourceBundle);
        company = app.controller.App.getInstance().getCompany();
        authFacade = company.getAuthFacade();
        userSession = authFacade.getCurrentUserSession();
        resultsBoard.setOpacity(0);
        try {
            getTestList();
            setTestsTable();
        }catch (Exception e){
            Alert.AlertType type = Alert.AlertType.WARNING;
            Alert alert = new Alert(type, "");

            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(email.getScene().getWindow());

            alert.getDialogPane().setContentText("At the moment you do not have any test registered, if you wish, please speak to the receptionist.");
            alert.getDialogPane().setHeaderText("No tests available :(");

            alert.showAndWait();
        }
    }

    public void setMainApp(App mainApp) {
        super.setMainApp(mainApp);
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

    public void getTestList(){
        testCodeCol.setCellValueFactory(new PropertyValueFactory<>("TestCode"));
        testRegCol.setCellValueFactory(new PropertyValueFactory<>("TestCreationDate"));
        testChemCol.setCellValueFactory(new PropertyValueFactory<>("SamplesAnalysedDate"));
        testDoctorCol.setCellValueFactory(new PropertyValueFactory<>("ReportMadeDate"));
        testValiCol.setCellValueFactory(new PropertyValueFactory<>("ValidationDate"));
        ClientTestResultsController testResultsController = new ClientTestResultsController();
        List<TestDTO> testDTOList = testResultsController.getTestsAvailable(userSession.getUserId().getEmail());
        for (TestDTO testDTO : testDTOList){
            testAvailable.add(new TestDTO(testDTO.getTestCode(), testDTO.getTestCreationDate(), testDTO.getSamplesAnalysedDate(), testDTO.getReportMadeDate(), testDTO.getValidationDate()));
        }
    }

    @FXML
    public void testResultsClicked(ActionEvent actionEvent) {
        resultsBoard.getChildren().clear();
        resultsBoard.setOpacity(1);
        TestDTO testDTO = testsTable.getSelectionModel().getSelectedItem();
        ConsultTestsController consultTestsController = new ConsultTestsController();
        TestDTO testDTOFull = consultTestsController.getFullTestDTO(testDTO);

        for (ParameterDTO parameterDTO : testDTOFull.getParameterDTO()) {
            Label label1 = new Label(parameterDTO.getCode());
            Label label = new Label("Result: " +
                    testDTOFull.getTestParameterFor(parameterDTO.getCode()).getTestParameterResul().getResult() + "\nMetric: " +
                    testDTOFull.getTestParameterFor(parameterDTO.getCode()).getTestParameterResul().getMetric() + "\n" +
                    testDTOFull.getTestParameterFor(parameterDTO.getCode()).getTestParameterResul().getRefValue());
            label.setFont(new Font("Arial", 16));
            label1.setFont(new Font("Arial", 16));
            resultsBoard.getChildren().add(label1);
            resultsBoard.getChildren().add(label);
        }
    }

    private void setTestsTable() {
        testRegCol.setSortType(TableColumn.SortType.DESCENDING);
        ObservableList<TestDTO> testObservableList = FXCollections.observableArrayList(testAvailable);
        testsTable.setItems(testObservableList);
        testsTable.getSortOrder().add(testRegCol);
    }
}