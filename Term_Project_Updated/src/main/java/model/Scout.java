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



/** The class containing the Scout */
//==============================================================
public class Scout extends EntityBase implements IView
{
    private static final String myTableName = "Scout";

    protected Properties dependencies;

    // GUI Components

    private String updateStatusMessage = "";

    // constructor for this class
    //----------------------------------------------------------
    public Scout(String troopId) //query with troopId, not scoutId- see sequence diagrams
            throws InvalidPrimaryKeyException
    {
        super(myTableName);

        setDependencies();
        String query = "SELECT * FROM " + myTableName + " WHERE (troopId = " + troopId + ")";

        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        // You must get one account at least
        if (allDataRetrieved != null)
        {
            int size = allDataRetrieved.size();

            // There should be EXACTLY one account. More than that is an error
            if (size != 1)
            {
                throw new InvalidPrimaryKeyException("Multiple scouts matching id : "
                        + troopId + " found.");
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
                    // scoutId = Integer.parseInt(retrievedAccountData.getProperty("scoutId"));

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
            throw new InvalidPrimaryKeyException("No scout matching troop id : "
                    + troopId + " found.");
        }
    }

    // Can also be used to create a NEW Account (if the system it is part of
    // allows for a new account to be set up)
    //----------------------------------------------------------
    public Scout(Properties props)
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
        System.out.println("Trying to save scout in db");
        try
        {
            if (persistentState.getProperty("scoutId") != null)
            {
                System.out.println("Trying to update scout in db");
                Properties whereClause = new Properties();
                whereClause.setProperty("scoutId",
                        persistentState.getProperty("scoutId"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "ScoutId: " + persistentState.getProperty("scoutId") + " updated successfully in database!";
            }

            else
            {
                System.out.println("Trying to insert scout in db");
                Integer accountNumber =
                        insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty("scoutId", "" + accountNumber.intValue());
                updateStatusMessage = "ScoutId for new Scout: " +  persistentState.getProperty("scoutId")
                        + " installed successfully in database!";
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex);
            ex.printStackTrace();
            updateStatusMessage = "Error in installing scout data in database!";
        }
    }


    /**
     * This method is needed solely to enable the Account information to be displayable in a table
     *
     */
    //--------------------------------------------------------------------------
    public Vector<String> getEntryListView()
    {
        Vector<String> v = new Vector<String>();

        //v.addElement(persistentState.getProperty("scoutId"));
        v.addElement(persistentState.getProperty("lastName"));
        v.addElement(persistentState.getProperty("firstName"));
        v.addElement(persistentState.getProperty("middleName"));
        v.addElement(persistentState.getProperty("dateOfBirth"));
        v.addElement(persistentState.getProperty("phoneNumber"));
        v.addElement(persistentState.getProperty("email"));
        v.addElement(persistentState.getProperty("troopId"));
        v.addElement(persistentState.getProperty("status"));
        v.addElement(persistentState.getProperty("dateStatusUpdated"));


        return v;
    }

    //-----------------------------------------------------------------------------------
    public String toString() {
        return "Scout Name " + persistentState.getProperty("lastName") + ", " + persistentState.getProperty("firstName") +
                "; Date Of Birth: " + persistentState.getProperty("dateOfBirth") +
                "; Email: " + persistentState.getProperty("email");
    }

    public static int compare(Scout a, Scout b) {
        String aNum = (String)a.getState("scoutId");
        String bNum = (String)b.getState("scoutId");

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
