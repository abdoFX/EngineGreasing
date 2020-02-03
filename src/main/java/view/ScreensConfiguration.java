package view;/*
 * Copyright (c) 2012, Stephen Chin
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL STEPHEN CHIN OR ORACLE CORPORATION BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


import controller.*;
import controller.mainWindow.RightMenuController;
import controller.mainWindow.mController;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.h2.engine.Right;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import util.DialogController;
import util.FXMLDialog;

@Configuration
@Lazy
public class ScreensConfiguration {
    private Stage primaryStage;

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void showScreen(Parent screen) {

    }


    @Bean
    @Scope("prototype")
    public FXMLDialog splashScreen() {
        return new FXMLDialog(splashScreenController(), getClass().getResource("/ui/splashScreen.fxml"), primaryStage);
    }


    @Bean
    @Scope("prototype")
    public FXMLDialog mainWindow() {
        return new FXMLDialog(mainWindowController(), getClass().getResource("/ui/MainWindow.fxml"), primaryStage);
    }


    @Bean
    @Scope("prototype")
    public FXMLDialog base() {
        return new FXMLDialog(baseController(), getClass().getResource("/ui/Base.fxml"), primaryStage);
    }

    @Bean
    @Scope("prototype")
    public FXMLDialog zone() {
        return new FXMLDialog(zoneController(), getClass().getResource("/ui/Zone.fxml"), primaryStage);
    }

    @Bean
    @Scope("prototype")
    public FXMLDialog engine() {
        return new FXMLDialog(engineController(), getClass().getResource("/ui/Engine.fxml"), primaryStage);
    }

    @Bean
    @Scope("prototype")
    public FXMLDialog engineDialog() {
        return new FXMLDialog(engineDialogController(), getClass().getResource("/ui/EngineDialog.fxml"), primaryStage);
    }


    @Bean
    @Scope("prototype")
    public FXMLDialog lubrifiant() {
        return new FXMLDialog(lubrifiantController(), getClass().getResource("/ui/Lubrifiant.fxml"), primaryStage);
    }


    @Bean
    @Scope("prototype")
    public FXMLDialog workingTimeDialog() {
        return new FXMLDialog(workingTimeEntryController(), getClass().getResource("/ui/workingTimeEntryDialog.fxml"), primaryStage);
    }

    @Bean
    @Scope("prototype")
    public FXMLDialog maintenanceDialog() {
        return new FXMLDialog(maintenanceController(), getClass().getResource("/ui/MaintenanceDialog.fxml"), primaryStage);
    }

    @Bean
    @Scope("prototype")
    public FXMLDialog workingTimeDetails() {
        return new FXMLDialog(workingTimeDetailsController(), getClass().getResource("/ui/workingTimeDetails.fxml"), primaryStage);
    }

    @Bean
    @Scope("prototype")
    public FXMLDialog mainteanaceDetailsDialog() {
        return new FXMLDialog(maintenanceDetailsDialogController(), getClass().getResource("/ui/MaintenanceDetailsDialog.fxml"), primaryStage);
    }

    @Bean
    @Scope("prototype")
    public FXMLDialog mWindow() {
        return new FXMLDialog(mWindowController(), getClass().getResource("/ui/mainWindow/mWindow.fxml"), primaryStage);
    }

    @Bean
    @Scope("prototype")
    public FXMLDialog mainWidowRightMenu() {
        return new FXMLDialog(mainWindowRightMenu(), getClass().getResource("/ui/mainWindow/rightMenu.fxml"), primaryStage);
    }


    /* -- Controlelr */
    @Bean
    @Scope("prototype")
    public DialogController splashScreenController() {
        return new SplashScreenController();
    }


    @Bean
    @Scope("prototype")
    MainWindowController mainWindowController() {
        return new MainWindowController();
    }


    @Bean
    @Scope("prototype")
    BaseController baseController() {
        return new BaseController();
    }

    @Bean
    @Scope("prototype")
    ZoneController zoneController() {
        return new ZoneController();
    }

    @Bean
    @Scope("prototype")
    EngineController engineController() {
        return new EngineController();
    }

    @Bean
    @Scope("prototype")
    EngineDialogController engineDialogController() {
        return new EngineDialogController();
    }


    @Bean
    @Scope("prototype")
    LubrifiantController lubrifiantController() {
        return new LubrifiantController();
    }

    @Bean
    @Scope("prototype")
    WorkingTimeEntryController workingTimeEntryController() {
        return new WorkingTimeEntryController();
    }


    @Bean
    @Scope("prototype")
    MaintenanceController maintenanceController() {
        return new MaintenanceController();
    }

    @Bean
    @Scope("prototype")
    WorkingTimeDetailsController workingTimeDetailsController() {
        return new WorkingTimeDetailsController();
    }


    @Bean
    @Scope("prototype")
    MaintenanceDetailsDialogController maintenanceDetailsDialogController() {
        return new MaintenanceDetailsDialogController();
    }


    @Bean
    @Scope("prototype")
    mController mWindowController() {
        return new mController();
    }

    @Bean
    @Scope("prototype")
    RightMenuController mainWindowRightMenu() {
        return new RightMenuController();
    }


}
