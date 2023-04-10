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
public class SearchBookTransaction extends Transaction
{
    private Book book; // needed for GUI only

    // GUI Components

    private String transactionErrorMessage = "";
    private BookCollection bookCollection;

    /**
     * Constructor for this class.
     *
     *
     */
    //----------------------------------------------------------
    public SearchBookTransaction()
            throws Exception
    {
        super();
        bookCollection = new BookCollection();
    }

    //----------------------------------------------------------
    protected void setDependencies()
    {
        dependencies = new Properties();
//        dependencies.setProperty("BookData", "TransactionError");
        dependencies.setProperty("OK", "CancelTransaction");
        dependencies.setProperty("BookSearch","TransactionError");
        dependencies.setProperty("CancelSearchBooks","CancelTransaction");

        myRegistry.setDependencies(dependencies);
    }

    /**
     * This method encapsulates all the logic of creating the account,
     * verifying ownership, crediting, etc. etc.
     */
    //----------------------------------------------------------
    public void processTransaction(String props) {
        try {
            bookCollection.findBooksWithTitleLike(props);
        }
        catch (Exception excep)
        {
            System.out.println("Anh, Liz and their teacher are all stupid");
            transactionErrorMessage = "ERROR: No books found!";

        }
    }

    //-----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("TransactionError") == true)
        {
            return transactionErrorMessage;
        }
        else if (key.equals("BookList") == true)
        {
            return bookCollection;
        }

        return null;
    }

    //-----------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {
        System.out.println("SearchBookTransaction.sCR: key: " + key + "; value: " + value);
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
        if (key.equals("BookSearch") == true)
        {

            processTransaction((String)value);
            createAndShowBookCollectionView();
        }


        myRegistry.updateSubscribers(key, this);
    }

    //-----------------------------------------------------
    protected void createAndShowBookCollectionView()
    {
        View v = ViewFactory.createView("BookCollectionView", this);
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
        Scene currentScene = myViews.get("SearchBookView");

        if (currentScene == null)
        {
            // create our new view
            View newView = ViewFactory.createView("SearchBookView", this);
            currentScene = new Scene(newView);
            myViews.put("SearchBookView", currentScene);

            return currentScene;
        }
        else
        {
            return currentScene;
        }
    }

    //------------------------------------------------------

}


