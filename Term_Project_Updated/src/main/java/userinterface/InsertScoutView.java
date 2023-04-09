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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

// project imports
import impresario.IModel;
import model.*;
//import java.util.SimpleDateFormat;

/** The class containing the Account View  for the ATM application */
//==============================================================
public class InsertScoutView extends View
{

    // GUI components
    protected TextField lastName;
    protected TextField firstName;
    protected TextField middleName;

    protected TextField dateOfBirth;
    protected TextField phoneNumber;
    protected TextField email;
    protected TextField troopId;

    protected ComboBox status;

    protected Button submitButton;
    protected Button doneButton;

    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public InsertScoutView(IModel scout)
    {
        super(scout, "InsertScoutView");

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

        Text titleText = new Text(" Register A Scout ");
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

        //Scout first name
        Text scoutLastName = new Text(" Last Name : ");
        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
        scoutLastName.setFont(myFont);
        scoutLastName.setWrappingWidth(150);
        scoutLastName.setTextAlignment(TextAlignment.RIGHT);
        grid.add(scoutLastName, 0, 1);

        lastName = new TextField();
        grid.add(lastName, 1, 1);

        //Scout last name
        Text scoutFirstName = new Text(" First Name : ");
        scoutFirstName.setFont(myFont);
        scoutFirstName.setWrappingWidth(150);
        scoutFirstName.setTextAlignment(TextAlignment.RIGHT);
        grid.add(scoutFirstName, 0, 2);

        firstName = new TextField();
        grid.add(firstName, 1, 2);

        //Scout middle name
        Text scoutMiddleName = new Text(" Middle Name : ");
        scoutMiddleName.setFont(myFont);
        scoutMiddleName.setWrappingWidth(150);
        scoutMiddleName.setTextAlignment(TextAlignment.RIGHT);
        grid.add(scoutMiddleName, 0, 3);

        middleName = new TextField();
        grid.add(middleName, 1, 3);

        //Scout DOB

        Text scoutDateOfBirth = new Text(" Date Of Birth : ");
        scoutDateOfBirth.setFont(myFont);
        scoutDateOfBirth.setWrappingWidth(150);
        scoutDateOfBirth.setTextAlignment(TextAlignment.RIGHT);
        grid.add(scoutDateOfBirth, 0, 4);

        dateOfBirth = new TextField();
        grid.add(dateOfBirth, 1, 4);

        //Scout phone number

        Text scoutPhoneNumber = new Text(" Phone Number : ");
        scoutPhoneNumber.setFont(myFont);
        scoutPhoneNumber.setWrappingWidth(150);
        scoutPhoneNumber.setTextAlignment(TextAlignment.RIGHT);
        grid.add(scoutPhoneNumber, 0, 5);

        phoneNumber = new TextField();
        grid.add(phoneNumber, 1, 5);

        //Scout email
        Text scoutEmail = new Text(" Email : ");
        scoutEmail.setFont(myFont);
        scoutEmail.setWrappingWidth(150);
        scoutEmail.setTextAlignment(TextAlignment.RIGHT);
        grid.add(scoutEmail, 0, 6);

        email = new TextField();
        grid.add(email, 1, 6);

        //Scout troopId
        Text scoutTroopId = new Text(" Troop ID : ");
        scoutTroopId.setFont(myFont);
        scoutTroopId.setWrappingWidth(150);
        scoutTroopId.setTextAlignment(TextAlignment.RIGHT);
        grid.add(scoutTroopId, 0, 7);

        troopId = new TextField();
        grid.add(troopId, 1, 7);

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
                String lastNameInput = lastName.getText();
                String firstNameInput = firstName.getText();
                String middleNameInput = middleName.getText();
                String dateOfBirthInput = dateOfBirth.getText();
                String phoneNumberInput = phoneNumber.getText();
                String emailInput = email.getText();
                String troopIdInput = troopId.getText();

                //Email must contain @
                if((emailInput == null) || (emailInput.length() == 0)) {
                    displayMessage("ERROR: Please enter a valid email.");
                    return;
                }
                else if (!emailInput.contains("@")) {
                    displayErrorMessage("ERROR: Please enter a valid email /n in the form xxx@xxx.xxx");
                    return;
                }

                //TroopId is 5 digit
                if ((troopIdInput == null) || (troopIdInput.length() == 0)) {
                    displayErrorMessage("Error: Please enter a valid troopId");
                    return;
                }
                else if (troopIdInput.length() != 5) {
                    displayErrorMessage("ERROR: Please enter a 5-digit troopId");
                    return;
                }
                else {
                    try {
                        Long.parseLong(troopIdInput);
                    }
                    catch (Exception ex)
                    {
                        displayErrorMessage("ERROR: TroopId must have only digits");
                        return;
                    }
                }

                //Check Date of Birth
                if (!dateOfBirthInput.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    displayErrorMessage("ERROR: Date of Birth input is not valid /n should be in YYYY-MM-DD");
                    return;
                }

                //Phone Number Check

                if ((phoneNumberInput == null) || (phoneNumberInput.length() == 0)) {
                    displayErrorMessage("ERROR: Please enter a 10-digit phone number");
                    return;
                }
                else if (phoneNumberInput.length() != 10) {
                        displayErrorMessage("ERROR: Please enter a 10-digit phone number");
                        return;
                }
                else {
                        try {
                            Long.parseLong(phoneNumberInput);
                        }
                        catch (Exception ex)
                        {
                            displayErrorMessage("ERROR: Phone number must have only digits");
                            return;
                        }

                    }

                props.setProperty("lastName",lastNameInput);
                props.setProperty("firstName",firstNameInput);
                props.setProperty("middleName",middleNameInput);
                props.setProperty("dateOfBirth",dateOfBirthInput);
                props.setProperty("phoneNumber",phoneNumberInput);
                props.setProperty("email",emailInput);
                props.setProperty("troopId",troopIdInput);

                myModel.stateChangeRequest("ScoutData", props);

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


