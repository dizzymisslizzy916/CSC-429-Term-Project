package userinterface;

import impresario.IModel;

//==============================================================================
public class ViewFactory {

	public static View createView(String viewName, IModel model)
	{
		if(viewName.equals("LibrarianView") == true) {
			return new LibrarianView(model);
		}
		else if (viewName.equals("InsertScoutView") == true) {
			return new InsertScoutView(model);
		}
		else if (viewName.equals("InsertTreeView") == true) {
			return new InsertTreeView(model);
		}
		else if (viewName.equals("InsertTreeTypeView") == true) {
			return new InsertTreeTypeView(model);
		}
		else if (viewName.equals("SearchScoutView") == true) {
			return new SearchScoutView(model);
		}
		else if (viewName.equals("ScoutCollectionView") == true) {
			return new ScoutCollectionView(model);
		}
		else if (viewName.equals("UpdateScoutView") == true) {
			return new UpdateScoutView(model);
		}
		else if (viewName.equals("TreeBarCodeEntryView") == true) {
			return new TreeBarCodeEntryView(model);
		}
		else if (viewName.equals("TreeTypeBarCodeEntryView") == true) {
			return new TreeTypeBarCodeEntryView(model);
		}
		else if (viewName.equals("TreeCollectionView") == true) {
			return new TreeBarCodeEntryView(model);
		}
		else if (viewName.equals("UpdateTreeView") == true) {
			return new UpdateTreeView(model);
		}
		else if (viewName.equals("UpdateTreeTypeView") == true) {
			return new UpdateTreeTypeView(model);
		}
		else if (viewName.equals("DeleteScoutConfirmView") == true) {
			return new DeleteScoutConfirmView(model);
		}
		else if (viewName.equals("DeleteTreeConfirmView") == true) {
			return new DeleteTreeConfirmView(model);
		}
		else if (viewName.equals("SessionInfoView") == true) {
			return new SessionInfoView(model);
		}
		else if (viewName.equals("ShiftInfoEntryView") == true) {
			return new ShiftInfoEntryView(model);
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
