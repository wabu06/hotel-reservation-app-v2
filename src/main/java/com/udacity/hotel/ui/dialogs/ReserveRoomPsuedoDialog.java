package com.udacity.hotel.ui.dialogs;



import javafx.stage.*;
import javafx.scene.Scene;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

//import javafx.geometry.Pos;

import javafx.event.ActionEvent;

import javafx.scene.paint.Color;

import com.udacity.hotel.resources.*;
import static com.udacity.hotel.resources.UtilityResource.*;

import java.util.Optional;


public class ReserveRoomPsuedoDialog
{
	private HotelResource HR;
	
	private Stage dialogStage;
	private Scene dialogScene;
	
	private TextField etf;
	private PasswordField ptf;
	
	private DatePicker checkInDatePicker;
  private DatePicker checkOutDatePicker;
	
	ReserveRoomPsuedoDialog(Stage owner)
	{
		HR = HotelResource.getInstance();
		
		Label el = new Label("Email:");
		etf = new TextField();
		HBox ebox = new HBox(el, etf);
		
		Label pl = new Label("Pin:");
		ptf = new PasswordField();
		HBox pbox = new HBox(pl, ptf);
		
		VBox dialogBox = new VBox(ebox, pbox);
		dialogBox.setSpacing(5.0d);
		dialogBox.setAlignment(Pos.TOP_CENTER);
		
		dialogScene = new Scene(dialogBox);
		
		dialogStage = new Stage(StageStyle.UTILITY);
		
		dialogStage.initOwner(owner);
		dialogStage.initModality(Modality.APPLICATION_MODAL);
		
		dialogStage.setWidth(350);
		dialogStage.setHeight(250);
		
		dialogStage.setScene(dialogScene);
	}
}


