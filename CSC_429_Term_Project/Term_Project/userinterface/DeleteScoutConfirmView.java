package userinterface;

import impresario.IModel;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.Scout;

import java.io.File;
import java.io.IOException;

public class DeleteScoutConfirmView extends View{
    public DeleteScoutConfirmView(IModel model) {
        super(model, "DeleteScoutConfirmView");

        File fxmlFile = new File("../resources/userinterface/deletescoutconfirmview.fxml");
        Parent root = null;
        try {
            root = FXMLLoader.load(fxmlFile.toURI().toURL());
        } catch(IOException e) {
            System.err.println("deletescoutconfirmview.fxml not found");
            e.printStackTrace();
        }
        handleForm(root);

        getChildren().add(root);
    }

    private void handleForm(Parent root) {
        AnchorPane anchorPane = (AnchorPane) root.lookup("#anchorPane");
        Text name = (Text) root.lookup("#name");
        Text email = (Text) root.lookup("#email");
        Text troopID = (Text) root.lookup("#troopID");
        Text phoneNumber = (Text) root.lookup("#phoneNumber");
        Text dateOfBirth = (Text) root.lookup("#dateOfBirth");
        Button cancel = (Button) root.lookup("#cancel");
        Button confirm = (Button) root.lookup("#confirm");

        //TODO - model implementation
        Scout scout = (Scout) myModel.getState("ScoutDelete");
        String firstName = (String) scout.getState("firstName");
        String middleName = (String) scout.getState("middleName");
        String lastName = (String) scout.getState("lastName");
        name.setText(firstName + " " + middleName + " " + lastName);
        email.setText((String) scout.getState("email"));
        troopID.setText((String) scout.getState("troopId"));
        phoneNumber.setText((String) scout.getState("phoneNumber"));
        dateOfBirth.setText(((String) scout.getState("dateOfBirth")));

        Platform.runLater(anchorPane::requestFocus);

        cancel.setOnAction(e -> {
            myModel.stateChangeRequest("TransactionChoiceView", null);
        });

        confirm.setOnAction(e -> {
            myModel.stateChangeRequest("DeleteScout", scout);
        });
    }

    @Override
    public void updateState(String key, Object value) {

    }
}
