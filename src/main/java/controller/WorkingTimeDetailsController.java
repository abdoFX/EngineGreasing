package controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.lowagie.text.Anchor;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.LocalDateTimeStringConverter;
import model.WorkingTimeEntry;
import org.controlsfx.tools.Borders;
import org.springframework.beans.factory.annotation.Autowired;
import util.DialogController;
import util.FXMLDialog;
import viewModel.WorkingTimeDetailsViewModel;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Created on 11-12-2016 at 15:08.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public class WorkingTimeDetailsController implements DialogController, Initializable {

    @Autowired
    EventBus eventBus;

    @FXML
    private JFXButton btnEdit;
    @FXML
    private JFXButton btnDelete;
    @FXML
    private JFXButton btnPrint;


    @FXML
    private TableColumn<WorkingTimeEntry, Integer> tcId;
    @FXML
    private TableColumn<WorkingTimeEntry, String> tcEngineName;
    @FXML
    private TableColumn<WorkingTimeEntry, LocalDateTime> tcDate;
    @FXML
    private TableColumn<WorkingTimeEntry, String> tcWorkingTime;
    @FXML
    private TableView<WorkingTimeEntry> tvListWorkingTimeEntries;

    @FXML
    private JFXComboBox<String> cbWorkingTimeDate;

    @FXML
    private JFXDatePicker jfxdpFrom;
    @FXML
    private JFXDatePicker jfxdpTo;


    @FXML
    private GridPane searchDateGroupe;

    private FXMLDialog dialog;

    @Autowired
    private WorkingTimeDetailsViewModel workingTimeDetailsViewModel;

    @Override
    public void setDialog(FXMLDialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setupBindings();
        setupThreads();
        initConrols();


    }




    // Setup
    private void setupThreads() {
        Platform.runLater(() -> {
            workingTimeDetailsViewModel.setScene(tvListWorkingTimeEntries.getScene());
        });
        eventBus.register(WorkingTimeDetailsController.this);
    }

    private void setupBindings() {
        workingTimeDetailsViewModel.selectedPeriodProperty().bind(cbWorkingTimeDate.getSelectionModel().selectedItemProperty());
        jfxdpTo.valueProperty().bindBidirectional(workingTimeDetailsViewModel.dateToProperty());
        jfxdpFrom.valueProperty().bindBidirectional(workingTimeDetailsViewModel.dateFromProperty());

        workingTimeDetailsViewModel.seletedWorkingTimeItemProperty()
                .bind(tvListWorkingTimeEntries.getSelectionModel().selectedItemProperty());


        // Controls state
        btnEdit.disableProperty().bind(workingTimeDetailsViewModel.btnEditAtiveStateProperty());
        btnDelete.disableProperty().bind(workingTimeDetailsViewModel.btnDeleteAtiveStateProperty());

    }

    private void initConrols() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        tcDate.setCellFactory(TextFieldTableCell
                .forTableColumn(new LocalDateTimeStringConverter(dtf, dtf)));

        tcWorkingTime.setCellValueFactory(cellData -> {
            WorkingTimeEntry value = cellData.getValue();
            String workingTime = value.getWtHours() + ":" + value.getWtMinutes() + ":" + value.getWtSeconds();
            return new ReadOnlyStringWrapper(workingTime);
        });

        cbWorkingTimeDate.getItems().addAll("Today", "Yesterday", "This Week", "This Month"
                , "This Year");
         cbWorkingTimeDate.getSelectionModel().select(0);

        jfxdpFrom.setValue(LocalDate.now());
        jfxdpTo.setValue(LocalDate.now());

        workingTimeDetailsViewModel.getSortedList().comparatorProperty().bind(tvListWorkingTimeEntries.comparatorProperty());
        tvListWorkingTimeEntries.setItems(workingTimeDetailsViewModel.getSortedList());


    }


    // Getters and Setters
    public WorkingTimeDetailsViewModel getWorkingTimeDetailsViewModel() {
        return workingTimeDetailsViewModel;
    }

    public void setWorkingTimeDetailsViewModel(WorkingTimeDetailsViewModel workingTimeDetailsViewModel) {
        this.workingTimeDetailsViewModel = workingTimeDetailsViewModel;
    }


    // Event Handling & Notification recieve
    @FXML
    private void forwardEventHandling(Event event) {
        workingTimeDetailsViewModel.handleEvent(event);
    }

    @Subscribe
    public void updateTableView(String command) {
        switch (command) {
            case "Working Time Data Changed":
                tvListWorkingTimeEntries.setItems(workingTimeDetailsViewModel.getSortedList());
                Default:
                break;

        }
    }


}
