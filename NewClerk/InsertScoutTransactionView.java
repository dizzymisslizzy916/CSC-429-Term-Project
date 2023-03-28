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
import javafx.scene.control.ComboBox;
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

import java.util.Properties;
import java.util.Vector;

// project imports
import impresario.IModel;

/** The class containing the Scout Transaction View  for the ATM application */
//==============================================================
public class InsertScoutTransactionView extends View
{

	// Model

	// GUI components
	private ComboBox<String> scoutNumbers;

	private Button submitButton;
	private Button cancelButton;

	// For showing error message
	private MessageView statusLog;

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public InsertScoutTransactionView(IModel trans)
	{
		super(trans, "InsertScoutTransactionView");

		// create a container for showing the contents
		VBox container = new VBox(10);
		container.setPadding(new Insets(15, 5, 5, 5));

		// create our GUI components, add them to this panel
		container.getChildren().add(createTitle());
		container.getChildren().add(createFormContent());

		// Error message area
		container.getChildren().add(createStatusLog("                          "));

		getChildren().add(container);

		populateFields();
	}


	// Create the label (Text) for the title
	//-------------------------------------------------------------
	private Node createTitle()
	{
		
		Text titleText = new Text("       Brockport Tree Lot         ");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.DARKGREEN);
		
		return titleText;
	}

	// Create the main data entry fields
	//-------------------------------------------------------------
	private VBox createFormContent()
	{
		VBox vbox = new VBox(10);

		GridPane grid = new GridPane();
        	grid.setAlignment(Pos.CENTER);
       		grid.setHgap(10);
        	grid.setVgap(10);
        	grid.setPadding(new Insets(25, 25, 25, 25));

		Label scoutLabel = new Label("New Scout : ");
		grid.add(scoutLabel, 0, 0);

		scoutNumbers = new ComboBox<String>();
		scoutNumbers.setMinSize(100, 20);
		grid.add(scoutNumbers, 1, 0);

		submitButton = new Button("Submit");
 		submitButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		     	clearErrorMessage(); 
			// do the Scout
			String selectedScoutNumber = scoutNumbers.getValue();
			processScoutNumber(selectedScoutNumber);  
            	     }
        	});

		cancelButton = new Button("Back");
 		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
			/**
			 * Process the Cancel button.
			 * The ultimate result of this action is that the transaction will tell the teller to
			 * to switch to the transaction choice view. BUT THAT IS NOT THIS VIEW'S CONCERN.
			 * It simply tells its model (controller) that the Scout transaction was canceled, and leaves it
			 * to the model to decide to tell the teller to do the switch back.
	 		*/
			//----------------------------------------------------------
       		     	clearErrorMessage();
			myModel.stateChangeRequest("CancelScout", null);   
            	     }
        	});

		HBox btnContainer = new HBox(100);
		btnContainer.setAlignment(Pos.CENTER);
		btnContainer.getChildren().add(submitButton);
		btnContainer.getChildren().add(cancelButton);

		vbox.getChildren().add(grid);
		vbox.getChildren().add(btnContainer);

		return vbox;
	}

	
	// Create the status log field
	//-------------------------------------------------------------
	private MessageView createStatusLog(String initialMessage)
	{
		statusLog = new MessageView(initialMessage);

		return statusLog;
	}

	//-------------------------------------------------------------
	public void populateFields()
	{
		Vector allScoutIDs = (Vector)myModel.getState("ScoutNumberList");

		if (allScoutIDs != null)
		{
			for (int cnt = 0; cnt < allScoutIDs.size(); cnt++)
			{
				String nextScoutID = (String)allScoutIDs.elementAt(cnt);
				scoutNumbers.getItems().add(nextScoutID);
			}

			if (scoutNumbers.getItems().size() > 0)
			{
				scoutNumbers.setValue(scoutNumbers.getItems().get(0));
			}
		}
	}

	
	/**
	 * Process scout number selected by user.
	 * Action is to pass this info on to the transaction object.
	 */
	//----------------------------------------------------------
	private void processScoutNumber(String scoutNumber)
	{
		Properties props = new Properties();
		props.setProperty("ScoutNumber", scoutNumber);
		myModel.stateChangeRequest("ScoutNumber", props);
	}


	/**
	 * Required by interface, but has no role here
	 */
	//---------------------------------------------------------
	public void updateState(String key, Object value)
	{

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
	 * Clear error message
	 */
	//----------------------------------------------------------
	public void clearErrorMessage()
	{
		statusLog.clearErrorMessage();
	}

}