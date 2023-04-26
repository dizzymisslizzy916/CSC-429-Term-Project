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
import userinterface.MessageView;


/** The class containing the Shift */
//==============================================================
public class Shift extends EntityBase implements IView
{
    private static final String myTableName = "Shift";

    protected Properties dependencies;

    // GUI Components

    private String updateStatusMessage = "";

    private boolean oldFlag = true;
   // protected MessageView statusLog;

    // constructor for this class
    //----------------------------------------------------------
    public Shift(String Id)
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
                throw new InvalidPrimaryKeyException("Multiple shift matching Id : "
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
            throw new InvalidPrimaryKeyException("No Shift matching Id : "
                    + Id + " found.");
        }
    }

    // Can also be used to create a NEW Account (if the system it is part of
    // allows for a new account to be set up)
    //----------------------------------------------------------
    public Shift(Properties props)
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
    public void setOldFlag(boolean val)
    {
        oldFlag = val;
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
       /* try {
            if (persistentState.getProperty("Id") != null) {
                Properties whereClause = new Properties();
                whereClause.setProperty("Id",
                        persistentState.getProperty("Id"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "Shift data for ID : " + persistentState.getProperty("Id") + " updated successfully in database!";
            } else {
                Integer id = insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty("Id", "" + id);
                updateStatusMessage = "ID data for new shift: " + persistentState.getProperty("Id")
                        + " installed successfully in database!";
            }
        } catch (SQLException ex){
            updateStatusMessage = "ERROR in installing shift data in database!";
        }
    }*/



        try
        {
            if (oldFlag == true)
            {
                //System.out.println("Getting here - shift update");
                Properties whereClause = new Properties();
                whereClause.setProperty("Id",
                        persistentState.getProperty("Id"));
                updatePersistentState(mySchema, persistentState, whereClause);
                //System.out.println("Getting here - finished updating tree");
                updateStatusMessage = "Shift with Id: " + persistentState.getProperty("Id") + " updated successfully in database!";
            }
            else
            {
                System.out.println("Getting here 1");
                insertPersistentState(mySchema, persistentState);
                oldFlag = true;
                updateStatusMessage = "Shift with Id: " +  persistentState.getProperty("Id")
                        + "installed successfully in database!";
            }
        }
        catch (SQLException ex)
        {
            //System.out.println("Getting here 2");
            System.out.println(ex);
            updateStatusMessage = "Error in installing shift in database!";

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

        //v.addElement(persistentState.getProperty("Id"));
        //v.addElement(persistentState.getProperty("sessionId"));
        v.addElement(persistentState.getProperty("scoutId"));
        v.addElement(persistentState.getProperty("companionName"));
        v.addElement(persistentState.getProperty("startTime"));
        v.addElement(persistentState.getProperty("endTime"));
        v.addElement(persistentState.getProperty("companionHours"));

        return v;
    }

    //-----------------------------------------------------------------------------------
    public String toString() {
        return "Shift Id " + persistentState.getProperty("Id") + "; ScoutId: " +
                persistentState.getProperty("scoutId") + "; Start Time " +
                persistentState.getProperty("startTime");
    }

    public static int compare(Shift a, Shift b) {
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

