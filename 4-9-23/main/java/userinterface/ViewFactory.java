package userinterface;

import impresario.IModel;

//==============================================================================
public class ViewFactory {

	public static View createView(String viewName, IModel model) {
		if (viewName.equals("LibrarianView") == true) {
			return new LibrarianView(model);
		} else if (viewName.equals("InsertScoutView") == true) {
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
		else if (viewName.equals("TreeBarCodeEntryView") == true) {
			return new TreeBarCodeEntryView(model);
		}
		else if (viewName.equals("TreeCollectionView") == true) {
			return new UpdateTreeView(model);
		}


		else
			return null;
	}
}