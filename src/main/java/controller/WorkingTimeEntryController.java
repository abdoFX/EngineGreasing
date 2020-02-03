package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import org.springframework.beans.factory.annotation.Autowired;
import util.DialogController;
import util.FXMLDialog;
import viewModel.WorkingTimeEntryViewModel;

import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Created on 30-11-2016 at 21:12.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public class WorkingTimeEntryController implements DialogController, Initializable {

    FXMLDialog dialog;


    @FXML
    private JFXButton btnOK;
    @FXML
    private JFXButton btnCancel;
    @FXML
    private Spinner<Integer> spHours;
    @FXML
    private Spinner<Integer> spMinutes;
    @FXML
    private Spinner<Integer> spSeconds;

    @FXML
    private JFXDatePicker jfxDatePicker;


    @Autowired
    private WorkingTimeEntryViewModel workingTimeEntryViewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        workingTimeEntryViewModel.hoursProperty().bind(spHours.valueProperty());
        workingTimeEntryViewModel.minutesProperty().bind(spMinutes.valueProperty());
        workingTimeEntryViewModel.secondsProperty().bind(spSeconds.valueProperty());


    }

    @Override
    public void setDialog(FXMLDialog dialog) {
        this.dialog = dialog;
    }


    // Getter and Setter

    public WorkingTimeEntryViewModel getWorkingTimeEntryViewModel() {
        return workingTimeEntryViewModel;
    }

    // Forward Event Handling
    @FXML
    private void forwardEventHandling(Event event) {
        workingTimeEntryViewModel.handleEvent(event);
    }

    @FXML
    private void closeWindow() {
        btnCancel.getScene().getWindow().hide();
    }

}
