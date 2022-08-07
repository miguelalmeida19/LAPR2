package app.ui.gui;

import app.controller.ClientTestResultsController;
import app.controller.ConsultTestsController;
import app.controller.ImportTestController;
import app.controller.UpdatePersonalDataController;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UpdatePersonalDataUI extends RoleUI implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField sexField;
    private UpdatePersonalDataController controller = new UpdatePersonalDataController();
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
    private Button button1;
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
        super.initialize(url, resourceBundle);
        company = app.controller.App.getInstance().getCompany();
        authFacade = company.getAuthFacade();
        userSession = authFacade.getCurrentUserSession();
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
    public void setUserData(ActionEvent actionEvent) {
        String email = controller.getEmail();
        ClientDTO clientDTO = controller.getClientByEmail(email);
        try {
            if (!nameField.getText().equals("")){
                clientDTO.setName(nameField.getText());
            }
            if (!addressField.getText().equals("")){
                clientDTO.setAddress(addressField.getText());
            }
            if (!phoneNumberField.getText().equals("")){
                clientDTO.setPhoneNumber(phoneNumberField.getText());
            }
            if (!sexField.getText().equals("")){
                clientDTO.setSex(sexField.getText());
            }
            controller.setClientData(clientDTO);
        } catch (Exception e) {
            Alert.AlertType type = Alert.AlertType.ERROR;
            Alert alert = new Alert(type, "");

            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(sexField.getScene().getWindow());

            alert.getDialogPane().setContentText(e.getMessage());
            alert.getDialogPane().setHeaderText("Invalid data");

            alert.showAndWait();
        }
    }
}