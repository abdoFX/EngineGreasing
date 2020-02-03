package viewModel;

import bo.MaintenanceBO;
import bo.WorkingTimeEntryBO;
import com.google.common.eventbus.EventBus;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import model.WorkingTimeEntry;
import model.maintenance;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;
import org.springframework.beans.factory.annotation.Autowired;
import printing.JRViewerFx;
import printing.JRViewerFxMode;
import report.ReportMananger;
import util.DialogUtil;
import view.ScreensConfiguration;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Created on 11-12-2016 at 15:19.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public class MaintenanceDetailsDialogViewModel {

    @Autowired
    EventBus eventBus;
    @Autowired
    ScreensConfiguration screensConfiguration;
    @Autowired
    private MaintenanceBO maintenanceBO;

    Scene scene;
    // Properties
    private ObservableList<maintenance> maintanceList = FXCollections.observableArrayList();
    private ObjectProperty<maintenance> seletedMaintenanceItem = new ReadOnlyObjectWrapper<>();

    private StringProperty selectedPeriod = new SimpleStringProperty();
    private ObjectProperty<LocalDate> dateFrom = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDate> dateTo = new SimpleObjectProperty<>();


    private BooleanProperty btnEditAtiveState = new ReadOnlyBooleanWrapper();
    private BooleanProperty btnDeleteAtiveState = new ReadOnlyBooleanWrapper();


    private FilteredList<maintenance> filtredMaintenanceList = new FilteredList<>(maintanceList, e -> true);
    private SortedList<maintenance> sortedList = new SortedList<>(filtredMaintenanceList);

    public MaintenanceDetailsDialogViewModel() {
        setupBinding();
        setupListener();
    }

    private void setupListener() {
        maintanceList.addListener(new ListChangeListener<maintenance>() {
            @Override
            public void onChanged(Change<? extends maintenance> c) {
                c.next();
                try {
                    filtredMaintenanceList.clear();
                    filtredMaintenanceList.setAll(c.getList());
                } catch (UnsupportedOperationException e) {

                }

            }
        });
        selectedPeriod.addListener((observable, oldValue, newValue) -> {
            if (newValue == null)
                return;

            switch (newValue) {

                case "Today":
                    filtredMaintenanceList.setPredicate(maintenanceItem -> {
                        if (maintenanceItem.getMaintDate().toLocalDate().isEqual(LocalDate.now()))
                            return true;
                        else
                            return false;

                    });

                    setDateFrom(LocalDate.now());
                    setDateTo(LocalDate.now());

                    break;
                case "Yesterday":
                    filtredMaintenanceList.setPredicate(maintenanceItem -> {
                        Long duration = ChronoUnit.DAYS.between(maintenanceItem.getMaintDate().toLocalDate(), LocalDate.now());

                        if (duration <= 1)
                            return true;
                        else
                            return false;

                    });
                    setDateFrom(LocalDate.now().minusDays(1));
                    setDateTo(LocalDate.now());

                    break;
                case "This Week":
                    filtredMaintenanceList.setPredicate(maintenanceItem -> {
                        Long duration = ChronoUnit.DAYS.between(maintenanceItem.getMaintDate().toLocalDate(), LocalDate.now());
                        if (duration <= 7)
                            return true;
                        else
                            return false;

                    });
                    setDateFrom(LocalDate.now().minusDays(7));
                    setDateTo(LocalDate.now());

                    break;

                case "This Month":
                    filtredMaintenanceList.setPredicate(maintenanceItem -> {

                        if (maintenanceItem.getMaintDate().toLocalDate().getYear() == LocalDate.now().getYear()) {
                            if (maintenanceItem.getMaintDate().toLocalDate().getMonth() == LocalDate.now().getMonth())
                                return true;
                            else return false;
                        } else
                            return false;

                    });
                    setDateFrom(LocalDate.now().minusDays(30));
                    setDateTo(LocalDate.now());


                    break;
                default:
                    break;
            }
        });

    }

    private void setupBinding() {
        btnDeleteAtiveState.bind(seletedMaintenanceItem.isNull());
        btnEditAtiveState.bind(seletedMaintenanceItem.isNull());
    }

    // Getters and Setters


    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public SortedList<maintenance> getSortedList() {
        return sortedList;
    }

    public void setSortedList(SortedList<maintenance> sortedList) {
        this.sortedList = sortedList;
    }

    public maintenance getSeletedMaintenanceItem() {
        return seletedMaintenanceItem.get();
    }

    public ObjectProperty<maintenance> seletedMaintenanceItemProperty() {
        return seletedMaintenanceItem;
    }

    public void setSeletedMaintenanceItem(maintenance seletedMaintenanceItem) {
        this.seletedMaintenanceItem.set(seletedMaintenanceItem);
    }

    public String getSelectedPeriod() {
        return selectedPeriod.get();
    }

    public StringProperty selectedPeriodProperty() {
        return selectedPeriod;
    }

    public void setSelectedPeriod(String selectedPeriod) {
        this.selectedPeriod.set(selectedPeriod);
    }

    public LocalDate getDateFrom() {
        return dateFrom.get();
    }

    public ObjectProperty<LocalDate> dateFromProperty() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom.set(dateFrom);
    }

    public LocalDate getDateTo() {
        return dateTo.get();
    }

    public ObjectProperty<LocalDate> dateToProperty() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo.set(dateTo);
    }

    public boolean getBtnEditAtiveState() {
        return btnEditAtiveState.get();
    }

    public BooleanProperty btnEditAtiveStateProperty() {
        return btnEditAtiveState;
    }

    public void setBtnEditAtiveState(boolean btnEditAtiveState) {
        this.btnEditAtiveState.set(btnEditAtiveState);
    }

    public boolean getBtnDeleteAtiveState() {
        return btnDeleteAtiveState.get();
    }

    public BooleanProperty btnDeleteAtiveStateProperty() {
        return btnDeleteAtiveState;
    }

    public void setBtnDeleteAtiveState(boolean btnDeleteAtiveState) {
        this.btnDeleteAtiveState.set(btnDeleteAtiveState);
    }

    public ObservableList<maintenance> getMaintanceList() {
        return maintanceList;
    }

    public void setMaintanceList(ObservableList<maintenance> maintanceList) {

        this.maintanceList.addAll(maintanceList);
        eventBus.post("Working Time Data Changed");
    }


    // Event Handling

    public void handleEvent(Event event) {
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
        if (!(event.getSource() instanceof Button))
            return;

        String id = ((Button) event.getSource()).getId();

        switch (id) {
            case "btnReturn":
                if (scene != null) {
                    StackPane stackPaneContent = (StackPane) scene.lookup("#contentPane");
                    stackPaneContent.getChildren().remove(scene.lookup("#maintenanceDetailsRoot"));
                }
                break;
            case "btnEdit":
                // maintenanceBO.Update(seletedMaintenanceItem.get());
                break;
            case "btnDelete":
                maintenanceBO.Delete(seletedMaintenanceItem.get());
                updateMaintenanceList();

                eventBus.post("Update Engine Data");

                break;
            case "btnPrint":


                if (!sortedList.isEmpty()) {
                    JasperReportBuilder report = ReportMananger.createMaintenanceReport((List) sortedList);
                    try {
                        JRViewerFx jrViewerFx = new JRViewerFx(report.toJasperPrint(), JRViewerFxMode.REPORT_VIEW, screensConfiguration.getPrimaryStage());
                    } catch (DRException e) {
                        e.printStackTrace();
                    }
                } else {
                    DialogUtil.showNotificaiton("No Thing To Print", "Printing", null).showWarning();
                }
                break;
            default:
                break;
        }


    }

    private void updateMaintenanceList() {
        maintanceList.clear();
        List<maintenance> allEngines = maintenanceBO.getAll();
        maintanceList.clear();
        allEngines.forEach(maintenance -> maintanceList.add(maintenance));
        eventBus.post("Maintenance Data Changed");
    }


}
