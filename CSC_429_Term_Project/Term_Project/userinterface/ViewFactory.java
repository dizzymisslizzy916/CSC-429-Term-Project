package userinterface;

import impresario.IModel;

//==============================================================================
public class ViewFactory {

	public static View createView(String viewName, IModel model)
	{
		if(viewName.equals("DeleteScoutConfirmView") == true)
		{
			return new DeleteScoutConfirmView(model);
		}
		/*else if(viewName.equals("InsertScoutTransactionView") == true)
		{
			return new InsertScoutTransactionView(model);
		}*/
		else if(viewName.equals("ScoutInfoEntryView") == true)
		{
			return new ScoutInfoEntryView(model);
		}
		else if(viewName.equals("SearchScoutView") == true)
		{
			return new SearchScoutView(model);
		}
		else if(viewName.equals("TransactionChoiceView") == true)
		{
			return new TransactionChoiceView(model);
		}
		else if(viewName.equals("UpdateScoutView") == true)
		{
			return new UpdateScoutView(model);
		}
		//Todo: views from remaining use cases
		else
			return null;
	}


	/*
	public static Vector createVectorView(String viewName, IModel model)
	{
		if(viewName.equals("SOME VIEW NAME") == true)
		{
			//return [A NEW VECTOR VIEW OF THAT NAME TYPE]
		}
		else
			return null;
	}
	*/

}
