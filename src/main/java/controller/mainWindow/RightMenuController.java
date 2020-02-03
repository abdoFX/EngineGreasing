package controller.mainWindow;

import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import org.springframework.beans.factory.annotation.Autowired;
import util.DialogController;
import util.FXMLDialog;
import view.ScreensConfiguration;
import viewModel.mainWindow.RightMenuViewModel;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created on 20-12-2016 at 19:05.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public class RightMenuController implements DialogController, Initializable {



    @Autowired
    RightMenuViewModel rightMenuViewModel;

    FXMLDialog dialog;

    @Override
    public void setDialog(FXMLDialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

      }

    public RightMenuViewModel getRightMenuViewModel() {
        return rightMenuViewModel;
    }

    public void setRightMenuViewModel(RightMenuViewModel rightMenuViewModel) {
        this.rightMenuViewModel = rightMenuViewModel;
    }


    @FXML
    private void forwardEventHandling(Event event) {
        rightMenuViewModel.handleEvent(event);
    }
}
