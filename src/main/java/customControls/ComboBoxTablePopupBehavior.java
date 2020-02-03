package customControls;

import com.sun.javafx.scene.control.behavior.ComboBoxBaseBehavior;
import com.sun.javafx.scene.control.behavior.KeyBinding;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;

import static javafx.scene.input.KeyCode.*;
import static javafx.scene.input.KeyEvent.KEY_PRESSED;

/**
 * Created on 04-12-2016 at 15:09.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public class ComboBoxTablePopupBehavior<S> extends ComboBoxBaseBehavior {
    /**
     * @param comboBox
     */
    public ComboBoxTablePopupBehavior(ComboBoxBase comboBox) {

        super(comboBox, COMBOBOX_TABLE_POPUP_BINDINGS);
    }


    /***************************************************************************
     * *
     * Key event handling                                                      *
     * *
     **************************************************************************/

    protected static final List<KeyBinding> COMBOBOX_TABLE_POPUP_BINDINGS = new ArrayList<KeyBinding>();

    static {

        COMBOBOX_TABLE_POPUP_BINDINGS.addAll(COMBO_BOX_BASE_BINDINGS);
    }


}
