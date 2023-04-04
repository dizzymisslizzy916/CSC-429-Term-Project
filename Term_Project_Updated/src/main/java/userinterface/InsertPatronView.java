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
public class InsertPatronView extends View
{

    // GUI components
    protected TextField name;
    protected TextField address;
    protected TextField city;
    protected TextField stateCode;
    protected TextField zip;
    protected TextField email;
    protected TextField dateOfBirth;
    protected ComboBox status;

    protected Button submitButton;
    protected Button doneButton;

    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public InsertPatronView(IModel patron)
    {
        super(patron, "InsertPatronView");

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

        Text titleText = new Text(" Insert A Patron ");
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

        //Patron Name
        Text patronName = new Text(" Patron Name : ");
        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
        patronName.setFont(myFont);
        patronName.setWrappingWidth(150);
        patronName.setTextAlignment(TextAlignment.RIGHT);
        grid.add(patronName, 0, 1);

        name = new TextField();
        grid.add(name, 1, 1);

        //Patron address

        Text patronAddress = new Text(" Patron Address : ");
        patronAddress.setFont(myFont);
        patronAddress.setWrappingWidth(150);
        patronAddress.setTextAlignment(TextAlignment.RIGHT);
        grid.add(patronAddress, 0, 2);

        address = new TextField();
        grid.add(address, 1, 2);

        //Patron city

        Text patronCity = new Text(" Patron City : ");
        patronCity.setFont(myFont);
        patronCity.setWrappingWidth(150);
        patronCity.setTextAlignment(TextAlignment.RIGHT);
        grid.add(patronCity, 0, 3);

        city = new TextField();
        grid.add(city, 1, 3);

        //Patron stateCode

        Text patronStateCode = new Text(" Patron State Code : ");
        patronStateCode.setFont(myFont);
        patronStateCode.setWrappingWidth(150);
        patronStateCode.setTextAlignment(TextAlignment.RIGHT);
        grid.add(patronStateCode, 0, 4);

        stateCode = new TextField();
        grid.add(stateCode, 1, 4);

        //Patron zip

        Text patronZip = new Text(" Patron Zip : ");
        patronZip.setFont(myFont);
        patronZip.setWrappingWidth(150);
        patronZip.setTextAlignment(TextAlignment.RIGHT);
        grid.add(patronZip, 0, 5);

        zip = new TextField();
        grid.add(zip, 1, 5);

        //Patron email

        Text patronEmail = new Text(" Patron Email : ");
        patronEmail.setFont(myFont);
        patronEmail.setWrappingWidth(150);
        patronEmail.setTextAlignment(TextAlignment.RIGHT);
        grid.add(patronEmail, 0, 6);

        email = new TextField();
        grid.add(email, 1, 6);

        //Patron date of birth

        Text patronDateOfBirth = new Text(" Patron Date of Birth : ");
        patronDateOfBirth.setFont(myFont);
        patronDateOfBirth.setWrappingWidth(150);
        patronDateOfBirth.setTextAlignment(TextAlignment.RIGHT);
        grid.add(patronDateOfBirth, 0, 7);

        dateOfBirth = new TextField();
        grid.add(dateOfBirth, 1, 7);

        //Patron status
        Text patronStatus = new Text(" Patron Status : ");
        patronStatus.setFont(myFont);
        patronStatus.setWrappingWidth(150);
        patronStatus.setTextAlignment(TextAlignment.RIGHT);
        grid.add(patronStatus,0, 8);

        String statusGiven[] = {"Active", "Inactive"};
        status = new ComboBox(FXCollections
                .observableArrayList(statusGiven));
        grid.add(status,1, 8);

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
                String nameInput = name.getText();
                String addressInput = address.getText();
                String cityInput = city.getText();
                String stateCodeInput = stateCode.getText();
                String zipInput = zip.getText();
                String emailInput = email.getText();
                String dateOfBirthInput = dateOfBirth.getText();
                String statusInput = status.getValue().toString();

                props.setProperty("name",nameInput);
                props.setProperty("address",addressInput);
                props.setProperty("city",cityInput);
                props.setProperty("stateCode",stateCodeInput);
                props.setProperty("zip",zipInput);
                props.setProperty("email",emailInput);
                props.setProperty("dateOfBirth",dateOfBirthInput);
                props.setProperty("status",statusInput);

                Patron temp = new Patron(props);
                temp.update();
                displayMessage("Insert Patron Successfully");



            }
        });
        doneCont.getChildren().add(submitButton);



        doneButton = new Button("Back");
        doneButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        doneButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                myModel.stateChangeRequest("PatronCancelled", null);
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


