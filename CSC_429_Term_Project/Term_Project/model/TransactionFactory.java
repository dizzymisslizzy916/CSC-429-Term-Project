// specify the package
package model;

// system imports
import java.util.Vector;
import javax.swing.JFrame;

// project imports

/** The class containing the TransactionFactory for the ATM application */
//==============================================================
public class TransactionFactory
{

	/**
	 *
	 */
	//----------------------------------------------------------
	public static Transaction createTransaction(String transType)
		throws Exception
	{
		Transaction retValue = null;

		if (transType.equals("RegisterScout") == true)
		{
			retValue = new InsertScoutTransaction();
		}
		/*else
		if (transType.equals("AddTree") == true)
		{
			retValue = new InsertTreeTransaction();
		}
		else
		if (transType.equals("AddTreeType") == true)
		{
			retValue = new InsertTreeTypeTransaction();
		}
		else
		if (transType.equals("UpdateTree") == true)
		{
			retValue = new TreeTransaction();
		}
		else
		if (transType.equals("UpdateTreeType") == true)
		{
			retValue = new UpdateTreeTypeTransaction();
		}*/
		else
		if (transType.equals("UpdateScout") == true)
		{
			retValue = new ScoutUpdateTransaction();
		}
		/*else
		if (transType.equals("SellTree") == true)
		{
			retValue = new TreeTransaction();
		}
		else
		if (transType.equals("RemoveTree") == true)
		{
			retValue = new TreeTransaction();
		}*/
		else
		if (transType.equals("RemoveScout") == true)
		{
			retValue = new ScoutUpdateTransaction();
		}
		/*else
		if (transType.equals("StartShift") == true)
		{
			retValue = new StartShiftTransaction();
		}
		else
		if (transType.equals("EndShift") == true)
		{
			retValue = new EndShiftTransaction();
		}*/
		return retValue;
	}
}

