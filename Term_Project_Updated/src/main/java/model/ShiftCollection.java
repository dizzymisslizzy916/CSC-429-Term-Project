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



/** The class containing the ShiftCollection */
//==============================================================
public class ShiftCollection  extends EntityBase implements IView
{
    private static final String myTableName = "Shift";
    private Vector<Shift> shiftList;
    // GUI Components

    // constructor for this class
    //----------------------------------------------------------
    public ShiftCollection() {
        super(myTableName);

        shiftList = new Vector<>();
    }
    //----------------------------------------------------------------------------------
    public void helper(String query)throws Exception{
        Vector allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null) {
            shiftList = new Vector <Shift>();

            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++) //for all items in vector
            {
                Properties nextShiftData = (Properties)allDataRetrieved.elementAt(cnt); //get next shift

                Shift shift = new Shift(nextShiftData);

                if (shift != null)
                {
                    addShift(shift); //add shift to collection
                }
            }

        } else {
            throw new Exception("No Shift found");
        }
    }
    //----------------------------------------------------------------------------------


    //----------------------------------------------------------------------------------
    public void addShift(Shift b)
    {
        int index = findIndexToAdd(b);
        shiftList.insertElementAt(b,index); // To build up a collection sorted on some key
    }

    //----------------------------------------------------------------------------------
    public int findIndexToAdd(Shift b)
    {
        //users.add(u);
        int low=0;
        int high = shiftList.size()-1;
        int middle;

        while (low <=high)
        {
            middle = (low+high)/2;

            Shift midSession = shiftList.elementAt(middle);

            int result = Shift.compare(b,midSession);

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
        if (key.equals("Shift"))
            return shiftList;
        else
        if (key.equals("ShiftList"))
            return this;
        return null;
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {

        myRegistry.updateSubscribers(key, this);
    }

    //----------------------------------------------------------
    public Shift retrieve(String Id)
    {
        Shift retValue = null;
        for (int cnt = 0; cnt < shiftList.size(); cnt++)
        {
            Shift nextShift = shiftList.elementAt(cnt);
            String nextShiftId = (String)nextShift.getState("Id");
            if (Id.equals(Id) == true) //if input shift ID matches retrieved shift ID
            {
                retValue = nextShift; //return that shift
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
        if (shiftList != null)
        {
            for (int cnt = 0; cnt < shiftList.size(); cnt++)
            {
                Shift b = shiftList.get(cnt);
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
