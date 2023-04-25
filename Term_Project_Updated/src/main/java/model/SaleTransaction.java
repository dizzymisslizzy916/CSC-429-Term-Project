// specify the package
package model;

// system imports
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import javax.swing.JFrame;

// project imports
import exception.InvalidPrimaryKeyException;
import database.*;

import impresario.IView;



/** The class containing the Sale Transaction */
//==============================================================
public class SaleTransaction extends EntityBase implements IView
{
    private static final String myTableName = "Transaction";

    protected Properties dependencies;

    // GUI Components

    private String updateStatusMessage = "";

    // constructor for this class
    //----------------------------------------------------------
    public SaleTransaction(String transId) //query with id- see sequence diagrams
            throws InvalidPrimaryKeyException
    {
        super(myTableName);

        setDependencies();
        String query = "SELECT * FROM " + myTableName + " WHERE (transactionId = " + transId + ")";

        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        // You must get one account at least
        if (allDataRetrieved != null)
        {
            int size = allDataRetrieved.size();

            // There should be EXACTLY one account. More than that is an error
            if (size != 1)
            {
                throw new InvalidPrimaryKeyException("Multiple transactions matching id : "
                        + transId + " found.");
            }
            else
            {
                // copy all the retrieved data into persistent state
                Properties retrievedTransData = allDataRetrieved.elementAt(0);
                persistentState = new Properties();

                Enumeration allKeys = retrievedTransData.propertyNames();
                while (allKeys.hasMoreElements() == true)
                {
                    String nextKey = (String)allKeys.nextElement();
                    String nextValue = retrievedTransData.getProperty(nextKey);
                    // scoutId = Integer.parseInt(retrievedAccountData.getProperty("scoutId"));

                    if (nextValue != null)
                    {
                        persistentState.setProperty(nextKey, nextValue);
                    }
                }

            }
        }
        // If no transaction found for this id, throw an exception
        else
        {
            throw new InvalidPrimaryKeyException("No transactions matching id : "
                    + transId + " found.");
        }
    }

    // Can also be used to create a NEW Transaction
    //----------------------------------------------------------
    public SaleTransaction(Properties props)
    {
        super(myTableName);

        setDependencies();
        persistentState = new Properties();
        Enumeration allKeys = props.propertyNames();
        while (allKeys.hasMoreElements() == true)
        {
            String nextKey = (String)allKeys.nextElement();
            String nextValue = props.getProperty(nextKey);

            if (nextValue != null)
            {
                persistentState.setProperty(nextKey, nextValue);
            }
        }
    }

    //-----------------------------------------------------------------------------------
    private void setDependencies()
    {
        dependencies = new Properties();

        myRegistry.setDependencies(dependencies);
    }

    //----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("UpdateStatusMessage") == true)
            return updateStatusMessage;

        return persistentState.getProperty(key);
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {
        persistentState.setProperty(key, (String)value);
        myRegistry.updateSubscribers(key, this);
    }

    /** Called via the IView relationship */
    //----------------------------------------------------------
    public void updateState(String key, Object value)
    {
        stateChangeRequest(key, value);
    }

    //-----------------------------------------------------------------------------------
    public void update()
    {
        updateStateInDatabase();
    }

    //-----------------------------------------------------------------------------------
    private void updateStateInDatabase()
    {
        System.out.println("Trying to save transaction in db");
        try
        {
            if (persistentState.getProperty("transactionId") != null)
            {
                System.out.println("Trying to update transaction in db");
                Properties whereClause = new Properties();
                whereClause.setProperty("transactionId",
                        persistentState.getProperty("transactionId"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "Transaction updated successfully in database!";
            }

            else
            {
                System.out.println("Trying to insert transaction in db");
                Integer trID =
                        insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty("transactionId", "" + trID.intValue());
                updateStatusMessage = "Transaction added successfully to database!";
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex);
            ex.printStackTrace();
            updateStatusMessage = "Error in installing transaction data in database!";
        }
    }


    /**
     * This method is needed solely to enable the Transaction information to be displayable in a table
     *
     */
    //--------------------------------------------------------------------------
    public Vector<String> getEntryListView()
    {
        Vector<String> v = new Vector<String>();

        //v.addElement(persistentState.getProperty("scoutId"));
        v.addElement(persistentState.getProperty("transactionType"));
        v.addElement(persistentState.getProperty("barCode"));



        return v;
    }

    //-----------------------------------------------------------------------------------
    //public String toString() {
    //    return "";
    //}

    public static int compare(SaleTransaction a, SaleTransaction b) {
        String aNum = (String)a.getState("transactionId");
        String bNum = (String)b.getState("transactionId");

        return aNum.compareTo(bNum);
    }

    //----------------------------------------------------------------------------------------
    protected void initializeSchema(String tableName)
    {
        if (mySchema == null)
        {
            mySchema = getSchemaInfo(tableName);
        }
    }
}
