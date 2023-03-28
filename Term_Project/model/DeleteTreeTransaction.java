// specify the package
package model;

// system imports
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.util.Properties;
import java.util.Vector;

// project imports
import event.Event;
import exception.InvalidPrimaryKeyException;

import userinterface.View;
import userinterface.ViewFactory;

//==============================================================
public class DeleteTreeTransaction extends Transaction
{

	protected DeleteTreeTransaction() throws Exception {
        super();
        //TODO Auto-generated constructor stub'
	System.out.println("Creating delete tree transaction");
    }
    private Tree myTree;

    // needed for GUI only

	// GUI Components

	private String transactionErrorMessage = "";
	private String treeDeleteStatusMessage = "Tree ";

	/**
	 * Constructor for this class.
	 *
	 *
	 */
	//----------------------------------------------------------

	//----------------------------------------------------------
	protected void setDependencies()
	{
		dependencies = new Properties();
        dependencies.setProperty("cancelTree", "CancelTransaction");
        dependencies.setProperty("DeleteTreeData", "TreeDeleteStatusMessage");

		myRegistry.setDependencies(dependencies);
	}


	//----------------------------------------------------------
	public void processTransaction(Properties props)
	{
        myTree = new Tree(props);
        myTree.delete();
	System.out.println("Tree deleted successfully");
        treeDeleteStatusMessage = (String)myTree.getState("TreeDeleteStatusMessage");
	createAndShowTreeBarCodeEntryView();
		
	}

	//-----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("TransactionError") == true)
		{
			return transactionErrorMessage;
		}
		else
		if (key.equals("DeleteTreeStatusMessage") == true)
		{
			return deleteTreeStatusMessage;
		}
		return null;		
	}

	//-----------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		// DEBUG System.out.println("DepositTransaction.sCR: key: " + key);

		if (key.equals("DoYourJob") == true)
		{
			doYourJob();
		}
		else
		if ((key.equals("DeleteScoutData") == true))
		{
			processTransaction((Properties)value);
		}

		myRegistry.updateSubscribers(key, this);
	}

	/**
	 * Create the view of this class. And then the super-class calls
	 * swapToView() to display the view in the frame
	 */
	//------------------------------------------------------
	protected Scene createView()
	{
		System.out.println("About to create insert scout view in Insert Scout Transaction");
		Scene currentScene = myViews.get("DeleteTreeConfirmView");

		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("DeleteTreeConfirmView", this);
			currentScene = new Scene(newView);
			myViews.put("DeleteTreeConfirmView", currentScene);

			return currentScene;
		}
		else
		{
			return currentScene;
		}
	}
	//---------------------------------------------------------------------
	protected void createAndShowTreeBarCodeEntryView()
	{

		// create our initial view
		View newView = ViewFactory.createView("TreeBarCodeEntryView", this);
		Scene newScene = new Scene(newView);

		myViews.put("TreeBarCodeEntryView", newScene);

		// make the view visible by installing it into the frame
		swapToView(newScene); 
	}

	
}

