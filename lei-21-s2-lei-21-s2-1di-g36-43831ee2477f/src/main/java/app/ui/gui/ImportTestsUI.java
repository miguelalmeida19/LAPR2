package app.ui.gui;

import app.controller.ImportTestController;
import app.domain.model.Company;
import auth.AuthFacade;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ImportTestsUI extends RoleUI implements Initializable {

    private ImportTestController importTestController = new ImportTestController();
    private Company company;
    private AuthFacade authFacade;
    @FXML
    private VBox dropFilesAnchorPane;
    @FXML
    private VBox dropFiles;
    @FXML
    private Label success1;
    @FXML
    private AnchorPane lateralMenuPane;
    @FXML
    private Label success;
    @FXML
    private ComboBox testTypes;
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
    private Pane anchorPane;
    @FXML
    private Label name;

    private app.ui.gui.App mainApp;

    private String fileName;

    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        success.setVisible(false);
        success1.setVisible(false);
        dropFiles.setOnDragExited(new EventHandler<DragEvent>() {
            public void handle(DragEvent dragEvent) {
                dropFiles.setOpacity(1);
            }
        });
        dropFiles.setOnDragOver(new EventHandler<DragEvent>() {

            public void handle(DragEvent event) {
                if (event.getGestureSource() != dropFiles
                        && event.getDragboard().hasFiles()) {
                    dropFiles.setOpacity(0.8);
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });
        dropFiles.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                success.setVisible(false);
                success1.setVisible(false);
                ObservableList<Node> node = dropFiles.getChildren();
                dropFiles.getChildren().clear();
                dropFiles.getChildren().add(dropFilesAnchorPane);
                dropFilesAnchorPane.setOpacity(1);
                fileName = "";
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    fileName = db.getFiles().get(0).getName();
                    addCSV(fileName);
                    //System.out.println(db.getFiles().get(0).getName());
                    success = true;
                }
                event.setDropCompleted(success);
                event.consume();
                dropFiles.setOpacity(1);
            }
        });
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
    public void importClicked(ActionEvent actionEvent) {
        final int initialTests = importTestController.getTestDTOList().size();
        int numberOfTestsImported = 0;
        try {
            try {
                if (fileName.equals("")){
                    throw new IllegalArgumentException("Please select a file first");
                }
                try {
                    importTestController.getTestList(fileName);
                    numberOfTestsImported = importTestController.getTestDTOList().size()-initialTests;
                }catch (Exception e){
                }

                if (numberOfTestsImported==0){
                    throw new IllegalArgumentException("Invalid file, or tests already in System");

                }
                success.setVisible(true);
                success1.setVisible(true);
                success1.setText("Tests imported: " + numberOfTestsImported);
                fileName="";
            }catch (Exception e){
                Alert.AlertType type = Alert.AlertType.WARNING;
                Alert alert = new Alert(type, "");

                alert.initModality(Modality.APPLICATION_MODAL);
                alert.initOwner(mainApp.getStage());

                alert.getDialogPane().setContentText(e.getMessage());
                alert.getDialogPane().setHeaderText("Error while importing");

                alert.showAndWait();
            }
        } catch (Exception e) {
        }
    }

    @FXML
    public void sessionClicked(ActionEvent actionEvent) throws Exception {
        super.sessionClicked(actionEvent);
    }

    @FXML
    public void addCSV(String fileName){
        dropFilesAnchorPane.setOpacity(0.05);
        ImageView imageView = new ImageView("/images/csv.png");
        Label label = new Label(fileName);
        dropFiles.getChildren().add(imageView);
        dropFiles.getChildren().add(label);
        label.setFont(new Font("Arial", 30));
    }

    @FXML
    public void selectFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        fileName = selectedFile.getName();
        addCSV(selectedFile.getName());
    }

    @FXML
    public void anotherFile(ActionEvent actionEvent) {
        success.setVisible(false);
        success1.setVisible(false);
        ObservableList<Node> node = dropFiles.getChildren();
        dropFiles.getChildren().clear();
        dropFiles.getChildren().add(dropFilesAnchorPane);
        dropFilesAnchorPane.setOpacity(1);
        fileName = "";
    }

    @FXML
    public void orderBy(ActionEvent actionEvent) {

    }
}
