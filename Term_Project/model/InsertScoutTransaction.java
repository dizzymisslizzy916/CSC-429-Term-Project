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
public class InsertScoutTransaction extends Transaction
{

	protected InsertScoutTransaction() throws Exception {
        super();
        //TODO Auto-generated constructor stub'
	System.out.println("Creating insert scout transaction");
    }
    private Scout myScout;

    // needed for GUI only

	// GUI Components

	private String transactionErrorMessage = "";
	private String scoutUpdateStatusMessage = "Scout ";

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
        dependencies.setProperty("cancelScout", "CancelTransaction");
        dependencies.setProperty("ScoutData", "ScoutUpdateStatusMessage");

		myRegistry.setDependencies(dependencies);
	}


	//----------------------------------------------------------
	public void processTransaction(Properties props)
	{
        myScout = new Scout(props);
        myScout.update();
	System.out.println("Scout inserted successfully");
        scoutUpdateStatusMessage = (String)myScout.getState("UpdateStatusMessage");
		
	}

	//-----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("TransactionError") == true)
		{
			return transactionErrorMessage;
		}
		else
		if (key.equals("ScoutUpdateStatusMessage") == true)
		{
			return scoutUpdateStatusMessage;
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
		if ((key.equals("ScoutData") == true))
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
		Scene currentScene = myViews.get("InsertScoutView");

		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("InsertScoutView", this);
			currentScene = new Scene(newView);
			myViews.put("InsertScoutView", currentScene);

			return currentScene;
		}
		else
		{
			return currentScene;
		}
	}

	
}

