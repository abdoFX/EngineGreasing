package controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Base;
import org.springframework.beans.factory.annotation.Autowired;
import util.DialogController;
import util.FXMLDialog;
import viewModel.BaseViewModel;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created on 29-11-2016 at 21:35.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public class BaseController implements DialogController, Initializable {

    @FXML
    private JFXButton btnAdd;
    @FXML
    private JFXButton btnEdit;
    @FXML
    private JFXButton btnDelete;
    @FXML
    private JFXButton btnReturn;
    @FXML
    private TableView<Base> tvListBase;
    @FXML
    private TableColumn<Base, Integer> tcId;
    @FXML
    private TableColumn<Base, String> tcName;


    @Autowired
    private BaseViewModel baseViewModel;
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
        eventBus.register(BaseController.this);
        // Initialisation
        tvListBase.setItems(baseViewModel.getBaseList());
        // Bindings
        btnEdit.disableProperty().bind(baseViewModel.btnEditActivatedProperty());
        btnDelete.disableProperty().bind(baseViewModel.btnDeleteActivatedProperty());

        baseViewModel.selecteItemProperty().bind(tvListBase.getSelectionModel().selectedItemProperty());

        initShortcut();
        Platform.runLater(() -> {

            baseViewModel.setScene(btnAdd.getScene());
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
    private void forwardEventHandling(Event event) {
        baseViewModel.handleEvent(event);
    }

    @Subscribe
    public void receiveEventBusEvents(String command) {
        if (command.equals("Data Updated"))
            tvListBase.setItems(baseViewModel.getBaseList());
    }

}
