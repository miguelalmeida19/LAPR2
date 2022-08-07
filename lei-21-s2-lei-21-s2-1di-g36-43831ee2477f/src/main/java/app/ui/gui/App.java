package app.ui.gui;

import app.controller.SendTask;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App extends Application {
    private Stage stage;
    private final double MINIMUM_WINDOW_WIDTH = 200;
    private final double MINIMUM_WINDOW_HEIGHT = 200;
    private final double SCENE_WIDTH = 1920;
    private final double SCENE_HEIGHT = 1080;
    private Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        cleanBarcodesNotifications();
        SendTask sendTask = new SendTask();
        sendTask.sendReport();
        this.stage = stage;
        stage.setTitle("ManyLabs");
        stage.setMinWidth(MINIMUM_WINDOW_WIDTH);
        stage.setMinHeight(MINIMUM_WINDOW_HEIGHT);
        stage.getIcons().add(new Image("/images/icon.png"));

        toIntro();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    toMainScene();
                    toDefault();
                });
            }
        }, 4000);
        stage.show();
    }

    public Stage getStage() {
        return this.stage;
    }

    public void toIntro() {
        try {
            IntroUI introUI = (IntroUI) replaceSceneContent("/fxml/Intro.fxml", stage);
            stage.setAlwaysOnTop(true);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setWidth(397);
            stage.setHeight(247);
            scene.setFill(Color.TRANSPARENT);
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
            introUI.setMainApp(this);

        } catch (Exception e) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void toMainScene() {
        try {
            AuthUI mainUI = (AuthUI) replaceSceneContent("/fxml/Auth.fxml", stage);
            mainUI.setMainApp(this);
            mainUI.setStage(stage);

        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Initializable replaceSceneContent(String fxml, Stage stage1) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = App.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(App.class.getResource(fxml));
        Pane page;
        try {
            page = (Pane) loader.load(in);
        } finally {
            in.close();
        }
        scene = new Scene(page, SCENE_WIDTH, SCENE_HEIGHT);
        scene.getStylesheets().add("/styles/Styles.css");
        stage1.setScene(scene);
        return (Initializable) loader.getController();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void toDefault() {
        stage.setAlwaysOnTop(false);
        stage.setMaximized(true);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void cleanBarcodesNotifications() {
        File a = new File("emailAndSMSMessages.txt");
        a.delete();
        File dir = new File("./barcodes");
        for (File file: dir.listFiles()) {
            file.delete();
        }
    }
}
