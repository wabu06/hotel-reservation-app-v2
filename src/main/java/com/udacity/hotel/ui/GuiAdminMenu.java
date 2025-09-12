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

//import static javafx.application.Platform.exit;


public class GuiAdminMenu
{
	Stage ms; // menu stage
	Scene ams; // admin menu scene
	
	String[] items_txt;
	Hyperlink[] items;
	
	final private static GuiAdminMenu gam = new GuiAdminMenu();

	public static GuiAdminMenu getInstance() {
		return gam;
	}
	
	private GuiAdminMenu()
	{
		BorderPane pane = new BorderPane();
		
		Text title = new Text("Admin Menu");
		pane.setTop(title);
		
		VBox vbox = new VBox();
		pane.setLeft(vbox);
		
		items_txt = new String[]{"See All Customers", "See All Rooms", "See All Reservations", "Add A Room", "Back To Main Menu"};
		items = new Hyperlink[items_txt.length];
		
		for(int t = 0; t < items_txt.length; t++)
			items[t] = new Hyperlink(items_txt[t]);
		
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
		items[items_txt.length - 1].setOnAction( e -> ms.setScene(mms) );
	}
}
