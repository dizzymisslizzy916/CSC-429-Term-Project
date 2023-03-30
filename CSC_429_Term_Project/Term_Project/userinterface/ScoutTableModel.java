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
	private final SimpleStringProperty dateStatusUpdated;

    public ScoutTableModel(Vector<String> scoutData) { //constructor
        lastName = new SimpleStringProperty(scoutData.elementAt(0));
        firstName = new SimpleStringProperty(scoutData.elementAt(1));
        middleName = new SimpleStringProperty(scoutData.elementAt(2));
        dateOfBirth = new SimpleStringProperty(scoutData.elementAt(3));
		phoneNumber = new SimpleStringProperty(scoutData.elementAt(4));
		email = new SimpleStringProperty(scoutData.elementAt(5));
		troopId = new SimpleStringProperty(scoutData.elementAt(6));
		status = new SimpleStringProperty(scoutData.elementAt(7));
		dateStatusUpdated = new SimpleStringProperty(scoutData.elementAt(8));
	}

    //setters and getters
    public String getLastName() {return lastName.get();}

    public void setLastName(String lastNameInput) {lastName.set(lastNameInput);}

    public String getFirstName() {return firstName.get();}

    public void setFirstName(String firstNameInput) {firstName.set(firstNameInput);}

    public String getMiddleName() {return middleName.get();}

    public void setMiddleName(String middleNameInput) {middleName.set(middleNameInput);}

    public String getDateOfBirth() {
        return dateOfBirth.get();
    }

    public void setDateOfBirth(String dateOfBirthInput) {dateOfBirth.set(dateOfBirthInput);}
	
	public String getPhoneNumber() {return phoneNumber.get();}

    public void setPhoneNumber(String phoneNumberInput) {phoneNumber.set(phoneNumberInput);}
	
	public String getEmail() {return email.get();}

    public void setEmail(String emailInput) {email.set(emailInput);}
	
	public String getTroopId() {return troopId.get();}

    public void setTroopId(String troopIdInput) {troopId.set(troopIdInput);}
	
	public String getStatus() {return status.get();}

    public void setStatus(String statusInput) {status.set(statusInput);}
	
	public String getDateStatusUpdated() {return dateStatusUpdated.get();}

    public void setDateStatusUpdated(String dateStatusUpdatedInput) {dateStatusUpdated.set(dateStatusUpdatedInput);}
}
