package com.udacity.hotel.ui;



import java.util.*;

import com.udacity.hotel.models.*;
import com.udacity.hotel.services.*;
import com.udacity.hotel.resources.*;
import static com.udacity.hotel.resources.UtilityResource.*;

import javafx.application.Application;

//import static javafx.application.Platform.exit;

import javafx.stage.Stage;

import javafx.scene.Scene;
//import javafx.scene.Node;

//import javafx.scene.layout.VBox;
//import javafx.scene.layout.BorderPane;

//import javafx.scene.control.Hyperlink;
//import javafx.scene.text.Text;



public class MenuWindow extends Application
{
	Stage ms;
	//Scene mms;
	//Scene ams;
	
	GuiMainMenu gmm;
	GuiAdminMenu gam;
	
	@Override
	public void start(Stage menuStage)
	{
		ms = menuStage;
		//mms = mainMenuScene();
		//ams = adminMenuScene();
		
		menuStage.setTitle("WABU's Hotel Reservation Application v1.0.0");
		menuStage.setWidth(450);
    menuStage.setHeight(350);
    
    gmm = GuiMainMenu.getInstance();
    gam = GuiAdminMenu.getInstance();
    
    gmm.setStage(ms);
    gam.setStage(ms);
    
    gmm.setAdminAndExitActionHandlers(gam.getMenu());
    gam.setMainMenuActionHandler(gmm.getMenu());
    
    menuStage.setScene(gmm.getMenu());
		menuStage.show();
	}
}

