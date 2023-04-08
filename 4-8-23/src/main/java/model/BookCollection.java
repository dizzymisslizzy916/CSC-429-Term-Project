// specify the package
package model;

// system imports
import java.util.Properties;
import java.util.Vector;
import javafx.scene.Scene;

// project imports
import exception.InvalidPrimaryKeyException;
import event.Event;
import database.*;

import impresario.IView;



/** The class containing the BookCollection */
//==============================================================
public class BookCollection  extends EntityBase implements IView
{
    private static final String myTableName = "Book";
    private Vector<Book> bookList;
    // GUI Components

    // constructor for this class
    //----------------------------------------------------------
    public BookCollection() {
        super(myTableName);

        bookList = new Vector<>();
    }
    //----------------------------------------------------------------------------------
    public void helper(String query)throws Exception{
        Vector allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null) {
            bookList = new Vector <Book>();

            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++) //for all items in vector
            {
                Properties nextBookData = (Properties)allDataRetrieved.elementAt(cnt); //get next book

                Book book = new Book(nextBookData);

                if (book != null)
                {
                    addBook(book); //add book to collection
                }
            }

        } else {
            throw new Exception("No Book found");
        }
    }
    //----------------------------------------------------------------------------------

    public void findBooksOlderThanDate(String year) throws Exception {
        String query = "SELECT * FROM " + myTableName + " WHERE (pubYear <= " + year + ")";
        helper(query);
    }

    //----------------------------------------------------------------------------------
    public void findBooksNewerThanDate(String year) throws Exception {
        String query = "SELECT * FROM " + myTableName + " WHERE (pubYear >= " + year + ")";
        helper(query);
    }

    //----------------------------------------------------------------------------------
    public void findBooksWithTitleLike(String title) throws Exception {
        String query = "SELECT * FROM " + myTableName + " WHERE (bookTitle LIKE '%" + title + "%')";
        helper(query);
    }

    //----------------------------------------------------------------------------------
    public void findBooksWithAuthorLike(String author) throws Exception {
        String query = "SELECT * FROM " + myTableName + " WHERE (author LIKE '%" + author + "%')";
        helper(query);
    }

    //----------------------------------------------------------------------------------
    public void addBook(Book b)
    {
        //books.add(b);
        int index = findIndexToAdd(b);
        bookList.insertElementAt(b,index); // To build up a collection sorted on some key
    }

    //----------------------------------------------------------------------------------
    public int findIndexToAdd(Book b)
    {
        //users.add(u);
        int low=0;
        int high = bookList.size()-1;
        int middle;

        while (low <=high)
        {
            middle = (low+high)/2;

            Book midSession = bookList.elementAt(middle);

            int result = Book.compare(b,midSession);

            if (result ==0)
            {
                return middle;
            }
            else if (result<0)
            {
                high=middle-1;
            }
            else
            {
                low=middle+1;
            }


        }
        return low;
    }


    /**
     *
     */
    //----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("Books"))
            return bookList;
        else
        if (key.equals("BookList")) //is this correct?
            return this;
        return null;
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {

        myRegistry.updateSubscribers(key, this);
    }

    //----------------------------------------------------------
    public Book retrieve(String bookId) //input book ID
    {
        Book retValue = null;
        for (int cnt = 0; cnt < bookList.size(); cnt++) //for all books in the collection
        {
            Book nextBook = bookList.elementAt(cnt); //retrieve next Book object
            String nextBookId = (String)nextBook.getState("bookId"); //retrieve its book ID
            if (bookId.equals(bookId) == true) //if input book ID matches retrieved book ID
            {
                retValue = nextBook; //return that book
                return retValue; // we should say 'break;' here
            }
        }

        return retValue;
    }

    /** Called via the IView relationship */
    //----------------------------------------------------------
    public void updateState(String key, Object value)
    {
        stateChangeRequest(key, value);
    }

    //------------------------------------------------------
    public void display(){

        String retVal = "";
        if (bookList != null)
        {
            for (int cnt = 0; cnt < bookList.size(); cnt++)
            {
                Book b = bookList.get(cnt);
                System.out.println(b.toString());
            }
        }
    }
    //-----------------------------------------------------------------------------------
    protected void initializeSchema(String tableName)
    {
        if (mySchema == null)
        {
            mySchema = getSchemaInfo(tableName);
        }
    }
}
