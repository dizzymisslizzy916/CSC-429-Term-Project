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
public class UpdateTreeTransaction extends Transaction
{
    private Tree selectedTree; // needed for GUI only

    private String transactionErrorMessage = "";
    private TreeCollection treeCollection;

public UpdateTreeTransaction() throws Exception //constructor
{
super();
treeCollection = new TreeCollection();
}

protected void setDependencies()
{
    dependencies = new Properties();
    dependencies.setProperty("TreeSearch", "TransactionError");
    dependencies.setProperty("OK", "CancelTransaction");
    dependencies.setProperty("CancelSearchTrees","CancelTransaction");

    myRegistry.setDependencies(dependencies);
}

public void processTransaction(Properties props) {
    try {
        treeCollection.retrieve(props.getProperty("BarCode"));
    }catch (Exception ex){
        transactionErrorMessage = "Error retrieving that tree.";
        new Event(Event.getLeafLevelClassName(this), "processTransaction",
                "Failed to retrieve a tree.",
                Event.ERROR);

    }
}

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
        return treeCollection;
    }
    else if (key.equals("SelectedTree") == true) {
        return selectedTree;
    }
    else if (selectedTree != null) {
        Object val = selectedTree.getState(key);
        if (val != null) {
            return val;
        }
    }
    else if (treeCollection != null) {
        Object val = treeCollection.getState(key);
        if (val != null) {
            return val;
        }
    }

        return null;
}

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
        createAndShowTreeCollectionView();
    }
   /* else
    if (key.equals("ScoutSearch") == true)
    {
        processTransaction((String)value);
        createAndShowScoutCollectionView();
    }*/
    else if (key.equals("TreeSelected") == true) {
        String barCode = (String)value;
        selectedTree = treeCollection.retrieve(barCode);
        //DO SOMETHING TO UPDATE THE TREE

    }
    else if (key.equals("DeleteTree")) {
        String barCode = (String) value;
        selectedTree = treeCollection.retrieve(barCode);
        createAndShowDeleteTreeConfirmView();
    }

    myRegistry.updateSubscribers(key, this);
}
    protected void createAndShowTreeCollectionView()
    {
        View v = ViewFactory.createView("TreeCollectionView", this);
        Scene newScene = new Scene(v);

        swapToView(newScene);
    }

    private void createAndShowDeleteTreeConfirmView() {
        View v = ViewFactory.createView("DeleteTreeConfirmView", this);
        Scene newScene = new Scene(v);

        swapToView(newScene);
    }


    protected Scene createView()
{
    Scene currentScene = myViews.get("TreeBarCodeEntryView");

    if (currentScene == null)
    {
        // create our new view
        View newView = ViewFactory.createView("TreeBarCodeEntryView", this);
        currentScene = new Scene(newView);
        myViews.put("TreeBarCodeEntryView", currentScene);

        return currentScene;
    }
    else
    {
        return currentScene;
    }
}

//------------------------------------------------------

}