package controller.mainWindow;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import util.DialogController;
import util.FXMLDialog;
import view.ScreensConfiguration;
import viewModel.mainWindow.RightMenuViewModel;
import viewModel.mainWindow.mWindowViewModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created on 19-12-2016 at 23:44.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public class mController implements DialogController, Initializable {


    @FXML
    private JFXHamburger Hamburger;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private StackPane contentPane;


    @FXML
    private VBox mainWindowRoot;

    @Autowired
    ScreensConfiguration screensConfiguration;

    @Autowired
    mWindowViewModel mWindowViewModel;

    FXMLDialog dialog;

    @Autowired
    EventBus eventBus;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventBus.register(mController.this);

        Platform.runLater(() -> {
            mWindowViewModel.setScene(mainWindowRoot.getScene());
        });
        FXMLDialog dialog = screensConfiguration.mainWidowRightMenu();


        RightMenuViewModel rightMenuViewModel = ((RightMenuController) dialog.getController()).getRightMenuViewModel();

        drawer.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                if (drawer.isHidden())
                    drawer.close();
            }
        });


        Platform.runLater(() -> {
            rightMenuViewModel.setScene(drawer.getScene());
        });

        drawer.setSidePane(screensConfiguration.mainWidowRightMenu().getScene().getRoot());


        /*
        HamburgerBackArrowBasicTransition burgerTask2 = new HamburgerBackArrowBasicTransition(Hamburger);
        Hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            burgerTask2.setRate(burgerTask2.getRate() * -1);
            burgerTask2.play();


            if (drawer.isShown()) {
                drawer.close();

            } else {
                drawer.open();
                rightMenuViewModel.getScene().getRoot().requestFocus();
            }


        });*/
    }

    @Override
    public void setDialog(FXMLDialog dialog) {
        this.dialog = dialog;
    }


    @FXML
    private void forwardEventHandling(Event event) {
        mWindowViewModel.handleEvent(event);
    }


    @Subscribe
    public void receiveNotification(String command) {
        switch (command) {
            case "show drawer":
                if (drawer.isHidden())
                    drawer.open();
                else
                    drawer.isShown();
                break;
            case "hide drawer":
                if (drawer.isShown())
                    drawer.close();
                else
                    drawer.isHidden();
                break;
            default:
                break;
        }
    }
}
