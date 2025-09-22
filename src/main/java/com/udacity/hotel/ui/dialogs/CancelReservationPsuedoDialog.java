package com.udacity.hotel.ui.dialogs;



import java.util.Collection;

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

import com.udacity.hotel.models.*;


public class CancelReservationPsuedoDialog
{
	private HotelResource HR;
	
	private Stage dialogStage;
	private Scene dialogScene;
	
	private ToggleGroup rtg; // reservation toggle group
	
	private int ID; // reservation ID

	private boolean okButtonClicked = false;	
	
	public CancelReservationPsuedoDialog(Stage owner, Collection<Reservation> reserves)
	{
		HR = HotelResource.getInstance();
		
		rtg = new ToggleGroup();
		
		RadioButton[] radioButtons = new RadioButton[reserves.size()];
		
		int b = 0;
		
		for(Reservation R: reserves)
		{
			radioButtons[b] = new RadioButton(R.toString());
			radioButtons[b].setToggleGroup(rtg);
			radioButtons[b].setUserData(Integer.valueOf(R.getID()));
			b++;
		}
		
		VBox rbBox = new VBox(5.0d, radioButtons);
		
		ScrollPane sp = new ScrollPane();
			
		sp.setVmax(440);
    sp.setPrefSize(400, 300);
    sp.setContent(rbBox);
		
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
		
		VBox dialogBox = new VBox(sp, new Separator(), bttnBox);
		dialogBox.setSpacing(5.0d);
		dialogBox.setAlignment(Pos.TOP_CENTER);
		
		dialogScene = new Scene(dialogBox);
		
		dialogStage = new Stage(StageStyle.UTILITY);
		
		dialogStage.initOwner(owner);
		dialogStage.initModality(Modality.APPLICATION_MODAL);
		
		dialogStage.setWidth(350);
		dialogStage.setHeight(350);
		
		dialogStage.setScene(dialogScene);
	}
	
	private void getAndParseDialogData(ActionEvent e)
	{
		if(rtg.getSelectedToggle() == null)
		{
			var alert = new Alert(Alert.AlertType.ERROR, "Please Select A Reservation!!");
			alert.initStyle(StageStyle.UNDECORATED);
			alert.showAndWait();
			return;
		}
		else
		{
			Integer selected = (Integer) rtg.getSelectedToggle().getUserData();
			ID = selected.intValue();
		}
		
		okButtonClicked = true;
		
		dialogStage.hide();
	}
	
	public boolean okButtonWasClicked() {
		return okButtonClicked;
	}
	
	public int getID() {
		return ID;
	}
	
	public void show() {
		dialogStage.showAndWait();
	}
}
