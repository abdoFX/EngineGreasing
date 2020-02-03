package controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableArray;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.converter.LocalDateTimeStringConverter;
import model.Engine;
import model.Lubrifiant;
import model.Zone;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import util.DialogController;
import util.FXMLDialog;
import viewModel.EngineViewModel;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created on 24-11-2016 at 22:17.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */

public class EngineController implements DialogController, Initializable {


    private FXMLDialog dialog;

    @Autowired
    private EngineViewModel engineViewModel;

    public EngineViewModel getEngineViewModel() {
        return engineViewModel;
    }

    @Autowired
    private EventBus eventBus;

    @FXML
    private JFXButton btnAdd;
    @FXML
    private JFXButton btnEdit;
    @FXML
    private JFXButton btnDelete;
    @FXML
    private JFXButton btnReturn;

    @FXML
    private JFXButton btnAddWorkingTime;
    @FXML
    private JFXButton btnAddMaintenance;
    @FXML
    private JFXButton btnShowEngineWorkingTimeDetails;
    @FXML
    private JFXButton btnShowEngineMaintenanceHistory;

    @FXML
    private VBox topArea;

    private CustomTextField searchTextField;


    @FXML
    private TableColumn<Engine, Integer> tcId;
    @FXML
    private TableColumn<Engine, String> tcType;
    @FXML
    private TableColumn<Engine, String> tcDesignation;
    @FXML
    private TableColumn<Engine, Integer> tcFrequincy;
    @FXML
    private TableColumn<Engine, Double> tcWorkingTime;
    @FXML
    private TableColumn<Engine, Lubrifiant> tcLibrifiant;
    @FXML
    private TableColumn<Engine, Zone> tcZone;

    @FXML
    private TableColumn<Engine, LocalDateTime> tcLastMaintenanceDate;


    @FXML
    private TableView<Engine> tvListEngine;


    public void setDialog(FXMLDialog dialog) {
        this.dialog = dialog;

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        tcLastMaintenanceDate.setCellFactory(TextFieldTableCell
                .forTableColumn(new LocalDateTimeStringConverter(dtf, dtf)));

        eventBus.register(EngineController.this);

        tvListEngine.setItems(engineViewModel.getSortedList());
        List<String> bindlist = new ArrayList<>();
        bindlist = engineViewModel.getSortedList().stream().map(engine -> engine.getDesignation())

                .collect(Collectors.toList());


        searchTextField = (CustomTextField) TextFields.createClearableTextField();
        TextFields.bindAutoCompletion(searchTextField, bindlist);
        searchTextField.setOnKeyPressed(event -> forwardEventHandling(event));


        topArea.getChildren().add(1, searchTextField);
        setupBinding();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (btnAdd.getScene() != null)

                    btnAdd.getScene().getAccelerators().put(
                            new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN),
                            new Runnable() {
                                @Override
                                public void run() {
                                    btnAdd.fire();
                                }
                            }
                    );
            }
        });

        Platform.runLater(() -> {
            engineViewModel.setScene(btnAdd.getScene());
        });


        setupAnimation();
        initShortcut();

    }

    private void setupBinding() {
        btnEdit.disableProperty().bind(engineViewModel.selectedEngineProperty().isNull());
        btnDelete.disableProperty().bind(engineViewModel.selectedEngineProperty().isNull());
        btnAddWorkingTime.disableProperty().bind(engineViewModel.selectedEngineProperty().isNull());
        btnAddMaintenance.disableProperty().bind(engineViewModel.selectedEngineProperty().isNull());
        btnShowEngineMaintenanceHistory.disableProperty().bind(engineViewModel.selectedEngineProperty().isNull());
        btnShowEngineWorkingTimeDetails.disableProperty().bind(engineViewModel.selectedEngineProperty().isNull());


        engineViewModel.getSortedList().comparatorProperty().bind(tvListEngine.comparatorProperty());
        engineViewModel.selectedEngineProperty().bind(tvListEngine.getSelectionModel().selectedItemProperty());
        searchTextField.textProperty().bindBidirectional(engineViewModel.searchTextProperty());
    }

    private void setupAnimation() {
        PseudoClass flashHighlight = PseudoClass.getPseudoClass("flash-highlight");

        tvListEngine.setRowFactory(tv -> {
            TableRow<Engine> rowAccount = new TableRow<Engine>();
            Timeline flasher = new Timeline(
                    new KeyFrame(Duration.seconds(0.5), e -> {
                        rowAccount.pseudoClassStateChanged(flashHighlight, true);
                    }),
                    new KeyFrame(Duration.seconds(1.0), e -> {
                        rowAccount.pseudoClassStateChanged(flashHighlight, false);
                    })

            );


            flasher.setCycleCount(Animation.INDEFINITE);

            rowAccount.itemProperty().addListener((obs, oldItem, newItem) -> {
                if (newItem == null) {
                    flasher.stop();
                    rowAccount.pseudoClassStateChanged(flashHighlight, false);

                } else {

                    System.out.println(newItem);
                    if (newItem.getWorkingTime() >= newItem.getFrequincy()) {
                        flasher.play();
                    } else {
                        flasher.stop();
                        rowAccount.pseudoClassStateChanged(flashHighlight, false);
                    }
                }

            });

            return rowAccount;
        });
    }

    private void initShortcut() {
        Platform.runLater(() -> {
            Scene scene = btnAdd.getScene();
            scene.getAccelerators()
                    .put(new KeyCodeCombination(KeyCode.N, KeyCombination.SHORTCUT_DOWN), () -> btnAdd.fire());
            scene.getAccelerators()
                    .put(new KeyCodeCombination(KeyCode.E, KeyCombination.SHORTCUT_DOWN), () -> btnEdit.fire());
            scene.getAccelerators()
                    .put(new KeyCodeCombination(KeyCode.D, KeyCombination.SHORTCUT_DOWN), () -> btnDelete.fire());
            scene.getAccelerators()
                    .put(new KeyCodeCombination(KeyCode.Z, KeyCombination.ALT_ANY), () -> btnReturn.fire());

        });

    }

    @FXML
    public void forwardEventHandling(Event event) {
        engineViewModel.handleEvent(event);
    }


    @Subscribe
    public void updateTableView(String command) {
        switch (command) {
            case "Engine Data Changed":
                tvListEngine.setItems(engineViewModel.getSortedList());
                Default:
                break;

        }
    }


}

