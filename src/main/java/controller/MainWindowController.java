package controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import customControls.ComboBoxTablePopup;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.SetChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jfxtras.labs.scene.control.gauge.Content;
import jfxtras.labs.scene.control.gauge.ContentBuilder;
import jfxtras.labs.scene.control.gauge.MatrixPanel;
import jfxtras.labs.scene.control.gauge.MatrixPanelBuilder;
import model.Engine;
import org.controlsfx.control.StatusBar;
import org.springframework.beans.factory.annotation.Autowired;
import util.DialogController;
import util.FXMLDialog;
import viewModel.MainWindowViewModel;

import java.net.URL;
import java.util.*;
import java.util.function.Predicate;

/**
 * Created on 01-12-2016 at 15:00.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public class MainWindowController implements DialogController, Initializable {
    @Autowired
    EventBus eventBus;

    @Autowired
    private MainWindowViewModel mainWindowViewModel;

    @FXML
    private ComboBoxTablePopup<Engine> cbTpTest;
    @FXML
    private StatusBar sbState;

    @FXML
    private TabPane tpMainArea;


    @Override
    public void setDialog(FXMLDialog dialog) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        eventBus.register(MainWindowController.this);

        mainWindowViewModel.getTabs().addAll(tpMainArea.getTabs());
        tpMainArea.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        tpMainArea.getSelectionModel().selectedItemProperty().addListener((obv, oldval, newval) -> {
            mainWindowViewModel.setSelectedTab(newval);
        });

        Platform.runLater(() -> {
            mainWindowViewModel.setStage((Stage) tpMainArea.getScene().getWindow());

        });

    }


    @FXML
    private void forwardEventHandling(Event event) {
        mainWindowViewModel.handleEvent(event);
    }


    @Subscribe
    public void UpdateTabs(String command) {
        if (command.equals("update tabs")) {

            tpMainArea.getTabs().clear();
            tpMainArea.getTabs().addAll(mainWindowViewModel.getTabs());

        } else if (command.equals("update selected tab")) {
            tpMainArea.getSelectionModel().select(mainWindowViewModel.getSelectedTab());
        }
    }

}





