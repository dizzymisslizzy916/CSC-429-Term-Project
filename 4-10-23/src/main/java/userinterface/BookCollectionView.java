package userinterface;

// system imports
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.Vector;
import java.util.Enumeration;

// project imports
import impresario.IModel;
import model.Book;
import model.BookCollection;


public class BookCollectionView extends View {

    protected TableView<BookTableModel> tableOfBooks;
    protected Button cancelButton;

    protected MessageView statusLog;

    public BookCollectionView(IModel wsc)
    {
        super(wsc, "BookCollectionView");

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // create our GUI components, add them to this panel
        container.getChildren().add(createTitle());
        container.getChildren().add(createFormContent());

        // Error message area
        container.getChildren().add(createStatusLog("                                            "));

        getChildren().add(container);

        populateFields();
    }

    protected void populateFields()
    {
        getEntryTableModelValues();
    }

    protected void getEntryTableModelValues()
    {
        System.out.println("getEntryTableModelValues: are you getting here, stupid?");
        ObservableList<BookTableModel> tableData = FXCollections.observableArrayList();
        try
        {
            BookCollection bookCollection = (BookCollection)myModel.getState("BookList");

            Vector entryList = (Vector)bookCollection.getState("Books");
            //System.out.println("Anh and Liz get a book collection of size: " + entryList.size());
            Enumeration entries = entryList.elements();

            while (entries.hasMoreElements() == true)
            {
                Book nextBook = (Book)entries.nextElement();
                Vector<String> view = nextBook.getEntryListView();

                // add this list entry to the list
                BookTableModel nextTableRowData = new BookTableModel(view);
                tableData.add(nextTableRowData);

            }

            tableOfBooks.setItems(tableData);
        }
        catch (Exception e) {//SQLException e) {
            // Need to handle this exception
        }
    }

    private Node createTitle()
    {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);

        Text titleText = new Text(" LIBRARY SYSTEM ");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setWrappingWidth(300);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.BLACK);
        container.getChildren().add(titleText);

        return container;
    }

    private VBox createFormContent()
    {
        VBox vbox = new VBox(10);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text prompt = new Text("LIST OF BOOKS");
        prompt.setWrappingWidth(350);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        tableOfBooks = new TableView<BookTableModel>();
        tableOfBooks.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        /*TableColumn bookNumberColumn = new TableColumn("Book ID") ;
        bookNumberColumn.setMinWidth(100);
        bookNumberColumn.setCellValueFactory(
                new PropertyValueFactory<BookTableModel, String>("bookId")); */

        TableColumn bookTitleColumn = new TableColumn("Book Title") ;
        bookTitleColumn.setMinWidth(200);
        bookTitleColumn.setCellValueFactory(
                new PropertyValueFactory<BookTableModel, String>("bookTitle"));

        TableColumn authorColumn = new TableColumn("Author") ;
        authorColumn.setMinWidth(100);
        authorColumn.setCellValueFactory(
                new PropertyValueFactory<BookTableModel, String>("author"));

        TableColumn pubYearColumn = new TableColumn("Publication Year") ;
        pubYearColumn.setMinWidth(100);
        pubYearColumn.setCellValueFactory(
                new PropertyValueFactory<BookTableModel, String>("pubYear"));

        TableColumn statusColumn = new TableColumn("Status") ;
        statusColumn.setMinWidth(100);
        statusColumn.setCellValueFactory(
                new PropertyValueFactory<BookTableModel, String>("status"));

        tableOfBooks.getColumns().addAll(/*bookNumberColumn, */
                bookTitleColumn, authorColumn, pubYearColumn, statusColumn);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(115, 150);
        scrollPane.setContent(tableOfBooks);

        cancelButton = new Button("Back");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                /**
                 * Process the Cancel button.
                 * Achieve low coupling by programming to the interface, not the implementation!
                 * The model changes views, not the view itself.
                 * This method informs the model that the inquiry is cancelled, and the model handles it.
                 */
                //----------------------------------------------------------
                clearErrorMessage();
                myModel.stateChangeRequest("CancelSearchBooks", null);
            }
        });

        HBox btnContainer = new HBox(100);
        btnContainer.setAlignment(Pos.CENTER);
        btnContainer.getChildren().add(cancelButton);

        vbox.getChildren().add(grid);
        vbox.getChildren().add(scrollPane);
        vbox.getChildren().add(btnContainer);

        return vbox;
    }

    public void updateState(String key, Object value)
    {
    }

    protected MessageView createStatusLog(String initialMessage)
    {
        statusLog = new MessageView(initialMessage);

        return statusLog;
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