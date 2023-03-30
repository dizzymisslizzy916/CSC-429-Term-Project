// specify the package
package userinterface;

// system imports
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
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;


import java.util.Properties;

// project imports
import impresario.IModel;
import impresario.*;
import event.*;

//==============================================================
public class InsertTreeView extends View
{

	// GUI components
	protected ComboBox<String> Status;
	protected TextField barCode;
	protected TextField treeType;
	protected TextField Notes;
	protected TextField dateStatusUpdated;
	protected Button doneButton;
	protected Button submitButton; 

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

		myModel.subscribe("TreeUpdateStatusMessage", this);
	}


	// Create the title container
	//-------------------------------------------------------------
	private Node createTitle()
	{
		HBox container = new HBox();
		container.setAlignment(Pos.CENTER);	

		Text titleText = new Text(" TREE SYSTEM ");
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
        
      	        Text prompt = new Text("BOOK INFORMATION");
       		prompt.setWrappingWidth(400);
        	prompt.setTextAlignment(TextAlignment.CENTER);
       	 	prompt.setFill(Color.BLACK);
        	grid.add(prompt, 0, 0, 2, 1);

		Text barCodeLabel = new Text(" Barcode : ");
		Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
		barCodeLabel.setFont(myFont);
		barCodeLabel.setWrappingWidth(150);
		barCodeLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(barCodeLabel, 0, 1);

		barCode = new TextField();
		barCode.setEditable(true);
		barCode.setOnAction(new EventHandler<ActionEvent>() {
	
			@Override
			public void handle(ActionEvent e) {
				processAction(e);
			}
		});
		grid.add(barCode, 1, 1);

		Text treeTypeLabel = new Text(" Tree Type : ");
		treeTypeLabel.setFont(myFont);
		treeTypeLabel.setWrappingWidth(150);
		treeTypeLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(treeTypeLabel, 0, 2);

		treeType = new TextField();
		treeType.setEditable(true);
		treeType.setOnAction(new EventHandler<ActionEvent>() {
	
			@Override
			public void handle(ActionEvent e) {
				processAction(e);
			}
		});
		grid.add(treeType, 1, 2);

		Text notesLabel = new Text(" Notes : ");
		notesLabel.setFont(myFont);
		notesLabel.setWrappingWidth(150);
		notesLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(notes, 0, 3);

		notes = new TextField();
		notes.setEditable(true);
		notes.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				processAction(e);
			}
		});

		grid.add(notes, 1, 3);

		Text dateStatusUpdatedLabel = new Text(" Date Status Updated : ");
		dateStatusUpdatedLabel.setFont(myFont);
		dateStatusUpdatedLabel.setWrappingWidth(150);
		dateStatusUpdatedLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(dateStatusUpdated, 0, 4);

		dateStatusUpdated = new TextField();
		dateStatusUpdated.setEditable(true);
		dateStatusUpdated.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				processAction(e);
			}
		});

		grid.add(dateStatusUpdated, 1, 4);

		Status = new ComboBox<String>();
		Status.setMinSize(100, 20);
		grid.add(Status, 1, 4);


		
        submitButton = new Button("Submit");
        submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        submitButton.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent e) {
                    try {
                        processAction(e);
                        MessageView doneMessage = new MessageView("Tree sucessfully added !");
                        doneMessage.displayMessage("Tree sucessfully added !");
                    }
                    catch (Exception f) {
                        MessageView errorMessageView = new MessageView("Tree could not be added try again.");
                        errorMessageView.displayErrorMessage("Invalid input or Procedure error");
                    }
                       clearErrorMessage();
                  }
            });
	
	doneButton = new Button("Done");
	doneButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
	doneButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
        /**
             * Process the Cancel button.
             * The ultimate result of this action is that the transaction will tell the teller to
             * to switch to the transaction choice view. BUT THAT IS NOT THIS VIEW'S CONCERN.
             * It simply tells its model (controller) that the deposit transaction was canceled, and leaves it
             * to the model to decide to tell the teller to do the switch back.
             */
            //----------------------------------------------------------
                        clearErrorMessage();
            myModel.stateChangeRequest("CancelTransaction", null);  } 
                });

	HBox btnContainer = new HBox(10);
	btnContainer.setAlignment(Pos.CENTER);
	btnContainer.getChildren().add(submitButton);
	btnContainer.getChildren().add(doneButton);

	vbox.getChildren().add(grid);
        vbox.getChildren().add(btnContainer);

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
		/*
		barCode.setText((String)myModel.getState("BarCode"));
		treeType.setText((String)myModel.getState("TreeType"));
		notes.setText((String)myModel.getState("Notes"));
		*/
		statusValue.setValue("Available");
		statusValue.getItems().add("Available");
		statusValue.getItems().add("Sold");
		statusValue.getItems().add("Damaged");
		
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
	//---------------------------------------------------------------------------------------
	public void processAction(Event evt)
    {
        String bc = barCode.getText();
	String type = treeType.getText();
	String note = notes.getText();
	String dsu = dateStatusUpdated.getText();



        if (bc.length() != 5) {
            displayErrorMessage("Invalid input");
        }
        else {
            Properties myProps = new Properties();
            myProps.setProperty("barCode", bc );
            myProps.setProperty("treeType", type);
            myProps.setProperty("notes", note);
	    System.out.println("About to send tree data");
            myModel.stateChangeRequest("InsertTreeData", myProps);
        }

    }

	@Override
	public void updateState(String key, Object value) {
		if (key.equals("TreeUpdateStatusMessage") == true)
		{
			String val = (String)value;
			if ((val.startsWith("Err")) || (val.startsWith("ERR")))
			   displayErrorMessage(val);
			else
			   displayMessage(val);
		}
	}

	/**
	 * Process account number and amount selected by user.
	 * Action is to pass this info on to the transaction object by
	 * calling the processTransaction method of the transaction.
	 */
}

//---------------------------------------------------------------
//	Revision History:
//


