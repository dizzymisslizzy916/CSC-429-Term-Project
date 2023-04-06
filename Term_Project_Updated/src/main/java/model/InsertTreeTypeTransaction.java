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
public class InsertTreeTypeTransaction extends Transaction
{
    private TreeType myTreeType;

    // GUI Components

    private String transactionErrorMessage = "";

    /**
     * Constructor for this class.
     *
     *
     */
    //----------------------------------------------------------
    public InsertTreeTypeTransaction()
            throws Exception
    {
        super();
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
    public void processTransaction(Properties props) {
        String sentBPfix = props.getProperty("barcodePrefix");
        try
        {
            TreeType oldtreeType = new TreeType(sentBPfix);
            transactionErrorMessage = "ERROR: Tree type with barcode prefix: " + sentBPfix + " already exists";
        }
        catch (Exception ex)
        {
            myTreeType = new TreeType(props);
            myTreeType.update();
            transactionErrorMessage = (String)myTreeType.getState("UpdateStatusMessage");

        }
    }

    //-----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("TransactionError") == true)
        {
            return transactionErrorMessage;
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
            processTransaction((Properties)value);
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
        Scene currentScene = myViews.get("InsertTreeTypeView");

        if (currentScene == null)
        {
            // create our new view
            View newView = ViewFactory.createView("InsertTreeTypeView", this);
            currentScene = new Scene(newView);
            myViews.put("InsertTreeTypeView", currentScene);

            return currentScene;
        }
        else
        {
            return currentScene;
        }
    }

    //------------------------------------------------------

}


