package userinterface;

import Utilities.UIUtils;
import impresario.IModel;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class TreeTypeInfoEntryView extends View{
    MessageView statusLog;
    public TreeTypeInfoEntryView(IModel model) {
        super(model, "TreeTypeInfoEntryView");

        VBox container = new VBox(10);
        File fxmlFile = new File("../resources/userinterface/entertreetypeinfo.fxml");
        Parent root = null;
        try {
            root = FXMLLoader.load(fxmlFile.toURI().toURL());
        } catch(IOException e) {
            System.err.println("entertreetypeinfo.fxml not found");
            e.printStackTrace();
        }
        container.getChildren().add(root);
        container.getChildren().add(createStatusLog("                         "));
        handleForm(root);

        getChildren().add(container);

    }

    private void handleForm(Parent root) {
        AnchorPane anchorPane = (AnchorPane) root.lookup("#anchorPane");
        TextField typeDescription = (TextField) root.lookup("#typeDescription");
        TextField cost = (TextField) root.lookup("#cost");
        TextField prefix = (TextField) root.lookup("#barcodePref");
        Button submit = (Button) root.lookup("#button");
        Button done = (Button) root.lookup("#done");

        //setting up proper restrictions for text fields
        UIUtils.limitLength(prefix, 2);
        UIUtils.limitLength(cost, 20);
        UIUtils.limitLength(typeDescription, 25);

        Platform.runLater(anchorPane::requestFocus);

        done.setOnAction(e -> {
            myModel.stateChangeRequest("TransactionChoiceView", null);
        });

        submit.setOnAction(e -> {
            if(typeDescription.getText().isEmpty() || cost.getText().isEmpty() || prefix.getText().isEmpty()) {
                displayMessage("Please enter proper values for all given fields", true);
            } else {
                Properties treeTypeInfo = new Properties();
                treeTypeInfo.setProperty("typeDescription", typeDescription.getText());
                treeTypeInfo.setProperty("cost", cost.getText());
                treeTypeInfo.setProperty("barcodePrefix", prefix.getText());

                myModel.stateChangeRequest("Enter Tree Type", treeTypeInfo);
                displayMessage("Tree Type Added Successfully", false);
            }
        });

    }

    @Override
    public void updateState(String key, Object value) {

    }

    public void displayMessage(String message, boolean error) {
        if(error) {
            statusLog.displayErrorMessage(message);
        } else {
            statusLog.displayMessage(message);
        }
    }

    private MessageView createStatusLog(String initialMessage) {
        statusLog = new MessageView(initialMessage);
        return statusLog;
    }
}
