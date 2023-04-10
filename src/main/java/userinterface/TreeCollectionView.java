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
import model.Tree;
import model.TreeCollection;


public class TreeCollectionView extends View {

    protected TableView<TreeTableModel> tableOfTrees;
    protected Button cancelButton;

    protected MessageView statusLog;

    public TreeCollectionView(IModel wsc)
    {
        super(wsc, "TreeCollectionView");

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
        //System.out.println("getEntryTableModelValues: are you getting here, stupid?");
        ObservableList<TreeTableModel> tableData = FXCollections.observableArrayList();
        try
        {
            TreeCollection treeCollection = (TreeCollection)myModel.getState("TreeList");

            Vector entryList = (Vector)treeCollection.getState("Trees");
            Enumeration entries = entryList.elements();

            while (entries.hasMoreElements() == true)
            {
                Tree nextTree = (Tree)entries.nextElement();
                Vector<String> view = nextTree.getEntryListView();

                // add this list entry to the list
                TreeTableModel nextTableRowData = new TreeTableModel(view);
                tableData.add(nextTableRowData);

            }

            tableOfTrees.setItems(tableData);
        }
        catch (Exception e) {//SQLException e) {
            // Need to handle this exception
        }
    }

    private Node createTitle()
    {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);

        Text titleText = new Text(" BROCKPORT TREE ");
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

        Text prompt = new Text("LIST OF TREES");
        prompt.setWrappingWidth(350);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        tableOfTrees = new TableView<TreeTableModel>();
        tableOfTrees.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

//        TableColumn barCodeColumn = new TableColumn("Bar Code") ;
//        barCodeColumn.setMinWidth(200);
//        barCodeColumn.setCellValueFactory(
//                new PropertyValueFactory<TreeTableModel, String>("barCode"));

        TableColumn treeTypeColumn = new TableColumn("Tree Type") ;
        treeTypeColumn.setMinWidth(100);
        treeTypeColumn.setCellValueFactory(
                new PropertyValueFactory<TreeTableModel, String>("treeType"));

        TableColumn notesColumn = new TableColumn("Notes") ;
        notesColumn.setMinWidth(100);
        notesColumn.setCellValueFactory(
                new PropertyValueFactory<TreeTableModel, String>("Notes"));

        TableColumn statusColumn = new TableColumn("Status") ;
        statusColumn.setMinWidth(100);
        statusColumn.setCellValueFactory(
                new PropertyValueFactory<TreeTableModel, String>("status"));

        tableOfTrees.getColumns().addAll(
               treeTypeColumn, notesColumn, statusColumn);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(115, 150);
        scrollPane.setContent(tableOfTrees);

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
                myModel.stateChangeRequest("CancelSearchTrees", null);
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