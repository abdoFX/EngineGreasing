package viewModel;

import bo.MaintenanceBO;
import com.google.common.eventbus.EventBus;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.stage.Stage;
import javafx.util.Duration;
import model.Engine;
import model.maintenance;
import org.springframework.beans.factory.annotation.Autowired;
import util.DialogUtil;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created on 30-11-2016 at 22:19.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public class MaintenanceViewModel {

    @Autowired
    private EventBus eventBus;

    @Autowired
    private MaintenanceBO maintenanceBO;

    private ObjectProperty<LocalDate> dateMaintenance = new SimpleObjectProperty<>();

    private Engine engine = null;

    Scene scene;

    public MaintenanceViewModel() {

    }

    @PostConstruct
    public void init() {
        eventBus.register(MaintenanceViewModel.this);

    }
    // Getter and setter


    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }



    public LocalDate getDateMaintenance() {
        return dateMaintenance.get();
    }

    public ObjectProperty<LocalDate> dateMaintenanceProperty() {
        return dateMaintenance;
    }

    public void setDateMaintenance(LocalDate dateMaintenance) {
        this.dateMaintenance.set(dateMaintenance);
    }


    public void initForm(Engine engine) {
        this.engine = engine;
    }

    // Event handling
    public void handleEvent(Event event) {
        System.out.println(event.getEventType());
        switch (event.getEventType().toString()) {
            case "ACTION":
                handleActionEvent((ActionEvent) event);

                break;
            case "KEY_PRESSED":
                break;
            default:
                break;
        }
    }

    private void handleActionEvent(ActionEvent event) {
        if (!(event.getSource() instanceof Button))
            return;

        switch (((Button) event.getSource()).getText()) {
            case "O.K":
                if (engine.getLastMaintenanceDate().isAfter(LocalDateTime.of(dateMaintenance.get(), LocalTime.now()))) {
                    DialogUtil.showNotificaiton("You have to select a date greater than \n " +
                            "Last Maintenance date", "Maintenance", scene.getWindow()).hideAfter(Duration.millis(6000))
                            .showWarning();

                    break;
                }else if(dateMaintenance.get().isAfter(LocalDate.now())){
                    DialogUtil.showNotificaiton("You can't select a date greater than\n " +
                            "Today's date", "Maintenance", scene.getWindow()).hideAfter(Duration.millis(6000))
                            .showWarning();

                    break;
                }
                maintenance maintenance = new maintenance();
                maintenance.setMaintDate(LocalDateTime.of(dateMaintenance.getValue(), LocalTime.now()));
                maintenance.setEngine(engine);
                maintenanceBO.Save(maintenance);
                eventBus.post("Update Engine Data");
                ((Stage) scene.getWindow()).close();
                break;

            default:
                break;
        }
    }
}
