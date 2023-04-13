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



/** The class containing the TreeType */
//==============================================================
public class TreeType extends EntityBase implements IView
{
    private static final String myTableName = "TreeType";

    protected Properties dependencies;

    // GUI Components

    private String updateStatusMessage = "";

    // constructor for this class
    //----------------------------------------------------------
    public TreeType()
    {
        super(myTableName);
    }
    public TreeType(String bf)
            throws InvalidPrimaryKeyException
    {
        super(myTableName);

        setDependencies();
        String query = "SELECT * FROM " + myTableName + " WHERE (barcodePrefix = '" + bf + "')";

        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        // You must get one account at least
        if (allDataRetrieved != null)
        {
            int size = allDataRetrieved.size();

            // There should be EXACTLY one account. More than that is an error
            if (size != 1)
            {
                throw new InvalidPrimaryKeyException("Multiple Tree Type matching barcode prefix: "
                        + bf + " found.");
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
            throw new InvalidPrimaryKeyException("No Tree Type matching barcode prefix : "
                    + bf + " found.");
        }
    }

    // Can also be used to create a NEW Account (if the system it is part of
    // allows for a new account to be set up)
    //----------------------------------------------------------
    public TreeType(Properties props)
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

    public void findWithTreeTypeId(String ttId) throws InvalidPrimaryKeyException {
        setDependencies();
        String query = "SELECT * FROM " + myTableName + " WHERE (treeTypeId = " + ttId + ")";

        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        // You must get one account at least
        if (allDataRetrieved != null)
        {
            int size = allDataRetrieved.size();

            // There should be EXACTLY one account. More than that is an error
            if (size != 1)
            {
                throw new InvalidPrimaryKeyException("Multiple Tree Type matching id: "
                        + ttId + " found.");
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
            throw new InvalidPrimaryKeyException("No Tree Type matching id : "
                    + ttId + " found.");
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
        try
        {
            if (persistentState.getProperty("treeTypeId") != null)
            {
                Properties whereClause = new Properties();
                whereClause.setProperty("treeTypeId",
                        persistentState.getProperty("treeTypeId"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "Id: " + persistentState.getProperty("treeTypeId") + " updated successfully in database!";
            }
            else
            {
                Integer treetypeId =
                        insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty("treeTypeId", "" + treetypeId);
                updateStatusMessage = "Id for new Tree Type: " +  persistentState.getProperty("treeTypeId")
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

        v.addElement(persistentState.getProperty("typeDescription"));
        v.addElement(persistentState.getProperty("cost"));
        v.addElement(persistentState.getProperty("barcodePrefix"));


        return v;
    }

    //-----------------------------------------------------------------------------------
    public String toString() {
        return "Tree Type Description " + persistentState.getProperty("typeDescription") +
                ", Cost: " + persistentState.getProperty("cost") +
                "; Bar Code Prefix: " + persistentState.getProperty("barcodePrefix");
    }

    public static int compare(TreeType a, TreeType b) {
        String aNum = (String)a.getState("treeTypeId");
        String bNum = (String)b.getState("treeTypeId");

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



