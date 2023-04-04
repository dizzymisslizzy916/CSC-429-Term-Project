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

public class TreeBarCodeView extends View {

    MessageView statusLog;
    public TreeBarCodeView(IModel model) {
        super(model, "TreeBarCodeView");

        VBox container = new VBox(10);
        File fxmlFile = new File("../resources/userinterface/treebarcodeview.fxml");
        Parent root = null;
        try {
            root = FXMLLoader.load(fxmlFile.toURI().toURL());
        } catch(IOException e) {
            System.err.println("treebarcodeview.fxml not found");
            e.printStackTrace();
        }
        container.getChildren().add(root);
        container.getChildren().add(createStatusLog("                         "));
        handleForm(root);

        getChildren().add(container);
    }

    private void handleForm(Parent root) {
        AnchorPane anchorPane = (AnchorPane) root.lookup("#anchorPane");
        TextField barcode = (TextField) root.lookup("#barcode");
        Button done = (Button) root.lookup("#done");
        Button submit = (Button) root.lookup("#submit");

        UIUtils.limitLength(barcode, 5);

        Platform.runLater(anchorPane::requestFocus);

        done.setOnAction(e -> {
            myModel.stateChangeRequest("TransactionChoiceView", null);
        });

        submit.setOnAction(e -> {
            if(!barcode.getText().isEmpty()) {

            } else {
                displayMessage("Please enter barcode", true);
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
