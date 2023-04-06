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

    private TreeType selectedTreeType;

    public UpdateTreeTypeTransaction() {
        super();

        treeTypeCollection = new TreeTypeCollection();

        try {
            treeTypeCollection.lookupAllTreeTypes();
        } catch (Exception e) {
            transactionErrorMessage = e.getMessage();
        }
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
        Scene currentScene = myViews.get("UpdateTreeTypeTransactionView");

        if (currentScene == null) {
            View newView = ViewFactory.createView("UpdateTreeTypeTransactionView", this);
            currentScene = new Scene(newView);
            myViews.put("UpdateTreeTypeTransactionView", currentScene);
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
        switch (key) {
            case "TransactionError":
                return transactionErrorMessage;
            case "UpdateStatusMessage":
                return updateStatusMessage;
            case "TreeTypes":
                return treeTypeCollection.getState("TreeTypes");
            case "TreeTypeToDisplay":
                return selectedTreeType;
            default:
                return null;
        }
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
