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
	private Button registerScout;
	private Button updateScout;
	private Button removeScout;
	private Button addTree;
	private Button updateTree;
	private Button removeTree;
	private Button addTreeType;
	private Button updateTreeType;
	private Button startShift;
	private Button endShift;
	private Button sellTree;

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
		Text titleText = new Text("       Brockport Tree Lot         ");
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
		HBox dCont = new HBox(10);
		dCont.setAlignment(Pos.CENTER);
		registerScout = new Button("Register A Scout");
		registerScout.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		registerScout.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		     	myModel.stateChangeRequest("RegisterScout", null);
            	     }
        	});
		dCont.getChildren().add(registerScout);

		container.getChildren().add(dCont);

		HBox wCont = new HBox(10);
		wCont.setAlignment(Pos.CENTER);
		updateScout = new Button("Update A Scout");
		updateScout.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		updateScout.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		     	myModel.stateChangeRequest("UpdateScout", null);
            	     }
        	});
		wCont.getChildren().add(updateScout);

		container.getChildren().add(wCont);

		HBox tCont = new HBox(10);
		tCont.setAlignment(Pos.CENTER);
		removeScout = new Button("Remove A Scout");
		removeScout.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		removeScout.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		     	myModel.stateChangeRequest("RemoveScout", null);
            	     }
        	});
		tCont.getChildren().add(removeScout);

		container.getChildren().add(tCont);

		HBox biCont = new HBox(10);
		biCont.setAlignment(Pos.CENTER);
		addTree = new Button("Add A Tree");
		addTree.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		addTree.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		     	myModel.stateChangeRequest("AddTree", null);
            	     }
        	});
		biCont.getChildren().add(addTree);
		container.getChildren().add(biCont);

		HBox iscCont = new HBox(10);
		iscCont.setAlignment(Pos.CENTER);
		updateTree = new Button("Update A Tree");
		updateTree.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		updateTree.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		     	 myModel.stateChangeRequest("UpdateTree", null);
            	     }
        	});

		iscCont.getChildren().add(updateTree);
		container.getChildren().add(iscCont);

		HBox rtCont = new HBox(10);
		rtCont.setAlignment(Pos.CENTER);
		removeTree = new Button("Remove A Tree");
		removeTree.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		removeTree.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("RemoveTree", null);
			}
		});

		rtCont.getChildren().add(removeTree);
		container.getChildren().add(rtCont);

		HBox attCont = new HBox(10);
		attCont.setAlignment(Pos.CENTER);
		addTreeType = new Button("Add A Tree Type");
		addTreeType.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		addTreeType.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("AddTreeType", null);
			}
		});

		attCont.getChildren().add(addTreeType);
		container.getChildren().add(attCont);

		HBox uttCont = new HBox(10);
		uttCont.setAlignment(Pos.CENTER);
		updateTreeType = new Button("Update A Tree Type");
		updateTreeType.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		updateTreeType.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("UpdateTreeType", null);
			}
		});

		uttCont.getChildren().add(updateTreeType);
		container.getChildren().add(uttCont);

		//Todo: add buttons startShift, endShift and sellTree

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

