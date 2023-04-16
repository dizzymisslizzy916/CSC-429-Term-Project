/*package model;

import exception.InvalidPrimaryKeyException;
import impresario.IView;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import database.*;


public class Session extends EntityBase implements IView {
    public static final String myTableName = "Session";

    protected Properties dependencies;
    private String updateStatusMessage = "";



    public Session(String id) throws InvalidPrimaryKeyException {
        super(myTableName);

        setDependencies();


        String query = String.format("SELECT * FROM %s WHERE (ID = '%s')", myTableName, id);

        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null) {
            int size = allDataRetrieved.size();

            // There should be exactly one session. Any more will be an error.
            if (size != 1) {
                throw new InvalidPrimaryKeyException("Multiple Sessions matching id: "
                        + id + " found.");
            }
            else {
                // Copy all retrived data into persistent state.
                Properties retrievedSessionData = allDataRetrieved.elementAt(0);
                persistentState = new Properties();

                Enumeration allKeys = retrievedSessionData.propertyNames();
                while (allKeys.hasMoreElements()) {
                    String nextKey = (String) allKeys.nextElement();
                    String nextValue = retrievedSessionData.getProperty(nextKey);

                    if (nextValue != null) {
                        persistentState.setProperty(nextKey, nextValue);
                    }
                }
            }

        }
    }

    public Session(Properties props) {
        super(myTableName);
        setDependencies();


        persistentState = new Properties();

        Enumeration allKeys = props.propertyNames();
        while (allKeys.hasMoreElements()) {
            String nextKey = (String) allKeys.nextElement();
            String nextValue = props.getProperty(nextKey);

            if (nextValue != null) {
                persistentState.setProperty(nextKey, nextValue);
            }
        }
    }

    private Session() {
        super(myTableName);

        setDependencies();

    }

    private void setDependencies() {
        dependencies = new Properties();

        myRegistry.setDependencies(dependencies);
    }

    /**
    //--------------------------------------------------------------------------
    public void update() {
        updateStateInDatabase();
    }

    /**
    //--------------------------------------------------------------------------
    private void updateStateInDatabase()
    {
        try
        {
            if (persistentState.getProperty("ID") != null)
            {
                Properties whereClause = new Properties();
                whereClause.setProperty("ID",
                        persistentState.getProperty("ID"));
                updatePersistentState(mySchema, persistentState, whereClause);
                System.out.println("Running");
                updateStatusMessage = "Id: " + persistentState.getProperty("ID") + " updated successfully in database!";
            }
            else
            {
                Integer ID =
                        insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty("ID", "" + ID);
                updateStatusMessage = "New Session: #" +  persistentState.getProperty("ID")
                        + " installed successfully in database!";
            }
        }
        catch (SQLException ex)
        {
            updateStatusMessage = "Error in installing Session data in database!";
        }
        //DEBUG System.out.println("updateStateInDatabase " + updateStatusMessage);
    }



    public Object getState(String key) {
        if (key.equals("UpdateStatusMessage")== true)
            return updateStatusMessage;

        return persistentState.getProperty(key);
    }


    public void stateChangeRequest(String key, Object value)
    {

        myRegistry.updateSubscribers(key, this);
    }

    public BigDecimal getStartingCash() {
        String cashAmount = persistentState.getProperty("StartingCash");
        return new BigDecimal(cashAmount);
    }

    public void setEndingCash(BigDecimal amount) {
        amount = amount.setScale(2, BigDecimal.ROUND_DOWN);

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        df.setGroupingUsed(false);

        persistentState.setProperty("EndingCash", df.format(amount));
    }

    public void setTotalCheckTransactionsAmount(BigDecimal amount) {
        amount = amount.setScale(2, BigDecimal.ROUND_DOWN);

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        df.setGroupingUsed(false);

        persistentState.setProperty("TotalCheckTransactionsAmount", df.format(amount));
    }

    public void setEndTime(LocalTime time) {
        persistentState.setProperty("EndTime",
                time.format(DateTimeFormatter.ofPattern("HH:mm")));
    }

    public static Session findOpenSession() throws InvalidPrimaryKeyException {
        String query = "SELECT * FROM "+myTableName+" WHERE (EndTime = '<empty>')";

        Session session = new Session();

        Vector<Properties> allDataRetrieved = session.getSelectQueryResult(query);

        if (allDataRetrieved != null) {
            int size = allDataRetrieved.size();

            // There should be exactly one session. Any more will be an error.
            if (size != 1) {
                throw new InvalidPrimaryKeyException("No Open Session Found");
            }
            else {
                // Copy all retrived data into persistent state.
                Properties retrievedSessionData = allDataRetrieved.elementAt(0);
                session.persistentState = new Properties();

                Enumeration allKeys = retrievedSessionData.propertyNames();
                while (allKeys.hasMoreElements()) {
                    String nextKey = (String) allKeys.nextElement();
                    String nextValue = retrievedSessionData.getProperty(nextKey);

                    if (nextValue != null) {
                        session.persistentState.setProperty(nextKey, nextValue);
                    }
                }
            }
        }
        else {
            throw new InvalidPrimaryKeyException("Multiple open Sessions Found");
        }

        return session;
    }

    protected void initializeSchema(String tableName) {
        if (mySchema == null) {
            mySchema = getSchemaInfo(tableName);
        }
    }

}*/