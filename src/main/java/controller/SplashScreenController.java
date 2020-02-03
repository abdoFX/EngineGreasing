package controller;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import util.DialogController;
import util.FXMLDialog;
import view.ScreensConfiguration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created on 10-12-2016 at 14:24.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public class SplashScreenController implements Initializable, DialogController {
    @FXML
    private StackPane stackPane;

    private FXMLDialog splashScreenDialog;

    @Autowired
    ScreensConfiguration screensConfiguration;

    @FXML
    private ImageView imageView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imageView.setImage(new Image(getClass().getResource("/img/splash screen.jpg").toExternalForm()));
        imageView.setFitHeight(300);
        imageView.setFitWidth(500);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), stackPane);
                fadeIn.setCycleCount(1);
                fadeIn.setFromValue(1);
                fadeIn.setToValue(0.2);
                fadeIn.play();
                fadeIn.setOnFinished(event -> {
                    FadeTransition fadeout = new FadeTransition(Duration.millis(2000), stackPane);
                    fadeout.setFromValue(0.2);
                    fadeout.setToValue(1);
                    fadeout.setCycleCount(1);
                    fadeout.play();
                    fadeout.setOnFinished(event1 -> {

                        stackPane.getScene().getWindow().hide();
                        FXMLDialog dialog = screensConfiguration.mWindow();
                        dialog.initStyle(StageStyle.UNDECORATED);
                        dialog.show();

                    });
                });
            }
        });


    }

    @Override
    public void setDialog(FXMLDialog dialog) {
        this.splashScreenDialog = dialog;
    }
}
