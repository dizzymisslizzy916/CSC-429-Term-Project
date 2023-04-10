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



/** The class containing the TreeCollection */
//==============================================================
public class TreeCollection  extends EntityBase implements IView
{
    private static final String myTableName = "Tree";
    private Vector<Tree> treeList;
    // GUI Components

    // constructor for this class
    //----------------------------------------------------------
    public TreeCollection() {
        super(myTableName);

        treeList = new Vector<>();
    }
    //----------------------------------------------------------------------------------
    public void helper(String query)throws Exception{
        Vector allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null) {
            treeList = new Vector <Tree>();

            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++) //for all items in vector
            {
                Properties nextTreeData = (Properties)allDataRetrieved.elementAt(cnt); //get next tree

                Tree tree = new Tree(nextTreeData);

                if (tree != null)
                {
                    addTree(tree); //add tree to collection
                }
            }

        } else {
            throw new Exception("No Tree found");
        }
    }
    //----------------------------------------------------------------------------------


    //----------------------------------------------------------------------------------
    public void addTree(Tree b)
    {
        //trees.add(b);
        int index = findIndexToAdd(b);
        treeList.insertElementAt(b,index); // To build up a collection sorted on some key
    }

    //----------------------------------------------------------------------------------
    public int findIndexToAdd(Tree b)
    {
        //users.add(u);
        int low=0;
        int high = treeList.size()-1;
        int middle;

        while (low <=high)
        {
            middle = (low+high)/2;

            Tree midSession = treeList.elementAt(middle);

            int result = Tree.compare(b,midSession);

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
        if (key.equals("Trees"))
            return treeList;
        else
        if (key.equals("TreeList")) //is this correct?
            return this;
        return null;
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {

        myRegistry.updateSubscribers(key, this);
    }

    //----------------------------------------------------------
    public Tree retrieve(String barCode) //input tree barcode
    {
        Tree retValue = null;
        for (int cnt = 0; cnt < treeList.size(); cnt++) //for all trees in the collection
        {
            Tree nextTree = treeList.elementAt(cnt); //retrieve next Tree object
            String nextTreeCode = (String)nextTree.getState("barCode"); //retrieve its tree ID
            if (barCode.equals(barCode) == true) //if input tree ID matches retrieved tree ID
            {
                retValue = nextTree; //return that tree
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
        if (treeList != null)
        {
            for (int cnt = 0; cnt < treeList.size(); cnt++)
            {
                Tree b = treeList.get(cnt);
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
