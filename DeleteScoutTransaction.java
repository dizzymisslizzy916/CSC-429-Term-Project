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
public class DeleteScoutTransaction extends Transaction
{
	
	private Scout selectedScout;

	private ScoutCollection Scout;

	protected DeleteScoutTransaction() throws Exception {
        super();
	scoutCollection = new ScoutCollection();
        //TODO Auto-generated constructor stub'
	System.out.println("Creating delete scout transaction");
	}
    

    // needed for GUI only

	// GUI Components

	private String transactionErrorMessage = "";
	private String scoutDeleteStatusMessage = "Scout ";

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
		dependencies.setProperty("ScoutSearch", "TransactionError");
        	dependencies.setProperty("OK", "CancelTransaction");
        	dependencies.setProperty("CancelSearchScouts","CancelTransaction");

		myRegistry.setDependencies(dependencies);
	}


	//----------------------------------------------------------
	public void processTransaction(Properties props)
	{
		String statusInput = "Inactive";
		selectedScout.persistentState.setProperty("status", statusInput);

		selectedScout.update();
		
	}

	//-----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("TransactionError") == true)
		{
			return transactionErrorMessage;
		}
		else
		if (key.equals("ScoutDeleteStatusMessage") == true)
		{
			return scoutDeleteStatusMessage;
		}
		else if (key.equals("ScoutList") == true)
			return scoutCollection;
		}
		else if (key.equals("SelectedScout") == true) {
			return selectedScout;
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
		if (key.equals("ScoutSearch") == true)
		{
			processTransaction((String)value);
			createAndShowScoutCollectionView();
		}
		else if (key.equals("SelectedScout") == true) {
			selectedScout = scoutCollection.retrieveByTroopId((String)value);
			createAndShowUpdateScoutView();
		}
		else if (key.equals("DeleteScout") == true) {
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
		System.out.println("About to create search scout view in Delete Scout Transaction");
		Scene currentScene = myViews.get("SearchScoutView");

		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("SearchScoutView", this);
			currentScene = new Scene(newView);
			myViews.put("SearchScoutView", currentScene);

			return currentScene;
		}
		else
		{
			return currentScene;
		}
	}
	//---------------------------------------------------------------------
	protected void createAndShowDeleteScoutConfirmView()
	{

		View newView = ViewFactory.createView("DeleteScoutConfirmView", this);
		Scene newScene = new Scene(newView);

		myViews.put("DeleteScoutConfirmView", newScene);

		swapToView(newScene); 
	}
	protected void createAndShowScoutCollectionView()
	{

		View newView = ViewFactory.createView("ScoutCollectionView", this);
		Scene newScene = new Scene(newView);

		myViews.put("ScoutCollectionView", newScene);

		swapToView(newScene); 
	}

	
}

