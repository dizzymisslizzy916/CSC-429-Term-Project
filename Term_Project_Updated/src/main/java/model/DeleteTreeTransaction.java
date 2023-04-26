// specify the package
package model;

// system imports
import javafx.scene.Scene;

import java.util.Enumeration;
import java.util.Properties;


// project imports
import event.Event;

import userinterface.View;
import userinterface.ViewFactory;
public class DeleteTreeTransaction extends Transaction{

    private Tree selectedTree; // needed for GUI only

    private String transactionErrorMessage = "";
    private TreeCollection treeCollection;
    private String treeUpdateMessage = "";

    public DeleteTreeTransaction() throws Exception//constructor
    {
        super();
        treeCollection = new TreeCollection();
    }

    protected void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("TreeData", "TransactionError");
        dependencies.setProperty("OK", "CancelTransaction");
        dependencies.setProperty("UpdateTree","TreeUpdateMessage");
        dependencies.setProperty("CancelSearchTrees","CancelTransaction");

        myRegistry.setDependencies(dependencies);
    }

    public void processTreeBarcode(String barcode) {
        try {
            System.out.println("model/UpdateTreeTransactionMethod: processTreeBarcode is running");
            System.out.println(barcode);
            selectedTree = new Tree(barcode);
            System.out.println("HERE");
            createAndShowDeleteTreeConfirmView();

        }catch (Exception ex){
            transactionErrorMessage = "ERROR: Could not find tree with barcode: " + barcode;
            new Event(Event.getLeafLevelClassName(this), "processTransaction",
                    "Failed to retrieve a tree.",
                    Event.ERROR);

        }
    }

    public void processTransaction(){
        selectedTree.deleteFromDatabase();
    }

    public Object getState(String key)
    {
        if (key.equals("TransactionError") == true)
        {
            return transactionErrorMessage;
        }
        else if (key.equals("TreeList") == true)
        {
            return treeCollection;
        }
        else if (key.equals("SelectedTree") == true) {
            return selectedTree;
        }
        else if (key.equals("treeType") == true) {
            String val = (String) selectedTree.getState(key);
            TreeType tt = new TreeType();
            try {
                tt.findWithTreeTypeId(val);
                return tt.getState("typeDescription");
            } catch (Exception ex) {
                return "Description not found";
            }
        }
        else if (key.equals("TreeUpdateMessage") == true) {
            return treeUpdateMessage;
        }
        else if (selectedTree != null) {
            Object val = selectedTree.getState(key);
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
        else if (key.equals("TreeBarcodeEntered") == true)
        {
            processTreeBarcode((String)value);
        }
        else if (key.equals("TreeSelected") == true) {
            String barCode = (String)value;
            selectedTree = treeCollection.retrieve(barCode);
        }
        else if (key.equals("DeleteTree") == true) {

            processTransaction();
        }

        myRegistry.updateSubscribers(key, this);
    }

    protected void createAndShowDeleteTreeConfirmView()
    {
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
