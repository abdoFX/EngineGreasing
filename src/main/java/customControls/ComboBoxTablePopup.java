package customControls;


import com.google.common.collect.Multiset;
import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;
import com.sun.javafx.scene.control.skin.ComboBoxPopupControl;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.AccessibleAttribute;
import javafx.scene.control.*;
import javafx.util.StringConverter;


/**
 * Created on 03-12-2016 at 22:07.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public class ComboBoxTablePopup<S> extends ComboBoxBase {


    /***************************************************************************
     * *
     * Static properties and methods                                           *
     * *
     **************************************************************************/

    private static final String DEFAULT_STYLE_CLASS = "combobox-table-popup";

    private static <S> StringConverter<S> defaultStringConverter() {
        return new StringConverter<S>() {
            @Override
            public String toString(S t) {
                return t == null ? "" : t.toString();
            }

            @Override
            public S fromString(String string) {
                throw new UnsupportedOperationException();
            }
        };
    }


    private ObjectProperty<ObservableList<S>> items = new SimpleObjectProperty<ObservableList<S>>(this, "items");

    public final void setItems(ObservableList<S> value) {
        itemsProperty().set(value);
    }

    public final ObservableList<S> getItems() {
        return items.get();
    }

    public ObjectProperty<ObservableList<S>> itemsProperty() {
        return items;
    }


    public ObjectProperty<StringConverter<S>> converterProperty() {
        return converter;
    }

    private ObjectProperty<StringConverter<S>> converter =
            new SimpleObjectProperty<StringConverter<S>>(this, "converter", ComboBoxTablePopup.<S>defaultStringConverter());

    public final void setConverter(StringConverter<S> value) {
        converterProperty().set(value);
    }

    public final StringConverter<S> getConverter() {
        return converterProperty().get();
    }


    // Editor
    private ReadOnlyObjectWrapper<TextField> editor;
    public final TextField getEditor() {
        return editorProperty().get();
    }
    public final ReadOnlyObjectProperty<TextField> editorProperty() {
        if (editor == null) {
            editor = new ReadOnlyObjectWrapper<TextField>(this, "editor");
            editor.set(new ComboBoxListViewSkin.FakeFocusTextField());
        }
        return editor.getReadOnlyProperty();
    }


    private
    ObservableList<TableColumn<S, ?>> columns = FXCollections.observableArrayList();

    public ObservableList<TableColumn<S, ?>> getColumns() {
        return columns;
    }

    public void setColumns(ObservableList<TableColumn<S, ?>> columns) {
        this.columns = columns;
    }


    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/

    /**
     * Creates a default ComboboxTablePopup instance with an empty
     * {@link #itemsProperty() items} list and default
     * {@link #selectionModelProperty() selection model}.
     */


    public ComboBoxTablePopup() {
        this(FXCollections.<S>emptyObservableList());

    }


    /**
     * Creates a default ComboboxTablePopup instance with the provided items list and
     * a default {   selection model}.
     */

    public ComboBoxTablePopup(ObservableList<S> items) {
        setItems(items);
        getStyleClass().add(DEFAULT_STYLE_CLASS);
        setEditable(true);
        valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
        });



    }

    public ComboBoxTablePopup(ObservableList<S> items, ObservableList<TableColumn<S, ?>> columns) {
        this(items);
        this.columns = columns;

    }


    @Override
    protected Skin<?> createDefaultSkin() {
        return new ComboBoxTablePopupSkin<>(this);
    }



}
