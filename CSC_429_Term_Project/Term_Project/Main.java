import database.*;
import java.util.Properties;
import java.util.Scanner;
import model.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import userinterface.*;
import javafx.event.EventHandler;
import javafx.event.Event;
import javafx.application.Application;

public class Main extends Application {

    private Clerk myClerk;        // main interface agent

    private Stage mainStage;

	public void start(Stage primaryStage) {
			
        // Create the top-level container (main frame) and add contents to it.
        MainStageContainer.setStage(primaryStage, "CSC 429 Term Project");
        mainStage = MainStageContainer.getInstance();
		
		// Finish setting up the stage (ENABLE THE GUI TO BE CLOSED USING THE TOP RIGHT
        // 'X' IN THE WINDOW), and show it.
        mainStage.setOnCloseRequest(new EventHandler<javafx.stage.WindowEvent>() {
            @Override
            public void handle(javafx.stage.WindowEvent event) {
                System.exit(0);
            }
        });
		
        try {
            myClerk = new Clerk();
        } catch (Exception exc) {
            System.err.println("Could not create Clerk!");
            //new Event(Event.getLeafLevelClassName(this), "Main.<init>", "Unable to create Clerk object", Event.ERROR);
            exc.printStackTrace();
        }

        WindowPosition.placeCenter(mainStage);

        mainStage.show();
	}

	    public static void main(String[] args) {

        launch(args);
    }
}