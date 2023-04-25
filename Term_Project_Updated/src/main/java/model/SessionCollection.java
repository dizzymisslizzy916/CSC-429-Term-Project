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



/** The class containing the SessionCollection */
//==============================================================
public class SessionCollection  extends EntityBase implements IView
{
    private static final String myTableName = "Session";
    private Vector<Session> sessionList;
    // GUI Components

    // constructor for this class
    //----------------------------------------------------------
    public SessionCollection() {
        super(myTableName);

        sessionList = new Vector<>();
    }
    //----------------------------------------------------------------------------------
    public void helper(String query)throws Exception{
        Vector allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null) {
            sessionList = new Vector <Session>();

            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++) //for all items in vector
            {
                Properties nextSessionData = (Properties)allDataRetrieved.elementAt(cnt); //get next tree type

                Session session = new Session(nextSessionData);

                if (session != null)
                {
                    addSession(session); //add session to collection
                }
            }

        } else {
            throw new Exception("No Session found");
        }
    }
    //----------------------------------------------------------------------------------
    //methods for pulling SQL result if needed

    //----------------------------------------------------------------------------------

    public void addSession(Session b)
    {
        int index = findIndexToAdd(b);
        sessionList.insertElementAt(b,index); // To build up a collection sorted on some key
    }

    //----------------------------------------------------------------------------------
    public int findIndexToAdd(Session b)
    {
        //users.add(u);
        int low=0;
        int high = sessionList.size()-1;
        int middle;

        while (low <=high)
        {
            middle = (low+high)/2;

            Session midSession = sessionList.elementAt(middle);

            int result = Session.compare(b,midSession);

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
        if (key.equals("Session"))
            return sessionList;
        else
        if (key.equals("sessionList"))
            return this;
        return null;
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {

        myRegistry.updateSubscribers(key, this);
    }

    //----------------------------------------------------------
    public Session retrieve(String Id)
    {
        Session retValue = null;
        for (int cnt = 0; cnt < sessionList.size(); cnt++) //for all tree type in the collection
        {
            Session nextSession = sessionList.elementAt(cnt); //retrieve next tree type object
            String nextSessionId = (String)nextSession.getState("Id"); //retrieve its scout ID
            if (Id.equals(Id) == true) //if input tree type ID matches retrieved ID
            {
                retValue = nextSession; //return that scout
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
        if (sessionList != null)
        {
            for (int cnt = 0; cnt < sessionList.size(); cnt++)
            {
                Session b = sessionList.get(cnt);
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
