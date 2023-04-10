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
import javafx.scene.control.*;
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

import java.util.Properties;
import java.util.Vector;
import java.util.Enumeration;

// project imports
import impresario.IModel;
import model.Tree;
import model.TreeCollection;

public class UpdateTreeView extends View{

    protected TextField treeType;
    protected TextField notes;
    protected ComboBox status;

    protected Button submitButton;
    protected Button doneButton;

    // For showing error message
    protected MessageView statusLog;

    public UpdateTreeView(IModel wsc)
    {
        super(wsc, "UpdateTreeView");

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
        //getEntryTableModelValues();
        //do something
    }

    private Node createTitle()
    {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);

        Text titleText = new Text(" TROOP 209 TREE SALES SYSTEM ");
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

        Text prompt = new Text("Updating Tree "); //add barcode here?
        prompt.setWrappingWidth(350);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        //tree type
        Text treeTypeLabel = new Text(" Tree Type : ");
        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
        treeTypeLabel.setFont(myFont);
        treeTypeLabel.setWrappingWidth(150);
        treeTypeLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(treeTypeLabel, 0, 1);

        treeType = new TextField();
        grid.add(treeType, 1, 1);

        //notes
        Text notesLabel = new Text(" Notes : ");
        notesLabel.setFont(myFont);
        notesLabel.setWrappingWidth(150);
        notesLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(notesLabel, 0, 2);

        notes = new TextField();
        grid.add(notes, 1, 2);

        Text treeStatus = new Text(" Tree Status : ");
        treeStatus.setFont(myFont);
        treeStatus.setWrappingWidth(150);
        treeStatus.setTextAlignment(TextAlignment.RIGHT);
        grid.add(treeStatus,0, 3);

        String statusGiven[] = {"Available", "Sold","Damaged"};
        status = new ComboBox(FXCollections
                .observableArrayList(statusGiven));
        status.setValue("Available");
        grid.add(status,1, 3);

        //Submit and Done button

        HBox doneCont = new HBox(10);
        doneCont.setAlignment(Pos.CENTER);

        submitButton = new Button("Submit");
        submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        submitButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();

                Properties props = new Properties();
                String treeTypeInput = treeType.getText();
                String notesInput = notes.getText();
                String statusInput = status.getValue().toString();

                props.setProperty("treeType", treeTypeInput);
                props.setProperty("Notes",notesInput);
                props.setProperty("Status", statusInput);

                Tree temp = new Tree(props);
                temp.update();
                //displayMessage("Tree Updated Successfully"); validation should come from model

                //myModel.stateChangeRequest("TreeData", props); use instead of temp?

            }
        });
        doneCont.getChildren().add(submitButton);

        doneButton = new Button("Back");
        doneButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        doneButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                myModel.stateChangeRequest("CancelTransaction", null);
            }
        });
        doneCont.getChildren().add(doneButton);

        vbox.getChildren().add(grid);
        vbox.getChildren().add(doneCont);

        return vbox;
    }

    // Create the status log field
    //-------------------------------------------------------------
    protected MessageView createStatusLog(String initialMessage)
    {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    //-------------------------------------------------------------

    /**
     * Update method
     */
    //---------------------------------------------------------
    public void updateState(String key, Object value)
    {

        if (key.equals("TransactionError") == true)
        {
            String val = (String)value;
            if ((val.startsWith("Err")) || (val.startsWith("ERR"))) {
                displayErrorMessage(val);
            }
            else {
                displayMessage(val);
            }
        }
    }

    /**
     * Display error message
     */
    //----------------------------------------------------------
    public void displayErrorMessage(String message)
    {
        statusLog.displayErrorMessage(message);
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

//---------------------------------------------------------------
//	Revision History:
//



