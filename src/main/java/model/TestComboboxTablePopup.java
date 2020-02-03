package model;

import com.sun.javafx.scene.control.behavior.ComboBoxBaseBehavior;
import com.sun.javafx.scene.control.behavior.KeyBinding;
import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;
import com.sun.javafx.scene.control.skin.ComboBoxPopupControl;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.WeakInvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.WeakListChangeListener;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


public class TestComboboxTablePopup extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        ComboBoxTablePopupControl<dataModel> comboBoxTablePopup = new ComboBoxTablePopupControl<>();
        TableColumn<dataModel, Integer> tcId = new TableColumn<>("Id");
        TableColumn<dataModel, String> tcName = new TableColumn<>("Name");
        tcId.setCellValueFactory(new PropertyValueFactory<dataModel, Integer>("id"));
        tcName.setCellValueFactory(new PropertyValueFactory<dataModel, String>("name"));

        comboBoxTablePopup.setColumns(FXCollections.observableArrayList(tcId, tcName));
        comboBoxTablePopup.setItems(FXCollections.observableArrayList(
                new dataModel(1, "Data Model object 1"),
                new dataModel(2, "Data Model object 2"),
                new dataModel(3, "Data Model object 3")
        ));

        VBox vBox = new VBox(new TextField("Abdoo"),comboBoxTablePopup);

        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.setWidth(400);
        primaryStage.setHeight(300);
        primaryStage.show();
    }


    public class dataModel {
        private int id;
        private String name;

        public dataModel(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

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


    private class ComboBoxTablePopupControl<S> extends ComboBoxBase {


        /***************************************************************************
         * *
         * Static properties and methods                                           *
         * *
         **************************************************************************/

        private static final String DEFAULT_STYLE_CLASS = "combobox-table-popup";


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
                new SimpleObjectProperty<StringConverter<S>>(this, "converter", defaultStringConverter());

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


        public ComboBoxTablePopupControl() {
            this(FXCollections.<S>emptyObservableList());

        }


        /**
         * Creates a default ComboboxTablePopup instance with the provided items list and
         * a default {   selection model}.
         */

        public ComboBoxTablePopupControl(ObservableList<S> items) {
            setItems(items);
            getStyleClass().add(DEFAULT_STYLE_CLASS);
            setEditable(true);
            valueProperty().addListener((observable, oldValue, newValue) -> {
                System.out.println(newValue);
            });


        }

        public ComboBoxTablePopupControl(ObservableList<S> items, ObservableList<TableColumn<S, ?>> columns) {
            this(items);
            this.columns = columns;

        }


        @Override
        protected Skin<?> createDefaultSkin() {
            return new ComboBoxTablePopupControlSkin<>(this);
        }


    }


    public class ComboBoxTablePopupControlSkin<S> extends ComboBoxPopupControl {

        private ComboBoxTablePopupControl comboBoxTablePopup;
        private ObservableList<S> comboboxTablePopupItems;

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


        public ComboBoxTablePopupControlSkin(ComboBoxTablePopupControl comboBoxTablePopup) {
            super(comboBoxTablePopup, new ComboBoxBaseBehavior(comboBoxTablePopup, null));
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


            getEditor().caretPositionProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.intValue() != 0 ) {
                    if (!comboBoxTablePopup.isShowing())
                        comboBoxTablePopup.show();

                } else
                    comboBoxTablePopup.hide();
            });
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
            tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            tableView.setFocusTraversable(false);

            for (TableColumn tblColumn : tableColumns()) {
                tableView.getColumns().add(tblColumn);
            }

            tableView.getSelectionModel().selectedItemProperty().addListener(o -> {
                S selectedItem = tableView.getSelectionModel().getSelectedItem();
                //comboBoxTablePopup.setValue(selectedItem);

            });


            tableView.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ENTER ||

                        e.getCode() == KeyCode.SPACE) {

                    S selectedItem = tableView.getSelectionModel().getSelectedItem();
                    comboBoxTablePopup.setValue(selectedItem);
                    comboBoxTablePopup.hide();
                }

            });


            return tableView;
        }

        private ObservableList<TableColumn> tableColumns() {
            return ((ComboBoxTablePopupControl) getSkinnable()).getColumns();
        }

        @Override
        protected Node getPopupContent() {
            return this.tableViewPopupContent;
        }

        @Override
        protected TextField getEditor() {
            return ((ComboBoxTablePopupControl) getSkinnable()).getEditor();
        }

        @Override
        protected StringConverter<S> getConverter() {
            return ((ComboBoxTablePopupControl) getSkinnable()).getConverter();
        }

        @Override
        public Node getDisplayNode() {

            Node displayNode;
            displayNode = getEditableInputNode();


            updateDisplayNode();
          return displayNode;

        }

        @Override
        protected void handleControlPropertyChanged(String p) {


            if ("VALUE".equals(p)) {
                  Object value = comboBoxTablePopup.getValue();
                   updateDisplayNode();
                comboBoxTablePopup.fireEvent(new ActionEvent());
            } else if ("CONVERTER".equals(p)) {
                updateDisplayNode();
                System.out.println("Conveter  peroptery");
            } else if ("ITEMS".equals(p)) {

                updateComboBoxTablePopupItems();
                updateTableViewItems();
            } else if ("EDITOR".equals(p)) {
                getEditableInputNode();
            } else
                super.handleControlPropertyChanged(p);
        }


    }


}
