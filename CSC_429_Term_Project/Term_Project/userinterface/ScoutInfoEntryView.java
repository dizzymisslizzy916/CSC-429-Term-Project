package userinterface;

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

        troopID.textProperty().addListener((obs, oldVal, newVal) -> {
            int position = troopID.getCaretPosition();
            if (newVal.length() > 10) {
                Platform.runLater(() -> {
                    troopID.setText(oldVal);
                    troopID.positionCaret(position);
                });
            }
        });

        dateOfBirth.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) > 0);
            }
        });

        phoneNum1.textProperty().addListener((obs, oldVal, newVal) -> {
            int position = phoneNum1.getCaretPosition();
            if (!newVal.matches("\\d*")) {
                Platform.runLater(() -> {
                    phoneNum1.setText(newVal.replaceAll("[^\\d]", ""));
                    phoneNum1.positionCaret(position);
                });
            }
            if (newVal.length() > 3) {
                Platform.runLater(() -> {
                    phoneNum1.setText(oldVal);
                    phoneNum1.positionCaret(position);
                });
            }
        });

        phoneNum2.textProperty().addListener((obs, oldVal, newVal) -> {
            int position = phoneNum2.getCaretPosition();
            if (!newVal.matches("\\d*")) {
                Platform.runLater(() -> {
                    phoneNum2.setText(newVal.replaceAll("[^\\d]", ""));
                    phoneNum2.positionCaret(position);
                });
            }
            if (newVal.length() > 3) {
                Platform.runLater(() -> {
                    phoneNum2.setText(oldVal);
                    phoneNum2.positionCaret(position);
                });
            }
        });

        phoneNum3.textProperty().addListener((obs, oldVal, newVal) -> {
            int position = phoneNum3.getCaretPosition();
            if (!newVal.matches("\\d*")) {
                Platform.runLater(() -> {
                    phoneNum3.setText(newVal.replaceAll("[^\\d]", ""));
                    phoneNum3.positionCaret(position);
                });
            }
            if (newVal.length() > 4) {
                Platform.runLater(() -> {
                    phoneNum3.setText(oldVal);
                    phoneNum3.positionCaret(position);
                });
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
