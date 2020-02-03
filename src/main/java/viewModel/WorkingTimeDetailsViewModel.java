package viewModel;

import bo.WorkingTimeEntryBO;
import com.google.common.eventbus.EventBus;
import javafx.application.Platform;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Engine;
import model.WorkingTimeEntry;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JREmptyDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import printing.JRPrintPreview;
import printing.JRViewerFx;
import printing.JRViewerFxMode;
import printing.JasperViewerFX;
import report.ReportMananger;
import util.DialogUtil;
import view.ScreensConfiguration;

import java.io.BufferedOutputStream;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created on 11-12-2016 at 15:19.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public class WorkingTimeDetailsViewModel {

    @Autowired
    EventBus eventBus;

    @Autowired
    ScreensConfiguration screensConfiguration;

    @Autowired
    private WorkingTimeEntryBO workingTimeEntryBO;

    Scene scene;

    private BooleanProperty btnEditAtiveState = new ReadOnlyBooleanWrapper();
    private BooleanProperty btnDeleteAtiveState = new ReadOnlyBooleanWrapper();
    private ObservableList<WorkingTimeEntry> workingTimeEntries = FXCollections.observableArrayList();

    private ObjectProperty<WorkingTimeEntry> seletedWorkingTimeItem = new ReadOnlyObjectWrapper<>();
    private StringProperty selectedPeriod = new SimpleStringProperty();
    private ObjectProperty<LocalDate> dateFrom = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDate> dateTo = new SimpleObjectProperty<>();

    private FilteredList<WorkingTimeEntry> filtredWorkingTimeList = new FilteredList<WorkingTimeEntry>(workingTimeEntries, e -> true);
    private SortedList<WorkingTimeEntry> sortedList = new SortedList<WorkingTimeEntry>(filtredWorkingTimeList);


    public WorkingTimeDetailsViewModel() {

        workingTimeEntries.addListener(new ListChangeListener<WorkingTimeEntry>() {
            @Override
            public void onChanged(Change<? extends WorkingTimeEntry> c) {
                c.next();
                try {
                    filtredWorkingTimeList.clear();
                    filtredWorkingTimeList.setAll(c.getList());
                } catch (UnsupportedOperationException e) {

                }

            }
        });

        btnDeleteAtiveState.bind(seletedWorkingTimeItem.isNull());
        btnEditAtiveState.bind(seletedWorkingTimeItem.isNull());

        setupListeners();


    }

    private void setupListeners() {

        dateFrom.addListener((observable1, oldValue1, newValue1) -> {
            filtredWorkingTimeList.setPredicate(workingTimeEntry -> {


                LocalDate date = workingTimeEntry.getDateTime().toLocalDate();

                if ((date.isEqual(getDateFrom()) || date.isAfter(getDateFrom())) &&
                        (date.isEqual(getDateTo()) || date.isBefore(getDateTo())))
                    return true;
                else
                    return false;

            });
        });

        dateTo.addListener((observable1, oldValue1, newValue1) -> {
            filtredWorkingTimeList.setPredicate(workingTimeEntry -> {
                LocalDate date = workingTimeEntry.getDateTime().toLocalDate();

                if ((date.isEqual(getDateFrom()) || date.isAfter(getDateFrom())) &&
                        (date.isEqual(getDateTo()) || date.isBefore(getDateTo())))
                    return true;
                else
                    return false;

            });
        });

        selectedPeriod.addListener((observable, oldValue, newValue) -> {
            if (newValue == null)
                return;

            switch (newValue) {

                case "Today":
                    filtredWorkingTimeList.setPredicate(workingTimeEntry -> {
                        if (workingTimeEntry.getDateTime().toLocalDate().isEqual(LocalDate.now()))
                            return true;
                        else
                            return false;

                    });

                    setDateFrom(LocalDate.now());
                    setDateTo(LocalDate.now());

                    break;
                case "Yesterday":
                    filtredWorkingTimeList.setPredicate(workingTimeEntry -> {
                        Long duration = ChronoUnit.DAYS.between(workingTimeEntry.getDateTime().toLocalDate(), LocalDate.now());

                        if (duration <= 1)
                            return true;
                        else
                            return false;

                    });
                    setDateFrom(LocalDate.now().minusDays(1));
                    setDateTo(LocalDate.now());

                    break;
                case "This Week":
                    filtredWorkingTimeList.setPredicate(workingTimeEntry -> {
                        Long duration = ChronoUnit.DAYS.between(workingTimeEntry.getDateTime().toLocalDate(), LocalDate.now());
                        if (duration <= 7)
                            return true;
                        else
                            return false;

                    });
                    setDateFrom(LocalDate.now().minusDays(7));
                    setDateTo(LocalDate.now());

                    break;

                case "This Month":
                    filtredWorkingTimeList.setPredicate(workingTimeEntry -> {

                        if (workingTimeEntry.getDateTime().toLocalDate().getYear() == LocalDate.now().getYear()) {
                            if (workingTimeEntry.getDateTime().toLocalDate().getMonth() == LocalDate.now().getMonth())
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


    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public SortedList<WorkingTimeEntry> getSortedList() {
        return sortedList;
    }

    public void setSortedList(SortedList<WorkingTimeEntry> sortedList) {
        this.sortedList = sortedList;
    }

    public WorkingTimeEntry getSeletedWorkingTimeItem() {
        return seletedWorkingTimeItem.get();
    }

    public ObjectProperty<WorkingTimeEntry> seletedWorkingTimeItemProperty() {
        return seletedWorkingTimeItem;
    }

    public void setSeletedWorkingTimeItem(WorkingTimeEntry seletedWorkingTimeItem) {
        this.seletedWorkingTimeItem.set(seletedWorkingTimeItem);
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

    public ObservableList<WorkingTimeEntry> getWorkingTimeEntries() {
        return workingTimeEntries;
    }

    public void setWorkingTimeEntries(ObservableList<WorkingTimeEntry> workingTimeEntries) {
        this.workingTimeEntries.addAll(workingTimeEntries);
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
                    stackPaneContent.getChildren().remove(scene.lookup("#workingTimeDetailsRoot"));
                }
                break;

            case "btnEdit":
                // maintenanceBO.Update(seletedMaintenanceItem.get());
                break;
            case "btnDelete":
                workingTimeEntryBO.Delete(seletedWorkingTimeItem.get());
                updateMaintenanceList();
                eventBus.post("Working Time Data Changed");
                break;
            case "btnPrint":
                if (!sortedList.isEmpty()) {
                    JasperReportBuilder report = ReportMananger.createWorkingTimeReport((List) sortedList);
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
        List<WorkingTimeEntry> allWorkingTimesEntries = workingTimeEntryBO.getAll();
        workingTimeEntries.clear();
        allWorkingTimesEntries.forEach(workingTimeEntry -> workingTimeEntries.add(workingTimeEntry));
        eventBus.post("Working Time Data Changed");
    }

}
