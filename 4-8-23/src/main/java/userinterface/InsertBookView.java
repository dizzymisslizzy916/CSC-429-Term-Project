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
public class InsertBookView extends View
{

    // GUI components
    protected TextField bookTitle;
    protected TextField author;
    protected TextField pubYear;
    protected ComboBox status;

    protected Button submitButton;
    protected Button doneButton;

    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public InsertBookView(IModel book)
    {
        super(book, "InsertBookView");

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

        Text titleText = new Text(" Insert A Book ");
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

        //Book title
        Text title = new Text(" Book Title : ");
        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
        title.setFont(myFont);
        title.setWrappingWidth(150);
        title.setTextAlignment(TextAlignment.RIGHT);
        grid.add(title, 0, 1);

        bookTitle = new TextField();
        grid.add(bookTitle, 1, 1);

        //Book author

        Text bookAuthor = new Text(" Author : ");
        bookAuthor.setFont(myFont);
        bookAuthor.setWrappingWidth(150);
        bookAuthor.setTextAlignment(TextAlignment.RIGHT);
        grid.add(bookAuthor, 0, 2);

        author = new TextField();
        grid.add(author, 1, 2);

        //Book publish

        Text publishYear = new Text(" Publish Year : ");
        publishYear.setFont(myFont);
        publishYear.setWrappingWidth(150);
        publishYear.setTextAlignment(TextAlignment.RIGHT);
        grid.add(publishYear, 0, 3);

        pubYear = new TextField();
        grid.add(pubYear, 1, 3);

        //Book status
        Text bookStatus = new Text(" Book Status : ");
        bookStatus.setFont(myFont);
        bookStatus.setWrappingWidth(150);
        bookStatus.setTextAlignment(TextAlignment.RIGHT);
        grid.add(bookStatus,0, 4);

        String statusGiven[] = {"Active", "Inactive"};
        status = new ComboBox(FXCollections
                .observableArrayList(statusGiven));
        grid.add(status,1, 4);

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
                String bookTitleInput = bookTitle.getText();
                String authorInput = author.getText();
                String pubYearInput = pubYear.getText();
                String statusInput = status.getValue().toString();

                props.setProperty("bookTitle",bookTitleInput);
                props.setProperty("author",authorInput);
                props.setProperty("pubYear",pubYearInput);
                props.setProperty("status",statusInput);

                Book temp = new Book(props);
                temp.update();
                displayMessage("Insert Book Successfully");



            }
        });
        doneCont.getChildren().add(submitButton);



        doneButton = new Button("Back");
        doneButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        doneButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                myModel.stateChangeRequest("BookCancelled", null);
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


