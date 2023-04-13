// specify the package
package model;

// system imports
import java.util.Properties;
import java.util.Vector;
import javafx.scene.Scene;

// project imports
import exception.InvalidPrimaryKeyException;
import event.Event;
import database.*;

import impresario.IView;



/** The class containing the ScoutCollection */
//==============================================================
public class ScoutCollection  extends EntityBase implements IView
{
    private static final String myTableName = "Scout";
    private Vector<Scout> scoutList;
    // GUI Components

    // constructor for this class
    //----------------------------------------------------------
    public ScoutCollection() {
        super(myTableName);

        scoutList = new Vector<>();
    }
    //----------------------------------------------------------------------------------
    public void helper(String query)throws Exception{
        Vector allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null) {
            scoutList = new Vector <Scout>();

            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++) //for all items in vector
            {
                Properties nextScoutData = (Properties)allDataRetrieved.elementAt(cnt); //get next scout

                Scout scout = new Scout(nextScoutData);

                if (scout != null)
                {
                    addScout(scout); //add scout to collection
                }
            }

        } else {
            throw new Exception("No Scout found");
        }
    }
    //----------------------------------------------------------------------------------
    //methods for pulling SQL result if needed

    //----------------------------------------------------------------------------------
    public void findScoutsWithNameLike(String fName) throws Exception {
        String query = "SELECT * FROM " + myTableName + " WHERE (firstName LIKE '%" + fName + "%')";
        helper(query);
    }
    public void addScout(Scout b)
    {
        //scout.add(b);
        int index = findIndexToAdd(b);
        scoutList.insertElementAt(b,index); // To build up a collection sorted on some key
    }

    //----------------------------------------------------------------------------------
    public int findIndexToAdd(Scout b)
    {
        //users.add(u);
        int low=0;
        int high = scoutList.size()-1;
        int middle;

        while (low <=high)
        {
            middle = (low+high)/2;

            Scout midSession = scoutList.elementAt(middle);

            int result = Scout.compare(b,midSession);

            if (result ==0)
            {
                return middle;
            }
            else if (result<0)
            {
                high=middle-1;
            }
            else
            {
                low=middle+1;
            }


        }
        return low;
    }


    /**
     *
     */
    //----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("Scouts"))
            return scoutList;
        else
        if (key.equals("ScoutList")) //is this correct?
            return this;
        return null;
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {

        myRegistry.updateSubscribers(key, this);
    }

    //----------------------------------------------------------
    public Scout retrieve(String scoutId) //input scot ID
    {
        Scout retValue = null;
        for (int cnt = 0; cnt < scoutList.size(); cnt++) //for all scouts in the collection
        {
            Scout nextScout = scoutList.elementAt(cnt); //retrieve next Scout object
            String nextScoutId = (String)nextScout.getState("scoutId"); //retrieve its scout ID
            if (scoutId.equals(nextScoutId) == true) //if input scout ID matches retrieved scout ID
            {
                retValue = nextScout; //return that scout
                return retValue; // we should say 'break;' here
            }
        }

        return retValue;
    }

    //----------------------------------------------------------
    public Scout retrieveByTroopId(String troopId) //input scot ID
    {
        Scout retValue = null;
        for (int cnt = 0; cnt < scoutList.size(); cnt++) //for all scouts in the collection
        {
            Scout nextScout = scoutList.elementAt(cnt); //retrieve next Scout object
            String nextTroopId = (String)nextScout.getState("troopId"); //retrieve its scout ID
            if (troopId.equals(nextTroopId) == true) //if input scout ID matches retrieved scout ID
            {
                retValue = nextScout; //return that scout
                return retValue; // we should say 'break;' here
            }
        }

        return retValue;
    }

    /** Called via the IView relationship */
    //----------------------------------------------------------
    public void updateState(String key, Object value)
    {
        stateChangeRequest(key, value);
    }

    //------------------------------------------------------
    public void display(){

        String retVal = "";
        if (scoutList != null)
        {
            for (int cnt = 0; cnt < scoutList.size(); cnt++)
            {
                Scout b = scoutList.get(cnt);
                System.out.println(b.toString());
            }
        }
    }
    //-----------------------------------------------------------------------------------
    protected void initializeSchema(String tableName)
    {
        if (mySchema == null)
        {
            mySchema = getSchemaInfo(tableName);
        }
    }
}
