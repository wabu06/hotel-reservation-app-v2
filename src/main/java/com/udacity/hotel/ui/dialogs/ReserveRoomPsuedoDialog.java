package com.udacity.hotel.ui.dialogs;



import javafx.stage.*;
import javafx.scene.Scene;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.geometry.Pos;

import javafx.event.ActionEvent;

//import javafx.scene.paint.Color;

import com.udacity.hotel.resources.*;
import static com.udacity.hotel.resources.UtilityResource.*;

import java.util.Optional;
import java.util.Date;
import java.util.Calendar;

import java.time.LocalDate;
import java.time.ZoneId;

import com.udacity.hotel.models.*;



public class ReserveRoomPsuedoDialog
{
	private HotelResource HR;
	
	private Stage dialogStage;
	private Scene dialogScene;
	
	private DatePicker checkInDatePicker;
  private DatePicker checkOutDatePicker;
  
  private Date cid, cod;
  
  private ToggleGroup rtg; // room type toggle group
  
  private String roomType;
  
  private boolean okButtonClicked = false;
	
	public ReserveRoomPsuedoDialog(Stage owner)
	{
		HR = HotelResource.getInstance();
		
		rtg = new ToggleGroup();
		
		Label rtl = new Label("Room Type:");
		
		RadioButton srb = new RadioButton("Single ($" + Room.getSinglePrice() + ")");
    srb.setToggleGroup(rtg);
    srb.setUserData("Single");
    
    RadioButton drb = new RadioButton("Double ($" + Room.getDoublePrice() + ")");
    drb.setToggleGroup(rtg);
    drb.setUserData("Double");
    
    VBox rtBox = new VBox(rtl, srb, drb);
    rtBox.setSpacing(5.0d);
    
    Label cidl = new Label("Check In Date:");
    checkInDatePicker = new DatePicker();
    
    Label codl = new Label("Check Out Date:");
    checkOutDatePicker = new DatePicker();
    
    VBox dBox = new VBox(cidl, checkInDatePicker, codl, checkOutDatePicker);
    dBox.setSpacing(5.0d);
    
    Button okBttn = new Button("OK");
		//okBttn.setOnAction(e -> dialogStage.hide());
		okBttn.setOnAction(this::getAndParseDialogData);

		Button cancelBttn = new Button("CANCEL");
		cancelBttn.setOnAction( e ->	{
																		okButtonClicked = false;
																		dialogStage.hide();
																	});
																	
		HBox bttnBox = new HBox(okBttn, cancelBttn);
		bttnBox.setSpacing(5.0d);
		
		VBox dialogBox = new VBox(rtBox, new Separator(), dBox, new Separator(), bttnBox);
		dialogBox.setSpacing(5.0d);
		dialogBox.setAlignment(Pos.TOP_CENTER);
		
		dialogScene = new Scene(dialogBox);
		
		dialogStage = new Stage(StageStyle.UTILITY);
		
		dialogStage.initOwner(owner);
		dialogStage.initModality(Modality.APPLICATION_MODAL);
		
		dialogStage.setWidth(250);
		dialogStage.setHeight(300);
		
		dialogStage.setScene(dialogScene);
	}
	
	private void getAndParseDialogData(ActionEvent e)
	{
		if(rtg.getSelectedToggle() == null)
		{
			var alert = new Alert(Alert.AlertType.ERROR, "Please Select A Room Type!!");
			alert.initStyle(StageStyle.UNDECORATED);
			alert.showAndWait();
			return;
		}
		else
			roomType = rtg.getSelectedToggle().getUserData().toString();
			
		LocalDate cild = checkInDatePicker.getValue();
		
		if(cild == null)
		{
			var alert = new Alert(Alert.AlertType.ERROR, "Please Select A Check In Date!!");
			alert.initStyle(StageStyle.UNDECORATED);
			alert.showAndWait();
			return;		
		}
		else
			cid = Date.from( cild.atTime(11, 0).atZone( ZoneId.systemDefault() ).toInstant() );
		
		if( cid.before( Calendar.getInstance().getTime() ) )
		{
			var alert = new Alert(Alert.AlertType.ERROR, "Check In Date, Cannot Precede Current Date");
			alert.initStyle(StageStyle.UNDECORATED);
			alert.showAndWait();
			return;	
		}
			
		LocalDate cold = checkOutDatePicker.getValue();
		
		if(cold == null)
		{
			var alert = new Alert(Alert.AlertType.ERROR, "Please Select A Check Out Date!!");
			alert.initStyle(StageStyle.UNDECORATED);
			alert.showAndWait();
			return;		
		}
		else
			cod = Date.from( cold.atTime(15, 0).atZone( ZoneId.systemDefault() ).toInstant() );
		
		if( cod.before( Calendar.getInstance().getTime() ) )
		{
			var alert = new Alert(Alert.AlertType.ERROR, "Check Out Date, Cannot Precede Current Date");
			alert.initStyle(StageStyle.UNDECORATED);
			alert.showAndWait();
			return;
		}
		
		if( !cid.before(cod) )
		{
			var alert = new Alert(Alert.AlertType.ERROR, "The check in date must precede the check out date!!");
			alert.initStyle(StageStyle.UNDECORATED);
			alert.showAndWait();
			return;
		}
			
		okButtonClicked = true;
		
		dialogStage.hide();
	}
	
	public RoomType getRoomType()
	{
		if(roomType.compareTo("Single") == 0)
			return RoomType.SINGLE;
		else
			return RoomType.DOUBLE;
	}
	
	public Date getCheckInDate() {
	 return cid;
	}
	
	public Date getCheckOutDate() {
		return cod;
	}
	
	public void show() {
		dialogStage.showAndWait();
	}
	
	public boolean okButtonWasClicked() {
		return okButtonClicked;
	}
}


