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

import userinterface.ScoutTableModel;
import userinterface.View;
import userinterface.ViewFactory;


/** The class containing the WithdrawTransaction for the ATM application */
//==============================================================
public class SearchScoutTransaction extends Transaction
{
    private Scout selectedScout; // needed for GUI only

    // GUI Components

    private String transactionErrorMessage = "";
    private ScoutCollection scoutCollection;

    /**
     * Constructor for this class.
     *
     *
     */
    //----------------------------------------------------------
    public SearchScoutTransaction()
            throws Exception
    {
        super();
        scoutCollection = new ScoutCollection();
    }

    //----------------------------------------------------------
    protected void setDependencies()
    {
        dependencies = new Properties();
//        dependencies.setProperty("ScoutData", "TransactionError");
        dependencies.setProperty("OK", "CancelTransaction");
        dependencies.setProperty("ScoutSearch","TransactionError");
        dependencies.setProperty("CancelSearchScouts","CancelTransaction");

        myRegistry.setDependencies(dependencies);
    }

    /**
     * This method encapsulates all the logic of creating the account,
     * verifying ownership, crediting, etc. etc.
     */
    //----------------------------------------------------------
    public void processTransaction(String fName) {
        try {
            scoutCollection.findScoutsWithNameLike(fName);
        }
        catch (Exception excep)
        {
            transactionErrorMessage = "ERROR: No scouts found!";

        }
    }

    //-----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("TransactionError") == true)
        {
            return transactionErrorMessage;
        }
        else if (key.equals("ScoutList") == true)
        {
            return scoutCollection;
        }
        else if (key.equals("SelectedScout") == true) {
            return selectedScout;
        }
        else if (selectedScout != null) {
            Object val = selectedScout.getState(key);
            if (val != null) {
                return val;
            }
        }
        else if (scoutCollection != null) {
            Object val = scoutCollection.getState(key);
            if (val != null) {
                return val;
            }
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
        if (key.equals("ScoutSearch") == true)
        {
            processTransaction((String)value);
            createAndShowScoutCollectionView();
        }
        else if (key.equals("ScoutSelected") == true) {
            String scoutId = (String)value;
            selectedScout = scoutCollection.retrieve(scoutId);
            //DO SOMETHING TO UPDATE THE SCOUT
        }


        myRegistry.updateSubscribers(key, this);
    }


    //-----------------------------------------------------
    protected void createAndShowScoutCollectionView()
    {
        View v = ViewFactory.createView("ScoutCollectionView", this);
        Scene newScene = new Scene(v);

        swapToView(newScene);
    }



    /**
     * Create the view of this class. And then the super-class calls
     * swapToView() to display the view in the stage
     */
    //------------------------------------------------------
    protected Scene createView()
    {
        Scene currentScene = myViews.get("SearchScoutView");

        if (currentScene == null)
        {
            // create our new view
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

    //------------------------------------------------------

}


