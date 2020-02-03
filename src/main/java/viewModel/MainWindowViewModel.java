package viewModel;

import com.google.common.eventbus.EventBus;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import util.FXMLDialog;
import view.ScreensConfiguration;

import java.util.Iterator;

/**
 * Created on 01-12-2016 at 15:01.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public class MainWindowViewModel {


    @Autowired
    EventBus eventBus;


    @Autowired
    private ScreensConfiguration screensConfiguration;

    private Stage stage = null ;


    public MainWindowViewModel() {
        tabs.addListener((ListChangeListener) e -> {
            e.next();
            if (e.wasAdded() || e.wasRemoved())
                eventBus.post("update tabs");

        });

        selectedTab.addListener(observable -> {
            eventBus.post("update selected tab");
        });
    }

    // Properties
    private ObservableList<Tab> tabs = FXCollections.observableArrayList();
    private ObjectProperty<Tab> selectedTab = new SimpleObjectProperty<>();


    // Getter and setter


    public ObservableList<Tab> getTabs() {
        return tabs;
    }

    public void setTabs(ObservableList<Tab> tabs) {
        this.tabs = tabs;
    }

    public Tab getSelectedTab() {
        return selectedTab.get();
    }

    public ObjectProperty<Tab> selectedTabProperty() {
        return selectedTab;
    }

    public void setSelectedTab(Tab selectedTab) {
        this.selectedTab.set(selectedTab);
    }


    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // Event Handling
    // Event handling
    public void handleEvent(Event event) {
        System.out.println(event.getEventType());
        switch (event.getEventType().toString()) {
            case "ACTION":
                handleActionEvent((ActionEvent) event);
                break;
            case "KEY_PRESSED":

                break;
            default:
                break;
        }
    }

    private void handleActionEvent(ActionEvent event) {
        System.out.println(event.getSource());
        Object obj = event.getSource();
        if (obj instanceof MenuItem) {
            handleMenuItemEvents((MenuItem) obj);
        }
        if (!(event.getSource() instanceof Button))
            return;

        FXMLDialog dialog = null;
        Tab tab = null;
        AnchorPane content = new AnchorPane();
        //  Insets insets = new Insets(0, 0, 0, 0);
        //content.setOpaqueInsets(insets);
        Node node = new AnchorPane();
        int tabNum;
        switch (((Button) event.getSource()).getText()) {
            case "Engine":

                tabNum = checkTabExists("Engine");
                if (tabNum != -1) {
                    selectedTab.set(tabs.get(tabNum));
                    break;
                }
                dialog = screensConfiguration.engine();
                node = dialog.getScene().getRoot();
                content.setTopAnchor(node, 0.0);
                content.setRightAnchor(node, 0.0);
                content.setLeftAnchor(node, 0.0);
                content.setBottomAnchor(node, 0.0);


                content.getChildren().add(node);
                tab = new Tab("Engine", content);
                addNewTab(tab);

                break;
            case "Base":
                tabNum = checkTabExists("Base");
                if (tabNum != -1) {
                    selectedTab.set(tabs.get(tabNum));
                    break;
                }
                dialog = screensConfiguration.base();
                node = dialog.getScene().getRoot();
                content.setTopAnchor(node, 0.0);
                content.setRightAnchor(node, 0.0);
                content.setLeftAnchor(node, 0.0);
                content.setBottomAnchor(node, 0.0);

                content.getChildren().add(node);
                tab = new Tab("Base", content);
                addNewTab(tab);
                break;
            case "Zone":
                tabNum = checkTabExists("Zone");
                if (tabNum != -1) {
                    selectedTab.set(tabs.get(tabNum));
                    break;
                }

                dialog = screensConfiguration.zone();
                node = dialog.getScene().getRoot();
                content.setTopAnchor(node, 0.0);
                content.setRightAnchor(node, 0.0);
                content.setLeftAnchor(node, 0.0);
                content.setBottomAnchor(node, 0.0);

                content.getChildren().add(node);
                tab = new Tab("Zone", content);
                addNewTab(tab);
                break;
            case "Lubrifiant":
                tabNum = checkTabExists("Lubrifiant");
                if (tabNum != -1) {
                    selectedTab.set(tabs.get(tabNum));
                    break;
                }
                dialog = screensConfiguration.lubrifiant();
                node = dialog.getScene().getRoot();
                content.getChildren().add(node);
                content.setTopAnchor(node, 0.0);
                content.setRightAnchor(node, 0.0);
                content.setLeftAnchor(node, 0.0);
                content.setBottomAnchor(node, 0.0);

                tab = new Tab("Lubrifiant", content);
                addNewTab(tab);

                break;
            case "Cancel":
                break;
            default:
                break;
        }
    }

    private void handleMenuItemEvents(MenuItem me) {
        FXMLDialog dialg;
        switch (me.getId()) {


            case "miListEngine":
                dialg = screensConfiguration.engine();
                dialg.initModality(Modality.APPLICATION_MODAL);
                dialg.show();
                break;

            case "miHistoriqueOfMaintenance":
                dialg = screensConfiguration.mainteanaceDetailsDialog();
                dialg.initModality(Modality.APPLICATION_MODAL);
                dialg.show();
                break;
            case "miHistoriqueOfWorkingTime":
                dialg = screensConfiguration.workingTimeDetails();
                dialg.initModality(Modality.APPLICATION_MODAL);
                dialg.show();
                break;

            case "miCancel":
                   stage.close();
            default:
                break;
        }
    }

    private int checkTabExists(String tabName) {
        for (int i = 0; i < tabs.size(); i++) {
            if (tabs.get(i).getText().equals(tabName)) {
                return i;
            }

        }

        return -1;
    }

    private void addNewTab(Tab tab) {
        tabs.addAll(tab);
        selectedTab.set(tab);


    }
}
