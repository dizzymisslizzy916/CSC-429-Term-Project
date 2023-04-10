package userinterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;

public class BookTableModel {
    private final SimpleStringProperty bookTitle;
    private final SimpleStringProperty author;
    private final SimpleStringProperty pubYear;
    private final SimpleStringProperty status;

    public BookTableModel(Vector<String> bookData) { //constructor
        bookTitle = new SimpleStringProperty(bookData.elementAt(0));
        author = new SimpleStringProperty(bookData.elementAt(1));
        pubYear = new SimpleStringProperty(bookData.elementAt(2));
        status = new SimpleStringProperty(bookData.elementAt(3));
    }

    //setters and getters
    public String getBookTitle() {
        return bookTitle.get();
    }

    public void setBookTitle(String bookInput) {bookTitle.set(bookInput);}

    public String getAuthor() {return author.get();}

    public void setAuthor(String authorInput) {author.set(authorInput);}

    public String getPubYear() {return pubYear.get();}

    public void setPubYear(String pubYearInput) {pubYear.set(pubYearInput);}

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String statusInput) {status.set(statusInput);}
}
