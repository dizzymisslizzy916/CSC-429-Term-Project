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
public class InsertTreeTypeView extends View
{

    // GUI components
    protected TextField typeDescription;
    protected TextField cost;
    protected TextField barcodePrefix;

    protected Button submitButton;
    protected Button doneButton;

    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public InsertTreeTypeView(IModel treeType)
    {
        super(treeType, "InsertTreeTypeView");

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

        Text titleText = new Text(" Register A Tree Type ");
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

        //Tree Type Description
        Text treeTypeDescription = new Text(" Type Description : ");
        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
        treeTypeDescription.setFont(myFont);
        treeTypeDescription.setWrappingWidth(150);
        treeTypeDescription.setTextAlignment(TextAlignment.RIGHT);
        grid.add(treeTypeDescription, 0, 1);

        typeDescription = new TextField();
        grid.add(typeDescription, 1, 1);

        //Tree Type Cost
        Text treeTypeCost = new Text(" Cost : ");
        treeTypeCost.setFont(myFont);
        treeTypeCost.setWrappingWidth(150);
        treeTypeCost.setTextAlignment(TextAlignment.RIGHT);
        grid.add(treeTypeCost, 0, 2);

        cost = new TextField();
        grid.add(cost, 1, 2);

        //Tree Type Barcode
        Text treeTypeBarCode = new Text(" Bar Code Prefix : ");
        treeTypeBarCode.setFont(myFont);
        treeTypeBarCode.setWrappingWidth(150);
        treeTypeBarCode.setTextAlignment(TextAlignment.RIGHT);
        grid.add(treeTypeBarCode, 0, 3);

        barcodePrefix = new TextField();
        grid.add(barcodePrefix, 1, 3);


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
                String typeDescriptionInput = typeDescription.getText();
                String costInput = cost.getText();
                String barcodePrefixInput = barcodePrefix.getText();
                System.out.println("Received desc: " + typeDescriptionInput +
                        "; cost: " + costInput + "; barcode prefix: " + barcodePrefixInput);

                //Check the typeDescription
                if ((typeDescriptionInput == null) || (typeDescriptionInput.length() == 0)) {
                    displayErrorMessage("Error: Please enter a valid Description");
                    return;
                }
                else if ((typeDescriptionInput.length() > 200)) {
                    displayErrorMessage("Maximum description length reached.");
                }

                //Check the cost
                if ((costInput == null) || (costInput.length() == 0)) {
                    displayErrorMessage("Error: Please enter a valid cost");
                    return;
                }
                else {
                    try {
                        Long.parseLong(costInput);
                    }
                    catch (Exception ex)
                    {
                        displayErrorMessage("ERROR: Cost must have only digits");
                        return;
                    }
                }

                //Check the barcode prefix

                if ((barcodePrefixInput == null) || (barcodePrefixInput.length() == 0)) {
                    displayErrorMessage("Error: Please enter a valid BarCode Prefix");
                    return;
                }
                else if (barcodePrefixInput.length() != 2) {
                    displayErrorMessage("ERROR: Please enter a 2-digit barcode prefix");
                    return;
                }
                else {
                    try {
                        Long.parseLong(barcodePrefixInput);
                    }
                    catch (Exception ex)
                    {
                        displayErrorMessage("ERROR: BarCode Prefix must have only digits");
                        return;
                    }
                }


                props.setProperty("typeDescription", typeDescriptionInput);
                props.setProperty("cost", costInput);
                props.setProperty("barcodePrefix", barcodePrefixInput);

                myModel.stateChangeRequest("TreeTypeData", props);
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


