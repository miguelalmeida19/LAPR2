package app.ui.gui;

import app.controller.ConsultTestsController;
import app.controller.ImportTestController;
import app.domain.model.Client;
import app.domain.model.Company;
import app.mappers.ClientMapper;
import app.mappers.dto.ClientDTO;
import app.mappers.dto.ParameterDTO;
import app.mappers.dto.TestDTO;
import auth.AuthFacade;
import auth.UserSession;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

public class ConsultTestsUI extends RoleUI implements Initializable {
    @FXML
    private VBox resultsPane;
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
    private TableView<TestDTO> resultsTable;
    @FXML
    private TableColumn<ClientDTO, String> nameCol;
    @FXML
    private TableColumn<ClientDTO, String> tinCol;
    private ImportTestController importTestController = new ImportTestController();
    private Company company;
    private AuthFacade authFacade;
    private ClientMapper clientMapper = new ClientMapper();

    @FXML
    private TableView<ClientDTO> clientsTable;
    @FXML
    private ComboBox orderOptions;
    @FXML
    private AnchorPane lateralMenuPane;
    @FXML
    private ComboBox testTypes;
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

    private final ConsultTestsController controller = new ConsultTestsController();

    private String fileName;

    private List<ClientDTO> clientList = new ArrayList<>();


    List<String> clientListNameTIN = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTable();
        List<String> options = new ArrayList<>();
        options.add("Name");
        options.add("Tin");
        ObservableList<String> optionsList =
                FXCollections.observableArrayList(options);
        orderOptions.setItems(optionsList);
        super.initialize(url, resourceBundle);

        clientsTable.setOnMouseClicked(event -> {
            getTests();
        });

        resultsTable.setOnMouseClicked(event -> {
            showTestResults();
        });
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

    @FXML
    public void order(ActionEvent actionEvent) {
        String orderParam = String.valueOf(orderOptions.getSelectionModel().getSelectedItem());
        clearInfo();
        switch (orderParam) {
            case "Tin":
                String[] getClientTinName = getClientTinName(controller.getClientsList());
                try {
                    String[] clientListIdOrdered = controller.sortList(getClientTinName);
                    for (String line : clientListIdOrdered) {
                        clientListNameTIN.add(line);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                loadInfoTable("Tin");
                break;
            case "Name":
                String[] clientListName = getClientNameTin(controller.getClientsList());
                try {
                    String[] clientListNameOrdered = controller.sortList(clientListName);
                    for (String line : clientListNameOrdered) {
                        clientListNameTIN.add(line);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                loadInfoTable("Name");
                break;
        }
    }

    private String[] getClientNameTin(List<ClientDTO> clientDTOList) {
        return controller.getClientNameTin(clientDTOList);
    }

    private String[] getClientTinName(List<ClientDTO> clientDTOList) {
        return controller.getClientTinName(clientDTOList);
    }

    private void initializeTable() {
        nameCol.setText("Name");
        tinCol.setText("Tin");
        nameCol.setSortable(false);
        tinCol.setSortable(false);
    }

    private void loadInfoTable(String param) {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        tinCol.setCellValueFactory(new PropertyValueFactory<>("Tin"));
        for (String line : clientListNameTIN) {
            if (param.equals("Name")) {
                clientList.add(new ClientDTO(line.split(" - ")[0], line.split(" - ")[1]));
            } else {
                clientList.add(new ClientDTO(line.split(" - ")[1], line.split(" - ")[0]));
            }
        }
        ObservableList<ClientDTO> personObservableList = FXCollections.observableArrayList(clientList);
        clientsTable.setItems(personObservableList);
    }

    private void clearInfo() {
        clientListNameTIN.clear();
        clientList.clear();
        for (int i = 0; i < clientsTable.getItems().size(); i++) {
            clientsTable.getItems().clear();
        }
    }

    private void getTests() {
        testCodeCol.setCellValueFactory(new PropertyValueFactory<>("TestCode"));
        testRegCol.setCellValueFactory(new PropertyValueFactory<>("TestCreationDate"));
        testChemCol.setCellValueFactory(new PropertyValueFactory<>("SamplesAnalysedDate"));
        testDoctorCol.setCellValueFactory(new PropertyValueFactory<>("ReportMadeDate"));
        testValiCol.setCellValueFactory(new PropertyValueFactory<>("ValidationDate"));
        ClientDTO client = clientsTable.getSelectionModel().getSelectedItem();
        //System.out.println(client.getName() + " selected");
        ClientDTO clientFull = controller.getClientByTin(String.valueOf(client.getTin()));
        List<TestDTO> testDTOList = controller.getTestsAvailable(clientFull.getEmail());
        List<TestDTO> testDTOSDatesCode = controller.getTestCodeDatesList(testDTOList);
        ObservableList<TestDTO> testObservableList = FXCollections.observableArrayList(testDTOSDatesCode);
        resultsTable.setItems(testObservableList);
    }

    private void showTestResults() {
        resultsPane.getChildren().clear();
        TestDTO testDTO = resultsTable.getSelectionModel().getSelectedItem();
        try {
            TestDTO testDTOFull = controller.getFullTestDTO(testDTO);
            for (ParameterDTO parameterDTO : testDTOFull.getParameterDTO()) {
                Label label1 = new Label(parameterDTO.getCode());
                Label label = new Label("Result: " +
                        testDTOFull.getTestParameterFor(parameterDTO.getCode()).getTestParameterResul().getResult() + "\nMetric: " +
                        testDTOFull.getTestParameterFor(parameterDTO.getCode()).getTestParameterResul().getMetric() + "\n" +
                        testDTOFull.getTestParameterFor(parameterDTO.getCode()).getTestParameterResul().getRefValue());
                label.setFont(new Font("Arial", 16));
                label1.setFont(new Font("Arial", 16));
                resultsPane.getChildren().add(label1);
                resultsPane.getChildren().add(label);
            }
        }catch (Exception e){
            Alert.AlertType type = Alert.AlertType.WARNING;
            Alert alert = new Alert(type, "");

            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(email.getScene().getWindow());

            alert.getDialogPane().setContentText("Please select a test first");
            alert.getDialogPane().setHeaderText("No test selected");

            alert.showAndWait();
        }
    }
}
