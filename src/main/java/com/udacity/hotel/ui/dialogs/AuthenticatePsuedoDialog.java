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



public class AuthenticatePsuedoDialog
{
	private HotelResource HR;
	
	private Stage dialogStage;
	private Scene dialogScene;
	
	private TextField etf;
	private PasswordField ptf;

	private String email, pin;
	
	private boolean okButtonClicked = false;
	
	private boolean isAuthentic = false;	
	
	public AuthenticatePsuedoDialog(Stage owner)
	{
		HR = HotelResource.getInstance();
		
		Label el = new Label("Email:");
		etf = new TextField();
		HBox ebox = new HBox(el, etf);
		
		Label pl = new Label("Pin:");
		ptf = new PasswordField();
		HBox pbox = new HBox(pl, ptf);
	
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
		
		VBox dialogBox = new VBox(ebox, pbox, new Separator(), bttnBox);
		dialogBox.setSpacing(5.0d);
		dialogBox.setAlignment(Pos.TOP_CENTER);
		
		dialogScene = new Scene(dialogBox);
		
		dialogStage = new Stage(StageStyle.UTILITY);
		
		dialogStage.initOwner(owner);
		dialogStage.initModality(Modality.APPLICATION_MODAL);
		
		dialogStage.setWidth(250);
		dialogStage.setHeight(150);
		
		dialogStage.setScene(dialogScene);
	}
	
	private void getAndParseDialogData(ActionEvent e)
	{
		email = etf.getText();
		pin = ptf.getText();
		
		if( email.isEmpty() || pin.isEmpty() )
		{
			var alert = new Alert(Alert.AlertType.ERROR, "Must Enter Email & Pin!!");
			alert.initStyle(StageStyle.UNDECORATED);
			alert.showAndWait();
			return;
		}
		
		if( !isEmailValid(email) )
		{
			var alert = new Alert(Alert.AlertType.ERROR, "Email Address Must Be Entered As name@domain.com!!");
			alert.initStyle(StageStyle.UNDECORATED);
			alert.showAndWait();
			return;
		}
		
		Optional<String> errMsg = validatePin(pin);
		
		if( errMsg.isPresent() )
		{
			var alert = new Alert(Alert.AlertType.ERROR, errMsg.get());
			alert.initStyle(StageStyle.UNDECORATED);
			alert.showAndWait();
			return;
		}
		
		if( !HR.isAuthentic(email, pin) )
		{
			var alert = new Alert(Alert.AlertType.ERROR, "Incorrect Email or Pin!!");
			alert.initStyle(StageStyle.UNDECORATED);
			alert.showAndWait();
			return;
		}
		else
			isAuthentic = true;
		
		okButtonClicked = true;
		
		dialogStage.hide();
	}
	
	public void show() {
		dialogStage.showAndWait();
	}
	
	public boolean okButtonWasClicked() {
		return okButtonClicked;
	}
	
	public boolean wasAuthentic() {
		return isAuthentic;
	}
	
	public String getEmail() {
		return email;
	}
}
