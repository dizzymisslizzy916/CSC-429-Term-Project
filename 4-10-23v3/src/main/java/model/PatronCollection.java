// specify the package
package model;

// system imports

import event.Event;
import exception.InvalidPrimaryKeyException;
import impresario.IView;

import java.util.Properties;
import java.util.Vector;


/** The class containing the PatronCollection */
//==============================================================
public class PatronCollection extends EntityBase
{
	private static final String myTableName = "Patron";

	private Vector<Patron> patronList;

	// constructor for this class
	//----------------------------------------------------------
	public PatronCollection() {
		super(myTableName);
		patronList = new Vector<>();
	}
	// helper method to return query
	//----------------------------------------------------------
	public void helper(String query) throws Exception
	{

		Vector allDataRetrieved = getSelectQueryResult(query);

		if (allDataRetrieved != null)
		{
			patronList = new Vector<Patron>();

			for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
			{
				Properties nextPatronData = (Properties)allDataRetrieved.elementAt(cnt);

				Patron patron = new Patron(nextPatronData);

				if (patron != null)
				{
					addPatron(patron);
				}
			}

		}
		else
		{
			throw new Exception("No Patron found");
		}

	}

	//----------------------------------------------------------------------------------

	public void findPatronsOlderThan(String date) throws Exception {
		String query = "SELECT * FROM " + myTableName + " WHERE (dateOfBirth < " + date + ")";
		helper(query);

	}
	public void findPatronsYoungerThan(String date) throws Exception {
		String query = "SELECT * FROM " + myTableName + " WHERE (dateOfBirth > " + date + ")";
		helper(query);
	}

	public void findPatronsAtZipCode(String zip) throws Exception {
		String query = "SELECT * FROM " + myTableName + " WHERE (zip = " + zip + ")";
		helper(query);
	}

	public void findPatronsWithNameLike(String name) throws Exception {
		String query = "SELECT * FROM " + myTableName + " WHERE (name LIKE '%" + name + "%')";
		helper(query);
	}


	public void addPatron(Patron a)
	{
		//accounts.add(a);
		int index = findIndexToAdd(a);
		patronList.insertElementAt(a,index); // To build up a collection sorted on some key
	}

	//----------------------------------------------------------------------------------
	public int findIndexToAdd(Patron a)
	{
		//users.add(u);
		int low=0;
		int high = patronList.size()-1;
		int middle;

		while (low <=high)
		{
			middle = (low+high)/2;

			Patron midSession = patronList.elementAt(middle);

			int result = Patron.compare(a,midSession);

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
	//----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("Patrons"))
			return patronList;
		else
		if (key.equals("PatronList"))
			return this;
		return null;
	}

	//----------------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		
		myRegistry.updateSubscribers(key, this);
	}

	//----------------------------------------------------------

	/** Called via the IView relationship */
	//----------------------------------------------------------
	public void updateState(String key, Object value)
	{
		stateChangeRequest(key, value);
	}

	//------------------------------------------------------


	//-----------------------------------------------------------------------------------
	public void initializeSchema(String tableName)
	{
		if (mySchema == null)
		{
			mySchema = getSchemaInfo(tableName);
		}
	}
	public void display(){

		String retVal = "";
		if (patronList != null)
		{
			for (int cnt = 0; cnt < patronList.size(); cnt++)
			{
				Patron b = patronList.get(cnt);
				System.out.println(b.toString());
			}
		}
	}
}
