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


public class TreeTypeBarCodeEntryView extends View
{

    protected TextField treeTypeBarCode;
    protected Button submitButton;
    protected Button doneButton;

    // For showing error message
    protected MessageView statusLog;

    public TreeTypeBarCodeEntryView(IModel treeType) //constructor
    {
        super(treeType, "TreeTypeBarCodeEntryView");

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

        myModel.subscribe("UpdateStatusMessage", this);
    }

    private Node createTitle()
    {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);

        Text titleText = new Text(" Find Your Tree Type ");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setWrappingWidth(300);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKGREEN);
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

        Text prompt = new Text("");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        Text label = new Text(" Tree Type Bar Code Prefix: ");
        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
        label.setFont(myFont);
        label.setWrappingWidth(150);
        label.setTextAlignment(TextAlignment.RIGHT);
        grid.add(label, 0, 1);

        treeTypeBarCode = new TextField();
        treeTypeBarCode.setEditable(true);
        treeTypeBarCode.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                System.out.println("ABC");
                myModel.stateChangeRequest("TreeTypeBarCodePrefixEntered", treeTypeBarCode.getText());
            }
        });
        grid.add(treeTypeBarCode, 1, 1);

        submitButton = new Button("Find Tree Type");
        submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        submitButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();

                String barcode = treeTypeBarCode.getText();
                //Scout temp = new Scout(props);
                // temp.update();
                // displayMessage("Insert Scout Successfully"); //Use stateChangeRequest method instead of temp
                myModel.stateChangeRequest("TreeTypeBarCodePrefixEntered", barcode);
            }
        });
        HBox doneCont = new HBox();
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

    protected MessageView createStatusLog(String initialMessage)
    {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    public void populateFields()
    {

    }

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