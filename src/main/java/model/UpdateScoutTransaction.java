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
import userinterface.ScoutTableModel;


/** The class containing the WithdrawTransaction for the ATM application */
//==============================================================
public class UpdateScoutTransaction extends Transaction
{
    private Scout selectedScout;

    private String transactionErrorMessage = "";
    private ScoutCollection scoutCollection;
    private String scoutUpdateMessage = "";

    /**
     * Constructor for this class.
     *
     *
     */
    //----------------------------------------------------------
    public UpdateScoutTransaction()
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
        dependencies.setProperty("UpdateScoutData","ScoutUpdateMessage");
//        dependencies.setProperty("ScoutSearch","TransactionError");
//        dependencies.setProperty("CancelSearchScouts","CancelTransaction");

        myRegistry.setDependencies(dependencies);
    }

    /**
     * This method encapsulates all the logic of creating the account,
     * verifying ownership, crediting, etc. etc.
     */
    //----------------------------------------------------------
    public void processTransaction(Properties p) {
        String lastNameInput = p.getProperty("lastName");
        selectedScout.persistentState.setProperty("lastName", lastNameInput);
        String firstNameInput = p.getProperty("firstName");
        selectedScout.persistentState.setProperty("firstName", firstNameInput);
        String middleNameInput = p.getProperty("middleName");
        selectedScout.persistentState.setProperty("middleName", middleNameInput);
        String dateOfBirthInput = p.getProperty("dateOfBirth");
        selectedScout.persistentState.setProperty("dateOfBirth", dateOfBirthInput);
        String phoneNumberInput = p.getProperty("phoneNumber");
        selectedScout.persistentState.setProperty("phoneNumber", phoneNumberInput);
        String emailInput = p.getProperty("email");
        selectedScout.persistentState.setProperty("email", emailInput);
//        String dateStatusInput = p.getProperty("dateStatusUpdated");
//        selectedScout.persistentState.setProperty("dateStatusUpdated", dateStatusInput);

        selectedScout.update();


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
        else if (key.equals("ScoutUpdateMessage") == true) {
            return scoutUpdateMessage;
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
//            processTransaction((String)value);
            createAndShowScoutCollectionView();
        }
        else if (key.equals("SelectedScout") == true) {
            selectedScout = scoutCollection.retrieveByTroopId((String)value);
            createAndShowUpdateScoutView();
        }
        else if (key.equals("UpdateScout") == true) {
            processTransaction((Properties)value);
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

    protected void createAndShowUpdateScoutView() {
        View v = ViewFactory.createView("UpdateScoutView", this);
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


