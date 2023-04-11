package userinterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;

public class TreeTypeTableModel {
    private final SimpleStringProperty typeDescription;
    private final SimpleStringProperty cost;
    private final SimpleStringProperty barcodePrefix;

    public TreeTypeTableModel(Vector<String> treeTypeData) { //constructor
        typeDescription = new SimpleStringProperty(treeTypeData.elementAt(0));
        cost = new SimpleStringProperty(treeTypeData.elementAt(1));
        barcodePrefix = new SimpleStringProperty(treeTypeData.elementAt(2));
    }

    //setters and getters

    public String getTreeTypeDescription() {return typeDescription.get();}

    public void setTreeTypeDescription(String typeDescriptionInput) {typeDescription.set(typeDescriptionInput);}

    public String getCost() {return cost.get();}

    public void setCost(String costInput) {cost.set(costInput);}

    public String getBarCodePrefix() {
        return barcodePrefix.get();
    }

    public void setBarCodePrefix(String barCodePrefixInput) {barcodePrefix.set(barCodePrefixInput);}
}
