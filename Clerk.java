package model;


import event.Event;
import impresario.IModel;
import impresario.IView;
import impresario.ModelRegistry;
import javafx.scene.Scene;
import javafx.stage.Stage;
import userinterface.MainStageContainer;
import userinterface.View;
import userinterface.WindowPosition;
import java.util.Hashtable;
import java.util.Properties;
import userinterface.ClerkViewFactory;

public class Clerk implements IView, IModel
{
 private Properties dependencies;
 private ModelRegistry myRegistry;
 private Hashtable<String, Scene> myViews;
 private Stage myStage;
 private String loginErrorMessage = "";
 private String transactionErrorMessage = "";


 public Clerk()
 {
	myStage = MainStageContainer.getInstance();
	myViews = new Hashtable<String, Scene>();
	myRegistry = new ModelRegistry("Clerk");
	if(myRegistry == null)
	{
		new Event(Event.getLeafLevelClassName(this), "Clerk",
		"Could not instantiate Registry", Event.ERROR);
	}
	
	setDependencies();
	createAndShowClerkView();
	}
	
	private void setDependencies()
	{
		dependencies = new Properties();
		//add dependencies based on what we need
		myRegistry.setDependencies(dependencies);
	}
	
	public Object getState(String key)
	{
		//add any errors
		if (key.equals("error name") == true)
		{
			return loginErrorMessage;
		}
		else
			return "";
	}
	
	public void stateChangeRequest(String key, Object value)
	{
		if (key.equals("AddScout") == true)
		{
			Blank();
		}
		else if (key.equals("AddTree") == true)
		{
			Blank();
		}
		else if (key.equals("AddTreeType") == true)
		{
			Blank();
		}
		else if (key.equals("UpdateScout") == true)
		{
			Blank();
		}
		else if (key.equals("UpdateTree") == true)
		{
			Blank();
		}
		else if (key.equals("UpdateTreeType") == true)
		{
			Blank();
		}
		else if (key.equals("StartShift") == true)
		{
			Blank();
		}
		else if (key.equals("EndShift") == true)
		{
			Blank();
		}
		else if (key.equals("SellTree") == true)
		{
			Blank();
		}
		else if (key.equals("RemoveTree") == true)
		{
			Blank();
		}
		else if (key.equals("RemoveScout") == true)
		{
			Blank();
		}
		else if (key.equals("Done") == true)
		{
			createAndShowClerkView();
		}
	}
	
	public void updateState(String key, Object value)
	{

		stateChangeRequest(key, value);
	}
	
	public void doTransaction(String transactionType)
	{
		//add for type of transaction probably based on the four different actions
		//update further.
	}
	
	public void subscribe(String key, IView subscriber)
	{
		
		// forward to our registry
		myRegistry.subscribe(key, subscriber);
	}

	/** Unregister previously registered objects. */
	//----------------------------------------------------------
	public void unSubscribe(String key, IView subscriber)
	{
		// DEBUG: System.out.println("Cager.unSubscribe");
		// forward to our registry
		myRegistry.unSubscribe(key, subscriber);
	}
	//Area where views are created
	//--------------------------------
	private void createAndShowClerkView()
	{
		Scene currentScene = (Scene)myViews.get("ClerkView");

		if (currentScene == null)
		{
			// create our initial view
			View newView = LibraryViewFactory.createView("ClerkView", this); // USE VIEW FACTORY
			if(newView == null){
				System.out.println("It's null.");
			}
			currentScene = new Scene(newView);
			myViews.put("ClerkView", currentScene);
		}
				
		swapToView(currentScene);
		
	}
	
	private void createAndShowInsertBookView()
	{
		Scene currentScene = (Scene)myViews.get("InsertBookView");
		
		if (currentScene == null)
		{
			
			View newView = LibraryViewFactory.createView("InsertBookView", this); // USE VIEW FACTORY
			currentScene = new Scene(newView);
			myViews.put("InsertBookView", currentScene);
		}
				

		// make the view visible by installing it into the frame
		swapToView(currentScene);
	}
	
	
	
	
	//---------------------------------------------
	public void swapToView(Scene newScene)
	{
		if (newScene == null)
		{
			System.out.println("Clerk.swapToView(): Missing view for display");
			new Event(Event.getLeafLevelClassName(this), "swapToView",
				"Missing view for display ", Event.ERROR);
			return;
		} else if (newScene != null){
			System.out.println("The scene is NOT null.");
		}
		if (myStage == null){
			System.out.println("The stage is null.");
		} else if (myStage != null){
			System.out.println("The stage is NOT null.");
		}

		myStage.setScene(newScene);
		myStage.sizeToScene();


		//Place in center
		WindowPosition.placeCenter(myStage);

	}
	
}