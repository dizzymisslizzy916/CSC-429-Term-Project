package userinterface;

import impresario.IModel;
import model.Book;

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
		else if (viewName.equals("SearchScoutView") == true) {
			return new SearchScoutView(model);
		}

		else if (viewName.equals("InsertPatronView") == true) {
			return new InsertPatronView(model);
		}
		else if (viewName.equals("InsertBookView") == true) {
			return new InsertBookView(model);
		}
		else if (viewName.equals("SearchBookView") == true) {
			return new SearchBookView(model);
		}
		else if (viewName.equals("SearchPatronView") == true) {
			return new SearchPatronView(model);
		}
		else if (viewName.equals("BookCollectionView") == true) {
			return new BookCollectionView(model);
		}
		else if (viewName.equals("PatronCollectionView") == true) {
			return new PatronCollectionView(model);
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
