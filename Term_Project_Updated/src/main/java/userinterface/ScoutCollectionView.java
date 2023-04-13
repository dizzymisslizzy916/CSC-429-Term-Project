package userinterface;

// system imports
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.Vector;
import java.util.Enumeration;

// project imports
import impresario.IModel;
import model.Scout;
import model.ScoutCollection;


public class ScoutCollectionView extends View {

    protected TableView<ScoutTableModel> tableOfScouts;
    protected Button cancelButton;

    protected MessageView statusLog;

    public ScoutCollectionView(IModel wsc)
    {
        super(wsc, "ScoutCollectionView");

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // create our GUI components, add them to this panel
        container.getChildren().add(createTitle());
        container.getChildren().add(createFormContent());

        // Error message area
        container.getChildren().add(createStatusLog("                                            "));

        getChildren().add(container);

        populateFields();
    }

    protected void populateFields()
    {
        getEntryTableModelValues();
    }

    protected void getEntryTableModelValues()
    {
        ObservableList<ScoutTableModel> tableData = FXCollections.observableArrayList();
        try
        {
            ScoutCollection scoutCollection = (ScoutCollection)myModel.getState("ScoutList");
            System.out.println("Scout collection reference: " + scoutCollection);
            Vector entryList = (Vector)scoutCollection.getState("Scouts");
            System.out.println("Size of scout list retrieved: " + entryList.size());
            Enumeration entries = entryList.elements();

            while (entries.hasMoreElements() == true)
            {
                Scout nextScout = (Scout)entries.nextElement();
                Vector<String> view = nextScout.getEntryListView();

                // add this list entry to the list
                ScoutTableModel nextTableRowData = new ScoutTableModel(view);
                tableData.add(nextTableRowData);

            }

            tableOfScouts.setItems(tableData);
        }
        catch (Exception e) {//SQLException e) {
            System.out.println(e);
            e.printStackTrace();
            // Need to handle this exception
        }
    }

    private Node createTitle()
    {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);

        Text titleText = new Text(" BROCKPORT TREE SYSTEM ");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setWrappingWidth(300);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.BLACK);
        container.getChildren().add(titleText);

        return container;
    }

    private VBox createFormContent()
    {
        VBox vbox = new VBox(10);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text prompt = new Text("LIST OF SCOUTS");
        prompt.setWrappingWidth(350);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        tableOfScouts = new TableView<ScoutTableModel>();
        tableOfScouts.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


        TableColumn scoutLastNameColumn = new TableColumn("Last Name") ;
        scoutLastNameColumn.setMinWidth(200);
        scoutLastNameColumn.setCellValueFactory(
                new PropertyValueFactory<ScoutTableModel, String>("lastName"));

        TableColumn scoutFirstNameColumn = new TableColumn("First Name") ;
        scoutFirstNameColumn.setMinWidth(200);
        scoutFirstNameColumn.setCellValueFactory(
                new PropertyValueFactory<ScoutTableModel, String>("firstName"));

        TableColumn scoutMiddleNameColumn = new TableColumn("Middle Name") ;
        scoutMiddleNameColumn.setMinWidth(200);
        scoutMiddleNameColumn.setCellValueFactory(
                new PropertyValueFactory<ScoutTableModel, String>("middleName"));

        TableColumn scoutDateOfBirthColumn = new TableColumn("Date of Birth") ;
        scoutDateOfBirthColumn.setMinWidth(200);
        scoutDateOfBirthColumn.setCellValueFactory(
                new PropertyValueFactory<ScoutTableModel, String>("dateOfBirth"));

        TableColumn scoutPhoneNumberColumn = new TableColumn("Phone Number") ;
        scoutPhoneNumberColumn.setMinWidth(200);
        scoutPhoneNumberColumn.setCellValueFactory(
                new PropertyValueFactory<ScoutTableModel, String>("phoneNumber"));

        TableColumn scoutEmailColumn = new TableColumn("Email") ;
        scoutEmailColumn.setMinWidth(200);
        scoutEmailColumn.setCellValueFactory(
                new PropertyValueFactory<ScoutTableModel, String>("email"));

        TableColumn scoutTroopIdColumn = new TableColumn("Troop ID") ;
        scoutTroopIdColumn.setMinWidth(200);
        scoutTroopIdColumn.setCellValueFactory(
                new PropertyValueFactory<ScoutTableModel, String>("troopId"));

        TableColumn statusColumn = new TableColumn("Status") ;
        statusColumn.setMinWidth(100);
        statusColumn.setCellValueFactory(
                new PropertyValueFactory<ScoutTableModel, String>("status"));

        tableOfScouts.getColumns().addAll(/*scoutNumberColumn, */
                scoutLastNameColumn, scoutFirstNameColumn, scoutMiddleNameColumn, scoutDateOfBirthColumn,
                scoutPhoneNumberColumn, scoutEmailColumn, scoutTroopIdColumn, statusColumn);

        tableOfScouts.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event)
            {
                if (event.isPrimaryButtonDown() && event.getClickCount() >=2 ){
                    processScoutSelected();
                }
            }
        });

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(115, 150);
        scrollPane.setContent(tableOfScouts);

        cancelButton = new Button("Back");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                /**
                 * Process the Cancel button.
                 * Achieve low coupling by programming to the interface, not the implementation!
                 * The model changes views, not the view itself.
                 * This method informs the model that the inquiry is cancelled, and the model handles it.
                 */
                //----------------------------------------------------------
                clearErrorMessage();
                myModel.stateChangeRequest("CancelSearchScouts", null);
            }
        });

        HBox btnContainer = new HBox(100);
        btnContainer.setAlignment(Pos.CENTER);
        btnContainer.getChildren().add(cancelButton);

        vbox.getChildren().add(grid);
        vbox.getChildren().add(scrollPane);
        vbox.getChildren().add(btnContainer);

        return vbox;
    }

    //Try to do remove function

    protected void processScoutSelected()
    {
        System.out.println("Did you get here Anh?");
        ScoutTableModel selectedItem = tableOfScouts.getSelectionModel().getSelectedItem();
        String selectedTroopId = selectedItem.getTroopId();
        System.out.println("You here Anh?");
        myModel.stateChangeRequest("ScoutSelected", selectedTroopId);
    }

    public void updateState(String key, Object value)
    {
    }

    protected MessageView createStatusLog(String initialMessage)
    {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    /**
     * Display info message
     */
    //----------------------------------------------------------
    public void displayMessage(String message)
    {
        statusLog.displayMessage(message);
    }

    /**
     * Clear error message
     */
    //----------------------------------------------------------
    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }
}