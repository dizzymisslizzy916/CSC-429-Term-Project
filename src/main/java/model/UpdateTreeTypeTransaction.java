// specify the package
package model;

// system imports
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

// project imports
import event.Event;
import exception.InvalidPrimaryKeyException;

import userinterface.View;
import userinterface.ViewFactory;

/** The class containing the WithdrawTransaction for the ATM application */
//==============================================================
public class UpdateTreeTypeTransaction extends Transaction {
    private TreeType myTreeType;
    private TreeType selectedTreeType; // needed for GUI only
    // GUI Components

    private String transactionErrorMessage = "";

    private TreeTypeCollection TreeTypeCollection;

    /**
     * Constructor for this class.
     */
    //----------------------------------------------------------
    public UpdateTreeTypeTransaction()
            throws Exception {
        super();
        TreeTypeCollection = new TreeTypeCollection();
    }

    //----------------------------------------------------------
    protected void setDependencies() {
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
    public void processTreeTypeBarcodePrefix(String barcodePrefix) {
        try {
            System.out.println("I'm in process transaction");
            selectedTreeType = new TreeType(barcodePrefix);
            createAndShowUpdateTreeTypeView();
        } catch (Exception ex) {
            transactionErrorMessage = "ERROR: No TreeTypes found!";

        }
    }

    public void processTransaction(Properties props) {
        try {

            Enumeration allKeys = props.propertyNames();
            while (allKeys.hasMoreElements())
            {
                String nextKey = (String) allKeys.nextElement();
                String nextValue = props.getProperty(nextKey);
                selectedTreeType.stateChangeRequest(nextKey, nextValue);
            }
            selectedTreeType.update();
            transactionErrorMessage = (String)selectedTreeType.getState("UpdateStatusMessage");
            

        }catch (Exception ex){
            transactionErrorMessage = "ERROR:Could not save tree with barcode: " + selectedTreeType.getState("barcode") ;
            new Event(Event.getLeafLevelClassName(this), "processTransaction",
                    "Failed to save a tree.",
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
   /* else
    if (key.equals("UpdateStatusMessage") == true)
    {
     return transactionErrorMessage;*/
        else if (key.equals("TreeList") == true)
        {
            return TreeTypeCollection;
        }
        else if (key.equals("SelectedTreeType") == true) {
            return selectedTreeType;
        }
        else if (key.equals("TreeTypeCollection") == true) {
            String val = (String)selectedTreeType.getState(key);
            TreeTypeCollection tt = new TreeTypeCollection();
            try
            {
                tt.findTreeTypeWithBarcodePrefixLike(val);
                return tt.getState("typeDescription");
            }
            catch (Exception ex) {
                return "Description not found";
            }
        }
        else if (selectedTreeType != null) {
            Object val = selectedTreeType.getState(key);
            if (val != null) {
                return val;
            }
        }


        return null;
    }

    //-----------------------------------------------------------
    public void stateChangeRequest(String key, Object value) {
        System.out.println("key received: " + key);
        if (key.equals("DoYourJob") == true) {
            doYourJob();
        } else if (key.equals("TreeTypeBarCodePrefixEntered") == true) {
            String barCode = (String) value;
            try {
                selectedTreeType = new TreeType(barCode);
                //DO SOMETHING TO UPDATE THE TREE
                createAndShowUpdateTreeTypeView();
            }
            catch (InvalidPrimaryKeyException ex)
            {
                transactionErrorMessage = "ERROR: Barcode prefix invalid";
            }

                //processTreeTypeBarcodePrefix((String) value);



        } else if (key.equals("TreeTypeData") == true) {
            processTransaction((Properties) value);

        } else if (key.equals("TreeTypeSelected") == true) {

        }

        myRegistry.updateSubscribers(key, this);
    }

    /**
     * Create the view of this class. And then the super-class calls
     * swapToView() to display the view in the stage
     */
    //------------------------------------------------------
    protected void createAndShowUpdateTreeTypeView() {
        View v = ViewFactory.createView("UpdateTreeTypeView", this);
        Scene newScene = new Scene(v);

        swapToView(newScene);
    }


    protected Scene createView() {
        Scene currentScene = myViews.get("TreeTypeBarCodeEntryView");

        if (currentScene == null) {
            // create our new view
            View newView = ViewFactory.createView("TreeTypeBarCodeEntryView", this);
            currentScene = new Scene(newView);
            myViews.put("TreeTypeBarCodeEntryView", currentScene);

            return currentScene;
        } else {
            return currentScene;
        }
    }
}


