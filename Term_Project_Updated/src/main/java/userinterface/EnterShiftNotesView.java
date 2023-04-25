package userinterface;

import impresario.IModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;

public class EnterShiftNotesView extends View {

    MessageView statusLog;
    public EnterShiftNotesView(IModel model) {
        super(model, "EnterShiftNotesView");

        VBox container = new VBox(10);

        File fxmlFile = new File("src/main/resources/userinterface/entershiftnotesview.fxml");

        Parent root = null;
        try {
            root = FXMLLoader.load(fxmlFile.toURI().toURL());
        } catch(IOException e) {
            System.err.println("entershiftnotesview.fxml not found");
            e.printStackTrace();
        }
        handleForm(root);

        createStatusLog("                  ");

        container.getChildren().add(statusLog);
        container.getChildren().add(root);

        getChildren().add(container);
    }

    private void handleForm(Parent root) {
        TextArea notes = (TextArea) root.lookup("#notes");
        Button submit = (Button) root.lookup("#submit");
        Button done = (Button) root.lookup("#done");

        done.setOnAction(e -> {
            myModel.stateChangeRequest("CancelTransaction", null);
        });

        submit.setOnAction(e -> {
            clearErrorMessage();
            if(!notes.getText().isEmpty()) {
                myModel.stateChangeRequest("SubmitNotes", notes.getText());
            }
            displayMessage("Shift Note Updated Successfully");
        });
    }

    protected MessageView createStatusLog(String initialMessage)
    {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    @Override
    public void updateState(String key, Object value) {
        if (key.equals("EnterShiftNotesStatusMessage"))
        {
            String val = (String)value;
            if ((val.startsWith("Err")) || (val.startsWith("ERR"))) {
                displayErrorMessage(val);
            }
            else {
                displayMessage(val);
            }
        }
    }

    /**
     * Display error message
     */
    //----------------------------------------------------------
    public void displayErrorMessage(String message)
    {
        statusLog.displayErrorMessage(message);
    }

    /**
     * Display info message
     */
    //----------------------------------------------------------
    public void displayMessage(String message)
    {
        statusLog.displayMessage(message);
    }

    /**
     * Clear error message
     */
    //----------------------------------------------------------
    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }
}
