package viewModel.mainWindow;

import com.google.common.eventbus.EventBus;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created on 28-12-2016 at 22:47.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public class mWindowViewModel {


    Scene scene;

    @Autowired
    EventBus eventBus;

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void handleEvent(Event event) {


        if(event instanceof MouseEvent){
            handleMouseEvent( (MouseEvent) event);
            return;
        }

        switch (event.getEventType().toString()) {
            case "ACTION":
                handleActionEvent((ActionEvent) event);

                break;
            case "KEY_PRESSED":
                break;
            case "MOUSE_EVENT":


            default:
                break;
        }
    }

    private void handleMouseEvent(MouseEvent event) {
        switch (event.getEventType().toString()){
            case "MOUSE_ENTERED":
                eventBus.post("show drawer");

                break;
            case "MOUSE_EXITED":
                eventBus.post("hide drawer");
                break;
            default:
                break;
        }
    }

    private void handleActionEvent(ActionEvent event) {
        if (!(event.getSource() instanceof Button))
            return;


        String id = ((Button) event.getSource()).getId();


        switch (id) {
            case "btnMinimize":
                Stage stage = (Stage) scene.getWindow();
                stage.setIconified(true );
                break;

            case "btnFullScreen":
                Stage stage1 = (Stage) scene.getWindow();
                if (stage1.isFullScreen()) {
                    stage1.setFullScreen(false);

                } else
                    stage1.setFullScreen(true);

                break;
            case "btnClose":
                Stage stage2 = (Stage) scene.getWindow();
                stage2.close();
                break;
            default:
                break;
        }
    }
}
