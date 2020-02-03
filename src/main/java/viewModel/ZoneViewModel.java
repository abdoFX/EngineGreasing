package viewModel;

import bo.BaseBo;
import bo.ZoneBO;
import com.google.common.eventbus.EventBus;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import model.Base;
import model.Zone;
import org.springframework.beans.factory.annotation.Autowired;
import util.DialogUtil;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

/**
 * Created on 29-11-2016 at 21:36.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public class ZoneViewModel {

    @Autowired
    private ZoneBO zoneBO;

    @Autowired
    private EventBus eventBus;


    Scene scene;

    private BooleanProperty btnEditActivated = new SimpleBooleanProperty();
    private BooleanProperty btnDeleteActivated = new SimpleBooleanProperty();
    private ObjectProperty<Zone> selecteItem = new ReadOnlyObjectWrapper<>();

    private ObservableList<Zone> baseList = FXCollections.observableArrayList();

    public ZoneViewModel() {

    }

    @PostConstruct
    public void init() {
        updatedList();
    }

    // Getter and Setter


    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public boolean getBtnEditActivated() {
        return btnEditActivated.get();
    }

    public BooleanProperty btnEditActivatedProperty() {
        return btnEditActivated;
    }

    public void setBtnEditActivated(boolean btnEditActivated) {
        this.btnEditActivated.set(btnEditActivated);
    }

    public boolean getBtnDeleteActivated() {
        return btnDeleteActivated.get();
    }

    public BooleanProperty btnDeleteActivatedProperty() {
        return btnDeleteActivated;
    }

    public void setBtnDeleteActivated(boolean btnDeleteActivated) {
        this.btnDeleteActivated.set(btnDeleteActivated);
    }

    public Zone getSelecteItem() {
        return selecteItem.get();
    }

    public ObjectProperty<Zone> selecteItemProperty() {
        return selecteItem;
    }

    public void setSelecteItem(Zone selecteItem) {
        this.selecteItem.set(selecteItem);
    }

    public ObservableList<Zone> getBaseList() {
        return baseList;
    }

    public void setBaseList(ObservableList<Zone> baseList) {
        this.baseList = baseList;
    }

    private void updatedList() {
        List<Zone> baseListItem = zoneBO.getAll();
        baseList.clear();
        baseListItem.forEach(zone -> baseList.add(zone));
        eventBus.post("Data Updated");

    }


    // Event Handling
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
        KeyEvent events = (KeyEvent) event;
        switch (events.getCode()) {
            case ENTER:
                eventBus.post("Close Zone");
                break;
            default:
                break;
        }
    }

    private void handleActionEvent(ActionEvent event) {
        if (!(event.getSource() instanceof Button))
            return;
        TextInputDialog dialog = null;
        Optional<String> result;
        Zone zone;
        switch (((Button) event.getSource()).getText()) {
            case "Add":
                dialog = new TextInputDialog();
                dialog.setTitle("Add Installation");
                dialog.setContentText("Please enter the Installation name !");
                result = dialog.showAndWait();
                if (result.isPresent()) {
                    zone = new Zone();
                    zone.setName(result.get());
                    boolean thrown = false;
                    try {
                        zoneBO.Save(zone);
                    } catch (Exception e) {
                        thrown = true;
                    }

                    if (thrown == false) {
                        DialogUtil.showNotificaiton("Installation Inserted Successfully", "Installation", null).showInformation();

                        updatedList();

                    }
                }
                break;
            case "Edit":
                zone = selecteItem.get();
                if (zone == null) {
                    DialogUtil.showNotificaiton("Please Selecte an item", "Installation", null).hideAfter(Duration.millis(6000))
                            .showWarning();
                    return;
                }

                dialog = new TextInputDialog(zone.getName());
                dialog.setTitle("Edit Installation");
                dialog.setContentText("Please enter the Installation name !");
                result = dialog.showAndWait();
                if (result.isPresent()) {

                    zone.setName(result.get());
                    boolean thrown = false;
                    try {
                        zoneBO.Update(zone);
                    } catch (Exception e) {
                        thrown = true;
                    }

                    if (thrown == false) {
                        DialogUtil.showNotificaiton("Installation Updated Successfully", "Installation", null).showInformation();

                        updatedList();

                    }
                }
                break;
            case "Delete":
                zone = selecteItem.get();
                if (zone == null) {
                    DialogUtil.showNotificaiton("Please Selecte an item", "Installation", null).hideAfter(Duration.millis(6000))
                            .showWarning();
                    return;
                }

                boolean thrown = false;
                try {
                    zoneBO.Delete(zone);
                } catch (Exception e) {
                    thrown = true;
                }

                if (thrown == false) {
                    DialogUtil.showNotificaiton("Installation Deleted Successfully", "Installation", null).showInformation();

                    updatedList();

                }

                break;
            case "return":
                if (scene != null) {

                    StackPane stackPaneContent = (StackPane) scene.lookup("#contentPane");

                    stackPaneContent.getChildren().remove(scene.lookup("#zone"));
                }

            default:
                break;

        }

    }
}
