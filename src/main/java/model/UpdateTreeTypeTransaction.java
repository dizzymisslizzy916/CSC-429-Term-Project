package model;

import java.util.Enumeration;
import java.util.Optional;
import java.util.Properties;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import userinterface.View;
import userinterface.ViewFactory;
public class UpdateTreeTypeTransaction extends Transaction {
    protected String updateStatusMessage;

    protected TreeTypeCollection treeTypeCollection;
    private String transactionErrorMessage = "";
    private TreeType selectedTreeType;

    public UpdateTreeTypeTransaction()throws Exception {
        super();

        treeTypeCollection = new TreeTypeCollection();

    }


    protected void setDependencies() {
        Properties dependencies = new Properties();
        //dependencies.put("SearchTreeTypes", "TreeTypes,TransactionError");
        dependencies.put("EditTreeType", "TreeTypeToDisplay");
        dependencies.put("Submit", "TreeTypes,TransactionError,UpdateStatusMessage");
        dependencies.put("Cancel", "CancelTransaction");

        myRegistry.setDependencies(dependencies);
    }


    protected Scene createView() {
        Scene currentScene = myViews.get("TreeTypeBarCodeEntryView");

        if (currentScene == null) {
            View newView = ViewFactory.createView("TreeTypeBarCodeEntryView", this);
            currentScene = new Scene(newView);
            myViews.put("TreeTypeBarCodeEntryView", currentScene);
        }

        return currentScene;
    }

    private void createAndShowTreeTypeView() {
        Scene currentScene = myViews.get("TreeTypeDataView");

        if (currentScene == null) {
            View newView = ViewFactory.createView("TreeTypeDataView", this);
            currentScene = new Scene(newView);
            myViews.put("TreeTypeDataView", currentScene);
        }

        swapToView(currentScene);
    }


    public void stateChangeRequest(String key, Object value) {
        switch (key) {
            case "DoYourJob":
                doYourJob();
                break;
            case "EditTreeType":
                editTreeType((String)value);
                break;
            case "Submit":
                updateSelectedTreeType((Properties)value);
                break;
            case "Cancel":
                swapToView(createView());
                break;
        }

        myRegistry.updateSubscribers(key, this);
    }

    public Object getState(String key) {
        if (key.equals("TransactionError") == true)
        {
            return transactionErrorMessage;
        }
        else if (key.equals("UpdateStatusMessage") == true) {
            return updateStatusMessage;
        }
        else if (key.equals("TreeTypes") == true)
        {
            return treeTypeCollection;
        }
        else if (key.equals("SelectedTreeType") == true) {
            return selectedTreeType;
        }
        else if (selectedTreeType != null) {
            Object val = selectedTreeType.getState(key);
            if (val != null) {
                return val;
            }
        }
        else if (treeTypeCollection != null) {
            Object val = treeTypeCollection.getState(key);
            if (val != null) {
                return val;
            }
        }

        return null;
    }

    protected void editTreeType(String treeTypeId) {
        selectedTreeType = treeTypeCollection.retrieve(treeTypeId);
        System.out.println("Collection retrieved " + treeTypeCollection.retrieve(treeTypeId));
        createAndShowTreeTypeView();
    }

    protected void updateSelectedTreeType(Properties p) {

        Enumeration allKeys = p.propertyNames();
        while (allKeys.hasMoreElements()) {
            String nextKey = (String) allKeys.nextElement();
            String nextValue = p.getProperty(nextKey);

            if (nextValue != null) {
                selectedTreeType.stateChangeRequest(nextKey, nextValue);
            }
        }

        selectedTreeType.update();
        updateStatusMessage = (String)selectedTreeType.getState("UpdateStatusMessage");
        transactionErrorMessage = updateStatusMessage;

        swapToView(createView());
    }
}


