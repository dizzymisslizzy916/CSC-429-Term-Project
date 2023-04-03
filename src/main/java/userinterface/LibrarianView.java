// specify the package
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
import java.util.Properties;
import javafx.stage.Stage;

import java.util.Vector;
import java.util.Enumeration;

// project imports
import impresario.IModel;


//==============================================================

public class LibrarianView extends View {
    private Button insertNewScoutButton;
    private Button searchScoutsButton;
    private Button insertNewBookButton;
    private Button insertNewPatronButton;
    private Button searchBooksButton;
    private Button searchPatronsButton;
    private Button doneButton;
    protected MessageView statusLog;

    public LibrarianView(IModel librarian) //constructor
    {

        super(librarian, "LibrarianView");

        // create a container for showing the contents
        VBox container = new VBox(10);

        container.setPadding(new Insets(15, 5, 5, 5));

        // create a Node (Text) for showing the title
        container.getChildren().add(createTitle());

        // create a Node (GridPane) for showing data entry fields
        container.getChildren().add(createFormContents());

        // Error message area
        container.getChildren().add(createStatusLog("                          "));

        getChildren().add(container);

        populateFields();

        // STEP 0: Be sure you tell your model what keys you are interested in (not needed?)
        //  myModel.subscribe("LoginError", this);
    }

    private Node createTitle() {

        Text titleText = new Text("LIBRARY SYSTEM");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.BLACK);


        return titleText;
    }

    private VBox createFormContents() {

        VBox cont = new VBox();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        insertNewBookButton = new Button("Register A Scout");
        insertNewBookButton.setOnAction ((ActionEvent e) -> {
            myModel.stateChangeRequest("InsertScout", null);

        });
        grid.add(insertNewBookButton, 0, 0);

        insertNewPatronButton = new Button("Insert A Patron");
        insertNewPatronButton.setOnAction ((ActionEvent e) -> {

            myModel.stateChangeRequest("InsertPatron", null);

        });
        grid.add(insertNewPatronButton, 1, 0);

        searchBooksButton = new Button("Search Books");
        searchBooksButton.setOnAction ((ActionEvent e) -> {

            myModel.stateChangeRequest("SearchBook", null);

        });
        grid.add(searchBooksButton, 2, 0);

        searchScoutsButton = new Button("Search A Scout");
        searchScoutsButton.setOnAction ((ActionEvent e) -> {
            myModel.stateChangeRequest("SearchScout", null);

        });
        grid.add(searchScoutsButton, 3, 0);

        doneButton = new Button("Done");
        doneButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });
        //grid.add(doneButton, 2, 2);

        HBox doneCont = new HBox();
        doneCont.setAlignment(Pos.CENTER);
        cont.getChildren().add(grid);
        doneCont.getChildren().add(doneButton);
        cont.getChildren().add(doneCont);
        return cont;
    }

    // This method processes events generated from our GUI components.
    // Make the ActionListeners delegate to this method
    //-------------------------------------------------------------
    protected MessageView createStatusLog(String initialMessage) {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    protected void populateFields() {
        getEntryTableModelValues();
    }

    protected void getEntryTableModelValues() {
        //
    }


    /**
     * Display info message
     */
    //----------------------------------------------------------
    public void displayMessage(String message) {
        statusLog.displayMessage(message);
    }

    /**
     * Clear error message
     */
    //----------------------------------------------------------
    public void clearErrorMessage() {
        statusLog.clearErrorMessage();
    }

    public void processAction(Event evt) //how to handle event?
    {
        // DEBUG: System.out.println("LibrarianView.actionPerformed()");


        clearErrorMessage();
    }

        //----------------------------------------------------------


        //---------------------------------------------------------
    public void updateState(String key, Object value) //how to update state without login info or error message?
    {
        // STEP 6: Be sure to finish the end of the 'perturbation'
        // by indicating how the view state gets updated.
        if (key.equals("LoginError") == true) {
            // display the passed text
            displayMessage((String) value);
        }
    }

    }
