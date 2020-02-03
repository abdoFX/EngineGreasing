package controller;


import bo.ZoneBO;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import customControls.ComboBoxTablePopup;
import de.saxsys.mvvmfx.utils.validation.visualization.ControlsFxVisualizer;
import de.saxsys.mvvmfx.utils.validation.visualization.ValidationVisualizer;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.util.StringConverter;
import model.Lubrifiant;
import model.WorkingTimeType;
import model.Zone;
import org.controlsfx.control.spreadsheet.StringConverterWithFormat;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.tools.Borders;
import org.springframework.beans.factory.annotation.Autowired;
import tornadofx.control.NaviSelect;
import util.DialogController;
import util.FXMLDialog;
import view.ScreensConfiguration;
import viewModel.EngineDialogViewModel;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created on 22-11-2016 at 20:41.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */

public class EngineDialogController implements DialogController, Initializable {
    private FXMLDialog dialog;


    @Autowired
    ScreensConfiguration screensConfiguration;
    @Autowired
    private EngineDialogViewModel engineDialogViewModel;
    @Autowired
    private EventBus eventBus;

    @FXML
    private JFXTextField tfId;

    @FXML
    private JFXTextField tfType;

    @FXML
    private JFXTextField tfDesignation;

    @FXML
    private JFXTextField tfConsume;

    @FXML
    private Spinner<Integer> SPFrequincy;


    @FXML
    private ComboBoxTablePopup<Zone> cbtpSearchZone;
    @FXML
    private ComboBoxTablePopup<Lubrifiant> cbtpSearchLubrifiant;


    @FXML
    private JFXButton btnOk;


    @FXML
    private JFXButton btnReturn;


    @FXML
    private JFXRadioButton rbHour;
    @FXML
    private JFXRadioButton rbPeriod;

    private ToggleGroup toggleGroup = new ToggleGroup();


    public EngineDialogViewModel getEngineDialogViewModel() {
        return engineDialogViewModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        eventBus.register(EngineDialogController.this);
        toggleGroup.getToggles().addAll(rbHour, rbPeriod);

        engineDialogViewModel.selectedToggleProperty().bind(toggleGroup.selectedToggleProperty());
        Platform.runLater(() -> {

            engineDialogViewModel.setScene(btnOk.getScene());
        });

        setupBinding();
        // Validation Decoration
        Decoration();

        cbtpSearchLubrifiant.setColumns(engineDialogViewModel.getLubrifiantColumns());
        cbtpSearchLubrifiant.setItems(engineDialogViewModel.getLubrifiantList());

        cbtpSearchZone.setColumns(engineDialogViewModel.getZoneTableColumns());
        cbtpSearchZone.setItems(engineDialogViewModel.getZoneList());

        initShortcut();


    }

    private void setupBinding() {
        tfType.textProperty().bindBidirectional(engineDialogViewModel.typeProperty());
        tfDesignation.textProperty().bindBidirectional(engineDialogViewModel.designationProperty());
        tfConsume.textProperty().bindBidirectional(engineDialogViewModel.consummationProperty());
        SPFrequincy.valueProperty().addListener((observable1, oldValue1, newValue1) -> {
            engineDialogViewModel.setFrequincy(newValue1);
        });
        engineDialogViewModel.frequincyProperty().addListener((observable, oldValue, newValue) -> {
            SPFrequincy.getValueFactory().setValue(newValue);
        });


        cbtpSearchLubrifiant.valueProperty().bindBidirectional(engineDialogViewModel.selectedLubrifiantProperty());
        cbtpSearchZone.valueProperty().bindBidirectional(engineDialogViewModel.selectedZoneProperty());


        btnOk.disableProperty().bind(engineDialogViewModel.getEngineDialogValidator().getValidationStatus().validProperty().not());
    }

    public void setDialog(FXMLDialog dialog) {
        this.dialog = dialog;
    }


    // Validation Decoration  --- to be injected via spring
    private void Decoration() {

        ValidationVisualizer visualizer = new ControlsFxVisualizer();
        // visualizer.initVisualization(engineDialogViewModel.getTypeValidator().getValidationStatus(), tfType, true);
        //  visualizer.initVisualization(engineDialogViewModel.getDesignationValidator().getValidationStatus(), tfDesignation, true);
        // visualizer.initVisualization(engineDialogViewModel.getFrequincyValidator().getValidationStatus(), SPFrequincy, true);
    }

    private void initShortcut() {
        Platform.runLater(() -> {
            Scene scene = btnReturn.getScene();
            scene.getAccelerators()
                    .put(new KeyCodeCombination(KeyCode.Z, KeyCombination.ALT_ANY), () -> btnReturn.fire());

        });

    }

    // Event Handling
    @FXML
    private void forwardEventHandling(Event event) {
        engineDialogViewModel.handleEvent(event);
    }


    // Event Bus
    @Subscribe
    public void receivePublisherCommands(String command) {

        if (command.equals(WorkingTimeType.HOUR.toString())) {
            toggleGroup.selectToggle(rbHour);
        } else if (command.equals(WorkingTimeType.PERIOD.toString())) {
            toggleGroup.selectToggle(rbPeriod);
        }
    }

}
