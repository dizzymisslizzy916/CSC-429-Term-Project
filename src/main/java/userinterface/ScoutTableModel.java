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
    public String getScoutLastName() {return lastName.get();}
    public void setScoutLastName(String lName) {lastName.set(lName);}
    public String getScoutFirstName() {return firstName.get();}
    public void setScoutFirstName(String fName) {firstName.set(fName);}
    public String getScoutMiddleName() {return middleName.get();}
    public void setScoutMiddleName(String mName) {firstName.set(mName);}
    public String getScoutDOB() {return dateOfBirth.get();}
    public void setScoutDOB(String dob) {firstName.set(dob);}
    public String getScoutPhoneNumber() {return phoneNumber.get();}
    public void setScoutPhoneNumber(String pNumber) {firstName.set(pNumber);}
    public String getScoutEmail() {return email.get();}
    public void setScoutEmail(String e) {email.set(e);}
    public String getScoutTroopId() {return troopId.get();}
    public void setScoutTroopId(String troop) {troopId.set(troop);}

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String statusInput) {status.set(statusInput);}
}
