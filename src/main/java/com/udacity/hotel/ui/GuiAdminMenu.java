package com.udacity.hotel.ui;


//import java.util.*;

import java.util.Map;
import java.util.LinkedHashMap;

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
import javafx.scene.text.Font;

//import static javafx.application.Platform.exit;


public class GuiAdminMenu
{
	Stage ms; // menu stage
	Scene ams; // admin menu scene
	
	//String[] items_txt;
	//Hyperlink[] items;
	
	Map<String, Hyperlink> itemsMap;
	
	final private static GuiAdminMenu gam = new GuiAdminMenu();

	public static GuiAdminMenu getInstance() {
		return gam;
	}
	
	private GuiAdminMenu()
	{
		BorderPane pane = new BorderPane();
		
		Text title = new Text("Admin Menu");
		title.setUnderline(true);
		title.setFont(new Font(20));
		pane.setTop(title);
		
		VBox vbox = new VBox();
		//vbox.setSpacing(5.0d);
		pane.setLeft(vbox);
		
		String[] items_txt = new String[]{
			"See All Customers",
			"See All Rooms",
			"See All Reservations",
			"Change Price For Single Rooms",
			"Change Price For Double Rooms",
			"See Current Room Rates",
			"See All Reservations For A Room",
			"Show Rooms Vacant For A Particular Check-in & Check-out Date",
			"Change The Room Of A Reservation",
			"Show All Reservations For A Customer",
			"Back To Main Menu"
		};
		
		//items = new Hyperlink[items_txt.length];
		
//		for(int t = 0; t < items_txt.length; t++)
//			items[t] = new Hyperlink(items_txt[t]);

		itemsMap = new LinkedHashMap<>();
		
		for(String txt: items_txt)
			itemsMap.put(txt, new Hyperlink(txt));
		
		Hyperlink[] items = new Hyperlink[items_txt.length];
		
		items = itemsMap.values().toArray(items);
		
		vbox.getChildren().addAll(items);
		
		ams = new Scene(pane);
	}
	
	public void setStage(Stage menuStage) {
		ms = menuStage;
	}
	
	public Scene getMenu() {
		return ams;
	}
	
	public void setMainMenuActionHandler(Scene mms) {
		//items[items_txt.length - 1].setOnAction( e -> ms.setScene(mms) );
		itemsMap.get("Back To Main Menu").setOnAction( e -> ms.setScene(mms) );
	}
}
