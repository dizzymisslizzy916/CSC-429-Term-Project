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

import java.util.Properties;
import java.util.Vector;
import java.util.Enumeration;

// project imports
import impresario.IModel;
import model.*;

public class ShiftInfoEntryView extends View {

    protected TextField startDate;
    protected TextField startTime;
    protected TextField companionName;
    protected TextField companionHours;
    protected Button submitButton;
    protected Button doneButton;

    protected MessageView statusLog;

    public ShiftInfoEntryView (IModel shift){

        super(shift, "ShiftInfoEntryView");

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

    // Create the title container
    //-------------------------------------------------------------
    private Node createTitle()
    {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);

        Text titleText = new Text(" Enter Shift Information ");
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
        VBox vbox = new VBox(50);

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

        Text startDateLabel = new Text(" Start Date : ");
        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
        startDateLabel.setFont(myFont);
        startDateLabel.setWrappingWidth(150);
        startDateLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(startDateLabel, 0, 1);

        startDate = new TextField();
        grid.add(startDate, 1, 1);

        Text startTimeLabel = new Text(" Start Time : ");
        startTimeLabel.setFont(myFont);
        startTimeLabel.setWrappingWidth(150);
        startTimeLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(startTimeLabel, 0, 2);

        startTime = new TextField();
        grid.add(startTime, 1, 2);

        Text companionNameLabel = new Text(" Companion Name : ");
        companionNameLabel.setFont(myFont);
        companionNameLabel.setWrappingWidth(150);
        companionNameLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(companionNameLabel, 0, 3);

        companionName = new TextField();
        grid.add(companionName, 1, 3);

        Text companionHoursLabel = new Text(" Companion Hours : ");
        companionHoursLabel.setFont(myFont);
        companionHoursLabel.setWrappingWidth(150);
        companionHoursLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(companionHoursLabel, 0, 4);

        companionHours = new TextField();
        grid.add(companionHours, 1, 4);

        HBox doneCont = new HBox(10);
        doneCont.setAlignment(Pos.CENTER);

        submitButton = new Button("Submit");
        submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        submitButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();

                Properties props = new Properties();
                String startDateInput = startDate.getText();
                String startTimeInput = startTime.getText();
                String companionNameInput = companionName.getText();
                String companionHoursInput = companionHours.getText();

                //do some validation

                props.setProperty("startDate",startDateInput);
                props.setProperty("startTime",startTimeInput);
                props.setProperty("companionName",companionNameInput);
                props.setProperty("companionHours",companionHoursInput);

                myModel.stateChangeRequest("ShiftData", props);
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

