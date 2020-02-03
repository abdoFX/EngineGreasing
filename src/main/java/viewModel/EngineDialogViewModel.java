package viewModel;

import bo.EngineBO;
import bo.LubrifiantBO;
import bo.ZoneBO;
import com.google.common.eventbus.EventBus;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXRadioButton;
import de.saxsys.mvvmfx.utils.validation.CompositeValidator;
import de.saxsys.mvvmfx.utils.validation.FunctionBasedValidator;
import de.saxsys.mvvmfx.utils.validation.ObservableRuleBasedValidator;
import de.saxsys.mvvmfx.utils.validation.ValidationMessage;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Toggle;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import model.Engine;
import model.Lubrifiant;
import model.WorkingTimeType;
import model.Zone;
import org.springframework.beans.factory.annotation.Autowired;
import util.DialogUtil;

import javax.persistence.Column;
import java.time.LocalDateTime;

/**
 * Created on 22-11-2016 at 20:50.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public class EngineDialogViewModel {

    // Service DAO
    @Autowired
    private EngineBO engineBO;
    @Autowired
    private ZoneBO zoneBO;
    @Autowired
    private LubrifiantBO lubrifiantBO;

    // Publisher subscrier mechanism
    @Autowired
    private EventBus eventBus;


    Scene scene;

    // Properties
    private StringProperty newOrUpdateState = new SimpleStringProperty("NEW");
    private BooleanProperty formChange = new SimpleBooleanProperty(false);

    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty type = new SimpleStringProperty();
    private StringProperty designation = new SimpleStringProperty();
    private StringProperty consummation = new SimpleStringProperty();
    private ObjectProperty<Integer> frequincy = new SimpleObjectProperty<>();
    private ObjectProperty<Lubrifiant> selectedLubrifiant = new SimpleObjectProperty<>();
    private ObjectProperty<Zone> selectedZone = new SimpleObjectProperty<>();
    private ObjectProperty<ObservableList<TableColumn<Lubrifiant, ?>>> lubrifiantColumns = new SimpleObjectProperty<>();
    private ObjectProperty<ObservableList<TableColumn<Zone, ?>>> zoneTableColumns = new SimpleObjectProperty<>();
    private ObjectProperty<Toggle> selectedToggle = new SimpleObjectProperty<>();
    private StringProperty selectedWorkingType = new SimpleStringProperty();


    // Validation
    private FunctionBasedValidator<String> typeValidator;
    private ObservableRuleBasedValidator designationValidator;
    private ObservableRuleBasedValidator frequincyValidator;
    private ObservableRuleBasedValidator lubrifiantValidator;
    private ObservableRuleBasedValidator zoneValidator;

    private CompositeValidator engineDialogValidator;

    public EngineDialogViewModel() {

        // Init Properties
        initRequiredProperties();
        // Init Listener
        initListener();
        // Init Validation support
        initValidation();


    }

    private void initRequiredProperties() {

        TableColumn<Lubrifiant, String> tcLubrifiantName = new TableColumn<>("Lubrifiant Name");
        tcLubrifiantName.setCellValueFactory(new PropertyValueFactory<Lubrifiant, String>("name"));
        lubrifiantColumns.setValue(FXCollections.observableArrayList(tcLubrifiantName));


        TableColumn<Zone, String> tcZoneName = new TableColumn<>("Installation Name");
        tcZoneName.setCellValueFactory(new PropertyValueFactory<Zone, String>("name"));
        zoneTableColumns.setValue(FXCollections.observableArrayList(tcZoneName));


    }


    // Getter and Setter


    public String getConsummation() {
        return consummation.get();
    }

    public StringProperty consummationProperty() {
        return consummation;
    }

    public void setConsummation(String consummation) {
        this.consummation.set(consummation);
    }

    public Toggle getSelectedToggle() {
        return selectedToggle.get();
    }

    public ObjectProperty<Toggle> selectedToggleProperty() {
        return selectedToggle;
    }

    public void setSelectedToggle(Toggle selectedToggle) {
        this.selectedToggle.set(selectedToggle);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getDesignation() {
        return designation.get();
    }

    public StringProperty designationProperty() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation.set(designation);
    }

    public Integer getFrequincy() {
        return frequincy.get();
    }

    public ObjectProperty<Integer> frequincyProperty() {
        return frequincy;
    }

    public void setFrequincy(Integer frequincy) {
        this.frequincy.set(frequincy);
    }

    public Zone getSelectedZone() {
        return selectedZone.get();
    }

    public ObjectProperty<Zone> selectedZoneProperty() {
        return selectedZone;
    }

    public void setSelectedZone(Zone selectedZone) {
        this.selectedZone.set(selectedZone);
    }

    public Lubrifiant getSelectedLubrifiant() {
        return selectedLubrifiant.get();
    }

    public ObjectProperty<Lubrifiant> selectedLubrifiantProperty() {
        return selectedLubrifiant;
    }

    public void setSelectedLubrifiant(Lubrifiant selectedLubrifiant) {
        this.selectedLubrifiant.set(selectedLubrifiant);
    }

    public String getNewOrUpdateState() {
        return newOrUpdateState.get();
    }

    public StringProperty newOrUpdateStateProperty() {
        return newOrUpdateState;
    }

    public void setNewOrUpdateState(String newOrUpdateState) {
        this.newOrUpdateState.set(newOrUpdateState);
    }

    public boolean getFormChange() {
        return formChange.get();
    }

    public BooleanProperty formChangeProperty() {
        return formChange;
    }

    public void setFormChange(boolean formChange) {
        this.formChange.set(formChange);
    }

    public FunctionBasedValidator<String> getTypeValidator() {
        return typeValidator;
    }

    public void setTypeValidator(FunctionBasedValidator<String> typeValidator) {
        this.typeValidator = typeValidator;
    }

    public ObservableRuleBasedValidator getDesignationValidator() {
        return designationValidator;
    }

    public void setDesignationValidator(ObservableRuleBasedValidator designationValidator) {
        this.designationValidator = designationValidator;
    }

    public ObservableRuleBasedValidator getFrequincyValidator() {
        return frequincyValidator;
    }

    public void setFrequincyValidator(ObservableRuleBasedValidator frequincyValidator) {
        this.frequincyValidator = frequincyValidator;
    }

    public ObservableRuleBasedValidator getLibrifiantValidator() {
        return lubrifiantValidator;
    }

    public void setLibrifiantValidator(ObservableRuleBasedValidator librifiantValidator) {
        this.lubrifiantValidator = librifiantValidator;
    }

    public CompositeValidator getEngineDialogValidator() {
        return engineDialogValidator;
    }

    public void setEngineDialogValidator(CompositeValidator engineDialogValidator) {
        this.engineDialogValidator = engineDialogValidator;
    }


    public ObservableList<TableColumn<Lubrifiant, ?>> getLubrifiantColumns() {
        return lubrifiantColumns.get();
    }

    public ObjectProperty<ObservableList<TableColumn<Lubrifiant, ?>>> lubrifiantColumnsProperty() {
        return lubrifiantColumns;
    }

    public void setLubrifiantColumns(ObservableList<TableColumn<Lubrifiant, ?>> lubrifiantColumns) {
        this.lubrifiantColumns.set(lubrifiantColumns);
    }

    public ObservableList<TableColumn<Zone, ?>> getZoneTableColumns() {
        return zoneTableColumns.get();
    }

    public ObjectProperty<ObservableList<TableColumn<Zone, ?>>> zoneTableColumnsProperty() {
        return zoneTableColumns;
    }

    public void setZoneTableColumns(ObservableList<TableColumn<Zone, ?>> zoneTableColumns) {
        this.zoneTableColumns.set(zoneTableColumns);
    }


    public ObservableList<Zone> getZoneList() {
        return FXCollections.observableList(zoneBO.getAll());
    }

    public ObservableList<Lubrifiant> getLubrifiantList() {
        return FXCollections.observableList(lubrifiantBO.getAll());
    }

    public String getSelectedWorkingType() {
        return selectedWorkingType.get();
    }

    public StringProperty selectedWorkingTypeProperty() {
        return selectedWorkingType;
    }

    public void setSelectedWorkingType(String selectedWorkingType) {
        this.selectedWorkingType.set(selectedWorkingType);
    }


    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    // Validation support
    private void initValidation() {
        typeValidator = new FunctionBasedValidator<String>
                (type, input -> input != null && !input.trim().isEmpty(),
                        ValidationMessage.error("Engine type may not be empty"));

        designationValidator = new ObservableRuleBasedValidator();
        designationValidator.addRule(designation.isNotEmpty(), ValidationMessage.error("Designation  can't be empty"));

        frequincyValidator = new ObservableRuleBasedValidator();
        frequincyValidator.addRule(frequincy.isNotEqualTo(0), ValidationMessage.error("Frequincy  can't be empty"));

        lubrifiantValidator = new ObservableRuleBasedValidator();
        lubrifiantValidator.addRule(selectedLubrifiant.isNull(), ValidationMessage.error("Lubrifiant  can't be empty"));

        zoneValidator = new ObservableRuleBasedValidator();
        zoneValidator.addRule(selectedLubrifiant.isNull(), ValidationMessage.error("Zone  can't be empty"));

        engineDialogValidator = new CompositeValidator();
        engineDialogValidator.addValidators(typeValidator, designationValidator, frequincyValidator);

    }

    // Listern
    private void initListener() {

        selectedWorkingType.addListener((observable, oldValue1, newValue1) -> {
            eventBus.post(newValue1);
        });

        type.addListener((obv, oldValue, newValue) -> {
            formChange.setValue(true);
        });

        designation.addListener((obv, oldValue, newValue) -> {
            formChange.setValue(true);
        });

        frequincy.addListener((obv, oldValue, newValue) -> {
            formChange.setValue(true);
        });

        selectedLubrifiant.addListener((obv, oldValue, newValue) -> {
            formChange.setValue(true);
        });

        selectedZone.addListener((obv, oldValue, newValue) -> {
            formChange.setValue(true);
        });

    }

    // Event Handling
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
        if (!(event.getSource() instanceof Button))
            return;


        String id = ((Button) event.getSource()).getId();


        switch (id) {
            case "btnOk":
                okBtnClicked();
                StackPane stackPaneContent1 = (StackPane) scene.lookup("#contentPane");
                stackPaneContent1.getChildren().remove(scene.lookup("#engineDialog"));



            case "btnReturn":
                StackPane stackPaneContent = (StackPane) scene.lookup("#contentPane");
                stackPaneContent.getChildren().remove(scene.lookup("#engineDialog"));

                break;
            default:
                break;

        }
    }

    public void okBtnClicked() {


        if (formChange.get() == false)
            return;
        Engine engine;

        if (newOrUpdateState.get().equals("UPDATE")) {
            engine = engineBO.finById(id.get());
            engine.setType(this.type.get());
            engine.setDesignation(this.designation.get());
            engine.setLubrifiantConsumation(consummation.get());
            engine.setFrequincy(frequincy.get());
            engine.setLubrifiant(selectedLubrifiant.get());
            engine.setZone(selectedZone.get());
            String toggleValue = ((JFXRadioButton) selectedToggle.getValue()).getText();
            engine.setWorkingTimeType(toggleValue.equalsIgnoreCase(WorkingTimeType.HOUR.name()) ? WorkingTimeType.HOUR : WorkingTimeType.PERIOD);

            engineBO.Update(engine);

            DialogUtil.showNotificaiton("Engine Updated Successfully", "Update", null)

                    .showInformation();

        } else {
            engine = new Engine();
            engine.setType(this.type.get());
            engine.setDesignation(this.designation.get());
            engine.setLubrifiantConsumation(consummation.get());
            engine.setFrequincy(frequincy.get());
            engine.setLubrifiant(selectedLubrifiant.get());
            engine.setZone(selectedZone.get());
            String toggleValue = ((JFXRadioButton) selectedToggle.get()).getText();
            engine.setWorkingTimeType(toggleValue.equalsIgnoreCase(WorkingTimeType.HOUR.name()) ? WorkingTimeType.HOUR : WorkingTimeType.PERIOD);

            engine.setStartSetupDate(LocalDateTime.now());

            engineBO.Save(engine);
            DialogUtil.showNotificaiton("Engine Inserted Successfully", "Inserted", null)
                    .hideAfter(Duration.millis(3000))
                    .showInformation();
        }

        newOrUpdateState.set("NEW");
        eventBus.post("update");

    }

    public void initializeFields(Engine engine) {
        id.set(engine.getId());
        type.setValue(engine.getType());
        designation.setValue(engine.getDesignation());
        consummation.setValue(engine.getLubrifiantConsumation());
        frequincy.setValue(engine.getFrequincy());

        selectedLubrifiant.setValue(engine.getLubrifiant());
        selectedZone.setValue(engine.getZone());
        setSelectedWorkingType(engine.getWorkingTimeType().toString());


        newOrUpdateState.set("UPDATE");
        formChange.setValue(false);

    }

    public void clearForm() {
        this.type.set("");
        this.designation.set("");
        this.selectedZone.setValue(null);
        this.selectedLubrifiant.setValue(null);
        this.frequincy.setValue(0);


    }
}
