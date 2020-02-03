package util;

import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * Created on 11-12-2016 at 21:29.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public class DialogUtil {
    public static Notifications showNotificaiton(String message, String title,Object owner ) {
        return Notifications.create()
                .hideAfter(Duration.millis(3000))
                .owner(owner)
                .text(message)
                .title(title);

    }
}
