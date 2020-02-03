package controller;

import com.jfoenix.controls.JFXDatePicker;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import org.springframework.beans.factory.annotation.Autowired;
import util.DialogController;
import util.FXMLDialog;
import viewModel.MaintenanceViewModel;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created on 30-11-2016 at 21:12.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public class MaintenanceController implements DialogController, Initializable {

    FXMLDialog dialog;


    @FXML
    private Button btnOK;
    @FXML
    private Button btnCancel;
    @FXML
    private JFXDatePicker dpMaintenance;


    @Autowired
    private MaintenanceViewModel maintenanceViewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        maintenanceViewModel.dateMaintenanceProperty().bindBidirectional(dpMaintenance.valueProperty());

        Platform.runLater(() -> {
            maintenanceViewModel.setScene(dpMaintenance.getScene());
        });

    }

    @Override
    public void setDialog(FXMLDialog dialog) {
        this.dialog = dialog;
    }


    // Getter and Setter


    public MaintenanceViewModel getMaintenanceViewModel() {
        return maintenanceViewModel;
    }

    // Forward Event Handling
    @FXML
    private void forwardEventHandling(Event event) {
        maintenanceViewModel.handleEvent(event);

    }


}
