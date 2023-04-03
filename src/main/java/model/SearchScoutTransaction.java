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


/** The class containing the WithdrawTransaction for the ATM application */
//==============================================================
public class SearchScoutTransaction extends Transaction
{
    private Scout scout; // needed for GUI only

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
            System.out.println("I'm in process transaction");
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

        return null;
    }

    //-----------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {

        if (key.equals("DoYourJob") == true)
        {
            doYourJob();
        }
//        else
//        if (key.equals("BookData") == true)
//        {
//            processTransaction((Properties)value);
//        }
        else
        if (key.equals("ScoutSearch") == true)
        {
            processTransaction((String)value);
            createAndShowScoutCollectionView();
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


