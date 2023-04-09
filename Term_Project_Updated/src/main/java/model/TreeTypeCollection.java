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



/** The class containing the TreeTypeCollection */
//==============================================================
public class TreeTypeCollection  extends EntityBase implements IView
{
    private static final String myTableName = "TreeType";
    private Vector<TreeType> treeTypeList;
    // GUI Components

    // constructor for this class
    //----------------------------------------------------------
    public TreeTypeCollection() {
        super(myTableName);

        treeTypeList = new Vector<>();
    }
    //----------------------------------------------------------------------------------
    public void helper(String query)throws Exception{
        Vector allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null) {
            treeTypeList = new Vector <TreeType>();

            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++) //for all items in vector
            {
                Properties nextTreeTypeData = (Properties)allDataRetrieved.elementAt(cnt); //get next tree type

                TreeType treeType = new TreeType(nextTreeTypeData);

                if (treeType != null)
                {
                    addTreeType(treeType); //add treetype to collection
                }
            }

        } else {
            throw new Exception("No Tree Type found");
        }
    }
    //----------------------------------------------------------------------------------
    //methods for pulling SQL result if needed

    //----------------------------------------------------------------------------------
    public void findTreeTypeWithBarcodePrefixLike(String barCode) throws Exception {
        String query = "SELECT * FROM " + myTableName + " WHERE (barcodePrefix LIKE '%" + barCode + "%')";
        helper(query);
    }
    public void addTreeType(TreeType b)
    {
        int index = findIndexToAdd(b);
        treeTypeList.insertElementAt(b,index); // To build up a collection sorted on some key
    }

    //----------------------------------------------------------------------------------
    public int findIndexToAdd(TreeType b)
    {
        //users.add(u);
        int low=0;
        int high = treeTypeList.size()-1;
        int middle;

        while (low <=high)
        {
            middle = (low+high)/2;

            TreeType midSession = treeTypeList.elementAt(middle);

            int result = TreeType.compare(b,midSession);

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
        if (key.equals("TreeType"))
            return treeTypeList;
        else
        if (key.equals("TreeTypeList"))
            return this;
        return null;
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {

        myRegistry.updateSubscribers(key, this);
    }

    //----------------------------------------------------------
    public TreeType retrieve(String Id)
    {
        TreeType retValue = null;
        for (int cnt = 0; cnt < treeTypeList.size(); cnt++) //for all tree type in the collection
        {
            TreeType nextTreeType = treeTypeList.elementAt(cnt); //retrieve next tree type object
            String nextTreeTypeId = (String)nextTreeType.getState("Id"); //retrieve its scout ID
            if (Id.equals(Id) == true) //if input tree type ID matches retrieved ID
            {
                retValue = nextTreeType; //return that scout
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
        if (treeTypeList != null)
        {
            for (int cnt = 0; cnt < treeTypeList.size(); cnt++)
            {
                TreeType b = treeTypeList.get(cnt);
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
