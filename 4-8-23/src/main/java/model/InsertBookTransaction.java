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
public class InsertBookTransaction extends Transaction
{
    private Book book; // needed for GUI only

    // GUI Components

    private String transactionErrorMessage = "";

    /**
     * Constructor for this class.
     *
     *
     */
    //----------------------------------------------------------
    public InsertBookTransaction()
            throws Exception
    {
        super();
    }

    //----------------------------------------------------------
    protected void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("BookData", "TransactionError");
        dependencies.setProperty("OK", "CancelTransaction");

        myRegistry.setDependencies(dependencies);
    }

    /**
     * This method encapsulates all the logic of creating the account,
     * verifying ownership, crediting, etc. etc.
     */
    //----------------------------------------------------------
    public void processTransaction(Properties props) {
        try
        {
            book = new Book(props);
            book.update();
        }
        catch (Exception ex)
        {
            transactionErrorMessage = "Error in inserting book.";
            new Event(Event.getLeafLevelClassName(this), "processTransaction",
                    "Failed to insert a book.",
                    Event.ERROR);

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
        if (key.equals("UpdateStatusMessage") == true)
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
        if (key.equals("BookData") == true)
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
        Scene currentScene = myViews.get("InsertBookView");

        if (currentScene == null)
        {
            // create our new view
            View newView = ViewFactory.createView("InsertBookView", this);
            currentScene = new Scene(newView);
            myViews.put("InsertBookView", currentScene);

            return currentScene;
        }
        else
        {
            return currentScene;
        }
    }

    //------------------------------------------------------

}


