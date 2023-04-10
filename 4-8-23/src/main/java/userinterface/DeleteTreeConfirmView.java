package userinterface;

import impresario.IModel;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.Tree;

import java.io.File;
import java.io.IOException;

public class DeleteTreeConfirmView extends View {
    public DeleteTreeConfirmView(IModel model) {
        super(model, "DeleteTreeConfirmView");

        File fxmlFile = new File("../resources/userinterface/deletetreeconfirmview.fxml");
        Parent root = null;
        try {
            root = FXMLLoader.load(fxmlFile.toURI().toURL());
        } catch(IOException e) {
            System.err.println("deletetreeconfirmview.fxml not found");
            e.printStackTrace();
        }
        handleForm(root);

        getChildren().add(root);
    }

    private void handleForm(Parent root) {
        AnchorPane anchorPane = (AnchorPane) root.lookup("#anchorPane");
        Text treeType = (Text) root.lookup("#treeType");
        Text status = (Text) root.lookup("#status");
        TextArea notes = (TextArea) root.lookup("#notes");
        Button cancel = (Button) root.lookup("#cancel");
        Button confirm = (Button) root.lookup("#confirm");

        Tree tree = (Tree) myModel.getState("SelectedTree");
        treeType.setText((String) tree.getState("treeType"));
        status.setText((String) tree.getState("Status"));
        notes.setText((String) tree.getState("Notes"));

        Platform.runLater(anchorPane::requestFocus);

        cancel.setOnAction(e -> {
            //TODO - model implementation
            myModel.stateChangeRequest("CancelTransaction", null);
        });

        confirm.setOnAction(e -> {
            myModel.stateChangeRequest("DeleteTree", tree);
        });

    }

    @Override
    public void updateState(String key, Object value) {

    }
}
