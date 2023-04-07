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

    private Librarian myLibrarian;        // main interface agent

    private Stage mainStage;

    public void start(Stage primaryStage) {
        // Create the top-level container (main frame) and add contents to it.
        MainStageContainer.setStage(primaryStage, "CSC 429 Project");
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
            myLibrarian = new Librarian();
        } catch (Exception exc) {
            System.err.println("Could not create Librarian!");
            //new Event(Event.getLeafLevelClassName(this), "Main.<init>", "Unable to create Librarian object", Event.ERROR);
            exc.printStackTrace();
        }


        WindowPosition.placeCenter(mainStage);

        mainStage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}


        /*Test cases for Hw 1
        Scanner input = new Scanner(System.in);
        Scanner input1 = new Scanner(System.in);

        //JDBCBroker broker = JDBCBroker.getInstance();
        //broker.getConnection();

        //1. Insert a new book into the database
        System.out.println("Enter a book information");
        System.out.println("Enter a Title: ");
        String title = input.nextLine();
        System.out.println("Enter an Author: ");
        String author = input.nextLine();
        System.out.print("Enter a Publish Year: ");
        String year = input.next();

        Properties props1 = new Properties();
        props1.setProperty("bookTitle", title);
        props1.setProperty("author", author);
        props1.setProperty("pubYear", year);
        props1.setProperty("status", "Active");

        Book temp = new Book(props1);
        temp.update();

        System.out.println("--------------");

        //2. Insert a new patron to the database
        System.out.println("Enter a patron information");
        System.out.println("Enter a Name: ");
        String name = input1.nextLine();
        System.out.println("Enter an Address: ");
        String address = input1.nextLine();
        System.out.print("Enter a City: ");
        String city = input1.next();
        System.out.print("Enter a State Code: ");
        String state = input1.next();
        System.out.print("Enter a Zip: ");
        String zip = input1.next();
        System.out.print("Enter an Email: ");
        String email = input1.next();
        System.out.print("Enter DOB (yyyy/mm/dd): ");
        String dob = input1.next();

        Properties props2 = new Properties();
        props2.setProperty("name", name);
        props2.setProperty("address", address);
        props2.setProperty("city", city);
        props2.setProperty("stateCode", state);
        props2.setProperty("zip", zip);
        props2.setProperty("email", email);
        props2.setProperty("dateOfBirth", dob);
        props2.setProperty("status", "Active");

        Patron temp1 = new Patron(props2);


        temp1.update();
        System.out.println("--------------");



        //3. Given a part of a title of a book, print all book data for books that match this title
        BookCollection test = new BookCollection();
        try {
            test.findBooksWithTitleLike("hate");
            test.display();
            System.out.println("------------------");
        }
        catch(Exception ex) {
            System.out.println("Error");
        }
//
//        //4. Given a year, print all book data for books that are published before that year.
//
        try {
            test.findBooksNewerThanDate("2020/12/01");
            test.display();
            System.out.println("------------------");
        }
        catch(Exception ex) {
            System.out.println("Error");
        }

//

//        //5. Given a date, print all patron data for patrons that are younger than that date.
        PatronCollection test1 = new PatronCollection();
        try {
            test1.findPatronsYoungerThan("90");
            test1.display();
            System.out.println("------------------");
        }
        catch(Exception ex) {
            System.out.println("Error");
        }
//
//        //6. Given a zip, print all patron data for patrons that live at that zip
        try {
            test1.findPatronsAtZipCode("20020");
            test1.display();
            System.out.println("------------------");
        }
        catch(Exception ex) {
            System.out.println("Error");
        }


    }
}
*/