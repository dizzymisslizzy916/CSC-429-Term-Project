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
public class SearchPatronTransaction extends Transaction
{
    private Patron patron; // needed for GUI only

    // GUI Components

    private String transactionErrorMessage = "";

    private PatronCollection patronCollection;

    /**
     * Constructor for this class.
     *
     *
     */
    //----------------------------------------------------------
    public SearchPatronTransaction()
            throws Exception
    {
        super();
        patronCollection = new PatronCollection();
    }

    //----------------------------------------------------------
    protected void setDependencies()
    {
        dependencies = new Properties();
        //dependencies.setProperty("PatronData", "TransactionError");
        dependencies.setProperty("OK", "CancelTransaction");
        dependencies.setProperty("PatronSearch","TransactionError");
        dependencies.setProperty("CancelSearchPatrons","CancelTransaction");

        myRegistry.setDependencies(dependencies);
    }

    /**
     * This method encapsulates all the logic of creating the account,
     * verifying ownership, crediting, etc. etc.
     */
    //----------------------------------------------------------
    public void processTransaction(String props) {
        try {
            patronCollection.findPatronsAtZipCode(props);
        }
        catch (Exception excep)
        {
            transactionErrorMessage = "ERROR: No patrons found!";

        }
    }

    //-----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("TransactionError") == true)
        {
            return transactionErrorMessage;
        }
        else if (key.equals("PatronList") == true)
        {
            return patronCollection;
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
//        if (key.equals("PatronData") == true)
//        {
//            processTransaction((Properties)value);
//        }
        else
        if (key.equals("PatronSearch") == true)
        {
            processTransaction((String)value);
            createAndShowPatronCollectionView();
        }

        myRegistry.updateSubscribers(key, this);
    }

    protected void createAndShowPatronCollectionView()
    {
        View v = ViewFactory.createView("PatronCollectionView", this);
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
        Scene currentScene = myViews.get("SearchPatronView");

        if (currentScene == null)
        {
            // create our new view
            View newView = ViewFactory.createView("SearchPatronView", this);
            currentScene = new Scene(newView);
            myViews.put("SearchPatronView", currentScene);

            return currentScene;
        }
        else
        {
            return currentScene;
        }
    }

    //------------------------------------------------------

}


