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

// project imports
import impresario.IModel;

/** The class containing the Transaction Choice View  for the ATM application */
//==============================================================
public class TransactionChoiceView extends View
{

	// other private data
	private final int labelWidth = 120;
	private final int labelHeight = 25;

	// GUI components

	private Button depositButton;
	private Button withdrawButton;
	private Button transferButton;
	private Button balanceInquiryButton;
	private Button imposeServiceChargeButton;

	private Button cancelButton;

	private MessageView statusLog;

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public TransactionChoiceView(IModel teller)
	{
		super(teller, "TransactionChoiceView");

		// create a container for showing the contents
		VBox container = new VBox(10);
		container.setPadding(new Insets(15, 5, 5, 5));

		// Add a title for this panel
		container.getChildren().add(createTitle());
		
		// how do you add white space?
		container.getChildren().add(new Label(" "));

		// create our GUI components, add them to this Container
		container.getChildren().add(createFormContents());

		container.getChildren().add(createStatusLog("             "));

		getChildren().add(container);

		populateFields();

		myModel.subscribe("TransactionError", this);
	}

	// Create the labels and fields
	//-------------------------------------------------------------
	private VBox createTitle()
	{
		VBox container = new VBox(10);
		Text titleText = new Text("       Brockport Tree Lot         ");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleText.setWrappingWidth(300);
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.DARKGREEN);
		container.getChildren().add(titleText);

		Text inquiryText = new Text("What do you wish to do today?");
		inquiryText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		inquiryText.setWrappingWidth(300);
		inquiryText.setTextAlignment(TextAlignment.CENTER);
		inquiryText.setFill(Color.BLACK);
		container.getChildren().add(inquiryText);
	
		return container;
	}


	// Create the navigation buttons
	//-------------------------------------------------------------
	private VBox createFormContents()
	{

		VBox container = new VBox(15);

		// create the buttons, listen for events, add them to the container
		HBox dCont = new HBox(10);
		dCont.setAlignment(Pos.CENTER);
		depositButton = new Button("AddScout");
		depositButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		depositButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		     	myModel.stateChangeRequest("AddScout", null);
            	     }
        	});
		dCont.getChildren().add(depositButton);

		container.getChildren().add(dCont);

		HBox wCont = new HBox(10);
		wCont.setAlignment(Pos.CENTER);
		withdrawButton = new Button("AddTree");
		withdrawButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		withdrawButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		     	myModel.stateChangeRequest("AddTree", null);
            	     }
        	});
		wCont.getChildren().add(withdrawButton);

		container.getChildren().add(wCont);

		HBox wCont = new HBox(10);
		wCont.setAlignment(Pos.CENTER);
		withdrawButton = new Button("AddTreeType");
		withdrawButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		withdrawButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("AddTreeType", null);
			}
		});
		wCont.getChildren().add(withdrawButton);

		container.getChildren().add(wCont);

		HBox wCont = new HBox(10);
		wCont.setAlignment(Pos.CENTER);
		withdrawButton = new Button("UpdateTreeType");
		withdrawButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		withdrawButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("UpdateTreeType", null);
			}
		});
		wCont.getChildren().add(withdrawButton);

		container.getChildren().add(wCont);

		HBox wCont = new HBox(10);
		wCont.setAlignment(Pos.CENTER);
		withdrawButton = new Button("UpdateScout");
		withdrawButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		withdrawButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("UpdateScout", null);
			}
		});
		wCont.getChildren().add(withdrawButton);

		container.getChildren().add(wCont);

		HBox wCont = new HBox(10);
		wCont.setAlignment(Pos.CENTER);
		withdrawButton = new Button("SellTree");
		withdrawButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		withdrawButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("SellTree", null);
			}
		});
		wCont.getChildren().add(withdrawButton);

		container.getChildren().add(wCont);

		HBox wCont = new HBox(10);
		wCont.setAlignment(Pos.CENTER);
		withdrawButton = new Button("RemoveTree");
		withdrawButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		withdrawButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("RemoveTree", null);
			}
		});
		wCont.getChildren().add(withdrawButton);

		container.getChildren().add(wCont);

		HBox wCont = new HBox(10);
		wCont.setAlignment(Pos.CENTER);
		withdrawButton = new Button("RemoveScout");
		withdrawButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		withdrawButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("RemoveScout", null);
			}
		});
		wCont.getChildren().add(withdrawButton);

		container.getChildren().add(wCont);

		HBox tCont = new HBox(10);
		tCont.setAlignment(Pos.CENTER);
		transferButton = new Button("StartShift");
		transferButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		transferButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		     	myModel.stateChangeRequest("StartShift", null);
            	     }
        	});
		tCont.getChildren().add(transferButton);

		container.getChildren().add(tCont);

		HBox biCont = new HBox(10);
		biCont.setAlignment(Pos.CENTER);
		balanceInquiryButton = new Button("EndShift");
		balanceInquiryButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		balanceInquiryButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		     	myModel.stateChangeRequest("EndShift", null);
            	     }
        	});
		biCont.getChildren().add(balanceInquiryButton);

		container.getChildren().add(biCont);

		HBox doneCont = new HBox(10);
		doneCont.setAlignment(Pos.CENTER);
		cancelButton = new Button("Logout");
		cancelButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		     	myModel.stateChangeRequest("Logout", null);    
            	     }
        	});
		doneCont.getChildren().add(cancelButton);

		container.getChildren().add(doneCont);

		return container;
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

	}
	

	//---------------------------------------------------------
	public void updateState(String key, Object value)
	{
		if (key.equals("TransactionError") == true)
		{
			// display the passed text
			displayErrorMessage((String)value);
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
	 * Clear error message
	 */
	//----------------------------------------------------------
	public void clearErrorMessage()
	{
		statusLog.clearErrorMessage();
	}
}

