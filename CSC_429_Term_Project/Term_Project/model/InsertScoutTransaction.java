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
	public void processTransaction(Properties props) //sequence diagram steps 8-13
	{
		String troopId = props.getProperty("troopId");
		try
		{
			Scout s = new Scout(troopId);  //instantiate scoutinfo.troopId
			scoutUpdateStatusMessage = "ERROR: Scout with troopID: " + troopId + " already exists!"; 
		}
		catch (InvalidPrimaryKeyException ex) 
		{
			myScout = new Scout(props); //create(scout info)
			myScout.update(); //insert
			System.out.println("Scout inserted successfully"); //scout added
			scoutUpdateStatusMessage = (String)myScout.getState("UpdateStatusMessage");
		}
		
		
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
		Scene currentScene = myViews.get("ScoutInfoEntryView");

		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("ScoutInfoEntryView", this);
			currentScene = new Scene(newView);
			myViews.put("ScoutInfoEntryView", currentScene);

			return currentScene;
		}
		else
		{
			return currentScene;
		}
	}

	
}
