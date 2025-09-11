package com.udacity.hotel.ui;


import java.util.*;

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


public class GuiAdminMenu
{
	Stage ms;
	Scene ams;
	
	GuiAdminMenu(Stage menuStage)
	{
		BorderPane pane = new BorderPane();
		
		Text title = new Text("Admin Menu");
		pane.setTop(title);
		
		VBox vbox = new VBox();
		pane.setLeft(vbox);
		
		Hyperlink item1 = new Hyperlink("See All Customers?");
		Hyperlink item2 = new Hyperlink("See All Rooms?");
		Hyperlink item3 = new Hyperlink("See All Reservations?");
		Hyperlink item4 = new Hyperlink("Add A Room?");
		Hyperlink item5 = new Hyperlink("Back To Main Menu?");
		vbox.getChildren().addAll(item1, item2, item3, item4, item5);
		
		ms = menuStage;
		
		item5.setOnAction( e -> ms.setScene(mms) );
		
		ams = new Scene(pane);
	}
}
