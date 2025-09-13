package com.udacity.hotel.ui;


//import java.util.*;

import java.util.Optional;

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
//import javafx.scene.text.*;

import static javafx.application.Platform.exit;


public class GuiMainMenu
{
	AdminResource AR;
	
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
		AR = AdminResource.getInstance();
		
		BorderPane pane = new BorderPane();
		
		Text title = new Text("Main Menu");
		title.setUnderline(true);
		title.setFont(new Font(20));
		pane.setTop(title);
		
		VBox vbox = new VBox();
		vbox.setSpacing(15.0d);
		pane.setLeft(vbox);
		
		items_txt = new String[]{
			"Reserve A Room",
			"Change A Reservation",
			"Cancel A Reservation",
			"See My Reservations",
			"Create An Account",
			"Goto Admin Menu",
			"Exit APP"
		};
		
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
		items[items_txt.length - 2].setOnAction( e -> 
																							{
																								PassWordDialog pwDialog = new PassWordDialog();
																								Optional<String> result = pwDialog.showAndWait();
																								
																								int pwHash = AR.getPasswordHash();
																								
																								String pw;
																								
																								if(result.isPresent())
																									pw = result.get();
																								else
																									return;
																								
																								if(pwHash == pw.hashCode())
																									ms.setScene(ams);
																								return;
																							});
																							
		items[items_txt.length - 1].setOnAction( e -> exit() );
	}
}
