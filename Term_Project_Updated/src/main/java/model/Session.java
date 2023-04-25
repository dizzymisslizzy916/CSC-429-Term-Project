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



/** The class containing the Session */
//==============================================================
public class Session extends EntityBase implements IView
{
    private static final String myTableName = "Session";

    protected Properties dependencies;

    // GUI Components

    private String updateStatusMessage = "";

    // constructor for this class
    //----------------------------------------------------------
    public Session()
    {
        super(myTableName);
        setDependencies();
    }
    public Session(String Id)
            throws InvalidPrimaryKeyException
    {
        super(myTableName);

        setDependencies();
        String query = "SELECT * FROM " + myTableName + " WHERE (Id = '" + Id + "')";

        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        // You must get one account at least
        if (allDataRetrieved != null)
        {
            int size = allDataRetrieved.size();

            // There should be EXACTLY one account. More than that is an error
            if (size != 1)
            {
                throw new InvalidPrimaryKeyException("Multiple Session matching : "
                        + Id + " found.");
            }
            else
            {
                // copy all the retrieved data into persistent state
                Properties retrievedAccountData = allDataRetrieved.elementAt(0);
                persistentState = new Properties();

                Enumeration allKeys = retrievedAccountData.propertyNames();
                while (allKeys.hasMoreElements() == true)
                {
                    String nextKey = (String)allKeys.nextElement();
                    String nextValue = retrievedAccountData.getProperty(nextKey);
                    // Id = Integer.parseInt(retrievedAccountData.getProperty("Id"));

                    if (nextValue != null)
                    {
                        persistentState.setProperty(nextKey, nextValue);
                    }
                }

            }
        }
        // If no account found for this user name, throw an exception
        else
        {
            throw new InvalidPrimaryKeyException("No Session matching Id : "
                    + Id + " found.");
        }
    }

    // Can also be used to create a NEW Account (if the system it is part of
    // allows for a new account to be set up)
    //----------------------------------------------------------
    public Session(Properties props)
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

    public void findOpenSession() throws InvalidPrimaryKeyException {
        setDependencies();
        String query = "SELECT * FROM " + myTableName + " WHERE (endTime IS NULL) OR (endTime = '')";

        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        // You must get one account at least
        if (allDataRetrieved != null)
        {
            int size = allDataRetrieved.size();
            System.out.println("size: " + size);
            // There should be EXACTLY one account. More than that is an error
            if (size != 1)
            {
                throw new InvalidPrimaryKeyException("Multiple Session found");
            }
            else
            {
                // copy all the retrieved data into persistent state
                Properties retrievedAccountData = allDataRetrieved.elementAt(0);
                persistentState = new Properties();

                Enumeration allKeys = retrievedAccountData.propertyNames();
                while (allKeys.hasMoreElements() == true)
                {
                    String nextKey = (String)allKeys.nextElement();
                    String nextValue = retrievedAccountData.getProperty(nextKey);
                    // Id = Integer.parseInt(retrievedAccountData.getProperty("Id"));

                    if (nextValue != null)
                    {
                        persistentState.setProperty(nextKey, nextValue);
                    }
                }

            }
        }
        // If no account found for this user name, throw an exception
        else
        {
            throw new InvalidPrimaryKeyException("No Session found.");
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
        persistentState.setProperty(key, value.toString());
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
        try
        {
            if (persistentState.getProperty("Id") != null)
            {
                Properties whereClause = new Properties();
                whereClause.setProperty("Id",
                        persistentState.getProperty("Id"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "Id: " + persistentState.getProperty("Id") + " updated successfully in database!";
            }
            else
            {
                Integer treetypeId =
                        insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty("Id", "" + treetypeId);
                updateStatusMessage = "Id for new Tree Type: " +  persistentState.getProperty("Id")
                        + " installed successfully in database!";
            }
        }
        catch (SQLException ex)
        {
            updateStatusMessage = "Error in installing Tree Type data in database!";
        }
        //DEBUG System.out.println("updateStateInDatabase " + updateStatusMessage);
    }


    /**
     * This method is needed solely to enable the Account information to be displayable in a table
     *
     */
    //--------------------------------------------------------------------------
    public Vector<String> getEntryListView()
    {
        Vector<String> v = new Vector<String>();

        v.addElement(persistentState.getProperty("Id"));
        v.addElement(persistentState.getProperty("startDate"));
        v.addElement(persistentState.getProperty("endDate"));
        v.addElement(persistentState.getProperty("startTime"));
        v.addElement(persistentState.getProperty("endTime"));
        v.addElement(persistentState.getProperty("startingCash"));
        v.addElement(persistentState.getProperty("endingCash"));
        v.addElement(persistentState.getProperty("notes"));

        return v;
    }

    //-----------------------------------------------------------------------------------
    public String toString() {
        return "Session Description " + persistentState.getProperty("Id") +
                ", Start Date: " + persistentState.getProperty("startDate") +
                "; Starting Cash: " + persistentState.getProperty("startingCash");
    }

    public static int compare(Session a, Session b) {
        String aNum = (String)a.getState("Id");
        String bNum = (String)b.getState("Id");

        return aNum.compareTo(bNum);
    }
    protected void initializeSchema(String tableName)
    {
        if (mySchema == null)
        {
            mySchema = getSchemaInfo(tableName);
        }
    }


}



