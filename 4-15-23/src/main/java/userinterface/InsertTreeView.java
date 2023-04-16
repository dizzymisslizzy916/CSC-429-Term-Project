// specify the package
package userinterface;

// system imports
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.collections.*;
import java.util.*;

// project imports
import impresario.IModel;
import model.*;

/** The class containing the Account View  for the ATM application */
//==============================================================
public class InsertTreeView extends View
{

    // GUI components
    protected TextField barcodeVal;
    protected TextField Notes;
    protected ComboBox status;

    protected Button submitButton;
    protected Button doneButton;

    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public InsertTreeView(IModel tree)
    {
        super(tree, "InsertTreeView");

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // Add a title for this panel
        container.getChildren().add(createTitle());

        // create our GUI components, add them to this Container
        container.getChildren().add(createFormContent());

        container.getChildren().add(createStatusLog("             "));

        getChildren().add(container);

        populateFields();

        myModel.subscribe("TransactionError", this);
    }


    // Create the title container
    //-------------------------------------------------------------
    private Node createTitle()
    {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);

        Text titleText = new Text(" Add A Tree ");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setWrappingWidth(300);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKGREEN);
        container.getChildren().add(titleText);


        return container;
    }

    // Create the main form content
    //-------------------------------------------------------------
    private VBox createFormContent()
    {
        VBox vbox = new VBox(10);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text prompt = new Text("");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);

        Text type = new Text(" Barcode : ");
        type.setFont(myFont);
        type.setWrappingWidth(150);
        type.setTextAlignment(TextAlignment.RIGHT);
        grid.add(type, 0, 1);

        barcodeVal = new TextField();
        grid.add(barcodeVal, 1, 1);

        //Book publish

        Text treeNotes = new Text(" Notes : ");
        treeNotes.setFont(myFont);
        treeNotes.setWrappingWidth(150);
        treeNotes.setTextAlignment(TextAlignment.RIGHT);
        grid.add(treeNotes, 0, 2);

        Notes = new TextField();
        grid.add(Notes, 1, 2);


//        Text treeStatus = new Text(" Tree Status : ");
//        treeStatus.setFont(myFont);
//        treeStatus.setWrappingWidth(150);
//        treeStatus.setTextAlignment(TextAlignment.RIGHT);
//        grid.add(treeStatus,0, 3);
//
//        String statusGiven[] = {"Available", "Sold","Damaged"};
//        status = new ComboBox(FXCollections
//                .observableArrayList(statusGiven));
//        //status.setValue("Available");
//        grid.add(status,1, 3);

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
                String barcodeInput = barcodeVal.getText();
                String notesInput = Notes.getText();
                //String statusInput = status.getValue().toString();

                //Test validation barcode
                if ((barcodeInput == null) || (barcodeInput.length() == 0)) {
                    displayErrorMessage("Error: Please enter a valid barcode");
                    return;
                }
                else if (barcodeInput.length() != 5) {
                    displayErrorMessage("ERROR: Please enter a 5-digit barcode");
                    return;
                }
                else {
                    try {
                        Long.parseLong(barcodeInput);
                    }
                    catch (Exception ex)
                    {
                        displayErrorMessage("ERROR: Barcode must have only digits");
                        return;
                    }
                }


                props.setProperty("barCode",barcodeInput);
                props.setProperty("Notes",notesInput);

                myModel.stateChangeRequest("TreeData", props);


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
    public void populateFields()
    {

    }

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


