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

public class ScoutInfoEntryView extends View{

    private MessageView statusLog;
    public ScoutInfoEntryView(IModel model) {
        super(model, "ScoutInfoEntryView");

        VBox container = new VBox(10);
        File fxmlFile = new File("../resources/userinterface/enterscoutinfo.fxml");
        Parent root = null;
        try {
            root = FXMLLoader.load(fxmlFile.toURI().toURL());
        } catch(IOException e) {
            System.err.println("searchscoutview.fxml not found");
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

        done.setOnAction(e -> {
            myModel.stateChangeRequest("TransactionChoiceView", null);
        });

        Platform.runLater(anchorPane::requestFocus);

        submit.setOnAction(e -> {
            String phoneNumber = phoneNum1.getText() + phoneNum2.getText() + phoneNum3.getText();
            if(firstName.getText().isEmpty() ||
                    middleName.getText().isEmpty() ||
                    lastName.getText().isEmpty() ||
                    email.getText().isEmpty() ||
                    troopID.getText().isEmpty() ||
                    phoneNumber.length() < 10 ||
                    dateOfBirth.getEditor().getText().isEmpty()) {
                displayMessage("Please enter proper values for all given fields", true);
            } else {
                ArrayList<String> scoutInfo = new ArrayList<>();
                scoutInfo.add(firstName.getText());
                scoutInfo.add(middleName.getText());
                scoutInfo.add(lastName.getText());
                scoutInfo.add(email.getText());
                scoutInfo.add(troopID.getText());
                scoutInfo.add(phoneNumber);
                scoutInfo.add(dateOfBirth.getEditor().getText());
                //make a case under Scout.statechangerequest for "Enter Scout" which handles passing the scout info
                //to the model but doesn't change views
                myModel.stateChangeRequest("Enter Scout", scoutInfo);
                displayMessage("Scout added successfully", false);
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
