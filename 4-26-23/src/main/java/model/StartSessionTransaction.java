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
public class StartSessionTransaction extends Transaction
{
    private Session mySession; // needed for GUI only

    // GUI Components

    private String transactionErrorMessage = "";
    private ScoutCollection scoutCollection;
    private Scout selectedScout;

    private Shift myShift;

    private String sentSessionId;

    /**
     * Constructor for this class.
     *
     *
     */
    //----------------------------------------------------------
    public StartSessionTransaction()
            throws Exception
    {
        super();
        System.out.println("Creating start session transaction");
        scoutCollection = new ScoutCollection();
        System.out.println("Done creating start session transaction");
    }

    //----------------------------------------------------------
    protected void setDependencies()
    {
        dependencies = new Properties();
         //dependencies.setProperty("SessionData", "UpdateStatusMessage");
        dependencies.setProperty("OK", "CancelTransaction");
         dependencies.setProperty("StartShift","ShiftUpdateMessage");
         dependencies.setProperty("CancelStartSession","CancelTransaction");

        myRegistry.setDependencies(dependencies);
    }

    public void processTransaction(Properties props) {

            String sentSessionId = (String)props.getProperty("Id");
            try
            {
                Session s = new Session(sentSessionId);
                transactionErrorMessage = "ERROR: Session with Id: " + sentSessionId + " already exists";
            }
            catch (Exception ex) {
                mySession = new Session(props);
                mySession.update();
                transactionErrorMessage = (String) mySession.getState("UpdateStatusMessage");

            }
    }

    public void processSession(Properties props) {
        String sessionId = (String) mySession.getState("Id");
        String scoutId = (String) selectedScout.getState("scoutId");
        props.setProperty("scoutId", scoutId);
        props.setProperty("sessionId", sessionId);
        props.setProperty("endTime", "");
        myShift = new Shift(props);
        //myShift.update();
        transactionErrorMessage = (String) myShift.getState("UpdateStatusMessage");



       /* try
        {
            Shift s = new Shift(sessionId);
            transactionErrorMessage = "ERROR: Shift with Session Id: " + sessionId + " already exists";
        }
        catch (Exception ex)
        {
            myShift = new Shift(props);
            myShift.setOldFlag(false);
            myShift.update();
            transactionErrorMessage = (String)myShift.getState("UpdateStatusMessage");

        }*/
        createAndShowScoutCollectionView();
    }

    //-----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("TransactionError") == true)
        {
            return transactionErrorMessage;
        }
        else
        if (key.equals("ScoutList") == true)
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
        else
        if (key.equals("SessionData") == true) {
             processTransaction((Properties)value);
            System.out.println("Liz was here 1");
            try {
                scoutCollection.findAllScouts();
                createAndShowScoutCollectionView();
            } catch (Exception ex) {
                System.out.println("Liz was here 1 - Exception thrown");
                System.out.println(ex);
                ex.printStackTrace();
                transactionErrorMessage = "ERROR: No scouts found!";
            }
        } else if (key.equals("ScoutSelected") == true)
            {
                selectedScout = scoutCollection.retrieveByTroopId((String)value);
                createAndShowShiftInfoEntryView();

        } else if (key.equals("ShiftData") == true)
        {
            processSession((Properties)value);
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
        System.out.println("Liz was here - create session info view");
        Scene currentScene = myViews.get("SessionInfoView");

        if (currentScene == null)
        {
            // create our new view
            View newView = ViewFactory.createView("SessionInfoView", this);
            currentScene = new Scene(newView);
            myViews.put("SessionInfoView", currentScene);

            return currentScene;
        }
        else
        {
            return currentScene;
        }
    }

    //------------------------------------------------------
    protected void createAndShowScoutCollectionView()
    {
        System.out.println("Liz was here 2");
        View newView = ViewFactory.createView("ScoutCollectionView", this);
        Scene newScene = new Scene(newView);

        myViews.put("ScoutCollectionView", newScene);

        swapToView(newScene);
    }

    protected void createAndShowShiftInfoEntryView()
    {
        System.out.println("Liz was here 3");
        View newView = ViewFactory.createView("ShiftInfoEntryView", this);
        Scene newScene = new Scene(newView);

        myViews.put("ShiftInfoEntryView", newScene);

        swapToView(newScene);
    }
}

