package com.udacity.hotel.ui.dialogs;



import javafx.stage.*;
import javafx.scene.Scene;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.geometry.Pos;

import javafx.event.ActionEvent;

import javafx.scene.paint.Color;

import com.udacity.hotel.resources.*;
import static com.udacity.hotel.resources.UtilityResource.*;

import java.util.Optional;

//import javafx.scene.layout.BorderPane;

//import javafx.scene.text.Text;
//import javafx.scene.text.Font;


public class AccountPsuedoDialog
{
	private HotelResource HR;
	
	private Stage dialogStage;
	private Scene dialogScene;
	
	private TextField fntf; // 1st name textfield
	private TextField lntf; // last name textfield
	
	private TextField ptf; // phone textfield
	
	private TextField etf; // email text field
	
	private PasswordField pinField;
	
	private String firstName, lastName, phone, email, pin;
	
	private Label msg;
	
	private boolean okButtonClicked = false;

	public AccountPsuedoDialog(Stage owner)
	{
		HR = HotelResource.getInstance();
		
		Label fnl = new Label("First Name:");
		Label lnl = new Label("Last Name:");

		fntf = new TextField();
		lntf = new TextField();
		
		HBox firstNameBox = new HBox(fnl, fntf);
		firstNameBox.setSpacing(5.0d);
		
		HBox lastNameBox = new HBox(lnl, lntf);
		lastNameBox.setSpacing(5.0d);
		
		Label pl = new Label("Phone (###-###-####):"); // phone label
		ptf = new TextField();
		HBox phoneBox = new HBox(pl, ptf);
		phoneBox.setSpacing(5.0d);
		
		Label el = new Label("Email (name@domain.com):");
		etf = new TextField();
		HBox emailBox = new HBox(el, etf);
		emailBox.setSpacing(5.0d);
		
		Label pinLabel = new Label("Pin:");
		pinField = new PasswordField();
		HBox pinBox = new HBox(pinLabel, pinField);
		pinBox.setSpacing(5.0d);
		
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
		
		msg = new Label();
		
		VBox dialogBox = new VBox(firstNameBox, lastNameBox, phoneBox, emailBox, pinBox, bttnBox, msg);
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
	
	private void getAndParseDialogData(ActionEvent e)
	{
		firstName = fntf.getText();
		lastName = lntf.getText();
		
		phone = ptf.getText();
		email = etf.getText();
		
		pin = pinField.getText();
		
		if( firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty() || email.isEmpty() || pin.isEmpty() )
		{
			var alert = new Alert(Alert.AlertType.ERROR, "Cannot Have Blank Fields!!");
			alert.initStyle(StageStyle.UNDECORATED);
			alert.showAndWait();
			return;
		}
		
		if( !isPhoneNumberValid(phone) )
		{
			var alert = new Alert(Alert.AlertType.ERROR, "Phone Number Must Be Entered As, ###-###-####!!");
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
		
		if(HR.getCustomer(email) != null)
		{
			var alert = new Alert(Alert.AlertType.ERROR, "There Is An Existing Account With The Email " + email + ", Please enter another email address!!");
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
		
		okButtonClicked = true;
		
		dialogStage.close();
	}
	
	public boolean isOkButtonClicked() {
		return okButtonClicked;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getPhone() {
	 return phone;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPin() {
		return pin;
	}
	
	public Stage getStage() {
		return dialogStage;
	}
	
	public void show() {
		dialogStage.showAndWait();
	}
}
