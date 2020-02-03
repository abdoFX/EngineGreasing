package controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.LocalDateTimeStringConverter;
import model.WorkingTimeEntry;
import model.maintenance;
import org.springframework.beans.factory.annotation.Autowired;
import util.DialogController;
import util.FXMLDialog;
import viewModel.MaintenanceDetailsDialogViewModel;
import viewModel.MaintenanceViewModel;
import viewModel.WorkingTimeDetailsViewModel;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Created on 11-12-2016 at 15:08.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public class MaintenanceDetailsDialogController implements DialogController, Initializable {


    @Autowired
    EventBus eventBus;
    @FXML
    private JFXButton btnEdit;
    @FXML
    private JFXButton btnDelete;
    @FXML
    private TableColumn<maintenance, Integer> tcId;
    @FXML
    private TableColumn<maintenance, LocalDateTime> tcDate;

    @FXML
    private JFXComboBox<String> cbWorkingTimeDate;

    @FXML
    private JFXDatePicker jfxdpFrom;
    @FXML
    private JFXDatePicker jfxdpTo;


    @FXML
    private TableView<maintenance> tvListMaintenanace;

    private FXMLDialog dialog;


    @Autowired
    private MaintenanceDetailsDialogViewModel maintenanceDetailsDialogViewModel;

    @Override
    public void setDialog(FXMLDialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initConrols();
        setupBindings();
        setupThreads();


    }


    private void setupThreads() {
        Platform.runLater(() -> {
            maintenanceDetailsDialogViewModel.setScene(btnDelete.getScene());
        });
        eventBus.register(MaintenanceDetailsDialogController.this);

    }

    private void initConrols() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        tcDate.setCellFactory(TextFieldTableCell
                .forTableColumn(new LocalDateTimeStringConverter(dtf, dtf)));


        cbWorkingTimeDate.getItems().addAll("Today", "Yesterday", "This Week", "This Month"
                , "This Year");
        cbWorkingTimeDate.getSelectionModel().select("Today");

        maintenanceDetailsDialogViewModel.getSortedList().comparatorProperty().bind(tvListMaintenanace.comparatorProperty());
        tvListMaintenanace.setItems(maintenanceDetailsDialogViewModel.getSortedList());


    }

    private void setupBindings() {
        maintenanceDetailsDialogViewModel.dateFromProperty().bindBidirectional(jfxdpFrom.valueProperty());
        maintenanceDetailsDialogViewModel.dateToProperty().bindBidirectional(jfxdpTo.valueProperty());
        maintenanceDetailsDialogViewModel.selectedPeriodProperty().bind(cbWorkingTimeDate.getSelectionModel().selectedItemProperty());


        btnEdit.disableProperty().bind(maintenanceDetailsDialogViewModel.btnEditAtiveStateProperty());
        btnDelete.disableProperty().bind(maintenanceDetailsDialogViewModel.btnDeleteAtiveStateProperty());

        maintenanceDetailsDialogViewModel.seletedMaintenanceItemProperty()
                .bind(tvListMaintenanace.getSelectionModel().selectedItemProperty());

    }


    public MaintenanceDetailsDialogViewModel getMaintenanceDetailsDialogViewModel() {
        return maintenanceDetailsDialogViewModel;
    }


    @FXML
    private void forwardEventHandling(Event event) {
        maintenanceDetailsDialogViewModel.handleEvent(event);
    }

    @Subscribe
    public void updateTableView(String command) {
        switch (command) {
            case "Maintenance Data Changed":
                tvListMaintenanace.setItems(maintenanceDetailsDialogViewModel.getSortedList());
                Default:
                break;

        }
    }


}
