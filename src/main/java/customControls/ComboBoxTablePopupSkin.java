package customControls;

import com.jfoenix.controls.JFXDatePicker;
import com.sun.javafx.scene.control.behavior.ComboBoxBaseBehavior;
import com.sun.javafx.scene.control.skin.BehaviorSkinBase;
import com.sun.javafx.scene.control.skin.ComboBoxPopupControl;
import javafx.beans.InvalidationListener;
import javafx.beans.WeakInvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.WeakListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.scene.AccessibleAttribute;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;
import model.Engine;

import java.awt.*;
import java.util.function.Predicate;

/**
 * Created on 03-12-2016 at 22:09.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public class ComboBoxTablePopupSkin<S> extends ComboBoxPopupControl {

    private ComboBoxTablePopup comboBoxTablePopup;
    private ObservableList<S> comboboxTablePopupItems;

    private ListCell<S> buttonCell;
    private Callback<ListView<S>, ListCell<S>> cellFactory;


    private TableView<S> tableViewPopupContent;

    private ObservableList<S> tableViewPopupItems;


    private Predicate<S> predicate;

    private final InvalidationListener itemsObserver;

    private final ListChangeListener<S> tableViewItemsListener = new ListChangeListener<S>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends S> c) {
            getSkinnable().requestLayout();
        }
    };

    private final WeakListChangeListener<S> weakListViewItemsListener =
            new WeakListChangeListener<S>(tableViewItemsListener);


    public ComboBoxTablePopupSkin(ComboBoxTablePopup comboBoxTablePopup) {
        super(comboBoxTablePopup, new ComboBoxTablePopupBehavior(comboBoxTablePopup));
        this.comboBoxTablePopup = comboBoxTablePopup;


        updateComboBoxTablePopupItems();


        itemsObserver = observable -> {
            updateComboBoxTablePopupItems();
            updateTableViewItems();

        };
        this.comboBoxTablePopup.itemsProperty().addListener(new WeakInvalidationListener(itemsObserver));

        tableViewPopupContent = createTableView();
        tableViewPopupContent.setManaged(false);

        getChildren().add(tableViewPopupContent);




        updateTableViewItems();


        registerChangeListener(comboBoxTablePopup.converterProperty(), "CONVERTER");
        registerChangeListener(comboBoxTablePopup.itemsProperty(), "ITEMS");
        registerChangeListener(comboBoxTablePopup.valueProperty(), "VALUE");
        registerChangeListener(comboBoxTablePopup.editorProperty(), "EDITABLE");


    }

    private void updateTableViewItems() {
        this.tableViewPopupItems = comboBoxTablePopup.getItems();
        this.tableViewPopupContent.setItems(this.tableViewPopupItems);

        if (tableViewPopupItems != null) {
            tableViewPopupItems.removeListener(weakListViewItemsListener);
        }

        this.tableViewPopupItems = comboboxTablePopupItems;
        tableViewPopupContent.setItems(tableViewPopupItems);

        if (tableViewPopupItems != null) {
            tableViewPopupItems.addListener(weakListViewItemsListener);
        }


        getSkinnable().requestLayout();
    }


    public void updateComboBoxTablePopupItems() {
        comboboxTablePopupItems = comboBoxTablePopup.getItems();
        comboboxTablePopupItems = comboboxTablePopupItems == null ? FXCollections.<S>emptyObservableList() : comboboxTablePopupItems;

    }

    private TableView<S> createTableView() {
        final TableView<S> tableView = new TableView<>();

            tableView.setId("table-view");
        tableView.setPrefWidth(comboBoxTablePopup.getMinWidth());
        //tableView.placeholderProperty().bind(comboBoxTablePopup.plac);
         tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableView.setFocusTraversable(false);

        for (TableColumn tblColumn : tableColumns()) {
            tableView.getColumns().add(tblColumn);
        }

        tableView.getSelectionModel().selectedItemProperty().addListener(o -> {
            S selectedItem = tableView.getSelectionModel().getSelectedItem();
            comboBoxTablePopup.setValue(selectedItem);

        });





        tableView.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER ||
                    e.getCode() == KeyCode.ESCAPE ||
                    e.getCode() == KeyCode.SPACE) {

                S selectedItem = tableViewPopupContent.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {

                    System.out.println(selectedItem.toString());
                    comboBoxTablePopup.setValue(selectedItem);
                    //displayNode.setText(selectedItem.toString());
                    comboBoxTablePopup.hide();
                    e.consume();


                }
                //comboBoxTablePopup.setValue(selectedItem);
                // comboBoxTablePopup.setValue(selectedItem);

                // comboBoxTablePopup.hide();
            }
        });


        return tableView;
    }

    private ObservableList<TableColumn> tableColumns() {
        return ((ComboBoxTablePopup) getSkinnable()).getColumns();
    }

    @Override
    protected Node getPopupContent() {
        return this.tableViewPopupContent;
    }

    @Override
    protected TextField getEditor() {
        return ((ComboBoxTablePopup) getSkinnable()).getEditor();
    }

    @Override
    protected StringConverter<S> getConverter() {
        return ((ComboBoxTablePopup) getSkinnable()).getConverter();
    }

    @Override
    public Node getDisplayNode() {

        Node displayNode;
        if (comboBoxTablePopup.isEditable()) {
            displayNode = getEditableInputNode();
        } else {
            displayNode = buttonCell;
        }

        updateDisplayNode();

        return displayNode;

    }

    @Override
    protected void handleControlPropertyChanged(String p) {



      /*  if ("ITEMS".equals(p)) {
            System.out.println("Text peroptery");
            updateComboBoxTablePopupItems();
            updateTableViewItems();
        } else if ("EDITABLE".equals(p)) {
            getEditableInputNode();
            System.out.println("Editable  peroptery");
        }   else if ("CONVERTER".equals(p)) {
            updateDisplayNode();
            System.out.println("Conveter  peroptery");
        } else if ("EDITOR".equals(p)) {
            getEditableInputNode();
            System.out.println("Editor  peroptery");
        }else{
            super.handleControlPropertyChanged(p);
        }*/
        if ("VALUE".equals(p)) {

            updateDisplayNode();
            System.out.println(comboBoxTablePopup.getValue());

        } else if ("CONVERTER".equals(p)) {
            updateDisplayNode();
            System.out.println("Conveter  peroptery");
        } else if ("ITEMS".equals(p)) {
            System.out.println("Text peroptery");
            updateComboBoxTablePopupItems();
            updateTableViewItems();
        } else if ("EDITOR".equals(p)) {
            getEditableInputNode();
        } else
            super.handleControlPropertyChanged(p);
    }

    @Override
    protected void focusLost() {

    }


    public void syncWithAutoUpdate() {
        if (!getPopup().isShowing() && comboBoxTablePopup.isShowing()) {
            // Popup was dismissed. Maybe user clicked outside or typed ESCAPE.
            // Make sure DatePicker button is in sync.
            comboBoxTablePopup.hide();
        }
    }
}
