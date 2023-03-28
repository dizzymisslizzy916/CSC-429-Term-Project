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
public class InsertTreeTransaction extends Transaction
{

	protected InsertTreeTransaction() throws Exception {
        super();
        //TODO Auto-generated constructor stub'
	System.out.println("Creating insert tree transaction");
    }
    private Tree myTree;

    // needed for GUI only

	// GUI Components

	private String transactionErrorMessage = "";
	private String treeUpdateStatusMessage = "Tree ";

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
        dependencies.setProperty("InsertTreeData", "TreeUpdateStatusMessage");

		myRegistry.setDependencies(dependencies);
	}


	//----------------------------------------------------------
	public void processTransaction(Properties props)
	{
        myTree = new Tree(props);
        myTree.update();
	System.out.println("Tree inserted successfully");
        treeUpdateStatusMessage = (String)myTree.getState("UpdateStatusMessage");
		
	}

	//-----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("TransactionError") == true)
		{
			return transactionErrorMessage;
		}
		else
		if (key.equals("TreeUpdateStatusMessage") == true)
		{
			return treeUpdateStatusMessage;
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
		if ((key.equals("InsertTreeData") == true))
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
		System.out.println("About to create insert tree view in Insert Tree Transaction");
		Scene currentScene = myViews.get("InsertTreeView");

		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("InsertTreeView", this);
			currentScene = new Scene(newView);
			myViews.put("InsertTreeView", currentScene);

			return currentScene;
		}
		else
		{
			return currentScene;
		}
	}

	
}

