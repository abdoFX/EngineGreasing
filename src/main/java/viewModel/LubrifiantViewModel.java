package viewModel;

import bo.BaseBo;
import bo.LubrifiantBO;
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
import javafx.util.Duration;
import model.Base;
import model.Lubrifiant;
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
public class LubrifiantViewModel {

    @Autowired
    private LubrifiantBO lubrifiantBO;

    @Autowired
    private EventBus eventBus;

    Scene scene ;

    private BooleanProperty btnEditActivated = new SimpleBooleanProperty();
    private BooleanProperty btnDeleteActivated = new SimpleBooleanProperty();
    private ObjectProperty<Lubrifiant> selecteItem = new ReadOnlyObjectWrapper<>();

    private ObservableList<Lubrifiant> lubrifiantList = FXCollections.observableArrayList();

    public LubrifiantViewModel() {

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

    public Lubrifiant getSelecteItem() {
        return selecteItem.get();
    }

    public ObjectProperty<Lubrifiant> selecteItemProperty() {
        return selecteItem;
    }

    public void setSelecteItem(Lubrifiant selecteItem) {
        this.selecteItem.set(selecteItem);
    }

    public ObservableList<Lubrifiant> getLubrifiantList() {
        return lubrifiantList;
    }

    private void updatedList() {
        List<Lubrifiant> lubrifiantListItem = lubrifiantBO.getAll();
        lubrifiantList.clear();
        lubrifiantListItem.forEach(lubrifiant -> lubrifiantList.add(lubrifiant));
        eventBus.post("Lubrifiant Data Updated");

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

    private void handleActionEvent(ActionEvent event) {
        if (!(event.getSource() instanceof Button))
            return;
        TextInputDialog dialog = null;
        Optional<String> result;
        Lubrifiant lubrifiant;
        switch (((Button) event.getSource()).getText()) {
            case "Add":
                dialog = new TextInputDialog();
                dialog.setTitle("Add Base");
                dialog.setContentText("Please enter the lubrifiant name !");
                result = dialog.showAndWait();
                if (result.isPresent()) {
                    lubrifiant = new Lubrifiant();
                    lubrifiant.setName(result.get());
                    boolean thrown = false;
                    try {
                        lubrifiantBO.Save(lubrifiant);
                    } catch (Exception e) {
                        thrown = true;
                    }

                    if (thrown == false) {
                        DialogUtil.showNotificaiton("Lubrifiant Inserted Successfully", "Lubrifiant", null).showInformation();

                        updatedList();

                    }
                }
                break;
            case "Edit":
                lubrifiant = selecteItem.get();
                if (lubrifiant == null)
                {
                    DialogUtil.showNotificaiton("Please Selecte an item", "Lubrifiant", null).hideAfter(Duration.millis(6000))
                            .showWarning();
                    return;
                }

                dialog = new TextInputDialog(lubrifiant.getName());
                dialog.setTitle("Edit lubrifiant");
                dialog.setContentText("Please enter the base name !");
                result = dialog.showAndWait();
                if (result.isPresent()) {

                    lubrifiant.setName(result.get());
                    boolean thrown = false;
                    try {
                        lubrifiantBO.Update(lubrifiant);
                    } catch (Exception e) {
                        thrown = true;
                    }

                    if (thrown == false) {
                        DialogUtil.showNotificaiton("Lubrifiant Updated Successfully", "Lubrifiant", null).showInformation();

                        updatedList();

                    }
                }
                break;
            case "Delete":
                lubrifiant = selecteItem.get();
                if (lubrifiant == null){
                    DialogUtil.showNotificaiton("Please Selecte an item", "Lubrifiant", null).hideAfter(Duration.millis(6000))
                            .showWarning();
                    return;
                }

                boolean thrown = false;
                try {
                    lubrifiantBO.Delete(lubrifiant);
                } catch (Exception e) {
                    thrown = true;
                }

                if (thrown == false) {
                    DialogUtil.showNotificaiton("Lubrifiant Deleted Successfully", "Lubrifiant", null).showInformation();

                    updatedList();

                }

                break;
            case "return":
                if (scene != null) {

                    StackPane stackPaneContent = (StackPane) scene.lookup("#contentPane");

                    stackPaneContent.getChildren().remove(scene.lookup("#lubrifiant"));
                }


            default:
                break;

        }

    }

    private void handleKeypressEvent(Event event) {
        KeyEvent events = (KeyEvent) event;
        switch (events.getCode()) {
            case ENTER:
                eventBus.post("Close Lubrifiant");
                break;
            default:
                break;
        }
    }
}
