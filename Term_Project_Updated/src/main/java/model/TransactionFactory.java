// specify the package
package model;

// system imports


// project imports

/** The class containing the TransactionFactory for the ATM application */
//==============================================================
public class TransactionFactory
{

	/**
	 *
	 */
	//----------------------------------------------------------
	public static Transaction createTransaction(String transType) throws Exception
	{
		Transaction retValue = null;

		if (transType.equals("InsertScout") == true)
		{
			retValue = new InsertScoutTransaction();
		}
		else if (transType.equals("InsertTree") == true)
		{
			retValue = new InsertTreeTransaction();
		}
		else if (transType.equals("InsertTreeType") == true)
		{
			retValue = new InsertTreeTypeTransaction();
		}
		else if (transType.equals("SearchScout") == true)
		{
			retValue = new SearchScoutTransaction();
		}


		return retValue;
	}
}
