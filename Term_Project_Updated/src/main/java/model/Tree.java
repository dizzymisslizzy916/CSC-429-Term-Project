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



/** The class containing the Tree */
//==============================================================
public class Tree extends EntityBase implements IView
{
    private static final String myTableName = "Tree";

    protected Properties dependencies;

    // GUI Components

    private String updateStatusMessage = "";

    private boolean oldFlag = true;

    // constructor for this class
    //----------------------------------------------------------
    public Tree(String barCode)
            throws InvalidPrimaryKeyException
    {
        super(myTableName);

        setDependencies();
        String query = "SELECT * FROM " + myTableName + " WHERE (barCode = '" + barCode + "')";

        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        // You must get one account at least
        if (allDataRetrieved != null)
        {
            int size = allDataRetrieved.size();

            // There should be EXACTLY one account. More than that is an error
            if (size != 1)
            {
                throw new InvalidPrimaryKeyException("Multiple accounts matching Bar Code : "
                        + barCode + " found.");
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
            throw new InvalidPrimaryKeyException("No Tree matching BarCode : "
                    + barCode + " found.");
        }
    }

    // Can also be used to create a NEW Account (if the system it is part of
    // allows for a new account to be set up)
    //----------------------------------------------------------
    public Tree(Properties props)
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
        try
        {
            if (oldFlag == true)
            {
                System.out.println("Getting here - tree update");
                Properties whereClause = new Properties();
                whereClause.setProperty("barCode",
                        persistentState.getProperty("barCode"));
                updatePersistentState(mySchema, persistentState, whereClause);
                System.out.println("Getting here - finished updating tree");
                updateStatusMessage = "Tree with barcode: " + persistentState.getProperty("barCode") + " updated successfully in database!";
            }
            else
            {
                System.out.println("Getting here 1");
                insertPersistentState(mySchema, persistentState);
                oldFlag = true;
                updateStatusMessage = "Tree with barcode: " +  persistentState.getProperty("barCode")
                        + "installed successfully in database!";
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Getting here 2");
            System.out.println(ex);
            updateStatusMessage = "Error in installing tree data in database!";

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

        //v.addElement(persistentState.getProperty("barCode"));
        v.addElement(persistentState.getProperty("treeType"));
        v.addElement(persistentState.getProperty("Notes"));
        v.addElement(persistentState.getProperty("Status"));
        v.addElement(persistentState.getProperty("dateStatusUpdated"));

        return v;
    }

    //-----------------------------------------------------------------------------------
    public String toString() {
        return "Tree Type " + persistentState.getProperty("treeType") + "; Notes: " +
                persistentState.getProperty("Notes") + "; Status " +
                persistentState.getProperty("Status");
    }

    public static int compare(Tree a, Tree b) {
        String aNum = (String)a.getState("barCode");
        String bNum = (String)b.getState("barCode");

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

