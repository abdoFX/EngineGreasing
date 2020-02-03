package viewModel;

import bo.BaseBo;
import com.google.common.eventbus.EventBus;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import model.Base;
import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.Notifications;
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
public class BaseViewModel {

    @Autowired
    private BaseBo baseBo;

    @Autowired
    private EventBus eventBus;

    Scene scene;

    private BooleanProperty btnEditActivated = new SimpleBooleanProperty();
    private BooleanProperty btnDeleteActivated = new SimpleBooleanProperty();
    private ObjectProperty<Base> selecteItem = new ReadOnlyObjectWrapper<>();

    private ObservableList<Base> baseList = FXCollections.observableArrayList();

    public BaseViewModel() {

        btnDeleteActivated.bind(selecteItem.isNull());
        btnEditActivated.bind(selecteItem.isNull());

        selecteItem.addListener((observable, oldValue, newValue) -> {
            System.out.println("Changed " + newValue);
        });
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

    public Base getSelecteItem() {
        return selecteItem.get();
    }

    public ObjectProperty<Base> selecteItemProperty() {
        return selecteItem;
    }

    public void setSelecteItem(Base selecteItem) {
        this.selecteItem.set(selecteItem);
    }

    public ObservableList<Base> getBaseList() {
        return baseList;
    }

    private void updatedList() {
        List<Base> baseListItem = baseBo.getAll();
        baseList.clear();
        baseListItem.forEach(engine -> baseList.add(engine));
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
        Base base;
        switch (((Button) event.getSource()).getText()) {
            case "Add":
                dialog = new TextInputDialog();
                dialog.setTitle("Add New Station");
                dialog.setContentText("Please enter the Station name !");
                result = dialog.showAndWait();
                if (result.isPresent()) {
                    base = new Base();
                    base.setName(result.get());
                    boolean thrown = false;
                    try {
                        baseBo.Save(base);
                    } catch (Exception e) {
                        thrown = true;
                    }

                    if (thrown == false) {
                        DialogUtil.showNotificaiton("Station Inserted Successfully", "Base", null).showInformation();

                        eventBus.post("Base Inserting....");
                        updatedList();

                    }
                }
                break;
            case "Edit":
                base = selecteItem.get();
                if (base == null) {
                    DialogUtil.showNotificaiton("Please Selecte an item", "base", null).hideAfter(Duration.millis(6000))
                            .showWarning();
                    return;
                }
                dialog = new TextInputDialog(base.getName());
                dialog.setTitle("Add Base");
                dialog.setContentText("Please enter the base name !");
                result = dialog.showAndWait();
                if (result.isPresent()) {

                    base.setName(result.get());
                    boolean thrown = false;
                    try {
                        baseBo.Update(base);
                    } catch (Exception e) {
                        thrown = true;
                    }

                    if (thrown == false) {
                        DialogUtil.showNotificaiton("Station Updated Successfully", "Station", null).showInformation();
                        updatedList();

                    }
                }
                break;
            case "Delete":
                base = selecteItem.get();
                if (base == null) {
                    DialogUtil.showNotificaiton("Please Selecte an item", "Station", null).hideAfter(Duration.millis(6000))
                            .showWarning();
                    return;
                }
                boolean thrown = false;
                try {
                    baseBo.Delete(base);
                } catch (Exception e) {
                    thrown = true;
                }

                if (thrown == false) {
                    DialogUtil.showNotificaiton("Base Deleted Successfully", "Base", null).showInformation();
                    updatedList();

                }

                break;

            case "return":
                if (scene != null) {

                    StackPane stackPaneContent = (StackPane) scene.lookup("#contentPane");

                    stackPaneContent.getChildren().remove(scene.lookup("#base"));
                }


            default:
                break;

        }

    }
}
