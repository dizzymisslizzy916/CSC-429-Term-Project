package userinterface;

import impresario.IModel;

//==============================================================================
public class ViewFactory {

	public static View createView(String viewName, IModel model)
	{
		if(viewName.equals("ClerkView") == true)
		{
			return new ClerkView(model);
		}
		else if(viewName.equals("InsertScoutView") == true)
		{
			return new InsertScoutView(model);
		}
		else if(viewName.equals("SearchScoutView") == true)
		{
			return new SearchScoutView(model);
		}
		else if(viewName.equals("ScoutInfoEntryView") == true)
		{
			return new ScoutInfoEntryView(model);
		}
		else if(viewName.equals("UpdateScoutView") == true)
		{
			return new UpdateScoutView(model);
		}
		else if(viewName.equals("DeleteScoutConfirmView") == true)
		{
			return new DeleteScoutConfirmView(model);
		}
		else if(viewName.equals("TreeBarCodeEntryView") == true)
		{
			return new TreeBarCodeEntryView(model);
		}
		else if(viewName.equals("UpdateTreeView") == true)
		{
			return new UpdateTreeView(model);
		}
		else if(viewName.equals("DeleteTreeConfirmView") == true)
		{
			return new DeleteTreeConfirmView(model);
		}
		else if(viewName.equals("TreeTypeInfoEntryView") == true)
		{
			return new TreeTypeInfoEntryView(model);
		}
		else if(viewName.equals("TreeTypeBarCodeEntryView") == true)
		{
			return new TreeTypeBarCodeEntryView(model);
		}
		else if(viewName.equals("UpdateTreeTypeView") == true)
		{
			return new UpdateTreeTypeView(model);
		}
		else if(viewName.equals("SessionInfoView") == true)
		{
			return new SessionInfoView(model);
		}
		else if(viewName.equals("CashConfirmView") == true)
		{
			return new CashConfirmView(model);
		}
		else if(viewName.equals("ScoutTableModel") == true)
		{
			return new ScoutTableModel(model);
		}
		else if(viewName.equals("TreeTableModel") == true)
		{
			return new TreeTableModel(model);
		}
		else if(viewName.equals("TreeTypeModel") == true)
		{
			return new TreeTypeModel(model);
		}
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
