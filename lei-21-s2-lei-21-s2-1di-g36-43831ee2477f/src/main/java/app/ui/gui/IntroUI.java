package app.ui.gui;

import javafx.fxml.Initializable;
import javafx.scene.Parent;

import java.net.URL;
import java.util.ResourceBundle;

public class IntroUI extends Parent implements Initializable {
    private App mainApp;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setMainApp(App mainApp) throws InterruptedException {
        this.mainApp = mainApp;
    }
}
