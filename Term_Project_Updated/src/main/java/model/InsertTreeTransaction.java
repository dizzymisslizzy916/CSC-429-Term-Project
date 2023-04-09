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
public class InsertTreeTransaction extends Transaction
{
    private Tree myTree; // needed for GUI only

    // GUI Components

    private String transactionErrorMessage = "";

    /**
     * Constructor for this class.
     *
     *
     */
    //----------------------------------------------------------
    public InsertTreeTransaction()
            throws Exception
    {
        super();
    }

    //----------------------------------------------------------
    protected void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("TreeData", "TransactionError");
        dependencies.setProperty("OK", "CancelTransaction");

        myRegistry.setDependencies(dependencies);
    }

    /**
     * This method encapsulates all the logic of creating the account,
     * verifying ownership, crediting, etc. etc.
     */
    //----------------------------------------------------------
    public void processTransaction(Properties props) {
        String barcodeVal = props.getProperty("barCode");
        String barcodePrefix = barcodeVal.substring(0,2);
        try {
            TreeType tt = new TreeType(barcodePrefix);
            String treeTypeId = (String)tt.getState("treeTypeId");
            props.setProperty("treeType", treeTypeId);
            try {
                Tree t = new Tree(barcodeVal);
                transactionErrorMessage = "ERROR: Tree with barcode: " + barcodeVal + " already exists!";
            }
            catch (InvalidPrimaryKeyException except)
            {
                myTree = new Tree(props);
                myTree.setOldFlag(false);
                myTree.update();
                transactionErrorMessage = (String)myTree.getState("UpdateStatusMessage");
            }
        }
        catch (InvalidPrimaryKeyException excep)
        {
            System.out.println(excep);
            transactionErrorMessage = "ERROR: Invalid barcode - no associated tree type found!";
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
        if (key.equals("TreeData") == true)
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
        Scene currentScene = myViews.get("InsertTreeView");

        if (currentScene == null)
        {
            // create our new view
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

    //------------------------------------------------------

}


