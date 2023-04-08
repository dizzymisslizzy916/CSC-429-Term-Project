package userinterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;

public class TreeTableModel {
    private final SimpleStringProperty treeType;
    private final SimpleStringProperty Notes;
    private final SimpleStringProperty status;

    public TreeTableModel(Vector<String> treeData) { //constructor
//        barCode = new SimpleStringProperty(treeData.elementAt(0));
        treeType = new SimpleStringProperty(treeData.elementAt(0));
        Notes = new SimpleStringProperty(treeData.elementAt(1));
        status = new SimpleStringProperty(treeData.elementAt(2));
    }

    //setters and getters

    public String getTreeType() {return treeType.get();}

    public void setTreeType(String treeTypeInput) {treeType.set(treeTypeInput);}

    public String getNotes() {return Notes.get();}

    public void setNotes(String notesInput) {Notes.set(notesInput);}

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String statusInput) {status.set(statusInput);}
}
