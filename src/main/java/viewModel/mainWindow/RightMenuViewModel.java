package viewModel.mainWindow;

import com.google.common.eventbus.EventBus;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import org.springframework.beans.factory.annotation.Autowired;
import util.FXMLDialog;
import view.ScreensConfiguration;

import java.util.Stack;

/**
 * Created on 20-12-2016 at 19:09.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public class RightMenuViewModel {


    @Autowired
    ScreensConfiguration screensConfiguration;

    @Autowired
    EventBus eventBus;

    Scene scene;


    public RightMenuViewModel() {
    }


    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void handleEvent(Event event) {
        System.out.println(event.getEventType());
        switch (event.getEventType().toString()) {
            case "ACTION":
                handleActionEvent((ActionEvent) event);
                break;
            case "KEY_PRESSED":
                if (event.getEventType().equals(KeyEvent.KEY_PRESSED))
                    handleKeyEvent((KeyEvent) event);
                break;
            default:
                break;
        }
    }

    private void handleActionEvent(ActionEvent event) {
        if (!(event.getSource() instanceof Button))
            return;

        String id = ((Button) event.getSource()).getId();
        System.out.println("This id is : " + id);

        FXMLDialog dialog = null;
        StackPane paneContent = null;

        switch (id) {
            case "btnBase":
                paneContent = (StackPane) scene.lookup("#contentPane");
                paneContent.getChildren().add(screensConfiguration.base().getScene().getRoot());
                scene.lookup("#base").requestFocus();
                break;
            case "btnZone":
                paneContent = (StackPane) scene.lookup("#contentPane");
                paneContent.getChildren().add(screensConfiguration.zone().getScene().getRoot());
                break;
            case "btnLubrifiant":
                paneContent = (StackPane) scene.lookup("#contentPane");
                paneContent.getChildren().add(screensConfiguration.lubrifiant().getScene().getRoot());

                break;
            case "btnEngine":
                paneContent = (StackPane) scene.lookup("#contentPane");
                paneContent.getChildren().add(screensConfiguration.engine().getScene().getRoot());
                scene.lookup("#topArea").requestFocus();
                break;

            default:
                break;

        }
    }


    private void handleKeyEvent(KeyEvent event) {
        if (!(event.getSource() instanceof Button))
            return;

        if (!event.getCode().equals(KeyCode.ENTER))
            return;
        String id = ((Button) event.getSource()).getId();
        System.out.println("This id is : " + id);

        FXMLDialog dialog = null;
        StackPane paneContent = null;

        switch (id) {
            case "btnBase":
                paneContent = (StackPane) scene.lookup("#contentPane");
                paneContent.getChildren().add(screensConfiguration.base().getScene().getRoot());
                scene.lookup("#base").requestFocus();
                break;
            case "btnZone":
                paneContent = (StackPane) scene.lookup("#contentPane");
                paneContent.getChildren().add(screensConfiguration.zone().getScene().getRoot());
                break;
            case "btnLubrifiant":
                paneContent = (StackPane) scene.lookup("#contentPane");
                paneContent.getChildren().add(screensConfiguration.lubrifiant().getScene().getRoot());

                break;
            case "btnEngine":
                paneContent = (StackPane) scene.lookup("#contentPane");
                paneContent.getChildren().add(screensConfiguration.engine().getScene().getRoot());
                scene.lookup("#topArea").requestFocus();
                break;

            default:
                break;

        }
    }

}
