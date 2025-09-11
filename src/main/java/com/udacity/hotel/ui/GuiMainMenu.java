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


public class GuiMainMenu
{
	Stage ms; // menu stage
	Scene mms; // main menu scene
	
	GuiMainMenu(Stage menuStage)
	{
		ms = menuStage;
		
		BorderPane pane = new BorderPane();
		
		Text title = new Text("Main Menu");
		pane.setTop(title);
		
		VBox vbox = new VBox();
		pane.setLeft(vbox);
		
		Hyperlink item1 = new Hyperlink("Find And Reserve A Room?");
		Hyperlink item2 = new Hyperlink("See My Reservations?");
		Hyperlink item3 = new Hyperlink("Create An Account?");
		Hyperlink item4 = new Hyperlink("Goto Admin Menu?");
		Hyperlink item5 = new Hyperlink("Exit APP?");
		vbox.getChildren().addAll(item1, item2, item3, item4, item5);
		
		item4.setOnAction( e -> ms.setScene(ams) );
		item5.setOnAction( e -> exit() );
		
		mms = new Scene(pane); 
	}
	
	Scene getMenu() {
		return mms
	}
}
