package viewModel;

import bo.EngineBO;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import controller.*;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.Engine;
import org.springframework.beans.factory.annotation.Autowired;
import util.DialogUtil;
import util.FXMLDialog;
import util.TransitionUtil;
import view.ScreensConfiguration;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created on 24-11-2016 at 22:23.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */

public class EngineViewModel {


    @Autowired
    private EngineBO engineBO;
    @Autowired
    private EventBus eventBus;


    Scene scene;

    private final ObjectProperty<Engine> selectedEngine = new SimpleObjectProperty<>();
    private ObservableList<Engine> engines = FXCollections.observableArrayList();
    private FilteredList<Engine> filtredEngineList = new FilteredList<Engine>(engines, e -> true);
    private SortedList<Engine> sortedList = new SortedList<Engine>(filtredEngineList);
    private StringProperty searchText = new SimpleStringProperty();


    @Autowired
    private ScreensConfiguration screens;

    public EngineViewModel() {

        searchText.addListener((observable, oldValue, newValue) -> {
            filtredEngineList.setPredicate(e -> {
                if (newValue == null || newValue.isEmpty())
                    return true;
                String tolowerCase = newValue.toLowerCase();
                if (e.getDesignation().toLowerCase().contains(tolowerCase))
                    return true;

                if (e.getZone() != null) {
                    if (e.getZone().getName().toLowerCase().contains(tolowerCase))
                        return true;
                }

                if (e.getLubrifiant() != null) {
                    if (e.getLubrifiant().getName().toLowerCase().contains(tolowerCase))
                        return true;
                }


                return false;
            });

        });

        engines.addListener(new ListChangeListener<Engine>() {
            @Override
            public void onChanged(Change<? extends Engine> c) {
                c.next();
                try {
                    filtredEngineList.clear();
                    filtredEngineList.setAll(c.getList());
                } catch (UnsupportedOperationException e) {

                }

            }
        });




    }


    @PostConstruct
    public void init() {
        eventBus.register(EngineViewModel.this);
        updateEngineList();
    }

    // Properties


    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public String getSearchText() {
        return searchText.get();
    }

    public StringProperty searchTextProperty() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText.set(searchText);
    }


    public SortedList<Engine> getSortedList() {
        return sortedList;
    }

    public void setSortedList(SortedList<Engine> sortedList) {
        this.sortedList = sortedList;
    }

    private void updateEngineList() {
        List<Engine> allEngines = engineBO.getAll();
        engines.clear();
        allEngines.forEach(engine -> engines.add(engine));
        eventBus.post("Engine Data Changed");

    }

    public ObservableList<Engine> getEngineList() {
        return engines;
    }

    public Engine getSelectedEngine() {
        return selectedEngine.get();
    }

    public ObjectProperty<Engine> selectedEngineProperty() {
        return selectedEngine;
    }

    public void setSelectedEngine(Engine selectedEngine) {
        this.selectedEngine.set(selectedEngine);
    }

    @Subscribe
    public void refrechEngineModel(String command) {
        switch (command) {
            case "update":
                updateEngineList();
                break;
            case "Update Engine Data":
                updateEngineList();
                Default:
                break;
        }


    }


    public void handleEvent(Event event) {
        System.out.println(event.getEventType());
        switch (event.getEventType().toString()) {
            case "ACTION":
                handleActionEvent((ActionEvent) event);

                break;
            case "KEY_PRESSED":
                handleKeypressEvent(event);
                break;
            default:
                break;
        }
    }

    private void handleKeypressEvent(Event event) {

    }

    private void handleActionEvent(ActionEvent event) {
        if (!(event.getSource() instanceof Button))
            return;

        FXMLDialog fxmlDialog = screens.engineDialog();
        EngineDialogController engineDialogController = (EngineDialogController) fxmlDialog.getController();


        String id = ((Button) event.getSource()).getId();
        System.out.println("This id is : " + id);
        switch (id) {
            case "btnAdd":

                engineDialogController.getEngineDialogViewModel().clearForm();
                  TransitionUtil.fadeOutTransition(fxmlDialog.getScene().getRoot());
             //   fxmlDialog.show();

               StackPane stackPanes =  (StackPane) scene.lookup("#contentPane");
                stackPanes.getChildren().add(fxmlDialog.getScene().getRoot());
                break;
            case "btnEdit":
                 engineDialogController.getEngineDialogViewModel().clearForm();
                engineDialogController.getEngineDialogViewModel().initializeFields(selectedEngine.get());

                StackPane stackPanes1 =  (StackPane) scene.lookup("#contentPane");
                stackPanes1.getChildren().add(fxmlDialog.getScene().getRoot());
                break;
            case "btnDelete":

                engineBO.Delete(selectedEngine.get());
                DialogUtil.showNotificaiton("Engine Deleted Successfully", "Delete", null)
                        .hideAfter(Duration.millis(3000))
                        .showInformation();
                updateEngineList();
                break;
            case "btnAddWorkingTime":

                  FXMLDialog fxmlDialogW = screens.workingTimeDialog();
                WorkingTimeEntryController workingTimeEntryController = (WorkingTimeEntryController) fxmlDialogW.getController();
                workingTimeEntryController.getWorkingTimeEntryViewModel().initForm(selectedEngine.get());
                fxmlDialogW.initModality(Modality.APPLICATION_MODAL);
                fxmlDialogW.initStyle(StageStyle.UNDECORATED);
                fxmlDialogW.show();
                break;
            case "btnAddMaintenance":

                FXMLDialog fxmlDialogM = screens.maintenanceDialog();
                MaintenanceController maintenanceController = (MaintenanceController) fxmlDialogM.getController();
                maintenanceController.getMaintenanceViewModel().initForm(selectedEngine.get());
                fxmlDialogM.initModality(Modality.APPLICATION_MODAL);
                fxmlDialogM.show();

                break;
            case "btnShowEngineWorkingTimeDetails":

                FXMLDialog fxmlDialogWT = screens.workingTimeDetails();
                WorkingTimeDetailsController workingTimeDetailsController = (WorkingTimeDetailsController) fxmlDialogWT.getController();
                workingTimeDetailsController.getWorkingTimeDetailsViewModel().setWorkingTimeEntries(FXCollections.observableArrayList(selectedEngine.get().getWorkingTimeEntries()));

                   StackPane stackPanes2 =  (StackPane) scene.lookup("#contentPane");
                stackPanes2.getChildren().add(fxmlDialogWT.getScene().getRoot());

                break;
            case "btnShowEngineMaintenanceHistory":

                FXMLDialog fxmlDialogMain = screens.mainteanaceDetailsDialog();
                MaintenanceDetailsDialogController maintenanceDetailsDialogController = (MaintenanceDetailsDialogController) fxmlDialogMain.getController();
                maintenanceDetailsDialogController.getMaintenanceDetailsDialogViewModel().setMaintanceList(FXCollections.observableArrayList(selectedEngine.get().getMaintenances()));

                StackPane stackPanes3 =  (StackPane) scene.lookup("#contentPane");
                stackPanes3.getChildren().add(fxmlDialogMain.getScene().getRoot());

                break;
            case "btnRefresh":
                updateEngineList();
                break;
            case "btnReturn":
                if (scene != null) {
                    StackPane stackPaneContent = (StackPane) scene.lookup("#contentPane");
                    stackPaneContent.getChildren().remove(scene.lookup("#topArea"));

                }


            default:
                break;
        }


    }


}
