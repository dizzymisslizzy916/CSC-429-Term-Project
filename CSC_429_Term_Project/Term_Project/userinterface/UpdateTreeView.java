package userinterface;

import Utilities.UIUtils;
import impresario.IModel;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Pattern;

public class UpdateTreeView extends View {

    MessageView statusLog;
    public UpdateTreeView(IModel model) {
        super(model, "UpdateTreeView");
        VBox container = new VBox(10);
        File fxmlFile = new File("../resources/userinterface/entertreeinfo.fxml");
        Parent root = null;
        try {
            root = FXMLLoader.load(fxmlFile.toURI().toURL());
        } catch(IOException e) {
            System.err.println("entertreeinfo.fxml not found");
            e.printStackTrace();
        }
        container.getChildren().add(root);
        container.getChildren().add(createStatusLog("                         "));
        handleForm(root);

        getChildren().add(container);
    }

    private void handleForm(Parent root) {
        AnchorPane anchorPane = (AnchorPane) root.lookup("#anchorPane");
        TextArea notes = (TextArea) root.lookup("#notes");
        ComboBox treeType = (ComboBox) root.lookup("#treeType");
        ComboBox status = (ComboBox) root.lookup("#status");
        Button submit = (Button) root.lookup("#submit");

        ArrayList<String> treeTypes;

        //setting up proper restriction for notes field
        UIUtils.limitLength(notes, 200);

        //TODO - model implementation
        treeTypes = (ArrayList<String>) myModel.getState("treeTypes");

        treeType.getItems().addAll(treeTypes);

        status.getItems().addAll("Available", "Sold", "Damaged");

        Platform.runLater(anchorPane::requestFocus);

        submit.setOnAction(e -> {
            Properties treeInfo = new Properties();
            if(!notes.getText().isEmpty()) {
                treeInfo.setProperty("Notes", notes.getText());
            }
            if(!treeType.getEditor().getText().isEmpty()) {
                treeInfo.setProperty("treeType", treeType.getEditor().getText());
            }
            if(!status.getEditor().getText().isEmpty()) {
                treeInfo.setProperty("Status", status.getEditor().getText());
            }
            if(!treeInfo.isEmpty()) {
                //TODO - model implementation
                myModel.stateChangeRequest("Update Tree", treeInfo);
                statusLog.clearErrorMessage();
            } else {
                displayMessage("Please enter some information to update", true);
            }
        });

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

    @Override
    public void updateState(String key, Object value) {

    }
}
