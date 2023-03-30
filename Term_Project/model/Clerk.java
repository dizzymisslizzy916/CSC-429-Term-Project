// specify the package
package model;

// system imports
import java.util.Hashtable;
import java.util.Properties;

import javafx.stage.Stage;
import javafx.scene.Scene;

// project imports
import impresario.IModel;
import impresario.ISlideShow;
import impresario.IView;
import impresario.ModelRegistry;

import exception.InvalidPrimaryKeyException;
import exception.PasswordMismatchException;
import event.Event;
import userinterface.MainStageContainer;
import userinterface.View;
import userinterface.ViewFactory;
import userinterface.WindowPosition;

public class Clerk implements IView, IModel{

    // For Impresario
    private Properties dependencies;
    private ModelRegistry myRegistry;

    // GUI Components
    private Hashtable<String, Scene> myViews;
    private Stage	  	myStage;

    private String loginErrorMessage = "";
    private String transactionErrorMessage = "";

    public Clerk() //constructor
    {
        myStage = MainStageContainer.getInstance();
        myViews = new Hashtable<String, Scene>();

        // STEP 3.1: Create the Registry object - if you inherit from
        // EntityBase, this is done for you. Otherwise, you do it yourself
        myRegistry = new ModelRegistry("Clerk");
        if(myRegistry == null)
        {
            new Event(Event.getLeafLevelClassName(this), "Clerk",
                    "Could not instantiate Registry", Event.ERROR);
        }


        // STEP 3.2: Be sure to set the dependencies correctly
        setDependencies();

        // Set up the initial view
        createAndShowTransactionChoiceView();
    }

    private void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("RegisterScout", "TransactionError");
        dependencies.setProperty("UpdateScout", "TransactionError");
        dependencies.setProperty("RemoveScout", "TransactionError");
        //more dependencies for other use cases

        myRegistry.setDependencies(dependencies);
    }
    public Object getState(String key)
    {
        if (key.equals("TransactionError") == true)
        {
            return transactionErrorMessage;
        }
        else
        {
            return "";
        }
    }
    public void stateChangeRequest(String key, Object value) //creates and displays views
    {
        // Write the sCR method component for the key you
        // just set up dependencies for
        if (key.equals("CancelTransaction") == true)
        {
            createAndShowTransactionChoiceView();
        }
        else if (key.equals("RegisterScout") == true)
        {
            System.out.println(key);
            String transType = key;

            createAndShowInsertScoutTransactionView();
        }
        else if ((key.equals("UpdateScout") == true)|| (key.equals("RemoveScout"))
        {
            System.out.println(key);
            String transType = key;

            createAndShowSearchScoutView();
        }
        else if ((key.equals("OTHER USE CASES") == true))
        {
            //Todo: createAndShow views for other use cases
        }
		myRegistry.updateSubscribers(key, this);
    }
	
    public void updateState(String key, Object value) //calls sCR to display view
    {
        stateChangeRequest(key, value);
    }
   /* public void doTransaction(String transactionType) {
        try {
            Transaction trans = TransactionFactory.createTransaction(
                    transactionType);

            trans.subscribe("CancelTransaction", this);
            trans.stateChangeRequest("DoYourJob", "");
        } catch (Exception ex) {
            transactionErrorMessage = "FATAL ERROR: TRANSACTION FAILURE: Unrecognized transaction!!";
            new Event(Event.getLeafLevelClassName(this), "createTransaction",
                    "Transaction Creation Failure: Unrecognized transaction " + ex.toString(),
                    Event.ERROR);
        }
    }*/
   private void createAndShowTransactionChoiceView()
   {
       Scene currentScene = (Scene)myViews.get("TransactionChoiceView");

       if (currentScene == null)
       {
           // create our initial view
           View newView = ViewFactory.createView("TransactionChoiceView", this); // USE VIEW FACTORY
           currentScene = new Scene(newView);
           myViews.put("TransactionChoiceView", currentScene);
       }

       swapToView(currentScene);

   }

    private void createAndShowInsertScoutTransactionView()
    {
        Scene currentScene = (Scene)myViews.get("InsertScoutTransactionView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("InsertScoutTransactionView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("InsertScoutTransactionView", currentScene);
        }

        swapToView(currentScene);

    }

    private void createAndShowSearchScoutView()
    {
        Scene currentScene = (Scene)myViews.get("SearchScoutView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("SearchScoutView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("SearchScoutView", currentScene);
        }

        swapToView(currentScene);

    }

    public void subscribe(String key, IView subscriber)
    {
        myRegistry.subscribe(key, subscriber);
    }

    public void unSubscribe(String key, IView subscriber)
    {
        myRegistry.unSubscribe(key, subscriber);
    }

    public void swapToView(Scene newScene)
    {

        if (newScene == null)
        {
            System.out.println("Clerk.swapToView(): Missing view for display");
            new Event(Event.getLeafLevelClassName(this), "swapToView",
                    "Missing view for display ", Event.ERROR);
            return;
        }

        myStage.setScene(newScene);
        myStage.sizeToScene();


        //Place in center
        WindowPosition.placeCenter(myStage);

    }

}
