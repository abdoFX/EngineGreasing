/**
 * Created on 22-11-2016 at 21:25.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import util.FXMLDialog;
import view.ScreensConfiguration;

public class EngineGreasing extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ApplicationContext context = new ClassPathXmlApplicationContext("/spring/config/beanLocations.xml");
        ScreensConfiguration screens = context.getBean(ScreensConfiguration.class);

        screens.setPrimaryStage(primaryStage);
        FXMLDialog dialog = screens.splashScreen();

        dialog.initStyle(StageStyle.UNDECORATED);

        dialog.show();




    }


}
