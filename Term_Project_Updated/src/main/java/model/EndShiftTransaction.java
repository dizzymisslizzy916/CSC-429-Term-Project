// specify the package
package model;

// system imports
import exception.InvalidPrimaryKeyException;
import javafx.scene.Scene;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;


// project imports
import event.Event;

import userinterface.View;
import userinterface.ViewFactory;

/** The class containing the WithdrawTransaction for the ATM application */
//==============================================================
public class EndShiftTransaction extends Transaction
{
    private Session selectedSession; // needed for GUI only
    private String transactionErrorMessage = "";
    private SaleTransactionCollection salesCollection;
    private ShiftCollection selectedShifts;

    private double endingCashVal = 0;
    private double totalCheckVal = 0;
    private String sessionUpdateStatusMessage = " ";


    public EndShiftTransaction() throws Exception//constructor
    {
        super();
        System.out.println("test");
        selectedSession = new Session();
        selectedShifts = new ShiftCollection();
        try {
            selectedSession.findOpenSession();
            selectedShifts.findOpenShifts(selectedSession);
            String startingCashVal = (String)selectedSession.getState("startingCash");
            double startingCash = Double.parseDouble(startingCashVal);
            salesCollection= new SaleTransactionCollection();
            salesCollection.findTransactionsForSession((String)selectedSession.getState("Id"));
            Vector<SaleTransaction> transList = (Vector) salesCollection.getState("Transaction");
            AtomicReference<Double> transactionCheck = new AtomicReference<>((double) 0);
            AtomicReference<Double> transactionCash = new AtomicReference<>((double) 0);
            transList.forEach(e -> {
                System.out.println("element: " + e);
                int addedMoney = Integer.parseInt(e.persistentState.getProperty("transactionAmount"));
                if(e.persistentState.getProperty("paymentMethod").equals("cash")) {
                    transactionCash.updateAndGet(v -> new Double((double) (v + addedMoney)));
                } else if(e.persistentState.getProperty("paymentMethod").equals("check")){
                    transactionCheck.updateAndGet(v -> new Double((double) (v + addedMoney)));
                } else {
                    System.err.println("what");
                }
            });

            endingCashVal = startingCash + transactionCash.get();
            totalCheckVal = transactionCheck.get();

        }
        catch (InvalidPrimaryKeyException excep)
        {
            excep.printStackTrace();
            transactionErrorMessage = "ERROR: No open shift found to close!";
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("OK", "CancelTransaction");
        dependencies.setProperty("EndShift","ShiftUpdateMessage");
        dependencies.setProperty("CancelSearchShift","CancelTransaction");

        myRegistry.setDependencies(dependencies);
    }

    public void processSession(String sessionId) {
        try {
            //System.out.println("model/UpdateTreeTransactionMethod: processTreeBarcode is running");
            System.out.println(sessionId);
            selectedSession = new Session(sessionId);
            createAndShowSaleConfirmView();


        }catch (Exception ex){
            transactionErrorMessage = "ERROR: Could not find session with Id: " + sessionId;
            new Event(Event.getLeafLevelClassName(this), "processTransaction",
                    "Failed to retrieve a session.",
                    Event.ERROR);

        }
    }

    public void processTransaction() {

        try {
            selectedSession.stateChangeRequest("endingCash", endingCashVal);
            selectedSession.stateChangeRequest("totalCheckTransactionAmount", totalCheckVal);
            DateFormat timeStamp = new SimpleDateFormat("yyyy/MM/dd_hh:mm a");

            Date date = new Date();
            System.out.println(timeStamp.format(date));
            String[] split = timeStamp.format(date).split("_");
            String endDate = split[0];
            String endTime = split[1];



            selectedSession.stateChangeRequest("endDate", endDate);
            selectedSession.stateChangeRequest("endTime", endTime);

            //selectedSession.update();
            sessionUpdateStatusMessage = "Successfully End A Shift";
        }
        catch (Exception e)
        {
            transactionErrorMessage = "ERROR: No Session found!";
        }

    }

    public void processTransaction2(String notes)
    {
        DateFormat timeStamp = new SimpleDateFormat("yyyy/MM/dd_hh:mm a");

        Date date = new Date();
        String[] split = timeStamp.format(date).split("_");
        String endTime = split[1];
        selectedSession.persistentState.setProperty("notes", notes);
        Vector<Shift> shiftList = (Vector<Shift>) selectedShifts.getState("Shift");
        shiftList.forEach(shift -> {
            shift.stateChangeRequest("endTime", endTime);
            shift.update();
        });
        selectedSession.update();
        sessionUpdateStatusMessage = "Session updated successfully!";

    }

    public Object getState(String key) {
        switch(key) {
            case "TransactionError":
                return transactionErrorMessage;
            case "SessionEndCash":
                return endingCashVal;
            case "SessionCheckSales":
                return totalCheckVal;
            case "SessionUpdateStatusMessage":
                return sessionUpdateStatusMessage;
            default:
                return null;
        }
    }


    public void stateChangeRequest(String key, Object value)
    {

        switch(key) {
            case "DoYourJob":
                doYourJob();
                break;
            case "SalesConfirmView":
                createAndShowSaleConfirmView();
                break;
            case "EnterShiftNotesView": {
                createAndShowEnterShiftNotesView();
                break;
            }
            case "SubmitNotes":
                processTransaction();
                processTransaction2((String)value);
                break;
        }
        myRegistry.updateSubscribers(key, this);

    }
    protected void createAndShowSaleConfirmView()
    {
        View v = ViewFactory.createView("SalesConfirmView", this);
        Scene newScene = new Scene(v);

        swapToView(newScene);
    }

    protected void createAndShowEnterShiftNotesView() {
        View v = ViewFactory.createView("EnterShiftNotesView", this);
        Scene newScene = new Scene(v);

        swapToView(newScene);
    }


    protected Scene createView()
    {
        System.out.println("In EndShiftTransaction - create view");

        // create our new view
        View newView = ViewFactory.createView("SalesConfirmView", this);
        Scene currentScene = new Scene(newView);

        System.out.println("In EndShiftTransaction - scene created");
        return currentScene;

    }

//------------------------------------------------------

}