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

/** The class containing the Transaction Choice View  for the Tree Lot Application */
//==============================================================
public class TransactionChoiceView extends View
{
	//GUI components
	private Button registerScoutButton;
	private Button updateOrRemoveScoutButton;
	private Button addTreeButton;
	private Button updateTreeButton;
	private Button removeTreeButton;
	private Button addTreeTypeButton;
	private Button updateTreeTypeButton;
	private Button startShiftButton;
	private Button endShiftButton;
	private Button sellTreeButton;

	private Button cancelButton;
	
	private MessageView statusLog;

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public TransactionChoiceView(IModel clerk)
	{
		super(clerk, "TransactionChoiceView");

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
		Text titleText = new Text("     Boy Scout Troop 209 Tree Sales         ");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleText.setWrappingWidth(300);
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.BLACK);
		container.getChildren().add(titleText);

		Text welcomeText = new Text("Welcome!");
		welcomeText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		welcomeText.setWrappingWidth(300);
		welcomeText.setTextAlignment(TextAlignment.CENTER);
		welcomeText.setFill(Color.BLACK);
		container.getChildren().add(welcomeText);

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
		HBox asCont = new HBox(10);
		asCont.setAlignment(Pos.CENTER);
		registerScout = new Button("Add Scout");
		registerScout.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		registerScout.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("Add Scout", null);
			}
		});
		asCont.getChildren().add(depositButton);

		container.getChildren().add(asCont);

		HBox atCont = new HBox(10);
		atCont.setAlignment(Pos.CENTER);
		addTreeButton = new Button("Add Tree");
		addTreeButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		addTreeButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("Add Tree", null);
			}
		});
		atCont.getChildren().add(withdrawButton);

		container.getChildren().add(atCont);

		HBox attCont = new HBox(10);
		attCont.setAlignment(Pos.CENTER);
		addTreeTypeButton = new Button("Add TreeType");
		addTreeTypeButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		addTreeTypeButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("Add TreeType", null);
			}
		});
		attCont.getChildren().add(withdrawButton);

		container.getChildren().add(attCont);

		HBox uttCont = new HBox(10);
		uttCont.setAlignment(Pos.CENTER);
		updateTreetypeButton = new Button("Update TreeType");
		updateTreetypeButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		updateTreetypeButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("Update TreeType", null);
			}
		});
		uttCont.getChildren().add(withdrawButton);

		container.getChildren().add(uttCont);

		HBox usCont = new HBox(10);
		usCont.setAlignment(Pos.CENTER);
		updateOrRemoveButton = new Button("Update/Remove Scout");
		updateOrRemoveButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		updateOrRemoveButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("Update/Remove Scout", null);
			}
		});
		usCont.getChildren().add(withdrawButton);

		container.getChildren().add(usCont);

		HBox stCont = new HBox(10);
		stCont.setAlignment(Pos.CENTER);
		sellTreeButton = new Button("Sell Tree");
		sellTreeButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		sellTreeButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("Sell Tree", null);
			}
		});
		stCont.getChildren().add(withdrawButton);

		container.getChildren().add(stCont);

		HBox rtCont = new HBox(10);
		rtCont.setAlignment(Pos.CENTER);
		removeTreeButton = new Button("Remove Tree");
		removeTreeButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		removeTreeButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("Remove Tree", null);
			}
		});
		rtCont.getChildren().add(withdrawButton);

		container.getChildren().add(rtCont);



		HBox ssCont = new HBox(10);
		ssCont.setAlignment(Pos.CENTER);
		startShiftButton = new Button("Start Shift");
		startShiftButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		startShiftButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("Start Shift", null);
			}
		});
		ssCont.getChildren().add(transferButton);

		container.getChildren().add(ssCont);

		HBox esCont = new HBox(10);
		esCont.setAlignment(Pos.CENTER);
		endShiftButton = new Button("End Shift");
		endShiftButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		endShiftButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("End Shift", null);
			}
		});
		esCont.getChildren().add(balanceInquiryButton);

		container.getChildren().add(esCont);

		HBox doneCont = new HBox(10);
		doneCont.setAlignment(Pos.CENTER);
		cancelButton = new Button("Exit");
		cancelButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("Exit", null);
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

