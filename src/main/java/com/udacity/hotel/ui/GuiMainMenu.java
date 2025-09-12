package com.udacity.hotel.ui;


//import java.util.*;

import com.udacity.hotel.models.*;
import com.udacity.hotel.services.*;
import com.udacity.hotel.resources.*;
import static com.udacity.hotel.resources.UtilityResource.*;

import javafx.stage.Stage;

import javafx.scene.Scene;
//import javafx.scene.Node;

import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;

import javafx.scene.control.Hyperlink;
import javafx.scene.text.Text;

import static javafx.application.Platform.exit;


public class GuiMainMenu
{
	Stage ms; // menu stage
	Scene mms; // main menu scene
	
	String[] items_txt;
	Hyperlink[] items;
	
	final private static GuiMainMenu gmm = new GuiMainMenu();

	public static GuiMainMenu getInstance() {
		return gmm;
	}
	
	private GuiMainMenu()
	{
		BorderPane pane = new BorderPane();
		
		Text title = new Text("Main Menu");
		pane.setTop(title);
		
		VBox vbox = new VBox();
		pane.setLeft(vbox);
		
		items_txt = new String[]{"Reserve A Room", "See My Reservations", "Create An Account", "Goto Admin Menu", "Exit APP"};
		items = new Hyperlink[items_txt.length];
		
		for(int t = 0; t < items_txt.length; t++)
			items[t] = new Hyperlink(items_txt[t]);
		
		vbox.getChildren().addAll(items);
			
		mms = new Scene(pane);
	}
	
	public void setStage(Stage menuStage) {
		ms = menuStage;
	}
	
	public Scene getMenu() {
		return mms;
	}
	
	public void setAdminAndExitActionHandlers(Scene ams)
	{
		items[items_txt.length - 2].setOnAction( e -> ms.setScene(ams) );
		items[items_txt.length - 1].setOnAction( e -> exit() );
	}
}
