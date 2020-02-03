package controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import model.Base;
import model.Zone;
import org.springframework.beans.factory.annotation.Autowired;
import util.DialogController;
import util.FXMLDialog;
import viewModel.BaseViewModel;
import viewModel.ZoneViewModel;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created on 29-11-2016 at 21:35.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public class ZoneController implements DialogController, Initializable {

    @FXML
    private JFXButton btnAdd;
    @FXML
    private JFXButton btnEdit;
    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnReturn;


    @FXML
    private TableView<Zone> tvListBase;
    @FXML
    private TableColumn<Zone, Integer> tcId;
    @FXML
    private TableColumn<Zone, String> tcName;


    @Autowired
    private ZoneViewModel zoneViewModel;


    @Autowired
    private EventBus eventBus;

    private FXMLDialog baseDialog;

    @Override
    public void setDialog(FXMLDialog dialog) {
        this.baseDialog = dialog;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        // event system
        eventBus.register(ZoneController.this);
        // Initialisation
        tvListBase.setItems(zoneViewModel.getBaseList());
        // Bindings
        btnEdit.disableProperty().bind(tvListBase.getSelectionModel().selectedItemProperty().isNull());
        btnDelete.disableProperty().bind(tvListBase.getSelectionModel().selectedItemProperty().isNull());

        zoneViewModel.selecteItemProperty().bind(tvListBase.getSelectionModel().selectedItemProperty());
        Platform.runLater(() -> {
            zoneViewModel.setScene(btnAdd.getScene());
        });

        initShortcut();

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

    public ZoneViewModel getZoneViewModel() {
        return zoneViewModel;
    }

    @FXML
    private void forwardEventHandling(Event event) {
        zoneViewModel.handleEvent(event);
    }


    @Subscribe
    public void receiveEventBusEvents(String command) {
        switch (command) {
            case "Data Updated":
                tvListBase.setItems(zoneViewModel.getBaseList());
                break;
            case "Close Zone":
                tvListBase.getScene().getWindow().hide();
                break;
            default:
                break;
        }


    }
}
