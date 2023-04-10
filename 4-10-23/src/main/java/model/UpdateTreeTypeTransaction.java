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
public class UpdateTreeTypeTransaction extends Transaction
{
    private TreeType myTreeType;

    // GUI Components

    private String transactionErrorMessage = "";

    private TreeTypeCollection TreeTypeCollection;
    /**
     * Constructor for this class.
     *
     *
     */
    //----------------------------------------------------------
    public UpdateTreeTypeTransaction()
            throws Exception
    {
        super();
        TreeTypeCollection  = new TreeTypeCollection ();
    }

    //----------------------------------------------------------
    protected void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("TreeTypeData", "TransactionError");
        dependencies.setProperty("OK", "CancelTransaction");

        myRegistry.setDependencies(dependencies);
    }

    /**
     * This method encapsulates all the logic of creating the account,
     * verifying ownership, crediting, etc. etc.
     */
    //----------------------------------------------------------
    public void processTransaction(String barCode) {
        try {
            System.out.println("I'm in process transaction");
            TreeTypeCollection.findTreeTypeWithBarcodePrefixLike(barCode);
        }
        catch (Exception excep)
        {
            transactionErrorMessage = "ERROR: No TreeTypes found!";

        }
    }

    //-----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("TransactionError") == true)
        {
            return transactionErrorMessage;
        }
        else if (key.equals("TreeTypeList") == true)
        {
            return TreeTypeCollection;
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
        if (key.equals("TreeTypeData") == true)
        {
            processTransaction((String) value);
            UpdateForm();
        }

        myRegistry.updateSubscribers(key, this);
    }

    /**
     * Create the view of this class. And then the super-class calls
     * swapToView() to display the view in the stage
     */
    //------------------------------------------------------
    protected Scene createView()
    {
        Scene currentScene = myViews.get("TreeTypeBarCodeEntryView");

        if (currentScene == null)
        {
            // create our new view
            View newView = ViewFactory.createView("TreeTypeBarCodeEntryView", this);
            currentScene = new Scene(newView);
            myViews.put("TreeTypeBarCodeEntryView", currentScene);

            return currentScene;
        }
        else
        {
            return currentScene;
        }
    }

    //------------------------------------------------------
    protected void UpdateForm()
    {
        View v = ViewFactory.createView("UpdateTreeTypeView", this);
        Scene newScene = new Scene(v);

        swapToView(newScene);
    }
}


