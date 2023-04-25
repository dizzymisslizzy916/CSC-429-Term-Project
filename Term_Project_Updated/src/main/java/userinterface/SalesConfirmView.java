package userinterface;

import impresario.IModel;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class SalesConfirmView extends View {

    protected MessageView statusLog;
    public SalesConfirmView(IModel model) {
        super(model, "SalesConfirmView");

        File fxmlFile = new File("src/main/resources/userinterface/salesconfirmview.fxml");

        Parent root = null;
        try {
            root = FXMLLoader.load(fxmlFile.toURI().toURL());
        } catch(IOException e) {
            System.err.println("salesconfirmview.fxml not found");
            e.printStackTrace();
        }
        handleForm(root);

        getChildren().add(root);
    }

    private void handleForm(Parent root) {
        Text checkSales = (Text) root.lookup("#checkSales");
        Text cash = (Text) root.lookup("#cash");
        Button cancel = (Button) root.lookup("#cancel");
        Button confirm = (Button) root.lookup("#confirm");

        double endCash = (double) myModel.getState("SessionEndCash");
        double totalCheckSales = (double) myModel.getState("SessionCheckSales");
        cash.setText(String.valueOf(endCash));
        checkSales.setText(String.valueOf(totalCheckSales));


        cancel.setOnAction(e -> {
            myModel.stateChangeRequest("CancelTransaction", null);
        });

        confirm.setOnAction(e -> {
            myModel.stateChangeRequest("EnterShiftNotesView", null);
        });
    }

    protected MessageView createStatusLog(String initialMessage)
    {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    public void populateFields()
    {

    }

    @Override
    public void updateState(String key, Object value) {
    }
}
