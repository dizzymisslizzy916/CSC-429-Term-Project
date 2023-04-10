package userinterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;

public class PatronTableModel {

    private final SimpleStringProperty name;
    private final SimpleStringProperty address;
    private final SimpleStringProperty city;
    private final SimpleStringProperty zip;
    private final SimpleStringProperty email;
    private final SimpleStringProperty dateOfBirth;
    private final SimpleStringProperty status;

    public PatronTableModel(Vector<String> patronData) { //constructor
        name = new SimpleStringProperty(patronData.elementAt(0));
        address = new SimpleStringProperty(patronData.elementAt(1));
        city = new SimpleStringProperty(patronData.elementAt(2));
        zip = new SimpleStringProperty(patronData.elementAt(3));
        email = new SimpleStringProperty(patronData.elementAt(4));
        dateOfBirth = new SimpleStringProperty(patronData.elementAt(5));
        status = new SimpleStringProperty(patronData.elementAt(6));
    }

    //setters and getters

    public String getName() { return name.get();}

    public void setName(String nameInput) {name.set(nameInput);}

    public String getAddress() { return address.get();}

    public void setAddress(String addressInput) {address.set(addressInput);}

    public String getCity() { return city.get();}

    public void setCity(String cityInput) {city.set(cityInput);}

    public String getZip() { return zip.get();}

    public void setZip(String zipInput) {zip.set(zipInput);}

    public String getEmail() { return email.get();}

    public void setEmail(String emailInput) {email.set(emailInput);}

    public String getDateOfBirth() { return dateOfBirth.get();}

    public void setDateOfBirth(String dateOfBirthInput) {dateOfBirth.set(dateOfBirthInput);}

    public String getStatus() { return status.get(); }

    public void setStatus(String statusInput) {status.set(statusInput);}


}