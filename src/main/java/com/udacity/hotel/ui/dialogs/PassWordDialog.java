package com.udacity.hotel.ui.dialogs;


import javafx.scene.control.*;
import javafx.scene.layout.HBox;


public class PassWordDialog extends Dialog<String>
{
	PasswordField pwField;
	
	public PassWordDialog()
	{
		Label pwLabel = new Label("Admin Password:");
		pwField = new PasswordField();
		
		HBox pwBox = new HBox(pwLabel, pwField);
		pwBox.setSpacing(5.0d);
		
		DialogPane dp = getDialogPane();
		
		ButtonType bType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
    dp.getButtonTypes().addAll(bType, ButtonType.CANCEL);
		dp.setContent(pwBox);
		
		setTitle("PassWord Dialog");
    //setResultConverter( bt -> { return pwField.getText(); } );
    setResultConverter(this::pwResult);
	}
	
	private String pwResult(ButtonType bt)
	{
		if(bt.getButtonData() == ButtonBar.ButtonData.OK_DONE)
			return pwField.getText();
		else
			return null;
	}
}


