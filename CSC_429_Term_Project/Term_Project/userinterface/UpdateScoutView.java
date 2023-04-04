package userinterface;

import Utilities.UIUtils;
import impresario.IModel;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;

public class UpdateScoutView extends View{
    private MessageView statusLog;
    public UpdateScoutView(IModel model) {
        super(model, "UpdateScoutView");
        VBox container = new VBox(10);
        File fxmlFile = new File("../resources/userinterface/enterscoutinfo.fxml");
        Parent root = null;
        try {
            root = FXMLLoader.load(fxmlFile.toURI().toURL());
        } catch(IOException e) {
            System.err.println("enterscoutinfo.fxml not found");
            e.printStackTrace();
        }
        container.getChildren().add(root);
        container.getChildren().add(createStatusLog("                         "));
        handleForm(root);

        getChildren().add(container);
    }

    private void handleForm(Parent root) {
        AnchorPane anchorPane = (AnchorPane) root.lookup("#anchorPane");
        TextField firstName = (TextField) root.lookup("#firstName");
        TextField middleName = (TextField) root.lookup("#middleName");
        TextField lastName = (TextField) root.lookup("#lastName");
        TextField email = (TextField) root.lookup("#email");
        TextField troopID = (TextField) root.lookup("#troopID");
        TextField phoneNum1 = (TextField) root.lookup("#phoneNum1");
        TextField phoneNum2 = (TextField) root.lookup("#phoneNum2");
        TextField phoneNum3 = (TextField) root.lookup("#phoneNum3");
        DatePicker dateOfBirth = (DatePicker) root.lookup("#dateOfBirth");
        Button submit = (Button) root.lookup("#submitButton");
        Button done = (Button) root.lookup("#doneButton");

        //setting up proper restrictions for text fields
        UIUtils.limitLength(troopID, 10);

        UIUtils.limitToDigits(phoneNum1);
        UIUtils.limitLength(phoneNum1, 3);

        UIUtils.limitToDigits(phoneNum2);
        UIUtils.limitLength(phoneNum2, 3);

        UIUtils.limitToDigits(phoneNum3);
        UIUtils.limitLength(phoneNum3, 4);

        //restricting date to be today or earlier
        dateOfBirth.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) > 0);
            }
        });

        done.setVisible(false);
        done.setManaged(false);

        Platform.runLater(anchorPane::requestFocus);

        submit.setOnAction(e -> {
            Properties updateData = new Properties();
            if(!firstName.getText().isEmpty()) {
                updateData.setProperty("firstName", firstName.getText());
            }
            if(!lastName.getText().isEmpty()) {
                updateData.setProperty("lastName", lastName.getText());
            }
            if(!middleName.getText().isEmpty()) {
                updateData.setProperty("middleName", middleName.getText());
            }
            if(!email.getText().isEmpty()) {
                updateData.setProperty("email", email.getText());
            }
            if(!troopID.getText().isEmpty()) {
                updateData.setProperty("troopId", troopID.getText());
            }
            String phoneNum = phoneNum1.getText() + phoneNum2.getText() + phoneNum3.getText();
            if(!phoneNum.isEmpty()) {
                if(phoneNum.length() == 10) {
                    updateData.setProperty("phoneNumber", phoneNum);
                } else {
                    displayMessage("Please enter a full phone number or none at all", true);
                    return;
                }
            }
            if(!dateOfBirth.getEditor().getText().isEmpty()) {
                updateData.setProperty("dateOfBirth", dateOfBirth.getEditor().getText());
            }
            if(!updateData.isEmpty()) {
                myModel.stateChangeRequest("Update Scout", updateData);
                statusLog.clearErrorMessage();
            } else {
                displayMessage("Please enter some data to update", true);
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
