package userinterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;

public class ScoutTableModel {
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty middleName;
    private final SimpleStringProperty dateOfBirth;
    private final SimpleStringProperty phoneNumber;
    private final SimpleStringProperty email;
    private final SimpleStringProperty troopId;
    private final SimpleStringProperty status;

    public ScoutTableModel(Vector<String> scoutData) { //constructor
        lastName = new SimpleStringProperty(scoutData.elementAt(0));
        firstName = new SimpleStringProperty(scoutData.elementAt(1));
        middleName = new SimpleStringProperty(scoutData.elementAt(2));
        dateOfBirth = new SimpleStringProperty(scoutData.elementAt(3));
        phoneNumber = new SimpleStringProperty(scoutData.elementAt(4));
        email = new SimpleStringProperty(scoutData.elementAt(5));
        troopId = new SimpleStringProperty(scoutData.elementAt(6));
        status = new SimpleStringProperty(scoutData.elementAt(7));
    }

    //setters and getters
    public String getLastName() {return lastName.get();}
    public void setLastName(String lName) {lastName.set(lName);}
    public String getFirstName() {return firstName.get();}
    public void setFirstName(String fName) {firstName.set(fName);}
    public String getMiddleName() {return middleName.get();}
    public void setMiddleName(String mName) {middleName.set(mName);}
    public String getDateOfBirth() {return dateOfBirth.get();}
    public void setDateOfBirth(String dob) {dateOfBirth.set(dob);}
    public String getPhoneNumber() {return phoneNumber.get();}
    public void setPhoneNumber(String pNumber) {phoneNumber.set(pNumber);}
    public String getEmail() {return email.get();}
    public void setEmail(String e) {email.set(e);}
    public String getTroopId() {return troopId.get();}
    public void setTroopId(String troop) {troopId.set(troop);}

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String statusInput) {status.set(statusInput);}
}
