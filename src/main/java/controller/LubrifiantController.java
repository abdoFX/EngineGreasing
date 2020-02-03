package controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import model.Lubrifiant;
import org.springframework.beans.factory.annotation.Autowired;
import util.DialogController;
import util.FXMLDialog;
import viewModel.LubrifiantViewModel;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created on 29-11-2016 at 21:35.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public class LubrifiantController implements DialogController, Initializable {

    @FXML
    private JFXButton btnAdd;
    @FXML
    private JFXButton btnEdit;
    @FXML
    private JFXButton btnDelete;
    @FXML
    private JFXButton btnReturn;
    @FXML
    private TableView<Lubrifiant> tvListLubrifiant;
    @FXML
    private TableColumn<Lubrifiant, Integer> tcId;
    @FXML
    private TableColumn<Lubrifiant, String> tcName;


    @Autowired
    private LubrifiantViewModel lubrifiantViewModel;
    @Autowired
    private EventBus eventBus;

    private FXMLDialog LubrifiantDialog;

    @Override
    public void setDialog(FXMLDialog dialog) {
        this.LubrifiantDialog = dialog;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // event system
        eventBus.register(LubrifiantController.this);
        // Initialisation
        tvListLubrifiant.setItems(lubrifiantViewModel.getLubrifiantList());
        // Bindings
        btnEdit.disableProperty().bind(tvListLubrifiant.getSelectionModel().selectedItemProperty().isNull());
        btnDelete.disableProperty().bind(tvListLubrifiant.getSelectionModel().selectedItemProperty().isNull());

        lubrifiantViewModel.selecteItemProperty().bind(tvListLubrifiant.getSelectionModel().selectedItemProperty());
        ;

        Platform.runLater(() -> {
            lubrifiantViewModel.setScene(btnAdd.getScene());
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

    public LubrifiantViewModel getLubrifiantViewModel() {
        return lubrifiantViewModel;
    }

    @FXML
    private void forwardEventHandling(Event event) {
        lubrifiantViewModel.handleEvent(event);
    }

    @Subscribe
    public void receiveEventBusEvents(String command) {
        switch (command) {
            case "Lubrifiant Data Updated":
                tvListLubrifiant.setItems(lubrifiantViewModel.getLubrifiantList());
                break;
            case "Close Lubrifiant":
                tvListLubrifiant.getScene().getWindow().hide();
                break;
            default:
                break;
        }
    }
}
