package viewModel;

import bo.WorkingTimeEntryBO;
import com.google.common.eventbus.EventBus;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.Button;
import model.Engine;

import model.WorkingTimeEntry;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

/**
 * Created on 30-11-2016 at 21:16.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public class WorkingTimeEntryViewModel {


    @Autowired
    private EventBus eventBus;
    @Autowired
    private WorkingTimeEntryBO workingTimeEntryBO;

    private IntegerProperty hours = new SimpleIntegerProperty();
    private IntegerProperty minutes = new SimpleIntegerProperty();
    private IntegerProperty seconds = new SimpleIntegerProperty();

    private Engine engine = null;

    // Getter and setter

    public int getHours() {
        return hours.get();
    }

    public IntegerProperty hoursProperty() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours.set(hours);
    }

    public int getMinutes() {
        return minutes.get();
    }

    public IntegerProperty minutesProperty() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes.set(minutes);
    }

    public int getSeconds() {
        return seconds.get();
    }

    public IntegerProperty secondsProperty() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds.set(seconds);
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
                WorkingTimeEntry workingTimeEntry = new WorkingTimeEntry();
                workingTimeEntry.setDateTime(LocalDateTime.now());
                workingTimeEntry.setWtHours(hours.get());
                workingTimeEntry.setWtMinutes(minutes.get());
                workingTimeEntry.setWtSeconds(seconds.get());
                workingTimeEntry.setEngine(engine);
                workingTimeEntryBO.Save(workingTimeEntry);

                eventBus.post("Update Engine Data");
                break;
            case "Cancel":
                break;
            default:
                break;
        }
    }


}
