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



/** The class containing the SaleTransactionCollection */
//==============================================================
public class SaleTransactionCollection extends EntityBase implements IView
{
    private static final String myTableName = "Transaction";
    private Vector<SaleTransaction> transList;
    // GUI Components

    // constructor for this class
    //----------------------------------------------------------
    public SaleTransactionCollection() {
        super(myTableName);

        transList = new Vector<>();
    }
    //----------------------------------------------------------------------------------
    public void helper(String query)throws Exception{
        Vector allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null) {
            transList = new Vector <SaleTransaction>();

            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++) //for all items in vector
            {
                Properties nextTransData = (Properties)allDataRetrieved.elementAt(cnt); //get next scout

                SaleTransaction saleTransaction = new SaleTransaction(nextTransData);

                if (saleTransaction != null)
                {
                    System.out.println("sale transaction: " + saleTransaction);
                    addSaleTransaction(saleTransaction); //add scout to collection
                }
            }

        } else {
            throw new Exception("No Transaction found");
        }
    }
    //----------------------------------------------------------------------------------
    //methods for pulling SQL result if needed

    //----------------------------------------------------------------------------------
   public void findTransactionsForSession(String sessionID) throws Exception {
        String query = "SELECT * FROM " + myTableName + " WHERE (sessionId = " + sessionID + ")";
        helper(query);
    }

    //--------------------------------------------------------------------------------------
    public void addSaleTransaction(SaleTransaction b)
    {
        //scout.add(b);
        int index = findIndexToAdd(b);
        transList.insertElementAt(b,index); // To build up a collection sorted on some key
    }

    //----------------------------------------------------------------------------------
    public int findIndexToAdd(SaleTransaction b)
    {
        //users.add(u);
        int low=0;
        int high = transList.size()-1;
        int middle;

        while (low <=high)
        {
            middle = (low+high)/2;

            SaleTransaction midSession = transList.elementAt(middle);

            int result = SaleTransaction.compare(b,midSession);

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
        if (key.equals("Transaction"))
            return transList;
        else
        if (key.equals("TransactionList")) //is this correct?
            return this;
        return null;
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {

        myRegistry.updateSubscribers(key, this);
    }

    //----------------------------------------------------------
    public int getSize()
    {
        if (transList != null)
        {
            return transList.size();
        }
        else
        {
            return 0;
        }
    }

    //----------------------------------------------------------
    public SaleTransaction retrieve(int index)
    {
        if (transList != null)
            return transList.get(index);
        else
            return null;
    }

    //----------------------------------------------------------
    public SaleTransaction retrieve(String transId) //input scot ID
    {
        SaleTransaction retValue = null;
        for (int cnt = 0; cnt < transList.size(); cnt++) //for all scouts in the collection
        {
            SaleTransaction nextTrans = transList.elementAt(cnt); //retrieve next Scout object
            String nextTransId = (String)nextTrans.getState("transactionId"); //retrieve its scout ID
            if (transId.equals(nextTransId) == true) //if input scout ID matches retrieved scout ID
            {
                retValue = nextTrans; //return that scout
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
        if (transList != null)
        {
            for (int cnt = 0; cnt < transList.size(); cnt++)
            {
                SaleTransaction b = transList.get(cnt);
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
