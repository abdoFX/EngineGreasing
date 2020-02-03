package util;

import java.sql.SQLException;

/**
 * Created on 14-12-2016 at 22:43.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public class ExceptionHandler {
    public static void handleSqlException(Exception e) {

        if (e instanceof SQLException) {
            switch (((SQLException) e).getErrorCode()) {
                case 1:
                    break;
                case 2:
                    break;
                default:
                    break;
            }
        }
    }
}
